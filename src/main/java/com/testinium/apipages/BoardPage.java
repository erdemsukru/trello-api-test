package com.testinium.apipages;

import com.testinium.models.Board;
import com.testinium.utils.RestUtils;
import io.restassured.response.Response;

public class BoardPage extends RestUtils {

    private static final String BOARDS_ENDPOINT = "/boards/";
    private static final String BOARD_ENDPOINT = "/boards/{id}";

    public Board createBoard(String boardName) {
        System.out.println("\n=== Board Oluşturuluyor: " + boardName + " ===");

        Board newBoard = new Board(boardName);
        Response response = post(BOARDS_ENDPOINT, newBoard);
        logResponse(response, "Board Oluşturma");

        return response.as(Board.class);
    }

    public Board getBoard(String boardId) {
        System.out.println("\n=== Board Getiriliyor: " + boardId + " ===");

        Response response = get(BOARD_ENDPOINT.replace("{id}", boardId));
        logResponse(response, "Board Getirme");

        return response.as(Board.class);
    }

    public boolean deleteBoard(String boardId) {
        System.out.println("\n=== Board Siliniyor: " + boardId + " ===");

        Response response = delete(BOARD_ENDPOINT.replace("{id}", boardId));
        logResponse(response, "Board Silme");

        return response.getStatusCode() == 200;
    }

    public String getFirstListId(String boardId) {
        System.out.println("\n=== Board Listeleri Getiriliyor ===");

        Response response = get(BOARD_ENDPOINT.replace("{id}", boardId) + "/lists");
        logResponse(response, "Listeler");

        // İlk listenin ID'sini al
        String listId = response.jsonPath().getString("[0].id");
        System.out.println("Bulunan liste ID: " + listId);
        return listId;
    }
}