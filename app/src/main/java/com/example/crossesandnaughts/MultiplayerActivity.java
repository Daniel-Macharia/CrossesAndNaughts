package com.example.crossesandnaughts;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MultiplayerActivity extends AppCompatActivity {

    private Button scan;
    private ListView deviceList;
    private static final int REQUEST_ENABLE_BT = 444;
    private static final int REQUEST_DISCOVERABLE_BT = 445;

    private UUID uniqueId = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");

    ArrayList<String> deviceNames = new ArrayList<>();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            try {
                if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                    Toast.makeText(getApplicationContext(), "Some new device has been found!", Toast.LENGTH_SHORT).show();

                    BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                            ActivityCompat.requestPermissions(MultiplayerActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    }

                    // if (btDevice.getBondState() != BluetoothDevice.BOND_BONDED)
                    if( btDevice != null )
                    {
                        deviceNames.add(new String(btDevice.getName() + " " + btDevice.getAddress()));
                        listAdapter.notifyDataSetChanged();
                    }
                }

                Toast.makeText(getApplicationContext(), "No action found in receiver!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
            }

        }
    };

    ArrayAdapter<String> listAdapter;

    public static final BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_activity);

        //turnBt = findViewById(R.id.turn_bt);
        deviceList = findViewById(R.id.device_list);
        scan = findViewById( R.id.scan_device );

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvailableBluetoothDevices();
            }
        });

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        listAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.device_layout, deviceNames);

        deviceList.setAdapter(listAdapter);

        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connectToServerDevice(deviceNames.get(position));
            }
        });

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        startMultiplayer();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK)
                getPairedDevices();
        }

        if (requestCode == REQUEST_DISCOVERABLE_BT) {
            boolean isDiscoverable = resultCode > 0;
             if (isDiscoverable) {
            //     listenForIncomingBluetoothConnections();
                //getAvailableBluetoothDevices();
             }

        }
    }

    private void startMultiplayer() {
        openBluetooth();
        Toast.makeText(getApplicationContext(), "Starting multiplayer!", Toast.LENGTH_SHORT).show();
    }

    private void openBluetooth() {
        try {
            if (btAdapter == null) {
                Toast.makeText(getApplicationContext(), "Bluetooth not supported!", Toast.LENGTH_SHORT).show();
                return;
            }

            //if bt is supported
            if (!btAdapter.isEnabled()) {
                Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    Toast.makeText(getApplicationContext(), "Starting BT", Toast.LENGTH_SHORT).show();
                }

                startActivityForResult(enableBTIntent, REQUEST_ENABLE_BT);
                Toast.makeText(getApplicationContext(), "Starting Bluetooth", Toast.LENGTH_SHORT).show();
            } else {
                getPairedDevices();
                Toast.makeText(getApplicationContext(), "BT is on", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void makeDeviceDiscoverable() {
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MultiplayerActivity.this, new String[]{Manifest.permission.BLUETOOTH_ADVERTISE}, 1);
            }

            Intent discoverableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableBTIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(discoverableBTIntent, REQUEST_DISCOVERABLE_BT);
            Toast.makeText(getApplicationContext(), "Making device discoverable", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void listenForIncomingBluetoothConnections() {
        try {
            UUID uuid = uniqueId;
            String serverName = "crosses_and_naughts_server";

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                } else {
                    Toast.makeText(getApplicationContext(), "Permission to connect bluetooth denied!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }

            try {
                final BluetoothServerSocket btServer = btAdapter.listenUsingRfcommWithServiceRecord(serverName, uuid);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            BluetoothSocket socket = btServer.accept();

                        } catch (IOException ioe) {
                            Toast.makeText(getApplicationContext(), "Error: Failed to create socket!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                thread.start();
            } catch (IOException ioe) {
                Toast.makeText(getApplicationContext(), "Error: Failed to create server!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void getAvailableBluetoothDevices() {
        try {
            //makeDeviceDiscoverable();
            Toast.makeText(getApplicationContext(), "Listing available devices...", Toast.LENGTH_SHORT).show();

            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_SCAN}, 1);
            }

            if (btAdapter.isDiscovering()) {
                btAdapter.cancelDiscovery();
            }

            btAdapter.startDiscovery();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);

            Toast.makeText(getApplicationContext(), "Getting available devices...", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void getPairedDevices() {
        try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions( this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                    Toast.makeText(getApplicationContext(), "requesting permission to connect BT", Toast.LENGTH_SHORT).show();
                }

            Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();

            if( deviceNames != null )
            {
                deviceNames.clear();
            }

            if( pairedDevices != null )
            {
                for( BluetoothDevice bt : pairedDevices )
                {
                    deviceNames.add( bt.getName() + " \t" + bt.getAddress() );
                }
                listAdapter.notifyDataSetChanged();
            }
            makeDeviceDiscoverable();
            getAvailableBluetoothDevices();

            Toast.makeText(getApplicationContext(), "Permission to connect BT allowed", Toast.LENGTH_SHORT).show();

        }catch( Exception e )
        {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void connectToServerDevice(String name) {
        try {
            Toast.makeText(getApplicationContext(), "Connecting to " + name, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

}

class ConnectBt  {
    private BluetoothSocket socket;
    private UUID id;
    private Context context;
    private Handler handler;
    private String name;
    private BluetoothAdapter adapter;

    public ConnectBt(Context context, UUID id) {
        try {
            this.context = context;
            this.handler = new Handler(Looper.getMainLooper());
            adapter = BluetoothAdapter.getDefaultAdapter();

            this.id = id;
            name = "TicTacToeBTServer";
        } catch (Exception e) {
            toast("Error: " + e);
        }
    }

    private void toast(String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Context getAppContext() {
        return this.context;
    }

    public void startThread() {
        try {

        } catch (Exception e) {
            toast("Error: " + e);
        }
    }

    public void createServerSocket() {
        try {
            
            Thread create = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        if (ActivityCompat.checkSelfPermission(getAppContext(), Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED)
                        {
                            final BluetoothServerSocket btServerSock = adapter.listenUsingRfcommWithServiceRecord(name, id);
                            if( btServerSock == null )
                            {
                                toast("Bluetooth server socket is null!");
                                return;
                            }

                            socket = btServerSock.accept();

                            toast("accepted connection!");
                            if(socket != null && socket.isConnected())
                            {
                                toast("Connected to bluetooth!");
                            }

                        }
                    }catch( Exception e )
                    {
                        toast("Error: " + e);
                    }
                }
            });

            create.start();

        } catch (Exception e) {
            toast("Error: " + e);
        }
    }

    public void connectToServer()
    {
        try
        {

        }catch( Exception e )
        {
            toast("Error: " + e);
        }
    }
}
/*
class BtReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive( Context context, Intent intent)
    {
        String action = intent.getAction();
        try {
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                Toast.makeText(getApplicationContext(), "Some new device has been found!", Toast.LENGTH_SHORT).show();

                BluetoothDevice btDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                        ActivityCompat.requestPermissions(MultiplayerActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 1);
                }

                // if (btDevice.getBondState() != BluetoothDevice.BOND_BONDED)
                if( btDevice != null )
                {
                    deviceNames.add(new String(btDevice.getName() + " " + btDevice.getAddress()));
                    listAdapter.notifyDataSetChanged();
                }
            }

            Toast.makeText(getApplicationContext(), "No action found in receiver!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
*/