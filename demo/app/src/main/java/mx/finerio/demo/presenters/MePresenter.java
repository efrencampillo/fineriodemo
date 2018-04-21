package mx.finerio.demo.presenters;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import mx.finerio.demo.core.NetworkInterface;
import mx.finerio.demo.models.Me;

public class MePresenter {

    private NetworkInterface apiInterface;
    private Me mMe = null;

    public MePresenter(NetworkInterface networkInterface) {
        this.apiInterface = networkInterface;
    }


    public Observable<Me> getMe() {
        if (mMe != null) {
            return Observable.just(mMe).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
        return apiInterface.getMe().map(me -> {
            mMe = me;
            return me;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
