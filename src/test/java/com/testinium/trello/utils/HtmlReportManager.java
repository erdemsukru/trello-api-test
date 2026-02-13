package com.testinium.utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HtmlReportManager {
    private static StringBuilder reportContent = new StringBuilder();
    private static int testCount = 0;
    private static int passCount = 0;
    private static int failCount = 0;

    public static void startReport() {
        reportContent = new StringBuilder();
        testCount = 0;
        passCount = 0;
        failCount = 0;

        reportContent.append("<!DOCTYPE html>");
        reportContent.append("<html>");
        reportContent.append("<head>");
        reportContent.append("<meta charset='UTF-8'>");
        reportContent.append("<title>Trello API Test Raporu</title>");
        reportContent.append("<style>");
        reportContent.append("body { font-family: Arial, Helvetica, sans-serif; background: #f5f5f5; }");
        reportContent.append(".header { background: #2c3e50; color: white; padding: 20px; text-align: center; }");
        reportContent.append(".summary { background: white; margin: 20px; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }");
        reportContent.append(".test { margin: 10px 20px; padding: 15px; border-radius: 5px; background: white; }");
        reportContent.append(".pass { border-left: 5px solid #27ae60; }");
        reportContent.append(".fail { border-left: 5px solid #e74c3c; }");
        reportContent.append(".test-name { font-weight: bold; font-size: 16px; }");
        reportContent.append(".test-time { color: #7f8c8d; font-size: 12px; }");
        reportContent.append(".test-status { display: inline-block; padding: 3px 8px; border-radius: 3px; font-size: 12px; font-weight: bold; margin-left: 10px; }");
        reportContent.append(".status-200 { background: #27ae60; color: white; }");
        reportContent.append(".status-400 { background: #e74c3c; color: white; }");
        reportContent.append(".status-404 { background: #f39c12; color: white; }");
        reportContent.append(".test-details { margin-top: 10px; }");
        reportContent.append("table { width: 100%; border-collapse: collapse; }");
        reportContent.append("th { background: #34495e; color: white; padding: 10px; }");
        reportContent.append("td { padding: 8px; border-bottom: 1px solid #ddd; }");
        reportContent.append("pre { background: #f8f9fa; padding: 10px; border-radius: 5px; overflow-x: auto; }");
        reportContent.append(".status-badge { font-weight: bold; color: #27ae60; }");
        reportContent.append("</style>");
        reportContent.append("</head>");
        reportContent.append("<body>");

        reportContent.append("<div class='header'>");
        reportContent.append("<h1>Trello API Test Raporu</h1>");
        reportContent.append("<p>Oluşturulma: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")) + "</p>");
        reportContent.append("</div>");
    }

    public static void addTestResult(String testName, boolean passed, String details, String request, String response, int statusCode) {
        testCount++;
        if (passed) passCount++; else failCount++;

        String statusClass = "status-" + statusCode;

        reportContent.append("<div class='test " + (passed ? "pass" : "fail") + "'>");
        reportContent.append("<div>");
        reportContent.append("<span class='test-name'>" + testName + "</span>");
        reportContent.append("<span class='test-status " + statusClass + "'>HTTP " + statusCode + "</span>");
        reportContent.append("<span class='test-time'>" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "</span>");
        reportContent.append("</div>");
        reportContent.append("<div class='test-details'>" + details + "</div>");

        if (request != null && !request.isEmpty()) {
            reportContent.append("<details style='margin-top: 10px;'>");
            reportContent.append("<summary>Request</summary>");
            reportContent.append("<pre>" + escapeHtml(request) + "</pre>");
            reportContent.append("</details>");
        }

        if (response != null && !response.isEmpty()) {
            reportContent.append("<details style='margin-top: 10px;'>");
            reportContent.append("<summary>Response (Status: " + statusCode + ")</summary>");
            reportContent.append("<pre>" + escapeHtml(response) + "</pre>");
            reportContent.append("</details>");
        }

        reportContent.append("</div>");
    }

    // Eski metod (geriye uyumluluk için)
    public static void addTestResult(String testName, boolean passed, String details, String request, String response) {
        addTestResult(testName, passed, details, request, response, passed ? 200 : 400);
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public static void endReport() {
        reportContent.append("<div class='summary'>");
        reportContent.append("<h2>Özet</h2>");
        reportContent.append("<table>");
        reportContent.append("<tr><th>Toplam Test</th><td>" + testCount + "</td></tr>");
        reportContent.append("<tr><th style='color: #27ae60;'>Başarılı (HTTP 200)</th><td>" + passCount + "</td></tr>");
        reportContent.append("<tr><th style='color: #e74c3c;'>Başarısız</th><td>" + failCount + "</td></tr>");
        reportContent.append("<tr><th>Başarı Oranı</th><td>%" + (testCount > 0 ? (passCount * 100 / testCount) : 0) + "</td></tr>");
        reportContent.append("</table>");
        reportContent.append("</div>");

        reportContent.append("</body>");
        reportContent.append("</html>");

        try {
            String reportPath = "test-output/TrelloTestReport.html";
            try (PrintWriter writer = new PrintWriter(new FileWriter(reportPath, java.nio.charset.StandardCharsets.UTF_8))) {
                writer.print(reportContent.toString());
            }
            System.out.println("\n✅ Rapor oluşturuldu: " + new java.io.File(reportPath).getAbsolutePath());
            System.out.println("📊 Raporu açmak için: " + reportPath);
        } catch (Exception e) {
            System.out.println("❌ Rapor yazılırken hata: " + e.getMessage());
        }
    }
}