package edu.hawaii.ics435;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Perceptron {

    private Integer[][] inputs;
    //Using Byte because it's the smallest data type in Java that is still treated as a numeric type
    private Byte[] labels;
    private Double[] weights;

    /**
     * The learning rate constant, can be used to scale how much the weights are adjusted by
     */
    private static double LEARNING_RATE = 100;

    /**
     * Sets the upper limit to how many learning rounds the perceptron algorithm will attempt before quitting
     */
    private static final int CONVERGENCE_ROUND_LIMIT = 400;
    private static final Logger logger = Logger.getLogger("Perceptron");
    private static final Level LOGGING_LEVEL = Level.FINE;

    /**
     * This is some magic to get the logger to actually log properly
     */
    static {
        logger.setLevel(LOGGING_LEVEL);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGGING_LEVEL);
        logger.addHandler(handler);

    }

    public Perceptron(Integer[][] inputs, Byte[] labels) {

        this.labels = labels;

        //Need +1 for weights & input lengths in order to add a bias field
        this.weights = new Double[inputs[0].length+1];
        for(int i = 0; i < this.weights.length; i++) {
            weights[i] = Math.random();
        }
        this.inputs = new Integer[inputs.length][inputs[0].length+1];
        for(int i = 0; i < inputs.length; i++) {
            this.inputs[i] = addBiasToInput(inputs[i]);
        }
    }

    public Perceptron(Point[] points, Byte[] labels) {
        this.inputs = new Integer[points.length][3];
        this.labels = labels;
        this.weights = new Double[3];
        for(int i = 0; i < this.weights.length; i++) {
            weights[i] = Math.random();
        }
        for(int i = 0; i < points.length; i++) {
            Point p = points[i];
            this.inputs[i][0] = 1;
            this.inputs[i][1] = p.x;
            this.inputs[i][2] = p.y;
        }
    }

    protected void learn() {
        int mismatches = 0;
        int round = 0;
        logger.info("Starting training with input data...");
        do {
            mismatches = 0;
            for (int j = 0; j < inputs.length; j++) {
                logger.finer("Processing input " + j);
                Integer[] input = inputs[j];
                Byte output = biasedClassify(input);
                Byte desiredOutput = labels[j];
                if (output != desiredOutput) {
                    mismatches++;
                    //Misclassification, update the weights
                    Byte difference = (byte) (desiredOutput - output);
                    logger.finer("Old weights: " + Arrays.toString(weights));
                    for (int i = 0; i < input.length; i++) {
                        Double delta = LEARNING_RATE * input[i] * difference;
                        weights[i] += delta;
                    }
                    logger.finer("New weights: " + Arrays.toString(weights));
                }
            }

            if(mismatches>=0) {
                Point2D.Double decisionBoundaryP1 = new Point2D.Double(0, -weights[0] / weights[2]);
                Point2D.Double decisionBoundaryP2 = new Point2D.Double(-weights[0] / weights[1], 0);
                Main.addLearnedLine(decisionBoundaryP1, decisionBoundaryP2, round);
            }


            LEARNING_RATE /= 1.2;

            logger.fine("Round " + round++ + " completed with " + mismatches + " mismatches");

        } while(mismatches>0 && CONVERGENCE_ROUND_LIMIT > round);

        if(round >= CONVERGENCE_ROUND_LIMIT) {
            logger.info("Attempted " + round + " rounds of learning; convergence highly unlikely. Ending Learning phase.");
        }

        logger.info("Convergence on training data reached in " + round + " epochs");
    }


    private Byte biasedClassify(Integer[] biasedInput) {
        Double summation = 0.0;

        for(int i = 0; i < biasedInput.length; i++) {
            summation += biasedInput[i]*weights[i];
        }

        return (byte) Math.signum(summation);
    }

    protected Byte classify(Integer[] input) {
        input = addBiasToInput(input);
        return biasedClassify(input);
    }

    protected Byte classifyPoint(Point p) {
        Integer[] input = new Integer[]{1, p.x, p.y};
        return biasedClassify(input);
    }
    private Integer[] addBiasToInput(Integer[] input) {
        Integer[] biasedInput = new Integer[input.length+1];
        System.arraycopy(input, 0, biasedInput, 1, input.length);
        biasedInput[0] = 1;
        return biasedInput;
    }
}
