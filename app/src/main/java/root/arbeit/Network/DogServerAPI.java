package root.arbeit.Network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogServerAPI {
    @GET("/api/breeds/list/all")
    Call<Answer> listBreeds();

    @GET("/api/breed/{bredName}/images/random")
    Call<DogAnswer> randomImage(@Path("bredName") String bredName);
}
