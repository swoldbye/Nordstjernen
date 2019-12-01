package com.example.skibslogapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogPunkt {
    long id;

    LocalDate dato;
    LocalTime tid;

    int antalroere;
    String position;
    String vindretning = "";
    String kurs = "";
    String sejlfoering = "";
    String sejlstilling = "";
    String note = "";
    boolean hals;
    boolean mandOverBord;
}
