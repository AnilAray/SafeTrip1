package taxiapp.safetrip.dialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;


public class WarningDialog extends DialogFragment {
    private String title;
    private String message;
    private Context context;

    public WarningDialog() {
    }

    @SuppressLint("ValidFragment")
    public WarningDialog(String title, String message, Context context) {
        this.context=context;
        this.title=title;
        this.message=message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                            }
                        });
        return builder.create();
    }
}
