package de.max.notenspiegel;

import java.text.DecimalFormat;
import java.util.Collection;

public final class PercentageCalculator {

    private PercentageCalculator() {
    }

    public static <T extends Collection<Double>> String getAbsolutePercentageString (T points, T maxPoints, int exerciseAmount) {
        return formatToPercentage(getAbsolutePercentage(points, maxPoints, exerciseAmount)*100);
    }

    public static <T extends Collection<Double>> double getAbsolutePercentage(T points, T maxPoints, int exerciseAmount) {
        assert points.size() <= exerciseAmount;
        return getSum(points)/(getAverage(maxPoints) * exerciseAmount);
    }

    public static <T extends Collection<Double>> String getCurrentPercentageString(T points, T maxPoints) {
        return formatToPercentage(getCurrentPercentage(points, maxPoints));
    }

    public static <T extends Collection<Double>> double getCurrentPercentage(T points, T maxPoints) {
        double currentPercentage;
        if (points.size() > maxPoints.size()) {
            currentPercentage = getSum(points)/(getAverage(maxPoints) * points.size());
        } else if (points.size() < maxPoints.size()) {
            double maxPointSum = getSum(maxPoints);
            Double[] maxPointsArray = (Double[]) maxPoints.toArray();
            for (int i = maxPoints.size() - 1; i > points.size(); i--) {
                maxPointSum -= maxPointsArray[i];
            }
            currentPercentage = getSum(points)/maxPointSum;
        } else {
            currentPercentage = getSum(points)/getSum(maxPoints);
        }
        return currentPercentage;
    }

    private static <T extends Collection<Double>> double getAverage(T collection) {
        double sum = 0;
        for (double number : collection) {
            sum += number;
        }
        return sum/collection.size();
    }

    private static <T extends Collection<Double>> double getSum(T collection) {
        double sum = 0;
        for (double number : collection) {
            sum += number;
        }
        return sum;
    }

    private static <T extends Collection<Double>> String formatToPercentage(Double number) {
        DecimalFormat f = new DecimalFormat("##.00");
        return f.format(number) + "%";
    }

}
