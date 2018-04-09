package root.arbeit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import root.arbeit.ButtonClicker.NewImageButtonClicker;

public class DogActivity extends AppCompatActivity {

    private TextView title;
    private Button buttonLoad;
    private ImageView image;
    private String breed;
    private String TAG = "DogActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        breed = getIntent().getStringExtra(getResources().getString(R.string.breed));
        title = findViewById(R.id.title);
        title.setText(breed);
        image = findViewById(R.id.image);

        buttonLoad = findViewById(R.id.buttonLoadRandom);
        new NewImageButtonClicker(buttonLoad, breed, image).request();  // Вызываем request() руками, для первоначальной загрузки
    }
}
