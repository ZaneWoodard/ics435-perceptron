package edu.hawaii.ics435;

import java.util.Arrays;
import java.util.logging.ConsoleHandler;
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
    static {
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
                Byte output = biasedClassify(input);
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

        logger.info("Convergence on training data reached in " + round + " epochs");
    }


    private Byte biasedClassify(Byte[] biasedInput) {
        Double summation = 0.0;

        for(int i = 0; i < biasedInput.length; i++) {
            summation += biasedInput[i]*weights[i];
        }

        return (summation > 0) ? (byte)1 : (byte)-1;
    }

    protected Byte classify(Byte[] input) {
        input = addBiasToInput(input);
        return biasedClassify(input);
    }

    private Byte[] addBiasToInput(Byte[] input) {
        Byte[] biasedInput = new Byte[input.length+1];
        System.arraycopy(input, 0, biasedInput, 1, input.length);
        biasedInput[0] = 1;
        return biasedInput;
    }
}
