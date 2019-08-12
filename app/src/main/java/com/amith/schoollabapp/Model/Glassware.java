package com.amith.schoollabapp.Model;

public class Glassware {

    private String id;
    private String glasswareName;
    private Long quentity;
    private Long available;
    private Long recomended;
    private String measurement;

    public Glassware(String id, String glasswareName, Long quentity, Long available, Long recomended, String measurement) {
        this.id = id;
        this.glasswareName = glasswareName;
        this.quentity = quentity;
        this.available = available;
        this.recomended = recomended;
        this.measurement = measurement;
    }

    public Glassware() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGlasswareName() {
        return glasswareName;
    }

    public void setGlasswareName(String glasswareName) {
        this.glasswareName = glasswareName;
    }

    public Long getQuentity() {
        return quentity;
    }

    public void setQuentity(Long quentity) {
        this.quentity = quentity;
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
