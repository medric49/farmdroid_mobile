package com.farmdroid.uteam.farmdroid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.farmdroid.uteam.farmdroid.fragments.BrightnessFragment;
import com.farmdroid.uteam.farmdroid.fragments.MainFragment;
import com.farmdroid.uteam.farmdroid.fragments.SecurityFragment;
import com.farmdroid.uteam.farmdroid.fragments.StateFragment;
import com.farmdroid.uteam.farmdroid.threads.ConnectThread;
import com.farmdroid.uteam.farmdroid.threads.ConnectedThread;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final int PAGE_0 = 0;
    public static final int PAGE_1 = 1;
    public static final int PAGE_2 = 2;
    public static final int PAGE_3 = 3;


    public static final int PAGE_NUMBRE = 4;

    private static final int ENABLE_REQUEST_BT = 1;

    private BluetoothAdapter mBluetoothAdapter;
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                /*
                Toast.makeText(context,deviceName,Toast.LENGTH_SHORT).show();
                */

                if (deviceHardwareAddress.equals("98:D3:21:F7:50:EB")) {
                    Log.d(" **** BLUETOOTH ","Module bluetooth discovered !!!");
                    (new ConnectThread(mBluetoothAdapter,device,context)).start();
                }

            }
        }
    };


    private ViewPager viewPager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionsPagerAdapter pagerAdapter = new ActionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(PAGE_0).setIcon(R.drawable.round_home_white_24);
        tabLayout.getTabAt(PAGE_1).setIcon(R.drawable.round_favorite_white_24);
        tabLayout.getTabAt(PAGE_2).setIcon(R.drawable.round_brightness_medium_white_24);
        tabLayout.getTabAt(PAGE_3).setIcon(R.drawable.round_security_white_24);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"Bluetooth is not detected !!!",Toast.LENGTH_LONG).show();
            System.exit(0);
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_REQUEST_BT);
        }
        else {
            startBluetoothActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ENABLE_REQUEST_BT:

                switch (resultCode) {
                    case RESULT_OK:
                        startBluetoothActivity();
                        break;
                    case RESULT_CANCELED:
                        System.exit(0);
                        break;
                }
                break;
        }
    }

    private void startBluetoothActivity() {
        Toast.makeText(this,"Bluetooth is activated !!!",Toast.LENGTH_LONG).show();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            String list = "";
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                list += deviceName+" ===> "+deviceHardwareAddress+"\n";
            }
        }

        mBluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

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

    public void treatBluetoothCommunication(BluetoothSocket socket) {
        (new ConnectedThread(socket,this)).start();
    }

    private Fragment chosenFragment(int index){
        switch (index)
        {
            case PAGE_0:
                MainFragment mainFragment = new MainFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putInt("_"+PAGE_0,index);
                mainFragment.setArguments(bundle1);
                return mainFragment;
            case PAGE_1:
                StateFragment stateFragment = new StateFragment();
                Bundle bundle0 = new Bundle();
                bundle0.putInt("_"+PAGE_1,index);
                stateFragment.setArguments(bundle0);
                return stateFragment;
            case PAGE_2:
                BrightnessFragment brightnessFragment = new BrightnessFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("_"+PAGE_2,index);
                brightnessFragment.setArguments(bundle2);
                return brightnessFragment;
            case PAGE_3:
                SecurityFragment securityFragment = new SecurityFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putInt("_"+PAGE_3,index);
                securityFragment.setArguments(bundle3);
                return securityFragment;
        }
        return null;
    }


    public class ActionsPagerAdapter extends FragmentPagerAdapter {

        public ActionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return chosenFragment(position);
        }

        @Override
        public int getCount() {
            return PAGE_NUMBRE;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case PAGE_0:
                    return getString(R.string.fragment_title_0);
                case PAGE_1:
                    return getString(R.string.fragment_title_1);
                case PAGE_2 :
                    return getString(R.string.fragment_title_2);
                case PAGE_3 :
                    return getString(R.string.fragment_title_3);
            }
            return super.getPageTitle(position);
        }

    }

}
