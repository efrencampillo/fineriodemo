package mx.finerio.demo.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import mx.finerio.demo.R;
import mx.finerio.demo.core.FinerioApp;
import mx.finerio.demo.core.NetworkInterface;
import mx.finerio.demo.models.LoginModel;

public class LoginActivity extends AppCompatActivity {

    CompositeDisposable disposables = null;

    @Inject
    NetworkInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (FinerioApp.getInstance().isTheUserLoged()) {
            passLogin();
        }
        setContentView(R.layout.activity_login);
        disposables = new CompositeDisposable();
        FinerioApp.getInstance().getAppComponent().inject(this);
        setListeners();
    }

    private void setListeners() {
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);

        login.setOnClickListener(v -> {
            //todo block ui and showw loader
            LoginModel model = new LoginModel();
            model.username = username.getText().toString();
            model.password = password.getText().toString();
            disposables.add(apiInterface.login(model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(
                            loginResponseModel -> {
                                FinerioApp.getInstance().setToken(loginResponseModel);
                                passLogin();
                            }, error ->
                                    Toast.makeText(LoginActivity.this, R.string.error_login, Toast.LENGTH_SHORT).show()
                    ));
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposables != null) disposables.clear();
    }

    private void passLogin() {
        finish();
        Intent intent = new Intent(this, HomeBankActivity.class);
        startActivity(intent);
    }
}
