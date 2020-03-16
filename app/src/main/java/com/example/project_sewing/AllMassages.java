package com.example.project_sewing;

import java.util.ArrayList;

public class AllMassages {
    private ArrayList<Massage> allMassages;

    public ArrayList<Massage> getAllMassages() {
        return allMassages;
    }
    public AllMassages(){
        allMassages = new ArrayList<>();
    }

    public AllMassages(ArrayList<Massage> allMassages) {
        this.allMassages = allMassages;
    }

    public void setAllMassages(ArrayList<Massage> allMassages) {
        this.allMassages = allMassages;
    }
}
