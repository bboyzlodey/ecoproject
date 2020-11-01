package skarlat.dev.ecoproject.customView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.DialogCompat;

public class StatisticDialog extends Dialog implements DialogInterface.OnClickListener {
    public StatisticDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(R.);
    }

    public StatisticDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected StatisticDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

}
