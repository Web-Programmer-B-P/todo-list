package ru.job4j.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.models.Item;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HandlerResponse {
    public HandlerResponse() {

    }

    public String getActiveItemsAsStringJson(List<Item> activeItems) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(activeItems);
    }

    public String getAllItemsAsJsonString(List<Item> allItems) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(allItems);
    }

    public void sendResponse(HttpServletResponse resp, String json) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.write(json);
        writer.flush();
    }
}
