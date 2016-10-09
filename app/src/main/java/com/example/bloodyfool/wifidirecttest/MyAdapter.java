package com.example.bloodyfool.wifidirecttest;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by bloodyfool on 9-10-16.
 */

public class MyAdapter extends ArrayAdapter<List> {

    public MyAdapter(Context context, List values) {
        super(context, R.layout.row_layout, values);
    }

    @Override
    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater theInflator = LayoutInflater.from(getContext());

        View theView = theInflator.inflate(R.layout.row_layout, parent, false);

        String deviceName;

        try {
            deviceName = ((WifiP2pDevice) getItem(position)).deviceName;
        }catch (NullPointerException e) {
            deviceName = "FAIL";
        }

        TextView theTextView = (TextView) theView.findViewById(R.id.textView1);

        theTextView.setText(deviceName);

        return theView;

    }
}
