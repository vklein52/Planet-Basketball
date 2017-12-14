package edu.illinois.finalproject;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by vijay on 12/13/2017.
 */

public class AlertDialogFactory {

    public static AlertDialog buildAlertDialog(String message, String title, DialogInterface.OnClickListener
            positiveListener, DialogInterface.OnClickListener negativeListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Yes", positiveListener);
        builder.setNegativeButton("No", negativeListener);

        return builder.create();
    }

    public static AlertDialog buildAlertDialog(String message, String title, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title);

        builder.setNeutralButton("Ok", null);

        return builder.create();
    }


}
