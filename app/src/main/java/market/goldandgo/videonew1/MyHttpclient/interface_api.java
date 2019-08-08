package market.goldandgo.videonew1.MyHttpclient;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Go Goal on 6/29/2017.
 */

public interface interface_api {


    @FormUrlEncoded
    @POST("v8/Checkversion830")
    Call<String> Checkversion(@Field("api") String name, @Field("pid") String pid, @Field("pmodel") String pmodel, @Field("hashKey") String hashKey);


    @FormUrlEncoded
    @POST("v8/mainpage")
    Call<String> Mainpage(@Field("api") String name);


    @FormUrlEncoded
    @POST("v8/getsearchmovie")
    Call<String> getsearchmovie(@Field("api") String name, @Field("text") String text);


    @FormUrlEncoded
    @POST("v8/getcategory")
    Call<String> getcategory(@Field("api") String name);


    @FormUrlEncoded
    @POST("v8/getseeallMovie")
    Call<String> getseeallMovie(@Field("api") String name, @Field("count") String count, @Field("cate") String cate);


    @FormUrlEncoded
    @POST("v8/SeeallSeries")
    Call<String> SeeallSeries(@Field("api") String name, @Field("count") String count, @Field("cate") String cate);


    @FormUrlEncoded
    @POST("v8/getMoviedetail")
    Call<String> getMoviedetail(@Field("api") String name, @Field("pid") String pid, @Field("mid") String mid);

    @FormUrlEncoded
    @POST("v8/getseriesdetail")
    Call<String> getseriesdetail(@Field("api") String name, @Field("pid") String pid, @Field("mid") String mid);

    @FormUrlEncoded
    @POST("v8/userlike")
    Call<String> userlike(@Field("api") String name, @Field("pid") String pid, @Field("mid") String mid);

    @FormUrlEncoded
    @POST("v8/postcomment")
    Call<String> postcomment(@Field("api") String name, @Field("userid") String userid, @Field("mid") String mid, @Field("comment") String comment, @Field("cid") String cid, @Field("count") String count);

    @FormUrlEncoded
    @POST("v8/Loadcomment")
    Call<String> Loadcomment(@Field("api") String name, @Field("userid") String userid, @Field("mid") String mid, @Field("count") String count);

    @FormUrlEncoded
    @POST("v8/getcategory_series")
    Call<String> getcategory_series(@Field("api") String name);

    @FormUrlEncoded
    @POST("v8/updateuserfb")
    Call<String> updateuserfb(@Field("api") String name, @Field("pid") String pid, @Field("fbname") String fbname, @Field("fbid") String fbid);


    @FormUrlEncoded
    @POST("v8/getseries_video")
    Call<String> getseries_video(@Field("api") String apikey, @Field("mid") String mid);


    @FormUrlEncoded
    @POST("v8/gettuto_video")
    Call<String> gettuto_video(@Field("api") String apikey, @Field("type") String type);





    @FormUrlEncoded
    @POST("main.php")
    Call<String> requesthdlink(@Field("vid") String name);


    @FormUrlEncoded
    @POST("v4/getreadmore")
    Call<String> getreadmore(@Field("api") String name, @Field("id") String cc);








    @FormUrlEncoded
    @POST("v4/getseeallMovie_cate")
    Call<String> getseeallMovie_cate(@Field("api") String name, @Field("count") String count, @Field("cid") String cid);


    @FormUrlEncoded
    @POST("v4/getnoti")
    Call<String> getnoti(@Field("api") String name, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("v4/getnotidetail")
    Call<String> getnotidetail(@Field("api") String name, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("v4/checkallnoti")
    Call<String> checkallnoti(@Field("api") String name, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("v4/getMyallmovies")
    Call<String> getMyallmovies(@Field("api") String name, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("v4/Editmovies")
    Call<String> Editmovies(@Field("api") String name, @Field("title") String title, @Field("detail") String detail, @Field("link") String link, @Field("cate") String cate, @Field("mid") String mid);


    @FormUrlEncoded
    @POST("v6/getsearchmovie")
    Call<String> getsearchmovie(@Field("api") String name, @Field("count") String count, @Field("text") String text, @Field("base64") String base, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("v4/userreprt")
    Call<String> userreprt(@Field("api") String name, @Field("pid") String pid, @Field("mid") String mid);

    @FormUrlEncoded
    @POST("v4/faosdonoaphin2")
    Call<String> faosdonoaphin(@Field("User-Agent") String name, @Field("userid") String api);

    @FormUrlEncoded
    @POST("v4/getuserinfo")
    Call<String> getuserinfo(@Field("api") String name, @Field("pid") String api);


    @FormUrlEncoded
    @POST("v4/buyseries")
    Call<String> buyseries(@Field("api") String name, @Field("pid") String api, @Field("mid") String mid);


    @FormUrlEncoded
    @POST("v4/coin")
    Call<String> coin(@Field("User-Agent") String name, @Field("userid") String api, @Field("userchoose") String userchoose, @Field("userbet") String userbet);



}
