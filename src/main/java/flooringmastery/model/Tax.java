package flooringmastery.model;

import java.math.BigDecimal;

public class Tax {

    //initialise the tax fields data

    private String state;
    private String stateName;
    private BigDecimal taxRate;

    //constructor
    public Tax(String state, String stateName, BigDecimal taxRate) {
        this.state = state;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    //gettors and setters.


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
}
