package com.kosherbacon.mmcfe_ng;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * Created by Joshua Kahn on 6/21/2014.
 */
public class ManagementFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
}
