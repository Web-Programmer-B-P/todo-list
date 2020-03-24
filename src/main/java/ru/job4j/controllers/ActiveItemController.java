package ru.job4j.controllers;

import ru.job4j.services.ItemService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/get-active")
public class ActiveItemController extends HttpServlet {
    private static final ItemService ITEM_SERVICE = ItemService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HandlerResponse handlerResponse = new HandlerResponse();
        String json = handlerResponse.getActiveItemsAsStringJson(ITEM_SERVICE.findActive());
        handlerResponse.sendResponse(resp, json);
    }
}
