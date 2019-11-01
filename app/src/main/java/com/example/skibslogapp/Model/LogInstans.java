package com.example.skibslogapp.Model;


public class LogInstans{
Togt togt;

    String tid = "";
    String vindretning = "";
    String kurs = "";
    String sejlføring = "";
    String sejlstilling = "";

    public LogInstans(String tid, String vindretning, String kurs, String sejlføring, String sejlstilling){
        this.tid = tid;
        this.vindretning = vindretning;
        this.kurs = kurs;
        this.sejlføring=sejlføring;
        this.sejlstilling = sejlstilling;
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
}
