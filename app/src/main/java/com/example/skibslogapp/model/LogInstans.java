package com.example.skibslogapp.model;


import com.example.skibslogapp.model.Togt;

public class LogInstans{

    Togt togt;

    String tid = "";
    String vindretning = "";
    String strømning = "";
    String kurs = "";
    String sejlføring = "";
    String sejlstilling = "";
    String note = "";

    public LogInstans(String tid, String vindretning,String strømning, String kurs, String sejlføring, String sejlstilling, String note){
        this.tid = tid;
        this.vindretning = vindretning;
        this.strømning = strømning;
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

    public String getStrømning() {return strømning;}

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