package com.example.skibslogapp.viewControl;


import com.example.skibslogapp.Model.Togt;

public class LogInstans {
Togt togt;

    String vindretning = "";
    String kurs = "";
    String sejlføring = "";
    String sejlstilling = "";

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
