package com.farmdroid.uteam.farmdroid.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.farmdroid.uteam.farmdroid.MainActivity;
import com.farmdroid.uteam.farmdroid.R;
import com.farmdroid.uteam.farmdroid.utilities.BluetoothData;
import com.farmdroid.uteam.farmdroid.utilities.Config;

public class MainFragment  extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>  {


    public TextView temp_text = null;
    public TextView hum_text = null;
    public TextView aci_text = null;
    public TextView lum_text = null;
    public TextView security_text = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.content_main, container, false);

        Button button_state = (Button) view.findViewById(R.id.button_state);
        Button config_state = (Button) view.findViewById(R.id.config_state);

        ImageButton button_light = (ImageButton) view.findViewById(R.id.button_light);
        ImageButton config_light = (ImageButton) view.findViewById(R.id.config_light);

        ImageButton button_security = (ImageButton) view.findViewById(R.id.button_security);
        ImageButton config_security = (ImageButton) view.findViewById(R.id.config_security);

        temp_text = (TextView) view.findViewById(R.id.temp_text);
        hum_text  = (TextView) view.findViewById(R.id.hum_text);
        aci_text  = (TextView) view.findViewById(R.id.aci_text);
        lum_text = (TextView) view.findViewById(R.id.lum_text);
        security_text = (TextView) view.findViewById(R.id.security_text);

        button_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)inflater.getContext()).viewPager.setCurrentItem(MainActivity.PAGE_1);
            }
        });

        config_state.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_param_state, null,false);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


                final EditText temp_max = (EditText) dialogView.findViewById(R.id.temp_max);
                final EditText hum_max = (EditText) dialogView.findViewById(R.id.hum_max);
                final Switch arrosage_auto = (Switch) dialogView.findViewById(R.id.arrosage_auto);
                final Switch arrosage_active = (Switch) dialogView.findViewById(R.id.arrosage_active);

                temp_max.setText(Config.TEMPERATURE_MAX);
                hum_max.setText(Config.HUMIDITE_MAX);
                arrosage_auto.setChecked( Integer.parseInt(Config.ARROSAGE_AUTORISE)==1);
                arrosage_active.setChecked( Integer.parseInt(Config.ARROSAGE_ACTIVE) == 1);

                arrosage_active.setEnabled(!arrosage_auto.isChecked());

                arrosage_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        arrosage_active.setEnabled(!arrosage_auto.isChecked());
                    }
                });


                builder.setView(dialogView)
                        .setTitle(R.string.content_main_setting)
                        .setIcon(R.drawable.round_favorite_white_24)
                        .setCancelable(true)
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try{
                                    double temp = Double.parseDouble(String.valueOf(temp_max.getText()));
                                    double hum = Double.parseDouble(String.valueOf(hum_max.getText()));
                                    int arrosageAuto = arrosage_auto.isChecked()?1:0;
                                    int arrosageActive = arrosage_active.isChecked()?1:0;

                                    if (temp>=0 && hum>=0) {
                                        Config.setTemperatureMax(temp+"",getContext());
                                        Config.setHumiditeMax(hum+"",getContext());
                                        Config.setArrosageAutorise(arrosageAuto+"",getContext());
                                        Config.setArrosageActive(arrosageActive+"",getContext());

                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_TEMPERATURE_MAX,temp+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_HUMIDITE_MAX,hum+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_AUTORISATION_ARROSAGE,arrosageAuto+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_ACTIVE_ARROSAGE,arrosageActive+""));

                                    }
                                    else
                                        Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();

                                }

                                catch (Exception e)  {

                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing
                            }
                        })
                        .show();

            }
        });


        button_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)inflater.getContext()).viewPager.setCurrentItem(MainActivity.PAGE_2);
            }
        });
        config_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        button_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)inflater.getContext()).viewPager.setCurrentItem(MainActivity.PAGE_3);
            }
        });
        config_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
