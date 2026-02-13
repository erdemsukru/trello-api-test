package com.testinium.trello;

import com.testinium.models.Board;
import com.testinium.models.Card;
import com.testinium.apipages.BoardPage;
import com.testinium.apipages.CardPage;
import com.testinium.utils.HtmlReportManager;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrelloTest {

    private static BoardPage boardPage;
    private static CardPage cardPage;
    private static Board testBoard;
    private static String firstListId;
    private static List<Card> createdCards = new ArrayList<>();
    private static Random random = new Random();

    @BeforeAll
    public static void setUp() {
        boardPage = new BoardPage();
        HtmlReportManager.startReport();
        System.out.println("\n==========================================");
        System.out.println("TRELLO API TEST BAŞLADI");
        System.out.println("==========================================\n");
    }

    @AfterAll
    public static void tearDown() {
        HtmlReportManager.endReport();
        System.out.println("\n==========================================");
        System.out.println("TRELLO API TEST TAMAMLANDI");
        System.out.println("Rapor: test-output/TrelloTestReport.html");
        System.out.println("==========================================\n");
    }

    // 1. Board oluşturma
    @Test
    @Order(1)
    public void boardOlustur() {
        try {
            System.out.println("\n---------- TEST 1: Board Oluşturma ----------");

            String boardName = "Test Board - " + System.currentTimeMillis();
            testBoard = boardPage.createBoard(boardName);

            assertNotNull(testBoard.getId(), "Board ID boş olamaz!");
            assertEquals(boardName, testBoard.getName(), "Board ismi eşleşmiyor!");

            System.out.println("✅ Board başarıyla oluşturuldu: " + testBoard);
            HtmlReportManager.addTestResult("Board Oluşturma", true,
                    "Board oluşturuldu: " + testBoard.getName() + " (ID: " + testBoard.getId() + ")",
                    "POST /boards/", testBoard.toString(), 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("Board Oluşturma", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }

    // 2. Board'daki ilk liste ID'sini al
    @Test
    @Order(2)
    public void listeIdAl() {
        try {
            System.out.println("\n---------- TEST 2: Board Listesi Getirme ----------");

            assertNotNull(testBoard, "Önce board oluşturulmalı!");

            firstListId = boardPage.getFirstListId(testBoard.getId());
            assertNotNull(firstListId, "Liste ID boş olamaz!");

            System.out.println("✅ İlk liste ID: " + firstListId);
            HtmlReportManager.addTestResult("Board Listesi Getirme", true,
                    "Liste ID alındı: " + firstListId,
                    "GET /boards/" + testBoard.getId() + "/lists", "Liste ID: " + firstListId, 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("Board Listesi Getirme", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }

    // 3. İki kart oluştur
    @Test
    @Order(3)
    public void ikiKartOlustur() {
        try {
            System.out.println("\n---------- TEST 3: İki Kart Oluşturma ----------");

            assertNotNull(firstListId, "Önce liste ID alınmalı!");

            cardPage = new CardPage(firstListId);

            Card card1 = cardPage.createCard("Kart 1", "Bu birinci karttır");
            assertNotNull(card1, "Kart 1 oluşturulamadı!");
            assertNotNull(card1.getId(), "Kart 1 ID boş olamaz!");
            createdCards.add(card1);
            System.out.println("✅ 1. kart oluşturuldu: " + card1);

            Card card2 = cardPage.createCard("Kart 2", "Bu ikinci karttır");
            assertNotNull(card2, "Kart 2 oluşturulamadı!");
            assertNotNull(card2.getId(), "Kart 2 ID boş olamaz!");
            createdCards.add(card2);
            System.out.println("✅ 2. kart oluşturuldu: " + card2);

            assertEquals(2, createdCards.size(), "2 kart oluşturulmalıydı!");

            HtmlReportManager.addTestResult("İki Kart Oluşturma", true,
                    "2 kart başarıyla oluşturuldu",
                    "POST /cards", "Kart1: " + card1.getId() + ", Kart2: " + card2.getId(), 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("İki Kart Oluşturma", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }

    // 4. Rastgele bir kartı güncelle
    @Test
    @Order(4)
    public void rastgeleKartGuncelle() {
        try {
            System.out.println("\n---------- TEST 4: Rastgele Kart Güncelleme ----------");

            assertFalse(createdCards.isEmpty(), "Kart listesi boş!");

            int randomIndex = random.nextInt(createdCards.size());
            Card randomCard = createdCards.get(randomIndex);

            System.out.println("Seçilen kart: " + randomCard);

            String newName = "Güncellenmiş Kart " + System.currentTimeMillis();
            String newDesc = "Bu kart güncellendi";

            Card updatedCard = cardPage.updateCard(randomCard.getId(), newName, newDesc);

            assertNotNull(updatedCard, "Kart güncellenemedi!");
            assertEquals(newName, updatedCard.getName(), "Kart ismi güncellenmemiş!");
            assertEquals(newDesc, updatedCard.getDesc(), "Kart açıklaması güncellenmemiş!");

            createdCards.set(randomIndex, updatedCard);

            System.out.println("✅ Kart başarıyla güncellendi: " + updatedCard);
            HtmlReportManager.addTestResult("Rastgele Kart Güncelleme", true,
                    "Kart güncellendi: " + updatedCard.getName(),
                    "PUT /cards/" + randomCard.getId(), updatedCard.toString(), 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("Rastgele Kart Güncelleme", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }

    // 5. Kartları sil
    @Test
    @Order(5)
    public void kartlariSil() {
        try {
            System.out.println("\n---------- TEST 5: Kartları Silme ----------");

            assertFalse(createdCards.isEmpty(), "Silinecek kart yok!");

            for (Card card : createdCards) {
                boolean deleted = cardPage.deleteCard(card.getId());
                assertTrue(deleted, "Kart silinemedi: " + card.getId());
                System.out.println("✅ Kart silindi: " + card);
            }

            createdCards.clear();
            System.out.println("✅ Tüm kartlar silindi!");

            HtmlReportManager.addTestResult("Kartları Silme", true,
                    "Tüm kartlar silindi",
                    "DELETE /cards/{id}", "Status: 200", 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("Kartları Silme", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }

    // 6. Board'u sil
    @Test
    @Order(6)
    public void boardSil() {
        try {
            System.out.println("\n---------- TEST 6: Board Silme ----------");

            assertNotNull(testBoard, "Silinecek board yok!");

            boolean deleted = boardPage.deleteBoard(testBoard.getId());
            assertTrue(deleted, "Board silinemedi!");

            System.out.println("✅ Board silindi: " + testBoard);
            HtmlReportManager.addTestResult("Board Silme", true,
                    "Board silindi: " + testBoard.getName(),
                    "DELETE /boards/" + testBoard.getId(), "Status: 200", 200);

        } catch (Exception e) {
            HtmlReportManager.addTestResult("Board Silme", false,
                    "Hata: " + e.getMessage(), "", "", 500);
            throw e;
        }
    }
}