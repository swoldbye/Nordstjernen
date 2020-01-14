package com.example.skibslogapp.datalayer.global;

public class GenerateCSV {

    public StringBuilder make(){
        StringBuilder data = new StringBuilder();
        data.append("Togt,Etape,LogpunktID:,vindretning, Kurs");
        for (int i = 0; i < 5; i++) {
            data.append("\n" + String.valueOf(i) + "," + String.valueOf(i * i)+ "," + String.valueOf(i + i)+ "," + String.valueOf(i * 5)+ "," + "NNØ");
        }
        return data;
    }
}
