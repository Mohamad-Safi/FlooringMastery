package flooringmastery.dao;

import flooringmastery.model.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Component
public class ExportDaoImpl implements ExportDao{


    private final String EXPORT_FILE = "Backup/DataExport.txt";
    private final String DELIMITER = ",";
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy");


    @Override
    public void exportAllData(List<Order> orders) throws PersistenceException {
        PrintWriter printer;
        try {
            printer = new PrintWriter(new FileWriter(EXPORT_FILE));
        }catch(IOException e){
            throw new PersistenceException("Could not export data", e);

        }
        printer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate");
        for (Order order : orders) {
            String line = order.getOrderNumber() + DELIMITER
                    + order.getCustomerName() + DELIMITER
                    + order.getState() + DELIMITER
                    + order.getTaxRate() + DELIMITER
                    + order.getProductType() + DELIMITER
                    + order.getArea() + DELIMITER
                    + order.getCostPerSquareFoot() + DELIMITER
                    + order.getLabourCostPerSquareFoot() + DELIMITER
                    + order.getMaterialCost() + DELIMITER
                    + order.getLabourCost() + DELIMITER
                    + order.getTax() + DELIMITER
                    + order.getTotal() + DELIMITER
                    + order.getOrderDate().format(DATE_FORMAT);   // ← the extra date column
            printer.println(line);
            }
        printer.close();;

    }


}

