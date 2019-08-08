package market.goldandgo.videonew1.Fragment;

/**
 * Created by Go Goal on 11/25/2016.
 */

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.wang.avi.AVLoadingIndicatorView;
import market.goldandgo.videonew1.Adapter.Cate_adapter;
import market.goldandgo.videonew1.Adapter.Mymoviesalladapter;
import market.goldandgo.videonew1.Detail;
import market.goldandgo.videonew1.Detail_recycler;
import market.goldandgo.videonew1.MyHttpclient.MyRequest;
import market.goldandgo.videonew1.Object.Jsonparser;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.R;
import market.goldandgo.videonew1.Utils.recycler_viewItemClickListener;

import java.util.ArrayList;


/**
 * Created by Go Goal on 11/20/2016.
 */

public class Fragment_movie extends Fragment implements recycler_viewItemClickListener {


    public static Fragment_movie newInstance() {

        Bundle args = new Bundle();
        Fragment_movie fragment = new Fragment_movie();
        fragment.setArguments(args);
        return fragment;
    }


    static FragmentActivity ac;
    static Cate_adapter ca_adapter;
    static RecyclerView rv;
    LinearLayoutManager llm;
    static Mymoviesalladapter adapter;
    static AVLoadingIndicatorView pg;
    static LinearLayout mainlayout;
    static RelativeLayout network;

    static SwipeRefreshLayout swipeLayout;
    static recycler_viewItemClickListener clickthis;





    @Override
    public void onDestroy() {
        super.onDestroy();
        cate = "0";
        reloadd = "b";
        shownet_footer = false;
    }

    String reloadd = "b";

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (clist.size() < 1) {
                Log.e("con", "1");

                MyRequest.getcategory();

            } else {
                Log.e("con", "2");
                MyRequest.getseeallMovie(count + "", cate, reloadd);
                ca_adapter.refresh(clist);
            }


        } catch (Exception e) {
            Log.e("con", "3");
            MyRequest.getcategory();
        }


        shownet_footer = false;
        reloadd = "a";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ac = getActivity();

        clickthis=this;
        ca_adapter = new Cate_adapter(ac, new ArrayList<get>());
        //MyRequest.getcategory();

    }


    static int count = 1;
    static String cate = "0";
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.movie, container, false);

        swipeLayout = (SwipeRefreshLayout) v.findViewById(R.id.refresh_layout);
        swipeLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyRequest.getseeallMovie(count + "", cate, "a");
            }
        });

        pg = (AVLoadingIndicatorView) v.findViewById(R.id.avi);
        mainlayout = (LinearLayout) v.findViewById(R.id.mainrlayout);
        network = (RelativeLayout) v.findViewById(R.id.networkerro);

        pg.show();

        Button reload = (Button) v.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                network.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
                MyRequest.getcategory();
            }
        });

     /*   cate_lv.setAdapter(ca_adapter);
        cate_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                for (int i = 0; i < clist.size(); i++) {
                    clist.get(i).setClick("0");
                }
                clist.get(position).setClick("1");
                ca_adapter.refresh(clist);
                cate = clist.get(position).getMid();
                MyRequest.getseeallMovie(count + "", cate, "b");
                rv.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();
            }
        });*/


        rv = (RecyclerView) v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        adapter = new Mymoviesalladapter(ac, list, this);
        rv.setAdapter(adapter);


        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            int ydy = 0;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!shownet_footer && !show_end_result) {
                    int visibleItemCount = llm.getChildCount();
                    int totalItemCount = llm.getItemCount();
                    int pastVisibleItems = llm.findFirstVisibleItemPosition();
                    if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                        count++;
                        adapter.load_pg(true);
                        MyRequest.getmoremovies(count + "", cate);
                    }
                }


            }
        });

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                CustomDialog();
                fab.setVisibility(View.GONE);
            }
        });


        return v;
    }

    public Dialog dialog;

    private void CustomDialog() {
        dialog = new Dialog(ac, R.style.full_screen_dialog);
        //   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.listview);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        dialog.setCanceledOnTouchOutside(true);
        ListView dialogview = (ListView) dialog.findViewById(R.id.lv);
        dialogview.setDividerHeight(0);
        dialogview.setAdapter(ca_adapter);
        dialog.show();


        dialogview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                fab.setVisibility(View.VISIBLE);
                cate = clist.get(position).getMid();
                MyRequest.getseeallMovie(count + "", cate, "cate");
                rv.setVisibility(View.GONE);
                pg.setVisibility(View.VISIBLE);
                pg.show();

            }
        });

        dialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onCancel(DialogInterface dialog) {
                fab.setVisibility(View.VISIBLE);
            }
        });

        FloatingActionButton lvfab = (FloatingActionButton) dialog.findViewById(R.id.fab);
        dialogview.setAnimation(AnimationUtils.loadAnimation(ac, R.anim.side_in));

        lvfab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                fab.setVisibility(View.VISIBLE);
            }
        });
    }


    public static void Cate_Feedback_Error() {
        swipeLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        adapter.load_pg(false);

    }


    static ArrayList<get> clist, list;

    public static void Cate_Feedback(String s) {
        clist = Jsonparser.getcatelist(s);
        ca_adapter.refresh(clist);

        MyRequest.getseeallMovie(count + "", cate, "b");
    }

    public static void Feedback(String s, String a) {
        show_end_result = false;
        shownet_footer = false;
        adapter.show_end_result(show_end_result);
        adapter.failload(shownet_footer);

        swipeLayout.setRefreshing(false);
        network.setVisibility(View.GONE);
        mainlayout.setVisibility(View.VISIBLE);
        pg.setVisibility(View.GONE);
        pg.hide();

        int currentsize = list.size();
        list = new ArrayList<>();
        list = Jsonparser.getMoviealllist(s);
        if (a.equals("cate")){
            adapter = new Mymoviesalladapter(ac, list, clickthis);
            rv.setAdapter(adapter);
        }else{
            adapter.refresh(list);
        }

        rv.setVisibility(View.VISIBLE);

        int total=Integer.parseInt(Jsonparser.getonestring(s,"mcount"));

        if (currentsize >= total) {
            show_end_result = true;
            adapter.show_end_result(show_end_result);
        }




    }



    public static void Feedback_Error() {
        swipeLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        pg.hide();
        rv.setVisibility(View.GONE);
        network.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        adapter.load_pg(false);
    }

    public static void Feedback_ErrorSW() {
        swipeLayout.setRefreshing(false);
    }

    public static void reload() {
        MyRequest.getmoremovies(count + "", cate);
    }

    static boolean shownet_footer = false;
    static boolean show_end_result = false;


    public static void getmoremovies_Error() {
        shownet_footer = true;
        //adapter.load_pg(false);
        adapter.failload(shownet_footer);
    }

    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "moviecover";

    @Override
    public void onRVlItemClick(int pos, String imgurl, ImageView shareImageView) {

        Intent intent = new Intent(ac, Detail_recycler.class);
        intent.putExtra("name", list.get(pos).getTitle());
        intent.putExtra(EXTRA_ANIMAL_ITEM, imgurl);
        intent.putExtra("mid", list.get(pos).getMid());
        intent.putExtra("cate", list.get(pos).getCategory());
        intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                ac,
                shareImageView,
                ViewCompat.getTransitionName(shareImageView));


        startActivity(intent, options.toBundle());
    }
}
