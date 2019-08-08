package market.goldandgo.videonew1.Utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import market.goldandgo.videonew1.Object.get;

import java.util.ArrayList;

/**
 * Created by Go Goal on 1/27/2018.
 */

public class Mydatabase extends SQLiteOpenHelper {

    SQLiteDatabase dbase;

    public Mydatabase(Context context) {
        super(context, "fmovie.db", null, 2);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE bookmark(mid CHAR, title CHAR,cate CHAR, love CHAR, vieww CHAR,talking CHAR,imgurl CHAR)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insertbookmark(String mid,String title,String cate,String love,String vieww,String talking,String imgurl) {
        dbase = getWritableDatabase();
        String sql = "INSERT INTO bookmark VALUES ('"+mid+"','" + title + "','" + cate + "','" + love + "','" + vieww + "','" + talking + "','" + imgurl + "')";
        dbase.execSQL(sql);
        dbase.close();
    }

    public ArrayList<get> getlist() {


        ArrayList<get> list =new ArrayList<get>();
        dbase = getReadableDatabase();
        String sel = "SELECT * FROM bookmark";
        Cursor c = dbase.rawQuery(sel, null);
        if (c.moveToFirst()) {
            do {

                get eg=new get();
                eg.setMid(c.getString(0));
                eg.setTitle(c.getString(1));
                eg.setCategory(c.getString(2));
                eg.setLike(c.getString(3));
                eg.setView(c.getString(4));
                eg.setCommentcount(c.getString(5));
                eg.setImage(c.getString(6));
                list.add(eg);

            } while (c.moveToNext());

        }
        dbase.close();

        return list;
    }


    public void deletebookmark(String mid) {
        dbase = getWritableDatabase();
        String sql = "DELETE FROM bookmark WHERE mid='"+mid+"'";
        dbase.execSQL(sql);
        dbase.close();
    }

    public Boolean rowexit(String mid) {
        Boolean b=false;
        dbase = getReadableDatabase();
        String sql = "SELECT * FROM  bookmark WHERE mid LIKE '"+mid+"'";
        Cursor c = dbase.rawQuery(sql, null);
        int count=c.getCount();
        if (count>0){
            b=true;
        }else{
            b=false;
        }

        return b;

    }


}
