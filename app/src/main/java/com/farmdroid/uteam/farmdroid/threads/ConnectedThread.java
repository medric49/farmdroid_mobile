package com.farmdroid.uteam.farmdroid.threads;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.farmdroid.uteam.farmdroid.MainActivity;
import com.farmdroid.uteam.farmdroid.utilities.BluetoothData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectedThread extends  Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final Context context;

    public ConnectedThread(BluetoothSocket socket,Context context) {
        this.context = context;
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1];  // buffer store for the stream

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                mmInStream.read(buffer);

                char c = (char)buffer[0];

                if (BluetoothData.isDataStarter(c)) {
                    double data = BluetoothData.getBluetoothData(mmInStream);
                    // Send the obtained bytes to the UI activity
                    BluetoothData.treatData(c,data,context);
                }
                buffer = new byte[1];

                while ( !((MainActivity)context).data.isEmpty() ) {

                    Pair<Character,String> d = ((MainActivity)context).data.get(0);
                    String string = ":"+d.first+d.second+"_";
                    Log.d("***** sent value : ",string+"  : "+ string.length());
                    ((MainActivity)context).data.remove(0);
                    try {
                        write(string.getBytes());
                        Thread.sleep(20);
                        write(string.getBytes());
                        Thread.sleep(20);
                        write(string.getBytes());
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                break;
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }

}
