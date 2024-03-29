package market.goldandgo.videonew1.Utils;

/**
 * Created by Go Goal on 6/24/2016.
 */


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import market.goldandgo.videonew1.Object.get;

import java.util.ArrayList;
import java.util.List;


public abstract class HFRecyclerView<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private List<T> data;

    private boolean mWithHeader;
    private boolean mWithFooter;

    public HFRecyclerView(List<T> data, boolean withHeader, boolean withFooter) {
        this.data = data;
        mWithHeader = withHeader;
        mWithFooter = withFooter;
    }

    //region Get View
    protected abstract RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent);
    //endregion

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_ITEM) {
            return getItemView(inflater, parent,viewType);
        } else if (viewType == TYPE_HEADER) {
            return getHeaderView(inflater, parent);
        } else if (viewType == TYPE_FOOTER) {
            return getFooterView(inflater, parent);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemCount() {
        int itemCount = data.size();
        if (mWithHeader)
            itemCount++;
        if (mWithFooter)
            itemCount++;
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    protected T getItem(int position) {
        return mWithHeader ? data.get(position - 1) : data.get(position);
    }


}