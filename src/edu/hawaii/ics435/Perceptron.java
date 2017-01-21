package edu.hawaii.ics435;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Perceptron {

    //Using Byte because it's the smallest data type in Java that is still treated as a numeric type
    private Byte[][] inputs;
    private Byte[] labels;
    private Double[] weights;

    /**
     * The learning rate constant, can be used to scale how much the weights are adjusted by
     */
    private static Byte LEARNING_RATE = 1;

    /**
     * Sets the upper limit to how many learning rounds the perceptron algorithm will attempt before quitting
     */
    private static final int CONVERGENCE_ROUND_LIMIT = 1000;
    private static final Logger logger = Logger.getLogger("Perceptron");
    private static final Level LOGGING_LEVEL = Level.FINER;

    /**
     * This is some magic to get the logger to actually log properly
     */
    {
        logger.setLevel(LOGGING_LEVEL);
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(LOGGING_LEVEL);
        logger.addHandler(handler);

    }

    public Perceptron(Byte[][] inputs, Byte[] labels) {

        this.inputs = inputs;
        this.labels = labels;
        this.weights = new Double[inputs[0].length];
        Arrays.fill(weights, 0.0);
    }

    protected void learn() {
        int mismatches = 0;
        int round = 0;
        logger.info("Starting training with input data...");
        do {
            mismatches = 0;
            for (int j = 0; j < inputs.length; j++) {
                logger.finer("Processing input " + j);
                Byte[] input = inputs[j];
                Byte output = classify(input);
                Byte desiredOutput = labels[j];
                if (output != desiredOutput) {
                    mismatches++;
                    //Misclassification, update the weights
                    Byte difference = (byte) (desiredOutput - output);
                    Byte delta = (byte) (difference*LEARNING_RATE);
                    logger.finer("Old weights: " + Arrays.toString(weights));
                    for (int i = 0; i < input.length; i++) {
                        weights[i] = weights[i] + difference * input[i];
                    }
                    logger.finer("New weights: " + Arrays.toString(weights));
                }
            }

            logger.fine("Round " + round++ + " completed with " + mismatches + " mismatches");

        } while(mismatches>0 && CONVERGENCE_ROUND_LIMIT > round);

        if(round >= CONVERGENCE_ROUND_LIMIT) {
            logger.info("Attempted " + round + " rounds of learning; convergence highly unlikely. Ending Learning phase.");
        }

        logger.info("Done training");
    }

    protected Byte classify(Byte[] input) {
        Double summation = 0.0;

        for(int i = 0; i < input.length; i++) {
            summation += input[i]*weights[i];
        }

        //TODO I'm assuming we have to round here. Not sure if this is true.
        //TODO I think a threshold value is supposed to be used somewhere around here
        /* TODO Classification currently outputs 0, when it should be either -1 or 1. Need to figure out a fix.
         * TODO It's probably due to using the wrong function (Math.sin)
         */
        return (byte) Math.round(Math.sin(summation));
    }
}
