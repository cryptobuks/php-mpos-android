package com.kosherbacon.mmcfe_ng;

import java.io.Serializable;
import java.util.ArrayList;

public class PreferencesData extends ArrayList<ServerEntry> implements Serializable{

    static final long serialVersionUID = -5731443267247103512L;

    public PreferencesData(){
        super();
    }
}
