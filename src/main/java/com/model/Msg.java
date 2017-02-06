package com.model;

public class Msg {
    private String name;
    private String msg;

    public Msg(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public Msg() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "name='" + name + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
