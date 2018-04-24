package mx.finerio.demo.presenters;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.finerio.demo.core.FinerioApp;
import mx.finerio.demo.core.NetworkInterface;
import mx.finerio.demo.models.Movement;

public class MovementsPresenter {

    private NetworkInterface apiInterface;

    public MovementsPresenter(NetworkInterface networkInterface) {
        apiInterface = networkInterface;
    }

    public Observable<List<Movement>> getMovements(int start, int max) {
        String token = FinerioApp.getInstance().getUserId();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        /// el API no esta preparado para decir cuantos hay en total
        return apiInterface.getMovements(token, start, max).map(info -> info.data)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
