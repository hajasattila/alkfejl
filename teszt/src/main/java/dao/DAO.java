package dao;

import model.Order;

import java.util.List;

public interface DAO {

    static List<Order> getOrders() {
        return null;
    }

    Order save(Order order);
}
