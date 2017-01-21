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

        this.labels = labels;

        //Need +1 for weights & input lengths in order to add a bias field
        this.weights = new Double[inputs[0].length+1];
        Arrays.fill(weights, 0.0);
        this.inputs = new Byte[inputs.length][inputs[0].length+1];
        for(int i = 0; i < inputs.length; i++) {
            System.arraycopy(inputs[i], 0, this.inputs[i], 1, inputs[i].length);
            this.inputs[i][0] = 1; //Set the bias
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
                Byte[] input = inputs[j];
                Byte output = classify(input);
                Byte desiredOutput = labels[j];
                if (output != desiredOutput) {
                    mismatches++;
                    //Misclassification, update the weights
                    Byte difference = (byte) (desiredOutput - output);
                    logger.finer("Old weights: " + Arrays.toString(weights));
                    for (int i = 0; i < input.length; i++) {
                        weights[i] = weights[i] + difference * input[i];
                    }
                    logger.finer("New weights: " + Arrays.toString(weights));
                }
            }

            logger.fine("Round " + round++ + " completed with " + mismatches + " mismatches");

        } while(mismatches>0);

        logger.info("Done training");
    }

    protected Byte classify(Byte[] input) {
        Double summation = 0.0;

        for(int i = 0; i < input.length; i++) {
            summation += input[i]*weights[i];
        }

        //TODO I'm assuming we have to round here. Not sure if this is true.
        //TODO I think a threshold value is supposed to be used somewhere around here
        return (byte) Math.round(Math.sin(summation));
    }
}
