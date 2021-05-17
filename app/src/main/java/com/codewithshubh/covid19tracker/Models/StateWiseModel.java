package com.codewithshubh.covid19tracker.Models;

public class StateWiseModel {
    private String state;
    private String confirmed;
    private String confirmed_new;
    private String active;
    private String death;
    private String death_new;
    private String recovered;
    private String recovered_new;
    private String lastupdate;

    public StateWiseModel(String state, String confirmed, String confirmed_new, String active,
                          String death, String death_new, String recovered, String recovered_new, String lastupdate) {
        this.state = state;
        this.confirmed = confirmed;
        this.confirmed_new = confirmed_new;
        this.active = active;
        this.death = death;
        this.death_new = death_new;
        this.recovered = recovered;
        this.recovered_new = recovered_new;
        this.lastupdate = lastupdate;
    }

    public String getState() {
        return state;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public String getConfirmed_new() {
        return confirmed_new;
    }

    public String getActive() {
        return active;
    }

    public String getDeath() {
        return death;
    }

    public String getDeath_new() {
        return death_new;
    }

    public String getRecovered() {
        return recovered;
    }

    public String getRecovered_new() {
        return recovered_new;
    }

    public String getLastupdate() {
        return lastupdate;
    }
}
