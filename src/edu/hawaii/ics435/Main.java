package edu.hawaii.ics435;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class Main {

    private static final Integer MAX_X = 100, MAX_Y = 100, MAX_POINTS = 500, MAX_SLOPE = 4, MAX_B = 100;
    protected static final long WAIT_MS = 1;
    private static PerceptronDataSet pds;
    private static PerceptronChart chart;

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$s] [%3$s] %5$s%6$s%n");

        pds = generateLinearlySeparableData(MAX_POINTS, MAX_X, MAX_Y, MAX_SLOPE, MAX_B);
        chart = new PerceptronChart("Perceptron Chart", pds);
        chart.addLine(pds.realLineP1, pds.realLineP2, "True Line");
        chart.pack();
        chart.setVisible(true);

        Perceptron perceptron = new Perceptron(pds.initialPoints, pds.initialLabels);
        perceptron.learn();

        Random rand = new Random();
        for(int i = 0; i < MAX_POINTS*3; i++) {
            Point p = new Point((int) (MAX_X*rand.nextDouble()), (int) (MAX_Y*rand.nextDouble()));
            Byte label = perceptron.classifyPoint(p);

            pds.addedPoints.add(p);
            pds.addedLabels.add(label);
            chart.addLearnedPoint(p, label);
        }

    }

    public static void addLearnedLine(Point2D.Double p1, Point2D.Double p2, Integer round) {
        pds.learnedLineP1 = p1;
        pds.learnedLineP2 = p2;

        //Extend the points to the maxX/Y of the chart
        double m = (p1.y - p2.y) / (p1.x - p2.x);
        double intercept = p1.y;

        p2.x = pds.maxX;
        p2.y = m*pds.maxX + intercept;


        chart.addLine(p1, p2, "Learned Line");
    }

    private static PerceptronDataSet generateLinearlySeparableData(int numPoints, int maxX, int maxY, int maxSlope, int maxB) {
        Random rand = new Random();
        double m = rand.nextDouble() * maxSlope;
        if(rand.nextBoolean()) m*=-1;
        double b = rand.nextDouble() * maxB;


        System.out.println("Slope: " + m);
        System.out.println("Intercept: " + b);

        Point[] points = new Point[numPoints];
        Byte[] labels = new Byte[numPoints];
        for(int i = 0; i < numPoints; i++) {
            Point randPoint = new Point((int) (rand.nextDouble() * maxX), (int) (rand.nextDouble() * maxY));

            points[i] = randPoint;
            double lineYAtX = m*randPoint.x + b;
            if(randPoint.y > lineYAtX) {
                labels[i] = -1;
            } else {
                labels[i] = 1;
            }
        }

        Point2D.Double realLineP1 = new Point2D.Double(0, b);
        Point2D.Double reallineP2 = new Point2D.Double(maxX, m*maxX+b);
        PerceptronDataSet pds = new PerceptronDataSet(points, labels, realLineP1, reallineP2, MAX_X, MAX_Y);

        return pds;
    }
}
