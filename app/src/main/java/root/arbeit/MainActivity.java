package root.arbeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
// BITBUCKET!!!!

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.arbeit.ButtonClicker.ConnectButtonClick;
import root.arbeit.Network.Answer;
import root.arbeit.Network.Message;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private Button buttonConnect;
    private ListView listView;
    private ArrayList<String> breeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnect = findViewById(R.id.buttonConnect);
        new ConnectButtonClick(buttonConnect);

        listView = findViewById(R.id.list);
        breeds = new ArrayList<>();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, breeds));

        // Запрос, чтобы получить список всех пород
        App.getServerAPI().listBreeds().enqueue(new ReceptionGetBreeds());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DogActivity.class);
                intent.putExtra(getResources().getString(R.string.breed), breeds.get(position));
                startActivity(intent);
            }
        });
    }

    public class ReceptionGetBreeds implements Callback<Answer> {
        @Override
        public void onResponse(Call<Answer> call, Response<Answer> response) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.OK), Toast.LENGTH_SHORT).show();
            Message message = response.body().getMessage();
            String status = response.body().getStatus();
            if (status.equals(getResources().getString(R.string.SUCCESS))) {

                Field f[] = message.getClass().getDeclaredFields();
                int n = f.length - 2;
                for (int i = 0; i < n; i++)
                    breeds.add(f[i].getName());

                ((ArrayAdapter<String>) listView.getAdapter()).notifyDataSetChanged();
                MainActivity.this.buttonConnect.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
                MainActivity.this.buttonConnect.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFailure(Call<Answer> call, Throwable t) {
            ivToast.makeText(MainActivity.this, getResources().getString(R.string.FAIL), Toast.LENGTH_SHORT).show();
            Log.d(TAG, t.getMessage());
            // Добавить кнопку для повторного запроса
            MainActivity.this.buttonConnect.setVisibility(View.VISIBLE);
        }
    }
}
