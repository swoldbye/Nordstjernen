package com.example.skibslogapp.datalayer.global;

class LogpunktString {

    private static final String MISSING_DATA_MSG = "";

    private String EtapeID = MISSING_DATA_MSG;
    private String Dato = MISSING_DATA_MSG;
    private String Roere = MISSING_DATA_MSG;
    private String vindretning = MISSING_DATA_MSG;
    private String vindhastighed= MISSING_DATA_MSG;
    private String StroemRetning= MISSING_DATA_MSG;
    private String Kurs = MISSING_DATA_MSG;
    private String Note = MISSING_DATA_MSG;
    private String MandOverBord = MISSING_DATA_MSG;
    private String BredeGrad = MISSING_DATA_MSG;
    private String HoejdeGrad = MISSING_DATA_MSG;
    private String SejlFoering = MISSING_DATA_MSG;
    private String Sejlstilling = MISSING_DATA_MSG;
    private String stroemHastighed = MISSING_DATA_MSG;

    String getEtapeID() {
        return EtapeID;
    }

    void setEtapeID(String etapeID) {
        EtapeID = etapeID;
    }

    String getDato() {
        return Dato;
    }

    void setDato(String dato) {
        Dato = dato;
    }

    String getRoere() {
        return Roere;
    }

    void setRoere(String roere) {
        Roere = roere;
    }

    String getVindretning() {
        return vindretning;
    }

    void setVindretning(String vindretning) {
        this.vindretning = vindretning;
    }

    String getVindhastighed() {
        return vindhastighed;
    }

    void setVindhastighed(String vindhastighed) {
        this.vindhastighed = vindhastighed;
    }

    String getStroemRetning() {
        return StroemRetning;
    }

    void setStroemRetning(String stroemRetning) {
        StroemRetning = stroemRetning;
    }

    String getKurs() {
        return Kurs;
    }

    void setKurs(String kurs) {
        Kurs = kurs;
    }

    String getNote() {
        return Note;
    }

    void setNote(String note) {
        Note = note;
    }

    String getMandOverBord() {
        return MandOverBord;
    }

    void setMandOverBord(String mandOverBord) {
        MandOverBord = mandOverBord;
    }

    String getBredeGrad() {
        return BredeGrad;
    }

    void setBredeGrad(String bredeGrad) {
        BredeGrad = bredeGrad;
    }

    String getHoejdeGrad() {
        return HoejdeGrad;
    }

    void setHoejdeGrad(String hoejdeGrad) {
        HoejdeGrad = hoejdeGrad;
    }

    String getSejlFoering() {
        return SejlFoering;
    }

    void setSejlFoering(String sejlFoering) {
        SejlFoering = sejlFoering;
    }

    String getSejlstilling() {
        return Sejlstilling;
    }

    void setSejlstilling(String sejlstilling) {
        Sejlstilling = sejlstilling;
    }

    
    String getStroemHastighed() {
        return stroemHastighed;
    }

    void setStroemHastighed(String stroemHastighed) {
        this.stroemHastighed = stroemHastighed;
    }

}
