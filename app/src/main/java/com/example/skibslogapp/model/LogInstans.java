package com.example.skibslogapp.model;


import com.example.skibslogapp.model.Togt;
//Todo: refactor this into LogPunkt, LogDAO, etape and Togt

public class LogInstans{

    Togt togt;

    String tid = "";
    String vindretning = "";
    String kurs = "";
    String sejlføring = "";
    String sejlstilling = "";
    String note = "";

    public LogInstans(String tid, String vindretning, String kurs, String sejlføring, String sejlstilling, String note){
        this.tid = tid;
        this.vindretning = vindretning;
        this.kurs = kurs;
        this.sejlføring=sejlføring;
        this.sejlstilling = sejlstilling;
        this.note = note;
    }

    public Togt getTogt() {
        return togt;
    }

    public String getTid() {
        return tid;
    }

    public String getVindretning() {
        return vindretning;
    }

    public String getKurs() {
        return kurs;
    }

    public String getSejlføring() {
        return sejlføring;
    }

    public String getSejlstilling() {
        return sejlstilling;
    }

    public String getNote() {
        return note;
    }
}