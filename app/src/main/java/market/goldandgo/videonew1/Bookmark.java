package market.goldandgo.videonew1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import market.goldandgo.videonew1.Adapter.Bookmarkadapter;
import market.goldandgo.videonew1.Adapter.Mymoviesalladapter;
import market.goldandgo.videonew1.Object.get;
import market.goldandgo.videonew1.Utils.Mydatabase;
import market.goldandgo.videonew1.Utils.bookmarkItemClickListener;
import market.goldandgo.videonew1.Utils.recycler_viewItemClickListener;

import java.util.ArrayList;

public class Bookmark extends AppCompatActivity implements bookmarkItemClickListener {

    Mydatabase mdb;
    TextView result;
    RecyclerView rv;
    LinearLayoutManager llm;
    AppCompatActivity ac;
    ArrayList<get> list;
    Bookmarkadapter adapter;
    LinearLayout background,titlebar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark);
        ac=this;
        mdb=new Mydatabase(getApplicationContext());
        Bitmap bitmap=MainActivity.getback();

        ImageView back = (ImageView) findViewById(R.id.close);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ac.finish();
            }
        });

        result= (TextView) findViewById(R.id.result);
        background= (LinearLayout) findViewById(R.id.backlayout);
        titlebar= (LinearLayout) findViewById(R.id.titlebar);
        titlebar.setBackgroundColor(MainActivity.getbackcolor());
        background.setBackground(new BitmapDrawable(ac.getResources(), bitmap));


        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        llm = new LinearLayoutManager(ac);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);

        list = new ArrayList<get>();
        list=mdb.getlist();
        if (list.size()>0){
            rv.setVisibility(View.VISIBLE);
            adapter = new Bookmarkadapter(ac, list,this);
            rv.setAdapter(adapter);
        }else{
            result.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            list = new ArrayList<get>();
            list=mdb.getlist();
            adapter.refresh(list);
            if (list.size()<1){
                result.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){

        }

    }

    public static final String EXTRA_ANIMAL_ITEM = "animal_image_url";
    public static final String EXTRA_ANIMAL_IMAGE_TRANSITION_NAME = "moviecover";

    @Override
    public void onRVlItemClick(int pos, String imgurl, ImageView shareImageView) {

        if (list.get(pos).getMid().contains("s")){
            String removie_s= list.get(pos).getMid().replace("s","");

            Intent intent = new Intent(ac, Series_Detail_recycler.class);
            intent.putExtra("name", list.get(pos).getTitle());
            intent.putExtra(EXTRA_ANIMAL_ITEM, imgurl);
            intent.putExtra("mid",removie_s);
            intent.putExtra("cate", list.get(pos).getCategory());
            intent.putExtra(EXTRA_ANIMAL_IMAGE_TRANSITION_NAME, ViewCompat.getTransitionName(shareImageView));

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    ac,
                    shareImageView,
                    ViewCompat.getTransitionName(shareImageView));

            startActivity(intent, options.toBundle());
        }else{

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
}
