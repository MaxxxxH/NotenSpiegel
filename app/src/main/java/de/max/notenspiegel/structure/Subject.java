package de.max.notenspiegel.structure;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Subject implements Serializable {
    private final String name;
    private final List<Paper> papers;
    private int maxAmount;


    public Subject(String name, int maxAmount) {
        this.maxAmount = maxAmount;
        this.name = name;
        papers = new ArrayList<>();

        //@Todo remove test
        papers.add(new Paper(3,5));
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
        //@ToDo implement
        return 300;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", papers=" + papers +
                ", maxAmount=" + maxAmount +
                '}';
    }
}
