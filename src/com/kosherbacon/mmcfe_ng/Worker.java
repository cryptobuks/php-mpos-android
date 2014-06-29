package com.kosherbacon.mmcfe_ng;


import java.io.Serializable;

public class Worker implements Serializable {

    private String name;
    private int hashrate;
    private boolean status;

    static final long serialVersionUID = 294537152359268666L;

    public Worker(String name, int hashrate, boolean status) {
        this.name = name;
        this.hashrate = hashrate;
        this.status = status;
    }

    public String getName(){
        return this.name;
    }

    public int getHashrate(){
        return this.hashrate;
    }

    public boolean getStatus(){
        return this.status;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setHashrate(int hashrate){
        this.hashrate = hashrate;
    }

    public void setStatus(boolean status){
        this.status = status;
    }
}
