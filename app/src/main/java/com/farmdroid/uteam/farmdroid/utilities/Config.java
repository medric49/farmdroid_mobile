package com.farmdroid.uteam.farmdroid.utilities;

import android.content.Context;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by root on 4/7/18.
 */

public abstract class Config {
    private static final String FILE_NAME = "config.xml";

    private static final String TAG_LUMIERE_AUTORISE = "lumiere-autorise";
    private static final String TAG_LUMIERE_ACTIVE = "lumiere-active";
    private static final String TAG_LUMIERE_MAX =  "lumiere_max";
    private static final String TAG_FLAG_LUMIERE =  "flag-lumiere";

    private static final String TAG_TEMPERATURE_MAX = "temperature-max";
    private static final String TAG_HUMIDITE_MAX = "humidite-max";
    private static final String TAG_ARROSAGE_AUTORISE =  "arrosage-autorise";
    private static final String TAG_ARROSAGE_ACTIVE =  "arrosage-active";
    private static final String TAG_FLAG_ARROSAGE =  "flag-active";

    private static final String TAG_ACIDITE_MAX = "acidite-max";
    private static final String TAG_FLAG_ACIDITE = "flag-acidite";

    private static final String TAG_DISTANCE_MAX = "distance-max";
    private static final String TAG_DISTANCE_AUTORISE = "distance-autorise";
    private static final String TAG_FLAG_DISTANCE = "flag-distance";

    private static final String TAG_NAME_ROOT = "root";


    private static File file = new File(FILE_NAME);
    private static SAXBuilder sax=new SAXBuilder();
    private static Document document;
    private static XMLOutputter xmlOutput=new XMLOutputter();

    private static InputStream in;

    public static String LUMIERE_AUTORISE;
    public static String LUMIERE_ACTIVE;
    public static String LUMIERE_MAX;
    public static String FLAG_LUMIERE;

    public static String TEMPERATURE_MAX;
    public static String HUMIDITE_MAX;
    public static String ARROSAGE_AUTORISE;
    public static String ARROSAGE_ACTIVE;
    public static String FLAG_ARROSAGE;

    public static String ACIDITE_MAX;
    public static String FLAG_ACIDITE;

    public static String DISTANCE_MAX;
    public static String DISTANCE_AUTORISE;
    public static String FLAG_DISTANCE;



    public static void initialize (Context context) {
        try {
            in = context.openFileInput(FILE_NAME);

            document = sax.build(in);

        } catch (FileNotFoundException e) {
            try {
                FileOutputStream out = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                out.write(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").getBytes());
                out.write(
                        (
                                "<"+TAG_NAME_ROOT+">" +

                                        "<"+TAG_LUMIERE_AUTORISE+">1</"+TAG_LUMIERE_AUTORISE+">" +
                                        "<"+TAG_LUMIERE_ACTIVE+">0</"+TAG_LUMIERE_ACTIVE+">" +
                                        "<"+TAG_LUMIERE_MAX+">30</"+TAG_LUMIERE_MAX+">" +
                                        "<"+TAG_FLAG_LUMIERE+">0</"+TAG_FLAG_LUMIERE+">" +

                                        "<"+TAG_TEMPERATURE_MAX+">30</"+TAG_TEMPERATURE_MAX+">" +
                                        "<"+TAG_HUMIDITE_MAX+">20</"+TAG_HUMIDITE_MAX+">" +
                                        "<"+TAG_ARROSAGE_AUTORISE+">1</"+TAG_ARROSAGE_AUTORISE+">" +
                                        "<"+TAG_ARROSAGE_ACTIVE+">0</"+TAG_ARROSAGE_ACTIVE+">" +
                                        "<"+TAG_FLAG_ARROSAGE+">0</"+TAG_FLAG_ARROSAGE+">" +

                                        "<"+TAG_ACIDITE_MAX+">7</"+TAG_ACIDITE_MAX+">" +
                                        "<"+TAG_FLAG_ACIDITE+">0</"+TAG_FLAG_ACIDITE+">" +

                                        "<"+TAG_DISTANCE_MAX+">1</"+TAG_DISTANCE_MAX+">" +
                                        "<"+TAG_DISTANCE_AUTORISE+">1</"+TAG_DISTANCE_AUTORISE+">" +
                                        "<"+TAG_FLAG_DISTANCE+">0</"+TAG_FLAG_DISTANCE+">" +

                                "</"+TAG_NAME_ROOT+">"

                        ).getBytes());
                out.close();
                in = context.openFileInput(FILE_NAME);
                document = sax.build(in);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JDOMException e1) {
                e1.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }

        LUMIERE_AUTORISE= document.getRootElement().getChild(TAG_LUMIERE_AUTORISE).getText();
        LUMIERE_ACTIVE=  document.getRootElement().getChild(TAG_LUMIERE_ACTIVE).getText();
        LUMIERE_MAX= document.getRootElement().getChild(TAG_LUMIERE_MAX).getText();
        FLAG_LUMIERE= document.getRootElement().getChild(TAG_FLAG_LUMIERE).getText();

        TEMPERATURE_MAX= document.getRootElement().getChild(TAG_TEMPERATURE_MAX).getText();
        HUMIDITE_MAX= document.getRootElement().getChild(TAG_HUMIDITE_MAX).getText();
        ARROSAGE_AUTORISE= document.getRootElement().getChild(TAG_ARROSAGE_AUTORISE).getText();
        ARROSAGE_ACTIVE= document.getRootElement().getChild(TAG_ARROSAGE_ACTIVE).getText();
        FLAG_ARROSAGE= document.getRootElement().getChild(TAG_FLAG_ARROSAGE).getText();

        ACIDITE_MAX= document.getRootElement().getChild(TAG_ACIDITE_MAX).getText();
        FLAG_ACIDITE= document.getRootElement().getChild(TAG_FLAG_ACIDITE).getText();

        DISTANCE_MAX= document.getRootElement().getChild(TAG_DISTANCE_MAX).getText();
        DISTANCE_AUTORISE= document.getRootElement().getChild(TAG_DISTANCE_AUTORISE).getText();
        FLAG_DISTANCE= document.getRootElement().getChild(TAG_FLAG_DISTANCE).getText();


    }
    
    public static void setLumiereAutorise(String value, Context context) {
        LUMIERE_AUTORISE = value;
        document.getRootElement().getChild(TAG_LUMIERE_AUTORISE).setText(value);
        setOutput(context);
    }
    public static void setLumiereActive(String value, Context context) {
        LUMIERE_ACTIVE = value;
        document.getRootElement().getChild(TAG_LUMIERE_ACTIVE).setText(value);
        setOutput(context);
    }
    public static void setLumiereMax(String value, Context context) {
        LUMIERE_MAX = value;
        document.getRootElement().getChild(TAG_LUMIERE_MAX).setText(value);
        setOutput(context);
    }
    public static void setFlagLumiere(String value, Context context) {
        FLAG_LUMIERE = value;
        document.getRootElement().getChild(TAG_FLAG_LUMIERE).setText(value);
        setOutput(context);
    }
    public static void setTemperatureMax(String value, Context context) {
        TEMPERATURE_MAX = value;
        document.getRootElement().getChild(TAG_TEMPERATURE_MAX).setText(value);
        setOutput(context);
    }
    public static void setHumiditeMax(String value, Context context) {
        HUMIDITE_MAX = value;
        document.getRootElement().getChild(TAG_HUMIDITE_MAX).setText(value);
        setOutput(context);
    }
    public static void setArrosageAutorise(String value, Context context) {
        ARROSAGE_AUTORISE = value;
        document.getRootElement().getChild(TAG_ARROSAGE_AUTORISE).setText(value);
        setOutput(context);
    }
    public static void setArrosageActive(String value, Context context) {
        ARROSAGE_ACTIVE = value;
        document.getRootElement().getChild(TAG_ARROSAGE_ACTIVE).setText(value);
        setOutput(context);
    }
    public static void setFlagArrosage(String value, Context context) {
        FLAG_ARROSAGE = value;
        document.getRootElement().getChild(TAG_FLAG_ARROSAGE).setText(value);
        setOutput(context);
    }
    public static void setAciditeMax(String value, Context context) {
        ACIDITE_MAX = value;
        document.getRootElement().getChild(TAG_ACIDITE_MAX).setText(value);
        setOutput(context);
    }
    public static void setFlagAcidite(String value, Context context) {
        FLAG_ACIDITE = value;
        document.getRootElement().getChild(TAG_FLAG_ACIDITE).setText(value);
        setOutput(context);
    }
    public static void setDistanceMax(String value, Context context) {
        DISTANCE_MAX = value;
        document.getRootElement().getChild(TAG_DISTANCE_MAX).setText(value);
        setOutput(context);
    }

    public static void setDistanceAutorise(String value, Context context) {
        DISTANCE_AUTORISE = value;
        document.getRootElement().getChild(TAG_DISTANCE_AUTORISE).setText(value);
        setOutput(context);
    }

    public static void setFlagDistance(String value, Context context) {
        FLAG_DISTANCE = value;
        document.getRootElement().getChild(TAG_FLAG_DISTANCE).setText(value);
        setOutput(context);
    }

    private static void setOutput(Context context)
    {
        try {
            xmlOutput.output(document, context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
