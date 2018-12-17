package com.farmdroid.uteam.farmdroid.fragments;

import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.farmdroid.uteam.farmdroid.MainActivity;
import com.farmdroid.uteam.farmdroid.R;

public class MainFragment  extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>  {


    public TextView temp_text = null;
    public TextView hum_text = null;
    public TextView aci_text = null;
    public TextView lum_text = null;
    public TextView security_text = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

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
            @Override
            public void onClick(View v) {

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
