package root.arbeit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    class ReceptionGetBreeds implements Callback<Answer> {
        @Override
        public void onResponse(Call<Answer> call, Response<Answer> response) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.OK), Toast.LENGTH_SHORT).show();
            Message message = response.body().getMessage();


            Field f[] = message.getClass().getDeclaredFields();
            int n = f.length - 2;
            for (int i = 0; i < n; i++)
                breeds.add(f[i].getName());

            ((ArrayAdapter<String>)listView.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<Answer> call, Throwable t) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.FAIL), Toast.LENGTH_SHORT).show();
            Log.d(TAG, t.getMessage());
        }
    }
}
