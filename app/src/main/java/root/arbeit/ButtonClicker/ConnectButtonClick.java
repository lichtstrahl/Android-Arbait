package root.arbeit.ButtonClicker;

import android.view.View;

import root.arbeit.App;
import root.arbeit.MainActivity;

public class ConnectButtonClick extends ButtonClick {
    public ConnectButtonClick(View button) {
        super(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getServerAPI().listBreeds().enqueue(((MainActivity)context).new ReceptionGetBreeds());
            }
        });
    }
}
