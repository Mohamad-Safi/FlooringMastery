package flooringmastery.service;

import flooringmastery.dao.OrderDao;
import flooringmastery.dao.PersistenceException;
import flooringmastery.dao.ProductDao;
import flooringmastery.dao.TaxDao;
import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class ServiceLayerImpl implements ServiceLayer{

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void calculateOrderCosts(Order order) throws PersistenceException {
        /**
         *
         *          materialCost = area × costPerSquareFoot
         *         labourCost   = area × labourCostPerSquareFoot
         *         tax          = (materialCost + labourCost) × (taxRate ÷ 100)
         *         total        = materialCost + labourCost + tax
         **/


        BigDecimal area = order.getArea();
        BigDecimal labourCostPerSquareFoot = order.getLabourCostPerSquareFoot();


        BigDecimal taxRate = order.getTaxRate();
        BigDecimal costPerSquareFoot = order.getCostPerSquareFoot();
        BigDecimal materialCost  = area.multiply(costPerSquareFoot).setScale
                (2, RoundingMode.HALF_UP);

        BigDecimal labourCost = area.multiply(labourCostPerSquareFoot).setScale(2,
                RoundingMode.HALF_UP);

        BigDecimal tax = materialCost.add(labourCost)
                .multiply(taxRate.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal total = materialCost.add(labourCost).add(tax)
                .setScale(2, RoundingMode.HALF_UP);

        order.setLabourCost(labourCost);
        order.setTax(tax);
        order.setTotal(total);
        order.setMaterialCost(materialCost);


    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException, NoSuchOrderException {
        return orderDao.getOrdersForDate(date);
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumer) throws PersistenceException, NoSuchOrderException {

        Order givenOrder = orderDao.getOrder(date, orderNumer);
        if (givenOrder == null){
            throw new NoSuchOrderException("This order doesnt exist, " +orderNumer+
                    "please try again with the correct inputs");
        }
        return givenOrder;
    }

    @Override
    public Order createOrder(Order order) throws PersistenceException {
        int nextOrderNumber = getNextOrderNumber();
        order.setOrderNumber(nextOrderNumber);
        calculateOrderCosts(order);
        orderDao.addOrder(order);
        return order;


    }

    @Override
    public Order editOrder(Order order) throws PersistenceException {
        calculateOrderCosts(order);
        orderDao.editOrder(order);

        return order;
    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException, NoSuchOrderException {
        getOrder(date, orderNumber);

        return orderDao.removeOrder(date, orderNumber);

    }

    @Override
    public List<Tax> getAllTaxes() throws PersistenceException {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        return productDao.getAllProducts();
    }

    @Override
    public int getNextOrderNumber() throws PersistenceException {
        return orderDao.getNextOrderNumber();
    }


}
