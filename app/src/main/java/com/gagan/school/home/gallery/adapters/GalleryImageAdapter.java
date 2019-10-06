package com.gagan.school.home.gallery.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.gagan.school.R;
import com.gagan.school.home.gallery.GalleryModel;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.view.adapter.OnClickRvItem;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.library.view.adapter.RvListAdapter;
import com.gagan.school.library.view.adapter.RvListViewHolder;
import com.gagan.school.picassos.PicassoTrustAll;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class GalleryImageAdapter extends RvListAdapter<GalleryImageAdapter.VH
        , GalleryModel> {
    public GalleryImageAdapter(List<GalleryModel> items,
                               IActivityHelper context,
                               OnClickRvItem<GalleryModel> listner) {
        super(items, context, listner);
    }


    @Override
    protected int getViewLayoutId(int viewType) {
        return R.layout.item_gallery;
    }

    @Override
    protected void setData(VH holder, GalleryModel galleryModel) {
        holder.setData(galleryModel);
    }

    @Override
    protected VH getViewHolder(View listItem, int viewType) {
        return new VH(listItem);
    }

    public class VH extends RvListViewHolder<GalleryModel> {
        @BindView(R.id.iv_item)
        ImageView iv_item;
        public VH(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(GalleryModel rvItems) {
            PicassoTrustAll.getInstance(mContext.getActivityContext())
                    .load(rvItems.url())
                    .into(iv_item);
        }
    }
}
