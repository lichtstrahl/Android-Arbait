package root.arbeit.ButtonClicker;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.arbeit.App;
import root.arbeit.Network.DogAnswer;
import root.arbeit.R;

import static android.content.ContentValues.TAG;

public class NewImageButtonClicker extends ButtonClick {
    private String breed;
    private ImageView imageView;

    public NewImageButtonClicker(View view, final String breed, final ImageView imageView) {
        super(view);
        this.breed = breed;
        this.imageView = imageView;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();
            }
        });
    }

    // Обработка запроса на новую картинку
    class ReceptionNewImage implements Callback<DogAnswer> {
        @Override
        public void onResponse(Call<DogAnswer> call, Response<DogAnswer> response) {
            String status = response.body().getStatus();
            if (status.equals(context.getResources().getString(R.string.SUCCESS))) {

                Glide
                        .with(context)
                        .load(response.body().getMessage())
                        .into(imageView);
            }
            else
                Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(Call<DogAnswer> call, Throwable t) {
            Toast.makeText(context, context.getResources().getString(R.string.FAIL), Toast.LENGTH_SHORT).show();
            Log.d(TAG, t.getMessage());
        }
    }

    public void request() {
        App.getServerAPI().randomImage(breed).enqueue(new ReceptionNewImage());
    }
}
