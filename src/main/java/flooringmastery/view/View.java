package flooringmastery.view;

import flooringmastery.model.Order;
import flooringmastery.model.Product;
import flooringmastery.model.Tax;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class View {

    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }


    public boolean getConfirmation(String message){
        //
        while (true){
            String aa = io.readString(message+ " (Yes/No): ");
            if (aa.trim().equalsIgnoreCase("Yes")){
                return true;
            } else if (aa.trim().equalsIgnoreCase("No")) {
                return false;

            }else
                io.print("Please Choose Yes/No");
        }
    }

    public LocalDate getDateInput(){
        return io.readLocalDate("please enter order date");
    }
    public int getOrderNumber (){
        return io.readInt("Please enter order number");
    }

    public void displayAddSuccessBanner(){
        io.print("Order added successfully");
    }

    public void displayRemoveSuccess(){
        io.print("Order removed successfully");
    }

    public void displayEditSuccess(){
        io.print("Order has been edited successfully");
    }


    public void displayOrder(Order order){
        io.print("Order #" + order.getOrderNumber());
        io.print("Customer: " + order.getCustomerName());
        io.print("State: " + order.getState());
        io.print("Product: " + order.getProductType());
        io.print("Area: " + order.getArea());
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labour Cost: $" + order.getLabourCost());
        io.print("Tax: $" + order.getTax());
        io.print("Total: $" + order.getTotal());
        io.print("");
    }

    public Product getProduct(String productType, List<Product> products){
        for (Product p : products){
            if (p.getProductType().equalsIgnoreCase(productType)){
                return p;
            }
        }
        return null;
    }

    public void displayOrderList(List<Order> order){
        for (Order o: order){
            displayOrder(o);
        }

    }
    public Tax getTaxForState(String state, List<Tax> taxes){
        for (Tax t : taxes){
            if (t.getState().equalsIgnoreCase(state)){
                return t;
            }
        }
        return null;
    }

    public Order getNewOrderInfo(List<Tax> taxes, List<Product> products){
        boolean isValid = false;
        LocalDate localDate = null;

        while (!isValid){
            localDate = io.readLocalDate("Enter the order date (MM-DD-YYYY): ");
            if (localDate.isAfter(LocalDate.now())){
                isValid = true;
            }else
                io.print("Date must be in the future");
        }
        isValid = false;
        String name = null;
        while(!isValid){
            name = io.readString("Please enter customer name: ");
            if (!name.trim().isEmpty()){
                isValid = true;
            }else {
                io.print("Name can not be blank and cannot contain special charcters ");
            }
        }

        isValid = false;
        String state = null;
        Tax customerTax = null;
        while(!isValid){
            state = io.readString("Please enter state : ");
            customerTax =  getTaxForState(state, taxes);
            if (customerTax !=null){
                isValid = true;
            }else
                io.print("State not found !");
        }

        io.print("Available Products: ");
        //loop to show the user list of available products
        for (Product p : products){
            io.print(p.getProductType()+ " - Material: $" + p.getCostPerSquareFoot()
                    + "/sqft, Labour: $" + p.getLabourCostPerSquareFoot() + "/sqft");
        }
        Product chosenProduct = null;
        isValid = false;
        while(!isValid){
            String productType = io.readString("Please enter a product type: ");
            chosenProduct = getProduct(productType, products);

            if (chosenProduct!=null){
                isValid = true;
            }else{
                io.print("Product could not be found, please choose from the list.");
            }
        }

        isValid = false;
        BigDecimal area = null;
        while(!isValid){
            area = io.readBigDecimal("Enter are, minmimum is 100 square foot.");
            if (area.compareTo(new BigDecimal("100"))>=0){
                isValid = true;

            }else
                io.print("area must be bigger then 100sq ft");
        }

        Order order = new Order(0, name, customerTax.getState(), localDate, customerTax.getTaxRate(),
                chosenProduct.getProductType(), chosenProduct.getCostPerSquareFoot(),
                chosenProduct.getLabourCostPerSquareFoot(),
                null, area, null, null, null);

        return order;


    }

    public int displayMenuAndGetChoice(){
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        return  io.readInt("Please Select an option", 1 , 6);

    }

    public void displayExitMessage(){
        io.print("Thanks for shopping with us!, GoodBye.");
    }

    public void displayErrorMessage(String message){
        io.print("Error: "+ message);
    }

    public void displayUnkownCommand(){
        io.print("Unknown command.");
    }



}
