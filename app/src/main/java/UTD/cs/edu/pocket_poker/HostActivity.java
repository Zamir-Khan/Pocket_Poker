package UTD.cs.edu.pocket_poker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import UTD.cs.edu.pocket_poker.ui.main.HostFragment;

public class HostActivity extends AppCompatActivity {

    Button btnOnOff, btnDiscover, btnSend;
    ListView listView;
    TextView read_msg_box, connectionStatus;
    EditText writeMsg;


    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;

    BroadcastReceiver mReceiver;
    IntentFilter mIntentFiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        initialWork();
        exqListener();
    }



    /*
    This works only somewhat, does not work on phone emulator but does work on my mobile device
         I think because they changed/updated something after api 28 the .setWifiEnabled does not
         work properly. You also have to manually toggle to wifi on and off for this reason
         If wifi is on, then it will display "wifi is on"
         otherwise it will display "wifi is off" \
     */

    /*
    Technically this class is not really necessary but still wanted to try it out

     */
    private void exqListener() {
        btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    btnOnOff.setText("Wifi is ON");
                } else {
                    wifiManager.setWifiEnabled(true);
                    btnOnOff.setText("Wifi is OFF");
                }
            }
        });
    }


    private void initialWork() {
        btnOnOff = (Button) findViewById(R.id.onOff);
        btnDiscover = (Button) findViewById(R.id.discover);
        btnSend = (Button) findViewById(R.id.sendButton);
        listView = (ListView) findViewById(R.id.peerListView);
        read_msg_box = (TextView) findViewById(R.id.readMsg);
        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        writeMsg = (EditText) findViewById(R.id.writeMsg);


        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);

        mReceiver = new WifiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFiler = new IntentFilter();

        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFiler.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFiler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}





       /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, HostFragment.newInstance())
                    .commitNow();
        }

        */