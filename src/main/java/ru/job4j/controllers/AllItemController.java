package ru.job4j.controllers;

import ru.job4j.services.ItemService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/get-all")
public class AllItemController extends HttpServlet {
    private static final ItemService ITEM_SERVICE = ItemService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandlerResponse handlerResponse = new HandlerResponse();
        String json = handlerResponse.getAllItemsAsJsonString(ITEM_SERVICE.findAll());
        handlerResponse.sendResponse(resp, json);
    }
}
