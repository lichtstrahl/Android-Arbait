package root.arbeit;

import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.arbeit.ButtonClicker.ConnectButtonClick;
import root.arbeit.Network.Answer;
import root.arbeit.Network.Message;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private Button buttonConnect;
    private RecyclerView recyclerView;
    private List<Breed> breeds2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnect = findViewById(R.id.buttonConnect);
        new ConnectButtonClick(buttonConnect);

        recyclerView = findViewById(R.id.recycler);
        BreedAdapter adapter = new BreedAdapter(this, breeds2);
        recyclerView.setAdapter(adapter);

        // Запрос, чтобы получить список всех пород
        App.getServerAPI().listBreeds().enqueue(new ReceptionGetBreeds());
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
                    breeds2.add(new Breed(f[i].getName(), "__"));

                recyclerView.getAdapter().notifyDataSetChanged();
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
//            for (int i = 0; i < 5; i++)
//                Log.d(TAG, t.getStackTrace()[i].toString());
            // Добавить кнопку для повторного запроса
            MainActivity.this.buttonConnect.setVisibility(View.VISIBLE);
        }
    }

    private String[] getData() {
        int n = 10;
        String str[] = new String[n];
        for (int i = 0; i < n; i++)
            str[i] = "ITEM + " + i;
        return str;
    }
}
