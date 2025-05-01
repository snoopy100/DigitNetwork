package com.example.grid;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationReLU;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.buffer.BufferedMLDataSet;
import org.encog.ml.train.strategy.StopTrainingStrategy;
import org.encog.neural.error.CrossEntropyErrorFunction;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.ContainsFlat;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.neural.networks.training.strategy.SmartLearningRate;
import org.encog.neural.pnn.BasicPNN;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.simple.EncogUtility;

import java.io.File;
import java.io.SyncFailedException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Network {
    private static final String NETWORK_FILE = "network.eg";
    //private BasicPNN network;
    private BasicNetwork network;
    private MLDataSet trainSet, testSet;
    //private BasicMLDataSet trainSet, testSet;

    public void doStuff(Network net) {
        // Load training and testing data
        trainSet = loadCSV("src/main/resources/com/example/grid/train.csv");
        testSet = loadCSV("src/main/resources/com/example/grid/test.csv");

        File file = new File(NETWORK_FILE);
        if (file.exists()) {
            network = (BasicNetwork) EncogDirectoryPersistence.loadObject(file);
            // network = (BasicPNN) EncogDirectoryPersistence.loadObject(file);
            System.out.println("Network file exists. Loaded trained model.");
            return;
        } else {
            createNetwork();
        }

        int epoch = 1;
        do {
            net.train(epoch);
            epoch++;
        } while (net.test() * 100 < 85);
       // network.setSamples(trainSet);
        System.out.println("Training finished.");
        net.test();

        if ((new Scanner(System.in)).nextInt() == 1) {
            net.save();
        }
    }

    private void createNetwork() {
        System.out.println("Creating new neural network...");
        network = new BasicNetwork();
        //network = new BasicPNN(PNNKernelType.Gaussian, PNNOutputMode.Classification, 784, 10);
        //network.setSamples(testSet);
        network.addLayer(new BasicLayer(null, true, 784));   // Input layer
        network.addLayer(new BasicLayer(new ActivationReLU(), true, 20));  // Hidden layer 1
        network.addLayer(new BasicLayer(new ActivationReLU(), true, 16));  // Hidden layer 2
        network.addLayer(new BasicLayer(new ActivationReLU(), false, 10)); // Output layer
        network.getStructure().finalizeStructure();
        network.reset();
    }

    public void train(int epoch) {
        System.out.println("\nEpoch Number : " + epoch);
        StopTrainingStrategy stop = new StopTrainingStrategy();
        System.out.println("Training started...");
        Backpropagation training = new Backpropagation(network, trainSet, 0.02, 0.3);
        training.setThreadCount(Runtime.getRuntime().availableProcessors() + 1);
        training.addStrategy(stop);
        training.setBatchSize(200);

        training.iteration();
        training.finishTraining();
        save();
    }

    public double[] calculate(double[] inputs) {
        double[] outputs = new double[10];
        /* network.compute(//Arrays.stream(inputs)
                //.mapToDouble(Double::intValue)
                //.toArray(),
                inputs, outputs); */
        outputs = network.compute(new BasicMLData(inputs)).getData();

        /* just in case for convertinf double[] to Double[]
        Arrays.stream(outputs)
                .boxed()
                .toArray(Double[]::new)
         */
        return outputs;

        //this code was hard to write, it should be hard to read
        //no useful comments for you!!!
    }

    public void save() {
        EncogDirectoryPersistence.saveObject(new File(NETWORK_FILE), network);
        System.out.println("Network saved to " + NETWORK_FILE);
    }

    public double test() {
        double correct = 0.0, incorrect = 0.0;
        System.out.println("******* Testing *********");

        int num = 0;
        for (MLDataPair pair : testSet) {
            MLData output = network.compute(pair.getInput());
            int predictedIndex = getMaxIndex(output.getData());
            int actualIndex = getMaxIndex(pair.getIdealArray());

            if (predictedIndex == actualIndex) {
                correct++;
            } else {
                incorrect++;
            }
        }

        double accuracy = correct / (correct + incorrect);

        System.out.println("Correct: " + correct);
        System.out.println("Incorrect: " + incorrect);
        System.out.println("Accuracy: " + accuracy * 100);
        return accuracy;
    }

    private BasicMLDataSet loadCSV(String filePath) {
        File file = new File(filePath);
        File egb = new File(filePath + ".egb");
        if(!egb.exists()) {
            EncogUtility.convertCSV2Binary(file, egb, 784, 10, false);
        }
        return new BasicMLDataSet(BasicMLDataSet.toList(new BufferedMLDataSet(egb)));
    }

    private int getMaxIndex(double[] array) {
        int maxIndex = 0;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[maxIndex]) maxIndex = i;
        }
        return maxIndex;
    }
}
