package mx.finerio.demo.core;

import java.util.logging.Level;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mx.finerio.demo.presenters.MePresenter;
import mx.finerio.demo.presenters.MovementsPresenter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class AppModule {


    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(logging);

        client.interceptors().add(chain -> {
            Request request = chain.request();
            if (FinerioApp.getInstance().isTheUserLoged()) {
                request = request.newBuilder()
                        .addHeader("authorization",
                                FinerioApp.getInstance().getTokenType() + " " + FinerioApp.getInstance().getToken())
                        .build();
            }

            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //this can be unharcoded by configurations
                .baseUrl("https://api.finerio.mx")
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