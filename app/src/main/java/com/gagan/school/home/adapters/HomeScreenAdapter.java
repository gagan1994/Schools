package com.gagan.school.home.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gagan.school.R;
import com.gagan.school.library.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gagan S Patil on 26/9/19.
 */
public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.VH> {

    private final List<HomeViewItems> mData;
    int selectedItem = -1;

    public HomeScreenAdapter(List<HomeViewItems> item) {
        mData = item;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rv_items, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.setData(mData.get(position), position == selectedItem);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class VH extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.ivIcon)
        ImageView imageView;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.layout1)
        View layout1;

        public VH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setData(HomeViewItems homeViewItems, boolean isSelected) {
            description.setText(homeViewItems.getText());
            imageView.setImageDrawable(itemView.getContext().getDrawable(isSelected ?
                    homeViewItems.getIconColor() : homeViewItems.getIcon()));
            description.setTextColor(itemView.getContext().getResources()
                    .getColor(isSelected ? R.color.selected : R.color.unSelected));
            layout1.setBackgroundColor(itemView.getContext().getResources().getColor(homeViewItems.getBg()));
        }

        @Override
        public void onClick(View v) {
            selectedItem = getAdapterPosition();
            notifyDataSetChanged();
        }
    }
}
