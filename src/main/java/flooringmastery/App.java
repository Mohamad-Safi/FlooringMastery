package flooringmastery;

import flooringmastery.controller.Controller;
import flooringmastery.dao.*;
import flooringmastery.service.ServiceLayerImpl;
import flooringmastery.view.UserIO;
import flooringmastery.view.UserIOConsoleImpl;
import flooringmastery.view.View;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args){

        //bottm layer of the service

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("flooringmastery");
        appContext.refresh();

        Controller controller = appContext.getBean("controller", Controller.class);
        controller.run();



    }
}
