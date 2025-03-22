package com.example.grid;
import javafx.css.converter.ColorConverter;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.nnet.learning.MomentumBackpropagation;

import java.util.*;
import java.util.stream.Stream;

public class Network implements LearningEventListener {
    NeuralNetwork network = new MultiLayerPerceptron(784, 32, 32, 10);
    DataSet testSet = DataSet.createFromFile("/Users/JacksonKotch/Desktop/Grid/src/main/resources/com/example/grid/test.csv", 784, 10, ",");

    DataSet trainSet = DataSet.createFromFile("/Users/JacksonKotch/Desktop/Grid/src/main/resources/com/example/grid/train.csv", 784, 10, ",");

    @Override
    public void handleLearningEvent(LearningEvent event) {
        BackPropagation bp = (BackPropagation) event.getSource();

        if (event.getEventType().equals(LearningEvent.Type.LEARNING_STOPPED)) {
            double error = bp.getTotalNetworkError();
            System.out.println("training finished in : " + bp.getCurrentIteration() + "iterations");
            System.out.println("with total error : " + error);
        } else {
            System.out.println("current iteration : " + bp.getCurrentIteration());
            System.out.println("current error rate : " + bp.getPreviousEpochError());
        }
    }
    public void doStuff() {
        MomentumBackpropagation learningRule = (MomentumBackpropagation) network.getLearningRule();
        learningRule.setLearningRate(0.006);
        learningRule.setMaxError(0.002);
        learningRule.setMaxIterations(1000);
        learningRule.addListener(this);

        System.out.println("training");
        network.learn(trainSet, learningRule);
        System.out.println("finsihed");

        network.save("network.nnet");

        test(network, testSet);
    }

    void test(NeuralNetwork net, DataSet test) {
        int correct = 0;
        int incorrect = 0;
        System.out.println("*******testing*********");
        for (DataSetRow row : test.getRows()) {
            net.setInput(row.getInput());
            net.calculate();
            ArrayList<Double> expected = new ArrayList<>();
            Collections.addAll(expected, Arrays.stream(row.getDesiredOutput()).boxed().toArray(Double[]::new));
            ArrayList<Double> output = new ArrayList<>();
            Collections.addAll(output, Arrays.stream(net.getOutput()).boxed().toArray(Double[]::new));
            if (output.indexOf(Collections.max(output)) == expected.indexOf(Collections.max(expected))) {
                correct++;
            } else {
                incorrect++;
            }
        }

        System.out.println("correct: " + correct);
        System.out.println("incorrect: " + incorrect);
        System.out.println("Accuraccy: " + correct/incorrect);
    }
}
