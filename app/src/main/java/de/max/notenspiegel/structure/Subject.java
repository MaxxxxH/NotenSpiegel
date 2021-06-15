package de.max.notenspiegel.structure;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.max.notenspiegel.R;

public class Subject implements Serializable {
    public enum Warn {
        INSUFFICIENT, WARN, GOOD
    }

    public static final int WARN_COLOR = R.color.warn_paper;
    public static final int INSUFFICIENT_COLOR = R.color.insufficient_paper;
    public static final int GOOD_COLOR = R.color.good_paper;
    public static final int WARN = 75;
    public static final int INSUFFICIENT = 50;
    private final String name;
    private final List<Paper> papers;
    private int maxAmount;
    private int nextNumber;

    public Subject(String name, int maxAmount) {
        this.maxAmount = maxAmount;
        this.name = name;
        papers = new ArrayList<>();

        //@Todo remove test
        papers.add(new Paper(3, 5, 0));
    }

    public int getMaxAmount() {
        return maxAmount <= 0 ? papers.size() : maxAmount;
    }

    public Iterable<Paper> getIterablePapers() {
        return papers;
    }

    public int getNextNumber() {
        return ++nextNumber;
    }

    public String getName() {
        return name;
    }

    public void addPaper(Paper paper) {
        this.papers.add(paper);
    }

    public void removePaper(Paper paper) {
        this.papers.remove(paper);
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getPercent() {
        Point s = getSum();
        if (s.y == 0) {
            return 0;
        }
        return (s.x * 100) / s.y;
    }

    private Point getSum() {
        int sum = 0;
        int sumMax = 0;
        for (Paper paper : papers) {
            sum += paper.getActualPoints();
            sumMax += paper.getMaxPoint();
        }
        return new Point(sum, sumMax);
    }

    public int getPercentTotal() {
        if (maxAmount <= 0) {
            return getPercent();
        }
        if(papers.size()==0){
            return 0;
        }
        Point s = getSum();
        int sum = s.x;
        int sumMax = s.y;
        sumMax *= maxAmount;
        sumMax /= papers.size();
        return sum * 100 / sumMax;
    }

    public Warn warnColor(int value) {
        if (value < INSUFFICIENT) {
            return Warn.INSUFFICIENT;
        } else if (value < WARN) {
            return Warn.WARN;
        } else {
            return Warn.GOOD;
        }
    }

    public static int getColor(Warn warn, Context context) {
        switch (warn) {
            case INSUFFICIENT:
                return context.getColor(R.color.insufficient_paper);
            case WARN:
                return context.getColor(R.color.warn_paper);
            default:
            return context.getColor(R.color.good_paper);
        }
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
