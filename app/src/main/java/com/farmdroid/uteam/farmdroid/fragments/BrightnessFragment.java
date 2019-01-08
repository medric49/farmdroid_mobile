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

public class BrightnessFragment  extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>  {

    public TextView lum_text = null;

    private TextView lum_max = null;
    private TextView lumiere_auto = null;

    public TextView state = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_brightness, container, false);


        lum_text = (TextView) view.findViewById(R.id.lum_text);


        lum_max = (TextView) view.findViewById(R.id.lum_max);
        lumiere_auto = (TextView) view.findViewById(R.id.lum_auto);

        state = (TextView) view.findViewById(R.id.state);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        lum_max.setText(Config.LUMIERE_MAX);
        lumiere_auto.setText(Function.auto( Integer.parseInt(Config.LUMIERE_AUTORISE),getContext()));

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
