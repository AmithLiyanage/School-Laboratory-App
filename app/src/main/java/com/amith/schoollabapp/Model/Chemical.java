package com.amith.schoollabapp.Model;

public class Chemical {

    private String id;
    private String chemicalName;
    private String available;
    private String recomended;
    private String measurement;

    public Chemical(String id, String chemicalName, String available, String recomended, String measurement) {
        this.id = id;
        this.chemicalName = chemicalName;
        this.available = available;
        this.recomended = recomended;
        this.measurement = measurement;
    }

    public Chemical() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChemicalName() {
        return chemicalName;
    }

    public void setChemicalName(String chemicalName) {
        this.chemicalName = chemicalName;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getRecomended() {
        return recomended;
    }

    public void setRecomended(String recomended) {
        this.recomended = recomended;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
