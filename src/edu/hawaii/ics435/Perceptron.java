package edu.hawaii.ics435;

import java.util.Arrays;

public class Perceptron {

    //Using Byte because it's the smallest data type in Java that is still treated as a numeric type
    private Byte[][] inputs;
    private Byte[] labels;
    private Double[] weights;

    public Perceptron(Byte[][] inputs, Byte[] labels) {
        this.inputs = inputs;
        this.labels = labels;
        this.weights = new Double[inputs[0].length];
        Arrays.fill(weights, 0.0);
    }

    protected void learn() {
        //TODO Currently not looping until no mismatches found
        for(int j = 0; j < inputs.length; j++) {
            Byte[] input = inputs[j];
            Byte output = classify(input);
            Byte desiredOutput = labels[j];
            System.out.println("Output for " + Arrays.toString(input) + ": " + output.byteValue() + "(" + desiredOutput.byteValue() + ")");
            if(output!=desiredOutput) {
                //Misclassification, update the weights
                Byte difference = (byte)(desiredOutput - output);
                for(int i = 0; i < input.length; i++) {
                    weights[i] = weights[i] + difference*input[i];
                }
                System.out.println("New weights: " + Arrays.toString(weights));
            }
        }
    }

    protected Byte classify(Byte[] input) {
        Double summation = 0.0;

        for(int i = 0; i < input.length; i++) {
            summation += input[i]*weights[i];
        }

        //TODO I'm assuming we have to round here. Not sure if this is true.
        return (byte) Math.round(Math.sin(summation));
    }
}
