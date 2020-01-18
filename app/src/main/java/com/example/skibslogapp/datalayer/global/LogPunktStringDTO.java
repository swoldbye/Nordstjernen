package com.example.skibslogapp.datalayer.global;

public class LogPunktStringDTO {
    public String getTogtID() {
        return TogtID;
    }

    public void setTogtID(String togtID) {
        TogtID = togtID;
    }

    public String getEtapeID() {
        return EtapeID;
    }

    public void setEtapeID(String etapeID) {
        EtapeID = etapeID;
    }

    public String getDato() {
        return Dato;
    }

    public void setDato(String dato) {
        Dato = dato;
    }

    public String getRoere() {
        return Roere;
    }

    public void setRoere(String roere) {
        Roere = roere;
    }

    public String getVindretning() {
        return vindretning;
    }

    public void setVindretning(String vindretning) {
        this.vindretning = vindretning;
    }

    public String getVindhastighed() {
        return vindhastighed;
    }

    public void setVindhastighed(String vindhastighed) {
        this.vindhastighed = vindhastighed;
    }

    public String getStroemRetning() {
        return StroemRetning;
    }

    public void setStroemRetning(String stroemRetning) {
        StroemRetning = stroemRetning;
    }

    public String getKurs() {
        return Kurs;
    }

    public void setKurs(String kurs) {
        Kurs = kurs;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getMandOverBord() {
        return MandOverBord;
    }

    public void setMandOverBord(String mandOverBord) {
        MandOverBord = mandOverBord;
    }

    public String getBredeGrad() {
        return BredeGrad;
    }

    public void setBredeGrad(String bredeGrad) {
        BredeGrad = bredeGrad;
    }

    public String getHoejdeGrad() {
        return HoejdeGrad;
    }

    public void setHoejdeGrad(String hoejdeGrad) {
        HoejdeGrad = hoejdeGrad;
    }

    public String getSejlFoering() {
        return SejlFoering;
    }

    public void setSejlFoering(String sejlFoering) {
        SejlFoering = sejlFoering;
    }

    public String getSejlstilling() {
        return Sejlstilling;
    }

    public void setSejlstilling(String sejlstilling) {
        Sejlstilling = sejlstilling;
    }
    String missingDataMsg = "ikke noteret";
    String TogtID = missingDataMsg;
    String EtapeID = missingDataMsg;
    String Dato = missingDataMsg;
    String Roere = missingDataMsg;
    String vindretning = missingDataMsg;
    String vindhastighed= missingDataMsg;
    String StroemRetning= missingDataMsg;
    String Kurs = missingDataMsg;
    String Note = missingDataMsg;
    String MandOverBord = missingDataMsg;
    String BredeGrad = missingDataMsg;
    String HoejdeGrad = missingDataMsg;
    String SejlFoering = missingDataMsg;
    String Sejlstilling = missingDataMsg;
}
