package com.example.bloodyfool.wifidirecttest;

import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Network network;
    Button sendbutton;
    Button scanbutton;
    //WifiP2pManager mManager;
    //WifiP2pManager.Channel mChannel;
    //BroadcastReceiver mReceiver;
    IntentFilter mIntentFilter;
    List peers = new ArrayList();
    ListAdapter theAdapter;
    ListView theListView;
    TextView infoip, msg;
    TextView response;
    EditText message;
    String ip = null;
    int port = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        //mChannel = mManager.initialize(this, getMainLooper(), null);
        //mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        network = new Network(this);

        //server = ((WiFiDirectBroadcastReceiver) mReceiver).getServer();
        infoip = (TextView) findViewById(R.id.infoip);
        msg = (TextView) findViewById(R.id.msg);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        scanbutton = (Button) findViewById(R.id.scan);
        sendbutton = (Button) findViewById(R.id.send);
        response = (TextView) findViewById(R.id.response);
        message = (EditText) findViewById(R.id.message);

        theAdapter = new MyAdapter(this, peers);

        theListView = (ListView) findViewById(R.id.deviceList);

        theListView.setAdapter(theAdapter);

        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String picked = "You selected " + String.valueOf(((WifiP2pDevice)parent.getItemAtPosition(position)).deviceName);
                //chipper(picked);
                WifiP2pDevice device = (WifiP2pDevice) parent.getItemAtPosition(position);
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;
                config.wps.setup = WpsInfo.PBC;
                network.connect(config);
                /**mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {

                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onFailure(int reason) {
                        chipper("Connect failed. Retry.");
                    }
                });*/
            }
        });

        scanbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                /**WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                if (wifi.getWifiState() == WifiManager.WIFI_STATE_DISABLED) {
                    wifi.setWifiEnabled(true);
                }**/
                network.enableWifi();

                /**mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        chipper("OnSuccess");
                    }

                    @Override
                    public void onFailure(int reasonCode) {
                        chipper("OnFailure");
                    }
                });**/
                network.discoverPeers();
            }
        });

        sendbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                network.send(1, message.getText().toString());
                /**Client client = new Client(ip, port, response, message.getText().toString());
                client.execute();**/
            }
        });

    }

    public void setDeviceList(List l) {
        peers.clear();
        peers.addAll(l);
        ((ArrayAdapter) theAdapter).notifyDataSetChanged();
    }

    public void chipper(String text) {
        Snackbar.make(findViewById(R.id.content_main), text, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ((WiFiDirectBroadcastReceiver) network.getReceiver()).getServer().onDestroy();
        } catch (NullPointerException e) {}
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(network.getReceiver(), mIntentFilter);
    }

    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(network.getReceiver());
    }
}
