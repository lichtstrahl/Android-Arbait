package root.arbeit.ButtonClicker;

import android.content.Context;
import android.view.View;

public abstract class ButtonClick {
    protected View button;
    protected Context context;

    ButtonClick(View button) {
        this.button = button;
        this.context = button.getContext();
    }
}
