package mx.finerio.demo.core;

import io.reactivex.Observable;
import mx.finerio.demo.models.LoginModel;
import mx.finerio.demo.models.LoginResponseModel;
import mx.finerio.demo.models.Me;
import mx.finerio.demo.models.MovementResponseModel;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkInterface {

    @POST("/api/login")
    Observable<LoginResponseModel> login(@Body LoginModel credentials);

    @GET("/api/me")
    Observable<Me> getMe();

    @GET("/api/users/{token}/movements?deep=true&includeCharges=true&includeDeposits=true"+
            "&startDate={start}&endDate={end}&tmz={timezone}")
    Observable<MovementResponseModel> getMovements(@Path("token")String token,
                                                   @Path("start") String start,
                                                   @Path("end") String end,
                                                   @Path("timezone") String timezone);
}
