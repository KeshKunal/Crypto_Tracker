package com.example;

public class Portfolio {
    private String id;
    private double holdings;

    public Portfolio()
    {}
    
    public Portfolio(String id, double holdings)
    {
        this.id = id;
        this.holdings = holdings;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public double getHoldings()
    {
        return holdings;
    }

    public void setHoldings(double holdings)
    {
        this.holdings = holdings;
    }
}
