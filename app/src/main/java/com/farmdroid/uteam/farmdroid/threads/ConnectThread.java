package com.farmdroid.uteam.farmdroid.threads;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.farmdroid.uteam.farmdroid.MainActivity;

import java.io.IOException;
import java.util.UUID;

public class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    private final Context context;
    private final BluetoothAdapter mBluetoothAdapter;
    public ConnectThread(BluetoothAdapter mBluetoothAdapter, BluetoothDevice device, Context context) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        this.mBluetoothAdapter = mBluetoothAdapter;
        this.mmDevice = device;
        this.context = context;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            Log.d("*** BLUETOOTH","UUID is valid");
        } catch (IOException e) {
            Log.e(" **** BLUETOOTH", "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
            Log.d("*** BLUETOOTH","Success !!!");

        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();


            } catch (IOException closeException) {
                Log.e(" **** BLUETOOTH", "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        manageMyConnectedSocket();
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(" **** BLUETOOTH", "Could not close the client socket", e);
        }
    }

    public void manageMyConnectedSocket() {
        ((MainActivity)context).treatBluetoothCommunication(mmSocket);
    }
}
