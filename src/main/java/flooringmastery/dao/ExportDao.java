package flooringmastery.dao;

import flooringmastery.model.Order;

import java.util.List;

public interface ExportDao {

    void exportAllData(List<Order> orders) throws PersistenceException;
}
