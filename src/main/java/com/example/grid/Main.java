package com.example.grid;

import org.encog.neural.networks.BasicNetwork;
import org.encog.persist.EncogDirectoryPersistence;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static Network net;
    public static void main(String[] args){
        net = new Network();
        net.doStuff(net);
        Application.main(args);

        /* Scanner scanner = new Scanner(new File("src/main/resources/com/example/grid/train.csv"));
        while (true) {

        } */
    }

    public static String compute(String[] inputs) {
        String[] inputString = inputs;
        double[] input = new double[784];
        for (int i = 0; i < 784; i++) {
            input[i] = Double.parseDouble(inputString[i]);
        }
        System.out.println(Arrays.toString(Arrays.stream(input).toArray()));
        int number = 1234;
        for(int i = 784; i < inputString.length; i++) {
            if (Double.parseDouble(inputString[i]) != 0) {
                number = i - 784;
                break;
            }
        }
        StringBuilder calculated = new StringBuilder();

        ArrayList<Double> outputs = new ArrayList<>(Arrays.stream(net.calculate(input)).boxed().toList());
        calculated.append(outputs + "\n");
        calculated.append("Network calculation : " + outputs.indexOf(Arrays.stream(net.calculate(input)).max().getAsDouble()));
        return calculated.toString();
    }
}