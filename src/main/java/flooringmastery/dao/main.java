package flooringmastery.dao;

import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.util.List;

public class main {

    public static void main(String[] args) throws  PersistenceException{
        TaxDao dao = new TaxDaoFileImpl();
        List<Tax> taxes = dao.getAllTaxes();
        for (Tax t : taxes) {
            System.out.println(t.getState() + " - " + t.getStateName() + " - " + t.getTaxRate());
        }
    }
}
