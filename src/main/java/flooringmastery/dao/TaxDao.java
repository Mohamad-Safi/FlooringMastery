package flooringmastery.dao;

import flooringmastery.model.Tax;

import java.util.List;

public interface TaxDao {

    List<Tax> getAllTaxes() throws PersistenceException;
}
