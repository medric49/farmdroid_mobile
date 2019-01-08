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
import android.widget.TextView;

import com.farmdroid.uteam.farmdroid.R;
import com.farmdroid.uteam.farmdroid.utilities.Config;
import com.farmdroid.uteam.farmdroid.utilities.Function;

public class StateFragment extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>  {

    public TextView temp_text = null;
    public TextView hum_text = null;
    public TextView aci_text = null;

    private TextView temp_max = null;
    private TextView hum_max = null;
    private TextView arrosage_auto = null;

    public TextView state = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_state, container, false);

        temp_text = (TextView) view.findViewById(R.id.temp_text);
        hum_text  = (TextView) view.findViewById(R.id.hum_text);
        aci_text  = (TextView) view.findViewById(R.id.aci_text);

        temp_max = (TextView) view.findViewById(R.id.temp_max);
        hum_max = (TextView) view.findViewById(R.id.hum_max);
        arrosage_auto = (TextView) view.findViewById(R.id.arrosage_auto);


        state = (TextView) view.findViewById(R.id.state);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        temp_max.setText(Config.TEMPERATURE_MAX);
        hum_max.setText(Config.HUMIDITE_MAX);
        arrosage_auto.setText(Function.auto( Integer.parseInt(Config.ARROSAGE_AUTORISE),getContext()));

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
