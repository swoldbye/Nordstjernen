package com.example.skibslogapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class LogPunkt {
    int ID;
    LocalDate dato;
    LocalTime tid;

    int AntalRoere;
    String position;
    String vindretning = "";
    String kurs = "";
    String sejlføring = "";
    String sejlstilling = "";
    String note = "";
    Boolean hals;
    Boolean MandOverBord;
}
