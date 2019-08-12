package com.amith.schoollabapp.Model;

public class Chemical {

    private String id;
    private String item_name;
    private Long available;
    private Long recomended;
    private String measurement;


    public Chemical(String id, String item_name, Long available, Long recomended, String measurement) {
        this.id = id;
        this.item_name = item_name;
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

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public Long getAvailable() {
        return available;
    }

    public void setAvailable(Long available) {
        this.available = available;
    }

    public Long getRecomended() {
        return recomended;
    }

    public void setRecomended(Long recomended) {
        this.recomended = recomended;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }
}
