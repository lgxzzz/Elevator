package com.smart.elevator.bean;

import java.io.Serializable;
/***
 * 封装的任务对象
 *
 * */
public class Task implements Serializable {
    String LIFT_FORMID;
    String LIFT_ID;
    String LIFT_PROCESSOR;
    String LIFT_FAIULTTIME;
    String LIFT_SENDTIME;
    String LIFT_PROCESSORPHONE;
    String LIFT_CURRENTSTATE;
    String LIFT_FAULTTYPE;
    String FORM_STATE;
    String FORM_PERIOD;

    public String getFORM_PERIOD() {
        return FORM_PERIOD;
    }

    public void setFORM_PERIOD(String FORM_PERIOD) {
        this.FORM_PERIOD = FORM_PERIOD;
    }

    Elevator elevator;

    public String getFORM_STATE() {
        return FORM_STATE;
    }

    public void setFORM_STATE(String FORM_STATE) {
        this.FORM_STATE = FORM_STATE;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public String getLIFT_FORMID() {
        return LIFT_FORMID;
    }

    public void setLIFT_FORMID(String LIFT_FORMID) {
        this.LIFT_FORMID = LIFT_FORMID;
    }

    public String getLIFT_ID() {
        return LIFT_ID;
    }

    public void setLIFT_ID(String LIFT_ID) {
        this.LIFT_ID = LIFT_ID;
    }

    public String getLIFT_PROCESSOR() {
        return LIFT_PROCESSOR;
    }

    public void setLIFT_PROCESSOR(String LIFT_PROCESSOR) {
        this.LIFT_PROCESSOR = LIFT_PROCESSOR;
    }

    public String getLIFT_FAIULTTIME() {
        return LIFT_FAIULTTIME;
    }

    public void setLIFT_FAIULTTIME(String LIFT_FAIULTTIME) {
        this.LIFT_FAIULTTIME = LIFT_FAIULTTIME;
    }

    public String getLIFT_SENDTIME() {
        return LIFT_SENDTIME;
    }

    public void setLIFT_SENDTIME(String LIFT_SENDTIME) {
        this.LIFT_SENDTIME = LIFT_SENDTIME;
    }

    public String getLIFT_PROCESSORPHONE() {
        return LIFT_PROCESSORPHONE;
    }

    public void setLIFT_PROCESSORPHONE(String LIFT_PROCESSORPHONE) {
        this.LIFT_PROCESSORPHONE = LIFT_PROCESSORPHONE;
    }

    public String getLIFT_CURRENTSTATE() {
        return LIFT_CURRENTSTATE;
    }

    public void setLIFT_CURRENTSTATE(String LIFT_CURRENTSTATE) {
        this.LIFT_CURRENTSTATE = LIFT_CURRENTSTATE;
    }

    public String getLIFT_FAULTTYPE() {
        return LIFT_FAULTTYPE;
    }

    public void setLIFT_FAULTTYPE(String LIFT_FAULTTYPE) {
        this.LIFT_FAULTTYPE = LIFT_FAULTTYPE;
    }
}
