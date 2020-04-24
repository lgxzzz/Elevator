package com.smart.elevator.bean;

public class Sign {
    int type;//维保任务 0，维修任务 1
    Task task;//任务内容
    int state;//签到状态  待签到 0，签到成功 1，签到失败 2 ，签到超时 3

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
