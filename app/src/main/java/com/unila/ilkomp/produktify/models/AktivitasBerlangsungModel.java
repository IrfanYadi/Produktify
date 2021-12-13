package com.unila.ilkomp.produktify.models;

public class AktivitasBerlangsungModel {
    private int aktivitasID;
    private String judulAktivitas,kontenAktivitas,aktivitasDate,
            aktivitasTime,labelAktivitas;

    public AktivitasBerlangsungModel(){}

    public AktivitasBerlangsungModel(String judulAktivitas, String kontenAktivitas, String labelAktivitas, String aktivitasDate, String aktivitasTime) {
        this.judulAktivitas = judulAktivitas;
        this.kontenAktivitas = kontenAktivitas;
        this.aktivitasDate = aktivitasDate;
        this.aktivitasTime = aktivitasTime;
        this.labelAktivitas = labelAktivitas;
    }

    public AktivitasBerlangsungModel(int aktivitasID, String judulAktivitas, String kontenAktivitas, String labelAktivitas, String aktivitasDate, String aktivitasTime) {
        this.aktivitasID = aktivitasID;
        this.judulAktivitas = judulAktivitas;
        this.kontenAktivitas = kontenAktivitas;
        this.aktivitasDate = aktivitasDate;
        this.aktivitasTime = aktivitasTime;
        this.labelAktivitas = labelAktivitas;
    }

    public int getAktivitasID() {
        return aktivitasID;
    }

    public void setAktivitasID(int aktivitasID) {
        this.aktivitasID = aktivitasID;
    }

    public String getJudulAktivitas() {
        return judulAktivitas;
    }

    public void setJudulAktivitas(String judulAktivitas) {
        this.judulAktivitas = judulAktivitas;
    }

    public String getKontenAktivitas() {
        return kontenAktivitas;
    }

    public void setKontenAktivitas(String kontenAktivitas) {
        this.kontenAktivitas = kontenAktivitas;
    }

    public String getAktivitasDate() {
        return aktivitasDate;
    }

    public void setAktivitasDate(String aktivitasDate) {
        this.aktivitasDate = aktivitasDate;
    }

    public String getAktivitasTime() {
        return aktivitasTime;
    }

    public void setAktivitasTime(String aktivitasTime) {
        this.aktivitasTime = aktivitasTime;
    }

    public String getLabelAktivitas() {
        return labelAktivitas;
    }

    public void setLabelAktivitas(String labelAktivitas) {
        this.labelAktivitas = labelAktivitas;
    }
}
