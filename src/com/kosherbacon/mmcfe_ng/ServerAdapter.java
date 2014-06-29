package com.kosherbacon.mmcfe_ng;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.IOException;
import java.util.ArrayList;

public class ServerAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<ServerItem> items;

    public ServerAdapter(Activity activity, ArrayList<ServerItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void swapItems(ArrayList<ServerItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = this.activity;
        if (this.items.size() == 1) {
            convertView = LayoutInflater.from(this.activity).inflate(R.layout.zero_servers, null);
            ((TextView) convertView.findViewById(R.id.no_servers)).setText("No Servers");
        }
        else if (!this.items.get(position).lastItem) {
            convertView = LayoutInflater.from(this.activity).inflate(R.layout.server_row, null);
            String[] url;
            if ((url = this.items.get(position).serverURL.split("index.php")).length > 1) {
                ((TextView) convertView.findViewById(R.id.urlAddressActual)).setText(url[0]);
            }
            else {
                ((TextView) convertView.findViewById(R.id.urlAddressActual)).setText(this.items.get(position).serverURL);
            }
            ((TextView) convertView.findViewById(R.id.apiKeyActual)).setText(this.items.get(position).apiKey);
            ((TextView) convertView.findViewById(R.id.userIDActual)).setText(this.items.get(position).userID);
            convertView.findViewById(R.id.server_row).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Switch main page to view server data
                    ((ViewFlipper) activity.findViewById(R.id.viewFlipper)).showNext();
                    // Close side menu
                    BaseActivity.sm.toggle();
                }
            });
        }
        else {
            convertView = LayoutInflater.from(this.activity).inflate(R.layout.clear_server_list, null);
            ((TextView) convertView.findViewById(R.id.clearServersTitle)).setText("Clear List");
            ((Button) convertView.findViewById(R.id.deleteAllServers)).setText("Delete");
            convertView.findViewById(R.id.deleteAllServers).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    try {
                                        Preferences.savePreferences(Preferences.toString(Preferences.servers), activity);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    ServerListFragment.serverItemArrayList.clear();
                                    ServerListFragment.serverItemArrayList.add(new ServerItem("", "", "", true));
                                    swapItems(ServerListFragment.serverItemArrayList);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                    Preferences.servers.clear();
                }
            });
        }
        return convertView;
    }
}
