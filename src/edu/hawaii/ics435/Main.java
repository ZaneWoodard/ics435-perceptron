package edu.hawaii.ics435;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$s] [%3$s] %5$s%6$s%n");
        //TODO this is currently just a simple place to interact with the Perceptron algorith, we need to implement data generation
        //TODO I think one element of the input and weight arrays is supposed to be a bias value
        Byte[][] trainingInput = new Byte[][] {
            {1,1,0,0}, {0,0,1,1}
        };
        Byte[] labels = new Byte[]{1, -1};

        Perceptron perceptron = new Perceptron(trainingInput, labels);
        perceptron.learn();
        System.out.println(perceptron.classify(new Byte[] {1,1,0,0}));
        System.out.println(perceptron.classify(new Byte[] {0,0,1,1}));

        /* TODO Classification currently outputs 0, when it should be either -1 or 1. Need to figure out a fix.
         * TODO It's probably due to using the wrong function (Math.sin)
         */
        System.out.println(perceptron.classify(new Byte[] {1,1,1,1}));

    }
}
