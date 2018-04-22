package root.arbeit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import root.arbeit.ButtonClicker.BreedListItemClick;
import root.arbeit.Network.DogAnswer;

import static android.content.ContentValues.TAG;

public class BreedAdapter extends RecyclerView.Adapter<BreedAdapter.BreedHolder> {

    private List<Breed> breed;
    private Context context;
    public BreedAdapter(Context context, List<Breed> breeds) {
        this.breed = breeds;
        this.context = context;
    }

    @NonNull
    @Override
    public BreedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new BreedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreedHolder holder, int position) {
        Breed br = breed.get(position);
        ReceptionNewImageAnswer answer = new ReceptionNewImageAnswer(holder.imageView);
        App.getServerAPI().randomImage(br.getName()).enqueue(answer);

        holder.imageView.setImageResource(R.drawable.ic_launcher_background);
        holder.nameView.setText(br.getName());

        new BreedListItemClick(holder.itemView, breed.get(position));
    }

    @Override
    public int getItemCount() {
        return breed.size();
    }

    // Представление одной породы
    public class BreedHolder extends RecyclerView.ViewHolder {
        final ImageView   imageView;
        final TextView    nameView;
        BreedHolder(View parent) {
            super(parent);
            imageView = parent.findViewById(R.id.breedImage);
            nameView = parent.findViewById(R.id.breedName);
        }
    }

    class ReceptionNewImageAnswer implements Callback<DogAnswer> {
        private ImageView imageView;

        public ReceptionNewImageAnswer(ImageView imageView) {
            this.imageView = imageView;
        }

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
}
