package flooringmastery.dao;

import flooringmastery.model.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderDao {


    List<Order> getOrdersForDate(LocalDate date) throws PersistenceException;
    Order getOrder(LocalDate date, int orderNumber) throws PersistenceException;
    Order addOrder(Order order) throws PersistenceException;
    Order editOrder(Order order) throws PersistenceException;
    Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException;
    List<Order> getAllOrders() throws PersistenceException;
    int getNextOrderNumber() throws PersistenceException;

}
