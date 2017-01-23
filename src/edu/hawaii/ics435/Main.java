package edu.hawaii.ics435;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$s] [%3$s] %5$s%6$s%n");

        noConvergence();
        largeExample();
//        smallExample();

//        Point[] points = new Point[4];
//        points[0] = new Point(1, 2);
//        points[1] = new Point(2, 3);
//        points[2] = new Point(3, 4);
//        points[3] = new Point(4, 5);


//        PerceptronDataSet pds = generateLinearlySeparableData(30, 10, 10, 8, 3);
//        PerceptronChart pchart = new PerceptronChart("Test", pds.initialPoints, pds.initialLabels);
//        pchart.pack();
//        pchart.setVisible(true);
//        pchart.addLine(pds.realLineP1, pds.realLineP2, "True Line");
//        pchart.addPoint(new Point(2, 2), (byte)-1);

    }


    private static void smallExample() {
        Byte[][] trainingInput = new Byte[][] {
                {
                    1,1,
                    0,0
                }, {
                    0,1,
                    0,0
                }, {
                    1,0,
                    0,0
                }, {
                    0,0,
                    0,0
                }, {
                    0,0,
                    1,0
                }
        };
        Byte[] labels = new Byte[]{1, 1, 1, 0, 0};
        Perceptron perceptron = new Perceptron(trainingInput, labels);
        perceptron.learn();


        System.out.println(perceptron.classify(trainingInput[0]));
        System.out.println(perceptron.classify(trainingInput[1]));
        System.out.println(perceptron.classify(trainingInput[2]));
        System.out.println(perceptron.classify(trainingInput[3]));

        System.out.println(perceptron.classify(new Byte[]{
                0,0,
                1,1
        }));
    }

    private static void largeExample() {
        Byte[][] trainingInput = new Byte[][] {
                {
                    0,0,0,0,
                    0,1,1,0,
                    0,1,1,0,
                    0,0,0,0,
                }, {
                    1,1,1,1,
                    1,0,0,1,
                    1,0,0,1,
                    1,1,1,1,
                }
        };
        Byte[] labels = new Byte[]{1, -1};
        Perceptron perceptron = new Perceptron(trainingInput, labels);
        perceptron.learn();


        System.out.println(perceptron.classify(trainingInput[0]));
        System.out.println(perceptron.classify(trainingInput[1]));
        System.out.println(perceptron.classify(new Byte[]{
                0, 0, 0, 0,
                1, 0, 1, 0,
                1, 1, 1, 0,
                1, 1, 0, 0,
                }));
    }

    /**
     * This is an example case where the data and labels follow a XOR pattern
     * It should be impossible for convergence to occur
     */
    private static void noConvergence() {
        Byte[][] trainingInput = new Byte[][] {
                {0, 0},
                {0, 1},
                {1, 0},
                {1, 1}
        };
        Byte[] labels = new Byte[]{0,1,1,0};

        Perceptron perceptron = new Perceptron(trainingInput, labels);
        perceptron.learn();
        System.out.println(perceptron.classify(trainingInput[0]));
        System.out.println(perceptron.classify(trainingInput[1]));
        System.out.println(perceptron.classify(trainingInput[2]));
        System.out.println(perceptron.classify(trainingInput[3]));
    }

    private static PerceptronDataSet generateLinearlySeparableData(int numPoints, int maxX, int maxY, int maxSlope, int maxB) {
        Random rand = new Random();
        double m = rand.nextDouble() * maxSlope;
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

        Point realLineP1 = new Point(0, (int)b);
        Point reallineP2 = new Point(maxX, (int) (m*maxX+b));
        PerceptronDataSet pds = new PerceptronDataSet(points, labels, realLineP1, reallineP2);

        return pds;
    }
}
