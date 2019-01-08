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
                                    Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();
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

                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_param_light,null,false);

                final EditText lum_max = (EditText) dialogView.findViewById(R.id.lum_max);
                final Switch lum_auto=  (Switch) dialogView.findViewById(R.id.lum_auto);
                final Switch lum_active = (Switch) dialogView.findViewById(R.id.lum_active);


                lum_max.setText(Config.LUMIERE_MAX);
                lum_auto.setChecked( Integer.parseInt(Config.LUMIERE_AUTORISE)==1);
                lum_active.setChecked( Integer.parseInt(Config.LUMIERE_ACTIVE) == 1);

                lum_active.setEnabled(!lum_auto.isChecked());

                lum_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        lum_active.setEnabled(!lum_auto.isChecked());
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(R.string.content_main_setting)
                        .setIcon(R.drawable.round_brightness_medium_white_24)
                        .setCancelable(true)
                        .setView(dialogView)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing
                            }
                        })
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try{
                                    double lum = Double.parseDouble(String.valueOf(lum_max.getText()));
                                    int lumAuto = lum_auto.isChecked()?1:0;
                                    int lumActive = lum_active.isChecked()?1:0;

                                    if (lum>=0) {
                                        Config.setLumiereMax(lum+"",getContext());
                                        Config.setLumiereAutorise(lumAuto+"",getContext());
                                        Config.setLumiereActive(lumActive+"",getContext());
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_LUMINOSITE_MAX,lum+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_AUTORISATION_LUMIERE,lumAuto+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_ACTIVE_LUMIERE,lumActive+""));
                                    }
                                    else
                                        Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();

                                }

                                catch (Exception e)  {
                                    Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .show();

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
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_param_security,null,false);

                final EditText distance_max = (EditText) dialogView.findViewById(R.id.distance_max);
                final Switch distance_auto=  (Switch) dialogView.findViewById(R.id.distance_auto);

                distance_max.setText(Config.DISTANCE_MAX);
                distance_auto.setChecked(Integer.parseInt(Config.DISTANCE_AUTORISE)==1?true:false);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(R.string.content_main_setting)
                        .setIcon(R.drawable.round_security_white_24)
                        .setView(dialogView)
                        .setCancelable(true)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Nothing
                            }
                        })
                        .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                try{
                                    double dist = Double.parseDouble(String.valueOf(distance_max.getText()));
                                    int distAuto = distance_auto.isChecked()?1:0;
                                    if (dist>=0) {
                                        Config.setDistanceMax(dist+"",getContext());
                                        Config.setDistanceAutorise(distAuto+"",getContext());

                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_DISTANCE_MAX,dist+""));
                                        ((MainActivity)getContext()).data.add(new Pair<Character, String>(BluetoothData.CODE_CONFIG_AUTORISATION_DISTANCE,distAuto+""));
                                    }
                                    else
                                        Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();

                                }

                                catch (Exception e)  {
                                    Toast.makeText(getContext(),R.string.incorrect_value, Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .show();

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
