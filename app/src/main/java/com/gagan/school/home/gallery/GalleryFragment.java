package com.gagan.school.home.gallery;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.gagan.school.R;
import com.gagan.school.home.gallery.adapters.GalleryImageAdapter;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.adapter.OnClickRvItem;

import butterknife.BindView;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class GalleryFragment extends BaseFragment {
    @BindView(R.id.rvListImages)
    RecyclerView rvListImages;
    private GalleryImageAdapter adapter;


    @Override
    protected void onCreatedView(View view) {
        adapter=new GalleryImageAdapter(GalleryModel.getGalleryList(),
                this, new OnClickRvItem<GalleryModel>() {
            @Override
            public void onClickItem(GalleryModel object) {

            }
        });

        rvListImages.setLayoutManager(new GridLayoutManager(getActivityContext(),4));
        rvListImages.setAdapter(adapter);
    }

    @Override
    public int getLayout() {
        return R.layout.gallery_layout;
    }
}
