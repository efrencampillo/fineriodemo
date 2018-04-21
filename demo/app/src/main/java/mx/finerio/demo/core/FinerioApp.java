package mx.finerio.demo.core;

import android.app.Application;

public class FinerioApp extends Application {

    private static FinerioApp mThis = null;
    private static String token;

    @Override
    public void onCreate() {
        super.onCreate();
        mThis = this;
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
}
