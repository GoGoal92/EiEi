package market.goldandgo.videonew1.MyHttpclient;

import android.util.Log;

import market.goldandgo.videonew1.*;
import market.goldandgo.videonew1.Fragment.Fragment_Home;
import market.goldandgo.videonew1.Fragment.Fragment_menu;
import market.goldandgo.videonew1.Fragment.Fragment_movie;
import market.goldandgo.videonew1.Fragment.Fragment_series;
import market.goldandgo.videonew1.Object.Constant;
import market.goldandgo.videonew1.Object.phoneid;
import market.goldandgo.videonew1.Utils.Stringconverter;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.util.concurrent.TimeUnit;

/**
 * Created by Go Goal on 6/29/2017.
 */

public class MyRequest {


    public static void Mainpage() {


        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Mainpage(Constant.apikey);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Fragment_Home.Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_Home.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_Home.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void getsearchmovie(String search) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getsearchmovie(Constant.apikey, search);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Search.Feedback(response.body().toString());
                } catch (Exception e) {
                    Search.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Search.Feedback_Error();
                t.printStackTrace();
            }
        });


    }

    public static void checkversion(String hashKey) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Checkversion(Constant.apikey, phoneid.getid(), phoneid.model(),hashKey);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    String a=response.body().toString();
                    Log.e("checkversion",a);
                    Splash.Feedback(a);
                } catch (Exception e) {
                    Splash.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Splash.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void getcategory() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getcategory(Constant.apikey);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Fragment_movie.Cate_Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_movie.Cate_Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_movie.Cate_Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void getseeallMovie(String s, String cate, final String a) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseeallMovie(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_movie.Feedback(response.body().toString(),a);
                } catch (Exception e) {
                    if (a.equals("b")) {
                        Fragment_movie.Feedback_Error();
                    } else {
                        Fragment_movie.Feedback_ErrorSW();
                    }


                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (a.equals("b")) {
                    Fragment_movie.Feedback_Error();
                } else {
                    Fragment_movie.Feedback_ErrorSW();
                }
                t.printStackTrace();
            }
        });
    }

    public static void getmoremovies(String s, String cate) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseeallMovie(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_movie.Feedback(response.body().toString(), "n");
                } catch (Exception e) {

                    Fragment_movie.getmoremovies_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_movie.getmoremovies_Error();
                t.printStackTrace();
            }
        });
    }

    public static void getMoviedetail(String mid) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getMoviedetail(Constant.apikey,phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Detail_recycler.Feedback(response.body().toString());
                } catch (Exception e) {
                    Detail_recycler.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Detail_recycler.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void userlike(String mid) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.userlike(Constant.apikey,phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {



            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    public static void postcomment(String userid, String mid, String comment, String cid,String count) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.postcomment(Constant.apikey,userid,mid,comment,cid,count);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Detail_recycler.loadcomment_feedback(response.body().toString());
                } catch (Exception e) {
                    Detail_recycler.loadcomment_feedbackerror();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Detail_recycler.postcomment_feedbackerror();
                t.printStackTrace();
            }
        });
    }

    public static void loadcommmnet(String mid, String userid,String count) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Loadcomment(Constant.apikey,userid,mid,count);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Detail_recycler.loadcomment_feedback(response.body().toString());
                } catch (Exception e) {
                    Detail_recycler.loadcomment_feedbackerror();
                    e.printStackTrace();

                }




            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Detail_recycler.loadcomment_feedbackerror();
                t.printStackTrace();
            }
        });
    }

    public static void getcategory_series() {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getcategory_series(Constant.apikey);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Fragment_series.Cate_Feedback(response.body().toString());
                } catch (Exception e) {
                    Fragment_series.Cate_Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_series.Cate_Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void getseeallseries(String s, String cate, final String a) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.SeeallSeries(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_series.Feedback(response.body().toString(),a);
                } catch (Exception e) {
                    if (a.equals("b")) {
                        Fragment_series.Feedback_Error();
                    } else {
                        Fragment_series.Feedback_ErrorSW();
                    }


                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (a.equals("b")) {
                    Fragment_movie.Feedback_Error();
                } else {
                    Fragment_movie.Feedback_ErrorSW();
                }
                t.printStackTrace();
            }
        });
    }

    public static void getmoreseries(String s, String cate) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.SeeallSeries(Constant.apikey, s, cate);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    Fragment_series.Feedback(response.body().toString(), "n");
                } catch (Exception e) {

                    Fragment_series.getmoremovies_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Fragment_series.getmoremovies_Error();
                t.printStackTrace();
            }
        });
    }


    public static void updateuserfb(String fbname, String fbid) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.updateuserfb(Constant.apikey,phoneid.getid(), fbname, fbid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {

                    FacebookLogin.Feedback(response.body().toString());
                } catch (Exception e) {

                    FacebookLogin.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                FacebookLogin.Feedback_Error();
                t.printStackTrace();
            }
        });
    }

    public static void getseriesdetail(String mid) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseriesdetail(Constant.apikey,phoneid.getid(),mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Series_Detail_recycler.Feedback(response.body().toString());
                } catch (Exception e) {
                    Series_Detail_recycler.Feedback_Error();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Series_Detail_recycler.Feedback_Error();
                t.printStackTrace();
            }
        });

    }

    public static void postcomment_series(String userid, String mid, String comment, String cid,String count) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.postcomment(Constant.apikey,userid,mid,comment,cid,count);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    Series_Detail_recycler.loadcomment_feedback(response.body().toString());
                } catch (Exception e) {
                    Series_Detail_recycler.loadcomment_feedbackerror();
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Series_Detail_recycler.postcomment_feedbackerror();
                t.printStackTrace();
            }
        });
    }

    public static void loadcommmnet_series(String mid, String userid,String count) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.Loadcomment(Constant.apikey,userid,mid,count);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Series_Detail_recycler.loadcomment_feedback(response.body().toString());
                } catch (Exception e) {
                    Series_Detail_recycler.loadcomment_feedbackerror();
                    e.printStackTrace();

                }




            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Series_Detail_recycler.loadcomment_feedbackerror();
                t.printStackTrace();
            }
        });
    }


    public static void getseries_video(String mid) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.getseries_video(Constant.apikey,mid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Load_series_video.feedback(response.body().toString());
                } catch (Exception e) {
                    Load_series_video.feedbackerror();
                    e.printStackTrace();

                }




            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Load_series_video.feedbackerror();
                t.printStackTrace();
            }
        });
    }

    public static void gettuto_video(final String type) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).client(okHttpClient).addConverterFactory(new Stringconverter()).build();


        interface_api service = retrofit.create(interface_api.class);
        Call<String> result = service.gettuto_video(Constant.apikey,type);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Loading_ourvideo.feedback(response.body().toString(),type);
                } catch (Exception e) {
                    Loading_ourvideo.feedbackerror();
                    e.printStackTrace();

                }




            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Loading_ourvideo.feedbackerror();
                t.printStackTrace();
            }
        });
    }
}
