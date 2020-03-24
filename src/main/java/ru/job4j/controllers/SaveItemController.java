package ru.job4j.controllers;

import ru.job4j.services.ItemService;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/save")
public class SaveItemController extends HttpServlet {
    private static final ItemService ITEM_SERVICE = ItemService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ITEM_SERVICE.add(req);
    }
}
