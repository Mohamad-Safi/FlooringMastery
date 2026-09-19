package flooringmastery.dao;

import flooringmastery.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getAllProducts() throws PersistenceException;


}
