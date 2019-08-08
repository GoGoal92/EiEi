package market.goldandgo.videonew1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Go Goal on 7/4/2017.
 */

public class Biged_series extends AppCompatActivity {

    EditText ed;
    TextView done;
    String text, pos="-1";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biged);
        ed = (EditText) findViewById(R.id.ed);
        done = (TextView) findViewById(R.id.upload);


        ed.requestFocus();
        ed.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(ed, InputMethodManager.SHOW_IMPLICIT);
            }
        }, 1000);


        text = getIntent().getExtras().getString("text");

        ImageView close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        if (!text.equals("0")) {
            pos =  getIntent().getExtras().getString("position");
            ed.setText(text);

        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed.getText().length() < 1) {
                    Toast.makeText(getApplicationContext(), "Need To Add Somewords", Toast.LENGTH_SHORT).show();
                } else {

                    InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(ed.getWindowToken(), 0);

                    finish();
                    if (!text.equals("0")) {

                        Series_Detail_recycler.Editcomment(ed.getText().toString(),pos);
                    }else{
                        Series_Detail_recycler.sendcomment(ed.getText().toString());
                    }



                }

            }
        });


    }
}
