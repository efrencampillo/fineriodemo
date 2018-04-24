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
import retrofit2.http.Query;

public interface NetworkInterface {

    @POST("/api/login")
    Observable<LoginResponseModel> login(@Body LoginModel credentials);

    @GET("/api/me")
    Observable<Me> getMe();

    @GET("/api/users/{userid}/movements?includeCharges=true&includeDeposits=true")
    Observable<MovementResponseModel> getMovements(@Path("userid")String user,
                                                   @Query("start") String start,
                                                   @Query("end") String end,
                                                   @Query("timezone") String timezone);

    @GET("/api/users/{userid}/movements?includeCharges=true&includeDeposits=true")
    Observable<MovementResponseModel> getMovements(@Path("userid")String user,
                                                   @Query("offset") int start,
                                                   @Query("max") int block);
}
