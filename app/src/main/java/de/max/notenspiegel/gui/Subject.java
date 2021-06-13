package de.max.notenspiegel.gui;

public class Subject {
    private final String name;
    private int maxAmount;


    public Subject(String name, int maxAmount) {
        this.maxAmount = maxAmount;
        this.name = name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public String getName() {
        return name;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getPercent() {
        return 300;
    }
}
