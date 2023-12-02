package UTD.cs.edu.pocket_poker;


import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utd.cs.edu.pokect_poker.R;

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


    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;

    static final int MESSAGE_READ = 1;

    ServerClass serverClass;

    ClientClass clientClass;

    Sendrecieve sendrecieve;

    private boolean isPlayersConnected = false;

    private int connectedPlayerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /* block is to check */

        StrictMode.ThreadPolicy policy;
        policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        /* Shafaq and Sidra */
        Button HostStartButton;
        HostStartButton = findViewById(R.id.HostStartButton);
        HostStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iPlayAgainNext;

                iPlayAgainNext = new Intent(HostActivity.this, TableActivity.class);
                startActivity(iPlayAgainNext);
            }
        });

        Button exitButton = findViewById(R.id.HostExitButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // This will exit the app
            }

        });


        initialWork();
        exqListener();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_READ:
                    byte[] readBuff = (byte[]) msg.obj;
                    int length = msg.arg1;

                    // Trim the array to the actual length of data
                    byte[] trimmedData = Arrays.copyOf(readBuff, length);

                    // Deserialize the GameState
                    GameState receivedState = deserializeGameState(trimmedData);

                    // Handle the received GameState as needed
                    // For example, update the UI or game state

                    break;
            }
            return true;
        }
    });

    // Method to deserialize bytes into a GameState object
    private GameState deserializeGameState(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInput in = new ObjectInputStream(bis)) {
            return (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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


        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        connectionStatus.setText("Discover Mode Started");
                    }

                    @Override
                    public void onFailure(int i) {
                        connectionStatus.setText("Discover Mode Failed");
                        Log.e(TAG, "Discovery failed with error code: " + i);

                    }
                });
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final WifiP2pDevice device = deviceArray[i];
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = device.deviceAddress;

                mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplicationContext(), "Connected to" + device.deviceName, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i) {
                        Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = writeMsg.getText().toString();
                sendrecieve.write(msg.getBytes());

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

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peerList) {
            if (!peerList.getDeviceList().equals(peers)) {
                peers.clear();
                peers.addAll(peerList.getDeviceList());

                deviceNameArray = new String[peerList.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peerList.getDeviceList().size()];
                int index = 0;

                for (WifiP2pDevice device : peerList.getDeviceList()) {
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                listView.setAdapter(adapter);
            }
            if (peers.size() == 0) {
                Toast.makeText(getApplicationContext(), "No Device Found", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    };


    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {

            final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;

            if (wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner) {
                connectionStatus.setText("Host");
                serverClass = new ServerClass();
                serverClass.start();

                //connectedPlayerCount++;
            } else if (wifiP2pInfo.groupFormed) {
                connectionStatus.setText("Client");
                clientClass = new ClientClass(groupOwnerAddress);
                clientClass.start();

                //connectedPlayerCount++;
            }

            if (connectedPlayerCount == 0) {
                // If both players are connected, call onPlayerConnected
                onPlayerConnected();
            }
        }
    };

    public void onPlayerConnected() {
        // Update the connection status
        isPlayersConnected = true;

        // Check if both players are now connected
        if (isPlayersConnected) {
            // If both players are connected, initiate the transition to TableActivity
            startTableActivity();
        }
    }

    // Method to start TableActivity
    private void startTableActivity() {
        // Create an Intent to start the TableActivity
        Intent intent = new Intent(HostActivity.this, TableActivity.class);


        // Start the TableActivity
        startActivity(intent);


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


    public class ServerClass extends Thread {
        Socket socket;
        ServerSocket serverSocket;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                sendrecieve = new Sendrecieve(socket);
                sendrecieve.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void write(byte[] serializedState) {
            sendrecieve.write(serializedState);
        }


        // Method to send the game state
        public void sendGameState(GameState gameState) {
            byte[] serializedState = serializeGameState(gameState);
            write(serializedState);
        }

        // Method to serialize the game state object into bytes
        private byte[] serializeGameState(GameState gameState) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bos)) {
                out.writeObject(gameState);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Method to deserialize bytes into a GameState object
        private GameState deserializeGameState(byte[] data) {

            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 ObjectInput in = new ObjectInputStream(bis)) {
                return (GameState) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Sendrecieve extends Thread {

        //these were quick fixes, originally did not have "final" on them
        private final Socket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        // Method to send the game state
        public void sendGameState(GameState gameState) {
            byte[] serializedState = serializeGameState(gameState);
            write(serializedState);
        }

        // Method to serialize the game state object into bytes
        private byte[] serializeGameState(GameState gameState) {
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream out = new ObjectOutputStream(bos)) {
                out.writeObject(gameState);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Method to deserialize bytes into a GameState object
        private GameState deserializeGameState(byte[] data, int length) {
            byte[] trimmedData = Arrays.copyOf(data, length);
            return deserializeGameState(trimmedData);
        }

        private GameState deserializeGameState(byte[] data) {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
                 ObjectInput in = new ObjectInputStream(bis)) {
                return (GameState) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }


        public Sendrecieve(Socket skt) {
            socket = skt;
            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (socket != null) {
                try {
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        handler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void write(byte[] bytes) {
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public class ClientClass extends Thread {
        Socket socket;
        String hostAdd;


        public ClientClass(InetAddress hostAddress) {
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();

        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);

                sendrecieve = new Sendrecieve(socket);
                sendrecieve.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


