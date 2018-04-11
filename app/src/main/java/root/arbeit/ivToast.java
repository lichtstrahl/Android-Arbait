package root.arbeit;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

public class ivToast extends Toast {
    ivToast(Context context) {
        super(context);
    }


    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast t = Toast.makeText(context, text, duration);
        TextView v = t.getView().findViewById(android.R.id.message);
        v.setGravity(Gravity.CENTER);
        return t;
    }
}
