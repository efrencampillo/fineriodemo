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
                String auth = FinerioApp.getInstance().getTokenType() + " " + FinerioApp.getInstance().getToken();
                request = request.newBuilder()
                        // as i have not the mobile user Agent
                        .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36")
                        .addHeader("Content-Type", "application/json;charset=UTF-8")
                        .addHeader("Accept","application/json;charset=UTF-8")
                        .addHeader("Authorization", auth)
                        .addHeader("Accept-Encoding", "gzip, deflate, br ")
                        .addHeader("Accept-Language", "es-ES,es;q=0.9")
                        .build();
            }

            return chain.proceed(request);
        });

        return new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //this can be un-harcoded by configurations
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