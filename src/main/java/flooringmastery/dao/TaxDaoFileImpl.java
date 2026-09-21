package flooringmastery.dao;

import flooringmastery.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.*;

@Component
public class TaxDaoFileImpl implements TaxDao{

    private final String TAX_FILE = "Data/Taxes.txt";
    private final String DELIMITER = ",";
    private Map<String, Tax> taxes = new HashMap<>();
    @Override
    public List<Tax> getAllTaxes() throws PersistenceException {

        loadTaxes();
        List<Tax> list = new ArrayList<>(taxes.values());
        return list;
    }


    private void loadTaxes() throws PersistenceException{
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
        }catch (FileNotFoundException e){
            throw new PersistenceException("Could not load the tax file! ", e);
        }
        scanner.nextLine();//always remember to skip header
        while(scanner.hasNextLine()){
            String currentLine = scanner.nextLine();
            String[] tokens = currentLine.split(DELIMITER);
            //build a tax from above array tokens.

            Tax currentTax = new Tax(tokens[0], tokens[1], new BigDecimal(tokens[2]));
            taxes.put(tokens[0], currentTax);
        }
        scanner.close();
    }


}
