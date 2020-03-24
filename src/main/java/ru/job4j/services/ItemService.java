package ru.job4j.services;

import ru.job4j.models.Item;
import ru.job4j.persistence.ItemDao;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

public class ItemService {
    private static final ItemDao ITEM_DAO = ItemDao.getInstance();
    private static final ItemService INSTANCE = new ItemService();

    private ItemService() {

    }

    public static ItemService getInstance() {
        return INSTANCE;
    }

    public void add(HttpServletRequest req) {
        ITEM_DAO.add(buildNewItem(req));
    }

    public void update(HttpServletRequest req) {
        ITEM_DAO.update(buildNewItem(req));
    }

    public void delete(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id != null) {
            Item item = new Item();
            item.setId(Integer.parseInt(id));
            ITEM_DAO.delete(item);
        }
    }

    public List<Item> findAll() {
        return ITEM_DAO.findAll();
    }

    public List<Item> findActive() {
        return ITEM_DAO.findAllActive();
    }

    private Item buildNewItem(HttpServletRequest req) {
        Item item = new Item();
        String id = req.getParameter("id");
        String description = req.getParameter("desc");
        String done = req.getParameter("done");
        if (id != null) {
            item.setId(Integer.parseInt(id));
        }
        if (description != null) {
            item.setDescription(description);
            item.setCreated(new Timestamp(System.currentTimeMillis()));
        }
        if (done != null) {
            item.setDone(Boolean.parseBoolean(done));
        }
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        return item;
    }
}
