//package com.gagan.school.payment;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.gagan.easycardpayment.CardPayment;
//import com.gagan.easycardpayment.Payment;
//import com.gagan.easyupi.EasyUpiPayment;
//import com.gagan.easyupi.listener.PaymentStatusListener;
//import com.gagan.easyupi.model.PaymentApp;
//import com.gagan.easyupi.model.TransactionDetails;
//import com.gagan.school.R;
//import com.gagan.school.home.adapters.model.NotificationModel;
//import com.gagan.school.home.adapters.model.PaymentModel;
//import com.gagan.school.library.utils.Utils;
//import com.gagan.school.model.payment.PaymentNotif;
//import com.google.android.material.textfield.TextInputEditText;
//import com.google.gson.Gson;
//
//import java.io.Serializable;
//import java.util.UUID;
//
//import static com.gagan.school.home.fragmets.NotificationFragment.PAYMENT_REQ_CODE;
//import static com.gagan.school.library.utils.Utils.TRANSACTION_FAILURE_REASON;
//
//public class PaymentActivity extends AppCompatActivity implements PaymentStatusListener, com.gagan.easycardpayment.PaymentStatusListener {
//    private ImageView imageView;
//
//    private TextView statusView;
//
//    private Button payButton;
//
//    private RadioGroup radioAppChoice;
//
//    private TextView fieldPayeeVpa;
//    private TextView fieldPayeeName;
//    private TextView fieldAmount;
//
//    private TextInputEditText fieldTransactionId;
//    private TextInputEditText fieldTransactionRefId;
//    private TextInputEditText fieldDescription;
//
//    private EasyUpiPayment mEasyUpiPayment;
//    private PaymentModel paymentObj;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//        paymentObj = (PaymentModel) getIntent().getSerializableExtra(Utils.PAYMENT_OBJECT);
//
//        initViews();
//
//        payButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pay();
//            }
//        });
//    }
//
//    private void initViews() {
//        imageView = findViewById(R.id.imageView);
//        statusView = findViewById(R.id.textView_status);
//        payButton = findViewById(R.id.button_pay);
//
//        fieldPayeeVpa = findViewById(R.id.field_vpa);
//        fieldPayeeName = findViewById(R.id.field_name);
//        fieldTransactionId = findViewById(R.id.field_transaction_id);
//        fieldTransactionRefId = findViewById(R.id.field_transaction_ref_id);
//        fieldDescription = findViewById(R.id.field_description);
//        fieldAmount = findViewById(R.id.field_amount);
//
//        fieldTransactionId.setText(paymentObj.getTransactionId());
//        fieldTransactionRefId.setText(paymentObj.getTransactionId());
//
//        fieldPayeeVpa.setText(paymentObj.getVpa());
//        fieldAmount.setText(Utils.currencyFormatter(paymentObj.getAmount()));
//        fieldPayeeName.setText(getString(R.string.school_name));
//
//
//        radioAppChoice = findViewById(R.id.radioAppChoice);
//        checkPaymentApp();
//    }
//
//    private void checkPaymentApp() {
//        findViewById(R.id.app_amazonpay).setVisibility(Utils
//                .isPackageInstalled(PaymentApp.AMAZON_PAY,
//                        getPackageManager()) ? View.VISIBLE : View.GONE);
//        ;
//        findViewById(R.id.app_bhim_upi).setVisibility(Utils
//                .isPackageInstalled(PaymentApp.BHIM_UPI,
//                        getPackageManager()) ? View.VISIBLE : View.GONE);
//        ;
//        findViewById(R.id.app_google_pay).setVisibility(Utils
//                .isPackageInstalled(PaymentApp.GOOGLE_PAY,
//                        getPackageManager()) ? View.VISIBLE : View.GONE);
//        ;
//        findViewById(R.id.app_phonepe).setVisibility(Utils
//                .isPackageInstalled(PaymentApp.PHONE_PE,
//                        getPackageManager()) ? View.VISIBLE : View.GONE);
//        ;
//        findViewById(R.id.app_paytm).setVisibility(Utils
//                .isPackageInstalled(PaymentApp.PAYTM,
//                        getPackageManager()) ? View.VISIBLE : View.GONE);
//        ;
//
//    }
//
//    private void pay() {
//        RadioButton paymentAppChoice = findViewById(radioAppChoice.getCheckedRadioButtonId());
//        if (paymentAppChoice.getId() == R.id.app_default) {
//            CardPayment cardPayment = new CardPayment.Builder()
//                    .with(this)
//                    .setAmount(fieldAmount.getText().toString())
//                    .build();
//            cardPayment.setPaymentListener(this);
//            cardPayment.startPayment();
//            return;
//        }
//        String payeeVpa = paymentObj.getVpa();
//        String payeeName = fieldPayeeName.getText().toString();
//        String transactionId = paymentObj.getTransactionId();
//        String transactionRefId = paymentObj.getTransactionRefId();
//        String description = fieldDescription.getText().toString();
//        String amount = paymentObj.getAmount();
//
//        // START PAYMENT INITIALIZATION
//        mEasyUpiPayment = new EasyUpiPayment.Builder()
//                .with(this)
//                .setPayeeVpa(payeeVpa)
//                .setPayeeName(payeeName)
//                .setTransactionId(transactionId)
//                .setTransactionRefId(transactionRefId)
//                .setDescription(description)
//                .setAmount(amount)
//                .build();
//
//        // Register Listener for Events
//        mEasyUpiPayment.setPaymentStatusListener(this);
//
//        switch (paymentAppChoice.getId()) {
//            case R.id.app_amazonpay:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.AMAZON_PAY);
//                break;
//            case R.id.app_bhim_upi:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.BHIM_UPI);
//                break;
//            case R.id.app_google_pay:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
//                break;
//            case R.id.app_phonepe:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.PHONE_PE);
//                break;
//            case R.id.app_paytm:
//                mEasyUpiPayment.setDefaultPaymentApp(PaymentApp.PAYTM);
//                break;
//        }
//
//        // Check if app exists or not
//        if (mEasyUpiPayment.isDefaultAppExist()) {
//            onAppNotFound();
//            return;
//        }
//        // END INITIALIZATION
//
//        // START PAYMENT
//        mEasyUpiPayment.startPayment();
//    }
//
//    @Override
//    public void onTransactionCompleted(TransactionDetails transactionDetails) {
//        // Transaction Completed
//        Log.d("TransactionDetails", transactionDetails.toString());
//        statusView.setText(transactionDetails.toString());
//    }
//
//    @Override
//    public void onTransactionSuccess() {
//        // Payment Success
//        Intent intent = new Intent();
//        intent.putExtra(Utils.TRANSACTION_ID, paymentObj.getTransactionId());
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    @Override
//    public void onTransactionSubmitted() {
//        // Payment Pending
//    }
//
//    @Override
//    public void onTransactionFailed() {
//        // Payment Failed
//        imageView.setImageResource(R.drawable.ic_failed);
//        Intent intent = new Intent();
//        intent.putExtra(Utils.TRANSACTION_ID, paymentObj.getTransactionId());
//        intent.putExtra(TRANSACTION_FAILURE_REASON, "Payment Failed");
//        setResult(RESULT_CANCELED, intent);
//        finish();
//    }
//
//    @Override
//    public void onTransactionCancelled() {
//        // Payment Cancelled by User
//        Intent intent = new Intent();
//        intent.putExtra(Utils.TRANSACTION_ID, paymentObj.getTransactionId());
//        intent.putExtra(TRANSACTION_FAILURE_REASON, "Payment Cancelled by User");
//        setResult(RESULT_CANCELED, intent);
//        finish();
//    }
//
//    @Override
//    public void onAppNotFound() {
//
//        Toast.makeText(this, "App Not Found", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mEasyUpiPayment.detachListener();
//    }
//
//    @Override
//    public void onSuccess(String text) {
//        Intent intent = new Intent();
//        intent.putExtra(Utils.TRANSACTION_ID, paymentObj.getTransactionId());
//        setResult(RESULT_OK, intent);
//        finish();
//    }
//
//    @Override
//    public void onError(String text) {
//        Intent intent = new Intent();
//        intent.putExtra(Utils.TRANSACTION_ID, paymentObj.getTransactionId());
//        intent.putExtra(TRANSACTION_FAILURE_REASON, "Card error " + text);
//        setResult(RESULT_CANCELED, intent);
//        finish();
//    }
//
//}