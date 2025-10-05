package com.example;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Coin {

    private final SimpleStringProperty name;
    private final SimpleDoubleProperty price;
    private final SimpleDoubleProperty holdings;
    private final SimpleDoubleProperty value;

    public Coin(String name, double price, double holdings)
    {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.holdings = new SimpleDoubleProperty(holdings);
        this.value = new SimpleDoubleProperty(price*holdings);

    }
    
    // Getters and property method
    public String getId()
    {
        return id.get();
    }

    public SimpleStringProperty idProperty()
    {
        return id;
    }
    public String getName()
    {
        return name.get();
    }

    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public double getPrice()
    {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty()
    {
        return price;
    }

    public void setPrice(double newPrice)
    {
        this.price.set(newPrice);
        this.value.set(newPrice * getHoldings());
    }

    public double getHoldings()
    {
        return holdings.get();
    }

    public SimpleDoubleProperty holdingsProperty()
    {
        return holdings;
    }

    public double getValue()
    {
        return value.get();
    }

    public SimpleDoubleProperty valueProperty()
    {
        return value;
    }
}
