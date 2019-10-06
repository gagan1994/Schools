package com.gagan.school.home.adapters;

/**
 * Created by Gagan S Patil on 6/10/19.
 */

import android.view.View;


import com.gagan.school.R;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.adapter.OnClickRvItem;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.library.view.adapter.RvListAdapter;

import java.util.List;


/**
 * Created by Gagan S Patil on 9/7/19.
 */
public class RvTaskAdapter extends RvListAdapter<ViewHolder, RvItems> {

    public RvTaskAdapter(List<RvItems> items,
                         IActivityHelper context,
                         OnClickRvItem<RvItems> lister) {
        super(items, context,lister);
    }

    @Override
    protected void setData(ViewHolder holder, RvItems taskItem) {
        if(taskItem==null)return;
        holder.setData(taskItem);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return viewType;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatas.get(position)==null? R.layout.task_item_load:R.layout.task_item;
    }

    @Override
    protected ViewHolder getViewHolder(View listItem, int viewType) {
        return new ViewHolder(listItem, mContext);
    }


    public void update(List<RvItems> newCases) {
        this.mDatas.clear();
        this.mDatas.addAll(newCases);
        notifyDataSetChanged();
    }
}
