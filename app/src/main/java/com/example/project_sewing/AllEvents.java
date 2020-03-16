package com.example.project_sewing;

import android.os.Build;

import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllEvents {
    private Map<String, ArrayList<Event1>> data;

    public Map<String,ArrayList<Event1>> getData() {
        return data;
    }

    public AllEvents() {
         data = new HashMap<String, ArrayList<Event1>>();
    }

    public void setData(Map<String,ArrayList<Event1>> data) {

        this.data = data;
    }
    public void addToList(String mapKey,Event1 event){
        ArrayList<Event1> itemsList = data.get(mapKey);
        if(itemsList == null) {
            itemsList = new ArrayList<Event1>();
            itemsList.add(event);
            data.put(mapKey, itemsList);
        } else
            if(!itemsList.contains(event)) itemsList.add(event);
    }
    public AllEvents(Map<String,ArrayList<Event1>> map) {
        if(this.data == null)
            data = new HashMap<String,ArrayList<Event1>>();
        else
            this.data = map;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean isExist(Event1 e)
    {
        if(e!=null) {
            ArrayList<Event1> elist = data.get(e.getNumber());
            if (elist != null) {
                for (Event1 eitem : elist) {
                    if (eitem.equals(e))
                        return false;
                }
            }
        }
        return true;
    }


}
