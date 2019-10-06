package com.gagan.school.library.view.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.ButterKnife;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public abstract class RvListViewHolder <T extends RvItems>
        extends RecyclerView.ViewHolder {
    public RvListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public abstract void setData(T rvItems);
}
