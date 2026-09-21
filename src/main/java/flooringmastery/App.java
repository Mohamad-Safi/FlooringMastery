package flooringmastery;

import flooringmastery.controller.Controller;
import flooringmastery.dao.*;
import flooringmastery.service.ServiceLayerImpl;
import flooringmastery.view.UserIO;
import flooringmastery.view.UserIOConsoleImpl;
import flooringmastery.view.View;

public class App {

    public static void main(String[] args){

        //bottm layer of the service

        UserIO io = new UserIOConsoleImpl();
        OrderDao orderdao = new OrderDaoFileImpl();
        ProductDao productDao = new ProductDaoFileImpl();
        TaxDao taxDao = new TaxDaoFileImpl();
        ExportDao exportDao = new ExportDaoImpl();


        // Middle layers
        View view = new View(io);
        ServiceLayerImpl service = new ServiceLayerImpl(orderdao, productDao, taxDao, exportDao);

        //top layer the controller

        Controller controller = new Controller(view, service);

        controller.run();



    }
}
