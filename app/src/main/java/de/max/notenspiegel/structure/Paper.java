package de.max.notenspiegel.structure;

import java.io.Serializable;

public class Paper implements Serializable {
    private final int maxPoint;
    private final int actualPoints;
    private final int number;

    public Paper() {
        this(-1, -2, 0);
    }

    public Paper(int actualPoints, int maxPoint, int number) {
        this.maxPoint = maxPoint;
        this.actualPoints = actualPoints;
        this.number = number;
    }


    public int getMaxPoint() {
        return maxPoint;
    }

    public int getActualPoints() {
        return actualPoints;
    }

    public int getPercent() {
        return (actualPoints * 100 / maxPoint);
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
