package com.tony.utils.business.entity;

public class StepEntity {

    private String curdate;
    private String steps;

    public StepEntity(String curdate, String steps) {
        this.curdate = curdate;
        this.steps = steps;
    }

    public String getCurdate() {
        return curdate;
    }

    public void setCurdate(String curdate) {
        this.curdate = curdate;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}
