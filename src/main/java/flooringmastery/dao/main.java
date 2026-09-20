package flooringmastery.dao;

import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.util.List;

public class main {

    public static void main(String[] args) throws  PersistenceException{
        OrderDaoFileImpl ord = new OrderDaoFileImpl();
        ord.loadAllOrders();
    }
}
