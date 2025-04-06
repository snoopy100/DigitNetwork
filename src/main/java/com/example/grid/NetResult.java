package com.example.grid;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NetResult {
    String string;
    public boolean boxExists = false;
    private HashMap<Double, Integer> outputMap = new HashMap<>();
    public HBox box = new HBox();
    private TextArea text = new TextArea("Neural Network Outputs");

    public NetResult (String[] netOutputs, Network net) {
        /* final int OUTPUTSIZE = 10;
        netOutputs = network.calculate(netOutputs);
        System.out.println("network outputs : " + Arrays.toString(netOutputs));
        for (int index = 0; index < OUTPUTSIZE; index++) {
            outputMap.put(netOutputs[index], index);
            System.out.println("putting into hashmap : " + netOutputs[index] + " - " + index);
        }

        Double[] sorted = outputMap.keySet().toArray(new Double[outputMap.keySet().toArray().length]);
        System.out.println(Arrays.toString(sorted));
        Arrays.sort(sorted);

        StringBuilder string = new StringBuilder(text.getText());

        for (int i = sorted.length - 1; i > -1; i--) {
            Double out = sorted[i];
            string.append("\n " + outputMap.get(out) + ") " + (out * 100));
            System.out.println("Appending text to output box");
        }

        text.setText(string.toString());
        box.getChildren().removeAll();
        box.getChildren().add(text);
        System.out.println("num of elemnts in hbox netresult : " + box.getChildren().size());
        System.out.println("Output box text : " + text.getText()); */
        string = Main.compute(netOutputs);
        System.out.println(string);
        text.setText(string);
        box.getChildren().add(text);
    }

    public HBox getBox() {
        boxExists = true;
        return box;
    }
}
