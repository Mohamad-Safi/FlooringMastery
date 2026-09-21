package flooringmastery.service;

import flooringmastery.dao.PersistenceException;
import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {

    List<Order> getOrdersForDate(LocalDate date) //display order for date
            throws PersistenceException, NoSuchOrderException;

    Order getOrder(LocalDate date, int orderNumer)//one specific order
            throws PersistenceException, NoSuchOrderException;

    Order createOrder(Order order) throws PersistenceException;//create an order

    Order editOrder (Order order) throws PersistenceException;

    Order removeOrder(LocalDate date, int orderNumber)
            throws PersistenceException, NoSuchOrderException;

    List<Tax> getAllTaxes() throws PersistenceException;//to display valid tax values

    List<Product> getAllProducts() throws PersistenceException;//get all products

    int getNextOrderNumber() throws PersistenceException;//we need the next order for addorder()

    void calculateOrderCosts(Order order) throws PersistenceException;

    void exportData() throws PersistenceException;

}
