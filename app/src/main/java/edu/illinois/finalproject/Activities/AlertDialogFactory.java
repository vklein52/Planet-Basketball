package edu.illinois.finalproject.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by vijay on 12/13/2017.
 */

public class AlertDialogFactory {

    /**
     * Generic function to build an AlertDialog
     *
     * @param message          The message for the AlertDialog
     * @param title            The title for the AlertDialog
     * @param positiveListener The Listener containing the callback if the positive option is chosen
     * @param negativeListener The Listener containing the callback if the negative option is chosen
     * @param context          The context from which to create the AlertDialog
     * @return An AlertDialog with the above fields specified, ready to be shown with a .show() call
     */
    public static AlertDialog buildAlertDialog(String message, String title, DialogInterface.OnClickListener
            positiveListener, DialogInterface.OnClickListener negativeListener, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title);

        builder.setPositiveButton("Yes", positiveListener);
        builder.setNegativeButton("No", negativeListener);

        return builder.create();
    }

    /**
     * Builds a simple message AlertDialog
     *
     * @param message The message for the AlertDialog
     * @param title   The title for the AlertDialog
     * @param context The context from which to create the AlertDialog
     * @return An AlertDialog with the above fields specified, ready to be shown with a .show() call
     */
    public static AlertDialog buildAlertDialog(String message, String title, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(message)
                .setTitle(title);

        builder.setNeutralButton("Ok", null);

        return builder.create();
    }


}
