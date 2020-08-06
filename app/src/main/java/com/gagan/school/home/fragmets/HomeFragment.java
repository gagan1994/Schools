package com.gagan.school.home.fragmets;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gagan.school.R;
import com.gagan.school.home.MainActivity;
import com.gagan.school.home.adapters.RvTaskAdapter;
import com.gagan.school.home.adapters.SliderAdapterExample;
import com.gagan.school.library.utils.Utils;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.adapter.OnClickRvItem;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.picassos.CircleTransform;
import com.gagan.school.picassos.PicassoTrustAll;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.imageSlider)
    SliderView imageSlider;
    @BindView(R.id.rvNews)
    RecyclerView rvNews;
    @BindView(R.id.profileImage)
    ImageView imageProfile;

    private List<RvItems> tasksList;
    protected RvTaskAdapter adapter;


    private List<RvItems> getDummyDatas() {
        List<RvItems> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add(null);
        }
        return items;
    }

    @Override
    protected void onCreatedView(View view) {
        imageSlider.setSliderAdapter(new SliderAdapterExample(getActivity()));
        imageSlider.startAutoCycle();
        imageSlider.setIndicatorAnimation(IndicatorAnimations.WORM);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        tasksList = getDummyDatas();
        adapter = new RvTaskAdapter(tasksList,
                this,
                new OnClickRvItem<RvItems>() {
                    @Override
                    public void onClickItem(RvItems object) {

                    }
                });
        rvNews.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNews.setAdapter(adapter);
        Utils.setProfileImage(getActivity(),imageProfile);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).addFragmet(new ProfileFragment());
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.home_fragment;
    }
}
