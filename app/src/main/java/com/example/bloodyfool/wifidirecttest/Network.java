package com.example.bloodyfool.wifidirecttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.TextView;

/**
 * Created by bloodyfool on 12-10-16.
 */

public class Network {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private BroadcastReceiver mReceiver;
    private int ID;
    TextView game;

    public Network(MainActivity main) {
        //TODO
        mManager = (WifiP2pManager) main.getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(main, main.getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, main);

        //get broadcast receiver
    }

    public void discoverPeers() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                //TODO if fails
            }

            @Override
            public void onFailure(int reasonCode) {
                //TODO if succeeds
            }
        });

    }

    public void registerGame(int id, TextView v) {
        ID = id;
        game = v;
        //TODO send a startup message
    }

    public void unregisterGame() {
        ID = 0;
        game = null;
        //TODO send shutdown message
    }

    public String getConnectedDeviceName() {
        //TODO return connected device name
        return "";
    }

    public Boolean sendData(int id, String data) {
        //TODO send data
        return true;
    }
}
