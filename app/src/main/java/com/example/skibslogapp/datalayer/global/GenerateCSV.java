package com.example.skibslogapp.datalayer.global;

public class GenerateCSV {

    public StringBuilder make(){
        StringBuilder data = new StringBuilder();
        data.append("TogtID,EtapeID,Dato, Roere, vindretning, vindhastighed, stroemRetning");
        for (int i = 0; i < 5; i++) {
            data.append("\n" + String.valueOf(i) + "," + String.valueOf(i * i)+ "," + String.valueOf(i + i)+ "," + String.valueOf(i * 5)+ "," + "NNØ");
        }
        return data;
    }
}
