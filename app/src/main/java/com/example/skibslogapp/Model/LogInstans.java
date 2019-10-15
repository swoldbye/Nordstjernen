package com.example.skibslogapp.Model;


public class LogInstans{
Togt togt;

    private String vindretning = "";
    private String kurs = "";
    private String sejlføring = "";
    private String sejlstilling = "";

    public LogInstans(String a, String b, String c, String d){
        this.vindretning = a;
        this.kurs = b;
        this.sejlføring=c;
        this.sejlstilling = d;

    }


    @Override
    public String toString() {
        return "Vindretning: "+vindretning+", kurs: "+ kurs+", sejlføring: "+sejlføring+"\n"+ ", sejlstilling: "+sejlstilling;
    }
}
