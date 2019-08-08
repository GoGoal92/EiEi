package market.goldandgo.videonew1.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import market.goldandgo.videonew1.Fragment.*;


/**
 * Created by Go Goal on 11/25/2016.
 */

public class Fragmentadapter_Main extends FragmentStatePagerAdapter {


    public Fragmentadapter_Main(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return Fragment_movie.newInstance();
        } else if (position == 1) {
            return Fragment_series.newInstance();
        } else {
            return Fragment_menu.newInstance();
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "MOVIES";
        } else if (position == 1) {
            return "SERIES";
        } else {
            return "MENU";
        }


    }

}
