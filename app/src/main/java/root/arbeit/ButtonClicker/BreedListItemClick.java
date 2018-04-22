package root.arbeit.ButtonClicker;

import android.content.Intent;
import android.view.View;

import root.arbeit.Breed;
import root.arbeit.DogActivity;
import root.arbeit.R;

public class BreedListItemClick extends ButtonClick {
    private Breed breed;
    public  BreedListItemClick(View view, final Breed breed) {
        super(view);
        this.breed = breed;

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DogActivity.class);
                intent.putExtra(context.getResources().getString(R.string.breed), breed.getName());
                context.startActivity(intent);
            }
        });
    }
}
