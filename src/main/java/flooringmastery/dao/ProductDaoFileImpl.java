package flooringmastery.dao;

import flooringmastery.model.Product;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao{

    private final String PRODUCT_FILE = "Data/Products.txt";
    private final String DELIMITER =",";
    private Map<String , Product> products = new HashMap<>();


    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        //return all the products from the map as lists

        loadProducts();
        List<Product> list = new ArrayList<>(products.values());
        return list;
    }
    //method to open the file
    //
    private void loadProducts() throws PersistenceException{

        Scanner scanner;//scanner object
        try {
            //buffer reader to read the characters fast shich wraps around filereader
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));

        }catch(FileNotFoundException e){
            throw  new PersistenceException("Could not load product data into memory", e);
        }
        scanner.nextLine();

        while (scanner.hasNextLine()){
            String currentLine = scanner.nextLine();
            //split the current delimiter into a string array
            String[] tokens = currentLine.split(DELIMITER);
            //create a new product by using Product constructor and converting string to Bigdecimal.

            Product currentProduct = new Product(tokens[0],
                    new BigDecimal(tokens[1]), new BigDecimal(tokens[2]));
            products.put(tokens[0], currentProduct);

        }
        scanner.close();
    }




}
