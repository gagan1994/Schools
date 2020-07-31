package com.gagan.school.library.view.adapter;

/**
 * Created by Gagan S Patil on 6/10/19.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gagan.school.library.interfaces.IActivityHelper;

import java.util.List;

/**
 * Created by Gagan S Patil on 9/7/19.
 */
public abstract class RvListAdapter<T extends RvListViewHolder, S extends RvItems>
        extends RecyclerView.Adapter<T> {

    protected final List<S> mDatas;
    protected final IActivityHelper mContext;
    protected final OnClickRvItem<S> listner;

    public RvListAdapter(List<S> items, IActivityHelper context, OnClickRvItem<S> listner) {
        this.mDatas = items;
        this.listner = listner;
        this.mContext = context;
    }

    @Override
    public void onBindViewHolder(@NonNull T holder, int position) {
        if (listner != null) {
            holder.itemView.setOnClickListener(v ->
                    listner.onClickItem(mDatas.get(position)));
        }
        setData(holder, mDatas.get(position));
    }

    protected abstract void setData(T holder, S s);

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(getViewLayoutId(viewType), parent, false);
        return getViewHolder(listItem, viewType);
    }

    protected abstract int getViewLayoutId(int viewType);

    protected abstract T getViewHolder(View listItem, int viewType);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}