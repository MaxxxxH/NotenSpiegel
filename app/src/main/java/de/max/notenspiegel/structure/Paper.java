package de.max.notenspiegel.structure;

import java.io.Serializable;

public class Paper implements Serializable {
    private final int maxPoint;
    private final int actualPoints;

    public Paper(int maxPoint, int actualPoints) {
        this.maxPoint = maxPoint;
        this.actualPoints = actualPoints;
    }

    public int getMaxPoint() {
        return maxPoint;
    }

    public int getActualPoints() {
        return actualPoints;
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
