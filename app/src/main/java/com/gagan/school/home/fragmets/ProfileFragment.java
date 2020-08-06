package com.gagan.school.home.fragmets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gagan.school.R;
import com.gagan.school.home.adapters.HomeScreenAdapter;
import com.gagan.school.library.utils.Utils;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.picassos.CircleTransform;
import com.gagan.school.picassos.PicassoTrustAll;

import butterknife.BindView;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class ProfileFragment extends BaseFragment {
    @BindView(R.id.rvList)
    RecyclerView recyclerView;
    @BindView(R.id.profileImage)
    ImageView profileImage;
    private HomeScreenAdapter iconAdapter;


    @Override
    protected void onCreatedView(View view) {
        iconAdapter = new HomeScreenAdapter(Utils.getHomeScreenItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(iconAdapter);
        Utils.setProfileImage(getActivity(), profileImage);
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            RectF rectF = new RectF(rect);
            int roundPx = pixels;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(0xff424242);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    @Override
    public int getLayout() {
        return R.layout.home_layout;
    }
}
