package mx.finerio.demo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import mx.finerio.demo.R;
import mx.finerio.demo.core.FinerioApp;
import mx.finerio.demo.presenters.MePresenter;
import mx.finerio.demo.presenters.MovementsPresenter;

public class HomeBankActivity extends AppCompatActivity {

    @Inject
    MePresenter mePresenter;

    @Inject
    MovementsPresenter movePresenter;

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bank);
        disposables = new CompositeDisposable();
        FinerioApp.getInstance().getAppComponent().inject(this);
        //TODO gegt ME
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO get movements
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }
}
