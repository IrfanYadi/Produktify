package com.unila.ilkomp.produktify.models;

public class LabelModel {
    private int labelID;
    private String judulLabel;

    public LabelModel(){}

    public LabelModel(String judulLabel) {
        this.judulLabel = judulLabel;
    }

    public LabelModel(int labelID, String judulLabel) {
        this.labelID = labelID;
        this.judulLabel = judulLabel;
    }

    public int getLabelID() {
        return labelID;
    }

    public void setLabelID(int labelID) {
        this.labelID = labelID;
    }

    public String getJudulLabel() {
        return judulLabel;
    }

    public void setJudulLabel(String judulLabel) {
        this.judulLabel = judulLabel;
    }
}
