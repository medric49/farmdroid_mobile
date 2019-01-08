package com.farmdroid.uteam.farmdroid.utilities;

import android.content.Context;

import com.farmdroid.uteam.farmdroid.R;

public abstract class Function {


    public static String formatTemp(double temp) {

        return temp+" °T";
    }

    public static String formatHum(double hum) {
        return  hum+" %";
    }

    public static String formatAci(double acidite) {
        return "ph = "+acidite;
    }

    public static String formatLight(double light) {
        return light+" %";
    }

    public static String arrosageState(int state, Context context) {
        if (state ==1) {
            return context.getString(R.string.arrosage_state_1);
        }
        else
            return context.getString(R.string.arrosage_state_0);
    }

    public static String eclairageState(int state, Context context) {
        if (state==1) {
            return context.getString(R.string.eclairage_state_1);
        }
        else
            return context.getString(R.string.eclairage_state_0);
    }

    public static String securityState(int state, Context context) {
        if (state==1) {
            return context.getString(R.string.securite_state_1);
        }
        else
            return context.getString(R.string.securite_state_0);
    }

    public static String auto(int flag, Context context){
        if (flag == 1) {
            return context.getString(R.string.yes);
        }
        else
            return context.getString(R.string.no);
    }
}
