package flooringmastery.dao;

import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class main {

    public static void main(String[] args) throws  PersistenceException{
        OrderDao dao = new OrderDaoFileImpl();

// build a test order (order number, name, state, date, taxRate, product, cost, labourCost, material, area, labour, tax, total)
        Order testOrder = new Order(
                dao.getNextOrderNumber(),          // next number
                "Test Customer",
                "CA",
                LocalDate.of(2013, 6, 1),          // add to June 1st (which already has Ada)
                new BigDecimal("25.00"),
                "Tile",
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("500.00"),          // materialCost (fake for now)
                new BigDecimal("100.00"),          // area
                new BigDecimal("500.00"),          // labourCost (fake)
                new BigDecimal("50.00"),           // tax (fake)
                new BigDecimal("1050.00")          // total (fake)
        );

        dao.addOrder(testOrder);
        System.out.println("Order added.");
    }
}
