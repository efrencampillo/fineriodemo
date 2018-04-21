package mx.finerio.demo.core;

import android.content.Context;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mx.finerio.demo.presenters.MePresenter;
import mx.finerio.demo.presenters.MovementsPresenter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class AppModule {

    Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(chain -> {
            Request request = chain.request();
            if (FinerioApp.getInstance().isTheUserLoged()) {
                request = request.newBuilder()
                        .addHeader("authorization", "Bearer " + FinerioApp.getInstance().getToken())
                        .build();
            }
            Response response = chain.proceed(request);

            return response;
        });

        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                //this can be unharcoded by configurations
                .baseUrl("https://apip.finerio.mx")
                .client(client.build())
                .build();
    }


    @Provides
    @Singleton
    NetworkInterface provideNetworkInterface(Retrofit retrofit) {
        return retrofit.create(NetworkInterface.class);
    }

    @Provides
    @Singleton
    MePresenter provideMePresenter(NetworkInterface apiInterface) {
        return new MePresenter(apiInterface);
    }

    @Provides
    @Singleton
    MovementsPresenter provideMovementsPresenter(NetworkInterface apiInterface) {
        return new MovementsPresenter(apiInterface);
    }

}