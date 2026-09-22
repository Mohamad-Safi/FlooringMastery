package flooringmastery.dao;
import org.springframework.stereotype.Component;

import flooringmastery.model.Order;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class OrderDaoFileImpl implements OrderDao{


    private final String ORDER_FOLDER = "Orders/";
    private final String DELIMITER = ",";

    // nested map: orders are grouped by date then by order number within that date
    private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

    // date formatter for the FILENAME (Orders_MMDDYYYY.txt)
    private final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");


    //Load all of the orders method!!

    public void loadAllOrders() throws PersistenceException{
        File ordersFolder = new File(ORDER_FOLDER);//Creates a new File instance by converting
        // the given pathname string into an abstract pathname.

        if (!ordersFolder.exists()){
            ordersFolder.mkdir();//if the order folder doesnt exist create it first.
        }
        File[] files = ordersFolder.listFiles();
        if (files!=null){

            for (File file : files){
                String fileName = file.getName();//returns the name of the fiel.
                //sstart at the orders prefix and end at .txt prefix
                String dateString = fileName.substring("Orders_".length(), fileName.length()-".txt".length());
                //then parse the dateString into the localdate
                LocalDate date = LocalDate.parse(dateString, FILE_NAME_FORMAT);

                readOrderFile(file, date);
            }

        }


    }

    private void writeOrderFile(LocalDate date) throws PersistenceException{
        String orderFileName = ORDER_FOLDER + "Orders_" + date.format(FILE_NAME_FORMAT) + ".txt";
        PrintWriter printer ;
        try {
            printer = new PrintWriter(new FileWriter(orderFileName));
        }catch (IOException e){
            throw  new PersistenceException("Sorry Order dtat could not be saved", e);
        }
        printer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area," +
                "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

        Map<Integer, Order> thisDatesOrder = orders.get(date);
        for (Order order : thisDatesOrder.values()){

            String customerName = order.getCustomerName().replace(",", "[comma]");
            String orderLine = order.getOrderNumber()+ DELIMITER
                    +customerName + DELIMITER+ order.getState()+DELIMITER+
                    order.getTaxRate()+DELIMITER+order.getProductType()+DELIMITER+
                    order.getArea()+DELIMITER+order.getCostPerSquareFoot()+DELIMITER+
                    order.getLabourCostPerSquareFoot() + DELIMITER
                    + order.getMaterialCost() + DELIMITER
                    + order.getLabourCost() + DELIMITER
                    + order.getTax() + DELIMITER
                    + order.getTotal();
            printer.println(orderLine);
        }
        printer.close();
    }

    private void readOrderFile(File file, LocalDate date) throws PersistenceException{
        Scanner scanner;
        //file reader opens the file
        //buffer makes the file reading fast
        //if file not found catch the exception
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(file)));
        }catch (FileNotFoundException e){
            throw  new PersistenceException("Could not read the order file !", e);
        }
        scanner.nextLine();
        Map<Integer, Order> ordersForThisDate = new HashMap<>();


        while(scanner.hasNextLine()){
            //scanner object reads line by line
            String currentLine = scanner.nextLine();
            String[] tokens = currentLine.split(DELIMITER);
            String restoredName = tokens[1].replace("[comma]", ",");

            Order order = new Order(Integer.parseInt(tokens[0]), restoredName+DELIMITER, tokens[2], date, new BigDecimal(tokens[3]),
                    tokens[4], new BigDecimal(tokens[6]), new BigDecimal(tokens[7]), new BigDecimal(tokens[8]), new BigDecimal(tokens[5]),
                    new BigDecimal(tokens[9]), new BigDecimal(tokens[10]), new BigDecimal(tokens[11]));

            ordersForThisDate.put(order.getOrderNumber(), order);
        }
        scanner.close();

        orders.put(date, ordersForThisDate);


    }

    @Override
    public int getNextOrderNumber() throws PersistenceException {

        loadAllOrders();//check if the ordersmap is popluated
        int maxNumOfOrders = 0;
        for (Map<Integer, Order> innerMap : orders.values()){
           for (Order order : innerMap.values()) {
               if (order.getOrderNumber() > maxNumOfOrders) {
                   maxNumOfOrders = order.getOrderNumber();
               }
           }
        }
        return maxNumOfOrders+1;



    }



    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException {

        loadAllOrders();//call loadallorders to make thee map popluate with orders.
        //the maps key is the date so extract the key

        Map<Integer, Order> todaysDate = orders.get(date);

        if (todaysDate == null){
            return List.of();
        }else
            return new ArrayList<>(todaysDate.values());

    }


    @Override
    public Order addOrder(Order order) throws PersistenceException {

        loadAllOrders();
    //ask the Order for its date and store it local var
        LocalDate date = order.getOrderDate();

        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate==null){
            ordersForDate = new HashMap<>();
            orders.put(date, ordersForDate);

        }
        ordersForDate.put(order.getOrderNumber(), order);


        writeOrderFile(date);

    return order;

    }

    @Override
    public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
        loadAllOrders();//load files
        Map<Integer, Order> ordersForDate = orders.get(date);//get the values of innermap
        if (ordersForDate==null){//if empty return null
            return null;
        }//if not remove the order
        Order removedOrder = ordersForDate.remove(orderNumber);
        //rewrite the file
        writeOrderFile(date);


        return removedOrder;
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {


        loadAllOrders();
        Map<Integer, Order> ordersForDate = orders.get(date);
        if (ordersForDate == null){
            return null;
        }else{
            return ordersForDate.get(orderNumber);
        }
    }




    @Override
    public Order editOrder(Order order) throws PersistenceException {
        loadAllOrders();
        LocalDate date = order.getOrderDate();
        Map<Integer, Order> ordersForDate = orders.get(date);
        ordersForDate.put(order.getOrderNumber(), order);
        writeOrderFile(date);
        return order;
    }


    @Override
    public List<Order> getAllOrders() throws PersistenceException {

        loadAllOrders();;
        List<Order> allOrders = new ArrayList<>();
        for (Map<Integer, Order> innerMap : orders.values()){
            for (Order order : innerMap.values()){
                allOrders.add(order);
            }

        }
        return allOrders;
    }


}


