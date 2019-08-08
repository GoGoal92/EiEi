package market.goldandgo.videonew1.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import market.goldandgo.videonew1.R;

public class tem_fragment extends Fragment {

    public static tem_fragment newInstance() {

        Bundle args = new Bundle();

        tem_fragment fragment = new tem_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tem_fragment, container, false);

        return v;
    }
}
