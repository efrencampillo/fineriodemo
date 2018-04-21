package mx.finerio.demo.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
        disposables.add(mePresenter.getMe()
                .subscribe(me -> {

                }, error ->
                        Toast.makeText(HomeBankActivity.this, R.string.error_user_info, Toast.LENGTH_SHORT).show()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        disposables.add(movePresenter.getMoveMents()
                .subscribe(movements -> {

                }, error ->
                        Toast.makeText(HomeBankActivity.this, R.string.error_movements, Toast.LENGTH_SHORT).show()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        disposables.clear();
    }
}
