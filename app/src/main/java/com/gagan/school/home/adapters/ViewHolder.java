package com.gagan.school.home.adapters;

/**
 * Created by Gagan S Patil on 6/10/19.
 */

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gagan.school.R;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.library.view.adapter.RvListViewHolder;

import butterknife.BindView;

/**
 * Created by Gagan S Patil on 9/7/19.
 */
public class ViewHolder extends RvListViewHolder<RvItems> {
    private final IActivityHelper mActivity;

    public ViewHolder(@NonNull View itemView, IActivityHelper activity) {
        super(itemView);
        this.mActivity=activity;
    }

    @Override
    public void setData(RvItems rvItems) {

    }
}
