package com.kosherbacon.mmcfe_ng;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ServerListFragment extends ListFragment {

    ServerAdapter adapter;
    public static ArrayList<ServerItem> serverItemArrayList = Preferences.getServerItems();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        adapter = new ServerAdapter(getActivity(), serverItemArrayList);
        setListAdapter(adapter);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list, null);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        serverItemArrayList = Preferences.getServerItems();
        adapter.swapItems(serverItemArrayList);
    }
}