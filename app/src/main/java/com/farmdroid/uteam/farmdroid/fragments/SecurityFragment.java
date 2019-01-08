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
import android.widget.ImageView;
import android.widget.TextView;

import com.farmdroid.uteam.farmdroid.R;
import com.farmdroid.uteam.farmdroid.utilities.Config;
import com.farmdroid.uteam.farmdroid.utilities.Function;

public class SecurityFragment  extends Fragment implements android.app.LoaderManager.LoaderCallbacks<Cursor>  {

    public ImageView security_text = null;

    private TextView dist_max = null;
    private TextView dist_auto = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_security, container, false);

        security_text = (ImageView) view.findViewById(R.id.security_text);

        dist_max = (TextView) view.findViewById(R.id.distance_max);
        dist_auto = (TextView) view.findViewById(R.id.distance_auto);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        dist_max.setText(Config.DISTANCE_MAX);
        dist_auto.setText(Function.auto( Integer.parseInt(Config.DISTANCE_AUTORISE),getContext()));
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
