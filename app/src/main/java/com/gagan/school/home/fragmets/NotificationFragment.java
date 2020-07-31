package com.gagan.school.home.fragmets;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gagan.school.R;
import com.gagan.school.home.adapters.NotificationAdapter;
import com.gagan.school.home.adapters.RvTaskAdapter;
import com.gagan.school.home.adapters.model.NotificationModel;
import com.gagan.school.home.adapters.model.PaymentModel;
import com.gagan.school.library.utils.Utils;
import com.gagan.school.library.view.BaseFragment;
import com.gagan.school.library.view.adapter.OnClickRvItem;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.model.payment.PaymentNotif;
//import com.gagan.school.payment.PaymentActivity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.gagan.school.library.utils.Utils.DATE_TIME_FORMAT;

/**
 * Created by Gagan S Patil on 6/10/19.
 */
public class NotificationFragment extends BaseFragment {
    public static final int PAYMENT_REQ_CODE = 1234;
    @BindView(R.id.rvNotification)
    RecyclerView rvNotification;
    private List<NotificationModel> tasksList;
    protected NotificationAdapter adapter;

    private List<NotificationModel> getDummyDatas() {
        List<NotificationModel> items = new ArrayList<>();
        NotificationModel notif = new NotificationModel();
        notif.setType(Utils.PAYMENT);
        notif.setHeading("Fee");
        notif.setDetails("Pay your school fee 14,000");
        notif.setDatas("{'amount':'14000.00','vpa':'9482718488@ybl'}");
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        String date = " 2020-03-23T12:56:31.807Z";
        notif.setTime(date);
        items.add(notif);
        for (int i = 0; i < 10; i++) {
            items.add(null);
        }
        return items;
    }

    @Override
    protected void onCreatedView(View view) {
        tasksList = getDummyDatas();
        adapter = new NotificationAdapter(tasksList,
                this,
                new OnClickRvItem<NotificationModel>() {
                    @Override
                    public void onClickItem(NotificationModel object) {
                        if (object.getType().equals(Utils.PAYMENT)) {
                            PaymentNotif paymentNotif = new Gson().fromJson(object.getData(), PaymentNotif.class);
                            openPayment(object);
                        }
                    }
                });
        rvNotification.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotification.setAdapter(adapter);
    }

    private void openPayment(NotificationModel paymentNotif) {
//        Intent intent = new Intent(getActivity(), PaymentActivity.class);
//        intent.putExtra(Utils.PAYMENT_OBJECT, new PaymentModel(paymentNotif));
//        startActivityForResult(intent, PAYMENT_REQ_CODE);
    }

    @Override
    public int getLayout() {
        return R.layout.notification_layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYMENT_REQ_CODE) {
            String transactionId = data.getStringExtra(Utils.TRANSACTION_ID);
            if (resultCode == RESULT_OK) {
                paymentSuccesfull(transactionId);
            } else {
                paymentFailed(transactionId, data.getStringExtra(Utils.TRANSACTION_FAILURE_REASON));
            }
        }
    }

    private void paymentFailed(String transactionId, String reason) {
        Toast.makeText(getActivityContext(), "Payment Failed transactionId:" + transactionId
                + " reason: " + reason, Toast.LENGTH_SHORT).show();
    }

    private void paymentSuccesfull(String transactionId) {
        Toast.makeText(getActivityContext(), "Payment succesfull transactionId:"
                + transactionId, Toast.LENGTH_SHORT).show();
    }
}