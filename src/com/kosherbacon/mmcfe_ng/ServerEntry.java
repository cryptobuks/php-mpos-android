package com.kosherbacon.mmcfe_ng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class ServerEntry implements Serializable {

    private String url, apiKey, userID;
    private ArrayList<Worker> workers;

    static final long serialVersionUID = -2107578148879413873L;

    public ServerEntry(String url, String apiKey, String userID, Worker... workers) {
        this.url = url;
        this.apiKey = apiKey;
        this.userID = userID;
        if (workers != null) {
            this.workers = new ArrayList<Worker>(Arrays.asList(workers));
        }
    }

    public String getUrl(){
        return this.url;
    }

    public String getApiKey(){
        return this.apiKey;
    }

    public String getUserID(){
        return this.userID;
    }

    public ArrayList<Worker> getWorkers() {
        return this.workers;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setWorkers(Worker... workers){
        this.workers.clear();
        this.workers = new ArrayList<Worker>(Arrays.asList(workers));
    }

    public void addWorker(Worker worker) {
        this.workers.add(worker);
    }

    public boolean removeWorker(String name) {
        // returns true of worker was removed, otherwise false
        for (int i = 0; i < workers.size(); i++) {
            if (workers.get(i).getName().equals(name)) {
                workers.remove(i);
                return true;
            }
        }
        return false;
    }
}
