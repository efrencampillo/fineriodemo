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

    public Observable<List<Movement>> getMoveMents() {
        String token = FinerioApp.getInstance().getToken();
        String start = (new Date()).toString();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        String end = calendar.getTime().toString();
        String timeZone = calendar.getTimeZone().toString();
        return apiInterface.getMovements(token, start, end,timeZone).map(info -> info.data)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
