package flooringmastery.view;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


@Component
public class UserIOConsoleImpl implements  UserIO{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt) {

        boolean validInput = false;
        int userChoice = 0;
        while(!validInput){
            String userInput = readString(prompt);
            try {
                userChoice = Integer.parseInt(userInput);
                validInput = true;

            }catch(NumberFormatException e){
                print("Please enter a whole number ");
            }
        }
        return userChoice;
    }

    @Override
    public int readInt(String prompt, int min, int max) {

        int result = 0;
        boolean isInputValid = false;
        while(!isInputValid){
            result = readInt(prompt);
            if (result>=min && result<=max){
                isInputValid = true;
            }else {
                print("Please enter a number between "+ min + " " + max);
            }
        }
        return result;
    }

    @Override
    public BigDecimal readBigDecimal(String prompt) {

        BigDecimal result = null;
        boolean isInputValid = false;
        while(!isInputValid){
            String userInput = readString(prompt);
            try {
                result = new BigDecimal(userInput);
                isInputValid = true;
            }catch( NumberFormatException e){
                print("Please enter a valid number !");
            }
        }

        return result;
    }

    @Override
    public LocalDate readLocalDate(String prompt) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate todaysDate = null;
        boolean isValid = false;
        while(!isValid){
            String userInput = readString(prompt);
            try {
                todaysDate = LocalDate.parse(userInput, formatter);
                isValid = true;
            }catch(DateTimeException e){
                print("Please enter the date in this format mm-dd-yyyy");
            }
        }
        return todaysDate;
    }
}
