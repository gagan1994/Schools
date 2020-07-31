package com.gagan.school.home.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gagan.school.R;
import com.gagan.school.home.adapters.model.NotificationModel;
import com.gagan.school.library.interfaces.IActivityHelper;
import com.gagan.school.library.utils.Utils;
import com.gagan.school.library.view.adapter.OnClickRvItem;
import com.gagan.school.library.view.adapter.RvItems;
import com.gagan.school.library.view.adapter.RvListAdapter;
import com.gagan.school.library.view.adapter.RvListViewHolder;
import com.gagan.school.model.payment.PaymentNotif;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;

public class NotificationAdapter extends RvListAdapter<NotificationAdapter.ViewHolder, NotificationModel> {
    public NotificationAdapter(List<NotificationModel> items,
                               IActivityHelper context,
                               OnClickRvItem<NotificationModel> listner) {
        super(items, context, listner);
    }

    @Override
    protected void setData(NotificationAdapter.ViewHolder holder, NotificationModel notificationModel) {
        holder.setData(notificationModel);
    }

    @Override
    protected int getViewLayoutId(int viewType) {
        return viewType;
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatas.get(position) == null) {
            return R.layout.task_item_load;
        }
        if (mDatas.get(position).getType().equals(Utils.PAYMENT)) {
            return R.layout.payment_item;
        }
        return R.layout.task_item_load;
    }

    @Override
    protected NotificationAdapter.ViewHolder getViewHolder(View listItem, int viewType) {
        if (viewType == R.layout.payment_item) {
            return new PaymentViewHolder(listItem);
        }
        return new ViewHolder(listItem);
    }

    public class ViewHolder extends RvListViewHolder<NotificationModel> {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NotificationModel rvItems) {

        }

    }

    public class PaymentViewHolder extends ViewHolder {
        @BindView(R.id.pay)
        View pay;
        @BindView(R.id.description)
        TextView desription;
        @BindView(R.id.heading)
        TextView heading;
        @BindView(R.id.cost)
        TextView cost;
        @BindView(R.id.time)
        TextView time;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NotificationModel rvItems) {
            heading.setText(rvItems.getHeading());
            desription.setText(rvItems.getNotificactionDetails());
            PaymentNotif paymentNotif=new Gson().fromJson(rvItems.getData(),PaymentNotif.class);
            cost.setText(Utils.currencyFormatter(paymentNotif.getAmount()));
            time.setText(Utils.getTimeFormat(rvItems.getTime()));
            pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onClickItem(rvItems);
                }
            });
        }
    }

}
