package flooringmastery.controller;

import flooringmastery.dao.PersistenceException;
import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;
import flooringmastery.service.NoSuchOrderException;
import flooringmastery.service.ServiceLayer;
import flooringmastery.view.View;

import java.time.LocalDate;
import java.util.List;

public class Controller {

    private View view;
    private ServiceLayer service;

    public Controller(View view, ServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        while (keepGoing) {
            int choice = view.displayMenuAndGetChoice();

            try {
                switch (choice) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportData();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        view.displayUnkownCommand();
                }
            }catch(PersistenceException | NoSuchOrderException e){
                view.displayErrorMessage(e.getMessage());
            }

        }
        view.displayExitMessage();
    }

    private void displayOrders() throws PersistenceException, NoSuchOrderException {
        LocalDate date = view.getDateInput();
        List<Order> orders = service.getOrdersForDate(date);
        view.displayOrderList(orders);
    }

    private void addOrder() throws PersistenceException{
        List<Tax> taxes = service.getAllTaxes();
        List<Product> products = service.getAllProducts();
        Order newOrder = view.getNewOrderInfo(taxes, products);
        service.calculateOrderCosts(newOrder);
        view.displayOrder(newOrder);
        boolean confirmed = view.getConfirmation("Plase this order?");
        if (confirmed){
            service.createOrder(newOrder);
            view.displayAddSuccessBanner();
        }
    }

    private void editOrder()throws  PersistenceException, NoSuchOrderException{
        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(date, orderNumber);
        List<Tax> taxes= service.getAllTaxes();
        List<Product> products = service.getAllProducts();
        order = view.getEditOrderInfo(order, taxes, products);
        service.calculateOrderCosts(order);
        view.displayOrder(order);
        boolean confirmed = view.getConfirmation("Save changes?");
        if (confirmed){
            service.editOrder(order);
            view.displayEditSuccess();
        }

    }

    private void removeOrder()throws  PersistenceException, NoSuchOrderException{
        LocalDate date = view.getDateInput();
        int orderNumber = view.getOrderNumber();
        Order order = service.getOrder(date, orderNumber);
        view.displayOrder(order);
        boolean confirmed = view.getConfirmation("Would you like to remove this order?");
        if (confirmed){
            service.removeOrder(date, orderNumber);
            view.displayRemoveSuccess();
        }


    }

    private void exportData() throws PersistenceException, NoSuchOrderException{
        service.exportData();
        view.displayExportSuccess();
    }





//    public void run(){
//
//        boolean keepGoping = true;
//        while (keepGoping){
//            int choice = view.displayMenuAndGetChoice();
//            switch (choice){
//                case 1:
//                    addOrder
//            }
//        }
//    }


}
