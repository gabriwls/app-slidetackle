package me.dio.soccernews.data;

import androidx.room.Room;

import me.dio.soccernews.App;
import me.dio.soccernews.data.local.StDatabase;
import me.dio.soccernews.data.remote.StApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StRepository {

    //region Constantes
    private static final String REMOTE_API_URL = "https://gabriwls.github.io/api-slidetackle/";
    private static final String LOCAL_DB_NAME = "slidetackle";
    //endregion

    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private StApi remoteApi;
    private StDatabase localDb;

    public StApi getRemoteApi() {
        return remoteApi;
    }

    public StDatabase getLocalDb() {
        return localDb;
    }
    //endregion

    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    private StRepository() {
        remoteApi = new Retrofit.Builder()
                .baseUrl(REMOTE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StApi.class);

        localDb = Room.databaseBuilder(App.getInstance(), StDatabase.class, LOCAL_DB_NAME).build();
    }

    public static StRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final StRepository INSTANCE = new StRepository();
    }
    //endregion
}
