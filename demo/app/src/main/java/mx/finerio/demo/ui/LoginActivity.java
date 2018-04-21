package mx.finerio.demo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.reactivex.disposables.CompositeDisposable;
import mx.finerio.demo.R;
import mx.finerio.demo.core.FinerioApp;

public class LoginActivity extends AppCompatActivity {

    CompositeDisposable disposables = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FinerioApp.getInstance().isTheUserLoged()) {
            finish();
            Intent intent = new Intent(this, HomeBankActivity.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_login);
        disposables = new CompositeDisposable();
        FinerioApp.getInstance().getAppComponent().inject(this);
        setListeners();
    }

    private void setListeners() {
        // TODO set buttons and inputs
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposables != null) disposables.clear();
    }
}
