package root.arbeit;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import root.arbeit.Network.DogServerAPI;

public class App extends Application {
    private static DogServerAPI serverAPI;

    @Override
    public void onCreate() {
        super.onCreate();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.baseURL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(DogServerAPI.class);
    }

    public static DogServerAPI  getServerAPI() {
        return serverAPI;
    }
}
