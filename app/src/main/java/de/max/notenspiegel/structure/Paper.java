package de.max.notenspiegel.structure;

import java.io.Serializable;

public class Paper implements Serializable {
    private double maxPoint;
    private double actualPoints;
    private final int number;

    public Paper() {
        this(-1, -2, 0);
    }

    public Paper(double actualPoints, double maxPoint, int number) {
        this.maxPoint = maxPoint;
        this.actualPoints = actualPoints;
        this.number = number;
    }

    public Paper(int actualPoints, int maxPoint, int number) {
        this((double) actualPoints, (double) maxPoint, number);
    }


    public double getMaxPoint() {
        return maxPoint;
    }

    public double getActualPoints() {
        return actualPoints;
    }

    public int getPercent() {
        return (int) (actualPoints * 100 / maxPoint);
    }

    public void setMaxPoint(double maxPoint) {
        this.maxPoint = maxPoint;
    }

    public void setActualPoints(double actualPoints) {
        this.actualPoints = actualPoints;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        //toDo implement better
        return "Paper{" +
                "maxPoint=" + maxPoint +
                ", actualPoints=" + actualPoints +
                '}';
    }
}
