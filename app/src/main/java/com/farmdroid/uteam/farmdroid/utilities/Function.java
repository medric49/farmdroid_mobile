package com.farmdroid.uteam.farmdroid.utilities;

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
}
