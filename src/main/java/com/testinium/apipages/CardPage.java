package com.testinium.apipages;

import com.testinium.models.Card;
import com.testinium.utils.RestUtils;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;

public class CardPage extends RestUtils {

    private static final String CARDS_ENDPOINT = "/cards";
    private static final String CARD_ENDPOINT = "/cards/{id}";
    private String idList;

    public CardPage(String idList) {
        this.idList = idList;
    }

    public Card createCard(String cardName, String cardDesc) {
        System.out.println("\n=== Kart Oluşturuluyor: " + cardName + " ===");

        // Request body oluştur
        Map<String, String> cardData = new HashMap<>();
        cardData.put("name", cardName);
        cardData.put("desc", cardDesc);
        cardData.put("idList", idList);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(cardData)
                .when()
                .post(CARDS_ENDPOINT)
                .then()
                .extract()
                .response();

        logResponse(response, "Kart Oluşturma");
        return response.as(Card.class);
    }

    public Card getCard(String cardId) {
        System.out.println("\n=== Kart Getiriliyor: " + cardId + " ===");

        Response response = get(CARD_ENDPOINT.replace("{id}", cardId));
        logResponse(response, "Kart Getirme");

        return response.as(Card.class);
    }

    public Card updateCard(String cardId, String newName, String newDesc) {
        System.out.println("\n=== Kart Güncelleniyor: " + cardId + " ===");

        Map<String, String> updateData = new HashMap<>();
        updateData.put("name", newName);
        updateData.put("desc", newDesc);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(updateData)
                .when()
                .put(CARD_ENDPOINT.replace("{id}", cardId))
                .then()
                .extract()
                .response();

        logResponse(response, "Kart Güncelleme");
        return response.as(Card.class);
    }

    public boolean deleteCard(String cardId) {
        System.out.println("\n=== Kart Siliniyor: " + cardId + " ===");

        Response response = delete(CARD_ENDPOINT.replace("{id}", cardId));
        logResponse(response, "Kart Silme");

        return response.getStatusCode() == 200;
    }
}