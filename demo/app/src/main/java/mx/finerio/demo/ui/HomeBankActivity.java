package mx.finerio.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import mx.finerio.demo.R;
import mx.finerio.demo.core.FinerioApp;
import mx.finerio.demo.models.Me;
import mx.finerio.demo.models.Movement;
import mx.finerio.demo.presenters.MePresenter;
import mx.finerio.demo.presenters.MovementsPresenter;

public class HomeBankActivity extends AppCompatActivity {

    @Inject
    MePresenter mePresenter;

    @Inject
    MovementsPresenter movePresenter;

    int BATCH_MOVES = 10;

    private CompositeDisposable disposables;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;

    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bank);
        disposables = new CompositeDisposable();
        FinerioApp.getInstance().getAppComponent().inject(this);
        disposables.add(mePresenter.getMe()
                .subscribe(me -> {
                    FinerioApp.getInstance().setUserId(me.id);
                    requestMovements();
                    showMe(me);
                }, error ->
                        Toast.makeText(HomeBankActivity.this, R.string.error_user_info, Toast.LENGTH_SHORT).show()));
        setUIComponents();
    }

    private void setUIComponents() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    requestMovements();
                }
            }
        });
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home_bank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        FinerioApp.getInstance().logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestMovements();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disposables.clear();
        isLoading = false;
    }

    private void requestMovements() {
        if (FinerioApp.getInstance().getUserId() == null || isLoading) {
            return;
        }
        //TODO show loader
        isLoading = true;
        disposables.add(movePresenter.getMovements(adapter.getItemCount(), BATCH_MOVES)
                .subscribe(this::showMoves, error ->
                        Toast.makeText(HomeBankActivity.this, R.string.error_movements, Toast.LENGTH_SHORT).show()));
    }


    private void showMe(Me me) {
        TextView name = findViewById(R.id.name);
        name.setText(me.name);
        TextView id = findViewById(R.id.id);
        id.setText(me.id);
        TextView email = findViewById(R.id.email);
        email.setText(me.email);
    }

    private void showMoves(List<Movement> moves) {
        adapter.addItems(moves);
        isLoading = false;
        Toast.makeText(this, "Movimientos Cargados "+ adapter.getItemCount(), Toast.LENGTH_SHORT).show();
    }
}
