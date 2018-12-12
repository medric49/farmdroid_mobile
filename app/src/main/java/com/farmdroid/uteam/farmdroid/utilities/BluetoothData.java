package com.farmdroid.uteam.farmdroid.utilities;


import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public abstract class BluetoothData {
    public static final char CODE_SEND_TEMPERATURE_VAL = 'a';
    public static final char CODE_SEND_HUMIDITE_VAL = 'b';
    public static final char CODE_SEND_ACIDITE_VAL = 'c';
    public static final char CODE_SEND_LUMINOSITE_VAL = 'd';
    public static final char CODE_SEND_DISTANCE_VAL = 'e';


    public static final char CODE_CONFIG_AUTORISATION_ARROSAGE = 'f';
    public static final char CODE_CONFIG_AUTORISATION_LUMIERE = 'l';
    public static final char CODE_CONFIG_AUTORISATION_DISTANCE = 'm';

    public static final char CODE_CONFIG_ACTIVE_ARROSAGE = 'n';
    public static final char CODE_CONFIG_ACTIVE_LUMIERE = 'o';

    public static final char CODE_CONFIG_TEMPERATURE_MAX = 'g';
    public static final char CODE_CONFIG_HUMIDITE_MAX = 'h';
    public static final char CODE_CONFIG_ACIDITE_MAX = 'i';
    public static final char CODE_CONFIG_LUMINOSITE_MAX = 'j';
    public static final char CODE_CONFIG_DISTANCE_MAX = 'k';

    public static final char CODE_SEND_ARROSAGE_FLAG = 'p';
    public static final char CODE_SEND_LUMIERE_FLAG = 'q';
    public static final char CODE_SEND_ACIDITE_FLAG = 'r';
    public static final char CODE_SEND_DISTANCE_FLAG = 's';

    public static final char[] CODE = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s'};

    public static boolean isDataStarter(char c) {
        boolean result = false;
        
        for (char i : CODE) {
            if (i == c)
                return true;
        }
        return result;
    }

    public static double getBluetoothData(InputStream mmInStream) {
        double result  = -1;

        byte[] buffer = new byte[1];
        String stringResult= "";
        while (true) {
            try {
                mmInStream.read(buffer);
                char c = (char) buffer[0];

                if (c != '@')
                    stringResult+= c;
                else
                    break;

                buffer = new byte[1];
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            result = Double.parseDouble(stringResult);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void treatData(char c,double value) {
        /*
        Log.d("**** Resultat ****","-"+c+"-  "+value);
        */
    }
}
