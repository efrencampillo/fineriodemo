package mx.finerio.demo.core;

import android.app.Application;

import mx.finerio.demo.models.LoginResponseModel;

public class FinerioApp extends Application {

    private static FinerioApp mThis = null;
    private String userId;
    private String token;
    private String tokenType;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mThis = this;
        appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();
    }


    public static FinerioApp getInstance() {
        return mThis;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    //those method should be part of a session manager but it is not completed on this MVP;
    public boolean isTheUserLoged() {
        return token != null;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setToken(LoginResponseModel newToken) {
        token = newToken.access_token;
        tokenType = newToken.token_type;
    }

    public void setUserId(String newId) {
        userId = newId;
    }

    public String getUserId() {
        return userId;
    }


    public void logout() {
        token = null;
        tokenType = null;
        userId = null;
    }
}
