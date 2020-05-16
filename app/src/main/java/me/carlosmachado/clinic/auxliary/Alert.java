package me.carlosmachado.clinic.auxliary;

import android.content.Context;
import android.widget.Toast;

public class Alert {

    public static void show(String message, Context c) {
        Toast.makeText(c, message, Toast.LENGTH_LONG).show();
    }

}
