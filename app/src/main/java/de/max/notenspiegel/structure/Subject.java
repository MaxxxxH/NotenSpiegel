package de.max.notenspiegel.structure;

import android.content.Context;
import android.graphics.Point;


import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.max.notenspiegel.R;

public class Subject implements Serializable {

    public enum Warn {
        INSUFFICIENT, WARN, GOOD;

        public int getColor(Context context) {
            switch (this) {
                case INSUFFICIENT:
                    return context.getColor(R.color.insufficient_paper);
                case WARN:
                    return context.getColor(R.color.warn_paper);
                default:
                    return context.getColor(R.color.good_paper);
            }

        }
    }

    public static final String PREFERENCES = "pref_subject";
    private static final String PREFIX_SUBJECT = "subject_";
    private static final int GOOD = 75;
    private static final int SUFFICIENT = 50;
    private final String key;
    private final String name;
    private final List<Paper> papers;
    private Setting setting;
    private int maxAmount;
    private int nextNumber;

    public Subject(String name, int maxAmount, Setting setting) {
        this.maxAmount = maxAmount;
        this.name = name;
        this.key = PREFIX_SUBJECT + name;
        papers = new ArrayList<>();
        this.setting = setting;
    }

    public String getKey() {
        return key;
    }

    public int getMaxAmount() {
        return setting.getMaxPaper() <= 0 ? papers.size() : setting.getMaxPaper();
    }

    public Iterable<Paper> getIterablePapers() {
        return papers;
    }

    public int getNextNumber() {
        return ++nextNumber;
    }

    public String getName() {
        return setting.getName();
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
        if (getMaxAmount() <= 0) {
            return getPercent();
        }
        if (papers.size() == 0) {
            return 0;
        }
        Point s = getSum();
        int sum = s.x;
        int sumMax = s.y;
        sumMax *= getMaxAmount();
        sumMax /= papers.size();
        return sum * 100 / sumMax;
    }

    public Warn warnColor(int value) {
        if (value < (setting != null ? setting.getSufficient() : SUFFICIENT)) {
            return Warn.INSUFFICIENT;
        } else if (value < (setting != null ? setting.getGood() : GOOD)) {
            return Warn.WARN;
        } else {
            return Warn.GOOD;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return key.equals(subject.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public @NotNull String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", papers=" + papers +
                ", maxAmount=" + maxAmount +
                setting +
                '}';
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }

    public void synchroniseSetting() {
        if (setting.getName().isEmpty())
            setting.setName(this.name);
        if (setting.getMaxPaper() <= 0)
            setting.setMaxPaper(this.maxAmount);
    }

    public Setting getSetting() {
        return setting;
    }
}
