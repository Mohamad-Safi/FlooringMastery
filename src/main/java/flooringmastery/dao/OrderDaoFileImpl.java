package flooringmastery.dao;

import flooringmastery.model.Order;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDaoFileImpl implements OrderDao{


        private final String ORDER_FOLDER = "Orders/";
        private final String DELIMITER = ",";

        // nested map: orders are grouped by date then by order number within that date
        private Map<LocalDate, Map<Integer, Order>> orders = new HashMap<>();

        // date formatter for the FILENAME (Orders_MMDDYYYY.txt)
        private final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("MMddyyyy");

        public void loadAllOrders() throws PersistenceException{
            File ordersFolder = new File(ORDER_FOLDER);//Creates a new File instance by converting
            // the given pathname string into an abstract pathname.
            File[] files = ordersFolder.listFiles();

            for (File file : files){
                String fileName = file.getName();//returns the name of the fiel.
                //sstart at the orders prefix and end at .txt prefix
                String dateString = fileName.substring("Orders_".length(), fileName.length()-".txt".length());
                //then parse the dateString into the localdate
                LocalDate date = LocalDate.parse(dateString, FILE_NAME_FORMAT);



            }
        }

        @Override
        public List<Order> getOrdersForDate(LocalDate date) throws PersistenceException {
            return List.of();
        }

        @Override
        public Order getOrder(LocalDate date, int orderNumber) throws PersistenceException {
            return null;
        }

        @Override
        public Order addOrder(Order order) throws PersistenceException {
            return null;
        }

        @Override
        public Order editOrder(Order order) throws PersistenceException {
            return null;
        }

        @Override
        public Order removeOrder(LocalDate date, int orderNumber) throws PersistenceException {
            return null;
        }

        @Override
        public List<Order> getAllOrders() throws PersistenceException {
            return List.of();
        }

        @Override
        public int getNextOrderNumber() throws PersistenceException {
            return 0;
        }
    }


