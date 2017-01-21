package edu.hawaii.ics435;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Main {

    public static void main(String[] args) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS] [%4$s] [%3$s] %5$s%6$s%n");
        //TODO this is currently just a simple place to interact with the Perceptron algorith, we need to implement data generation
        //TODO I think one element of the input and weight arrays is supposed to be a bias value

        noConvergence();
        largeExample();
        smallExample();





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
                }
        };
        Byte[] labels = new Byte[]{1, 1, 1, 0};
        Perceptron perceptron = new Perceptron(trainingInput, labels);
        perceptron.learn();


        System.out.println(perceptron.classify(trainingInput[0]));
        System.out.println(perceptron.classify(trainingInput[1]));
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
}
