package mx.finerio.demo.core;

import android.app.Application;

public class FinerioApp extends Application {

    private static FinerioApp mThis = null;
    private static String token;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mThis = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        //appComponent = DaggerAppC
    }


    public static FinerioApp getInstance() {
        return mThis;
    }

    //this method should be part of a session manager but it is not completed on this MVP;
    public boolean isTheUserLoged() {
        return token != null;
    }

    public String getToken() {
        return token;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
