package saluja.com.saluja.ui.fragment.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import saluja.com.saluja.R;
import saluja.com.saluja.database.DatabaseHandler;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.SessionManager;
import saluja.com.saluja.utilit.Utility;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class PaymentFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    Context ctx;
    LinearLayout next_ll;
    TextView total_tv, tv_payment_offer;
    RadioButton cob_rb;
    Activity activity;
    SessionManager sessionManager;
    Button couponCodeBtn;
    //ConnectionDetector connectionDetector;
    static int page = 0;
    String api = "";
    EditText EtcouponCode;
    private String offerCode = "";
    LinearLayout offer_layout;
    String currentDateTimeString;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";
    public static String totlaPrice = "0";
    RecyclerView rv_offer_list;
   /* private ArrayList<CouponModel> walletModelArrayList = new ArrayList<>();
    private AdapterOffer adapterOffer;*/
    Calendar c;

    @SuppressLint("ValidFragment")
    public PaymentFragment(Context ctx) {
        this.ctx = ctx;
        this.activity = activity;
        sessionManager = new SessionManager(ctx);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, null);
        initXml(view);
        return view;
    }

    private void initXml(View view) {
        c = Calendar.getInstance();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        currentDateTimeString = df.format(c.getTime());
        next_ll = view.findViewById(R.id.ll_payment_next);
        total_tv = view.findViewById(R.id.tv_payment_total);
        rv_offer_list = view.findViewById(R.id.rv_offer_list);
        tv_payment_offer = view.findViewById(R.id.tv_payment_offer);
        offer_layout = view.findViewById(R.id.offer_layout);
        cob_rb = view.findViewById(R.id.rb_payment_cod);
        couponCodeBtn = view.findViewById(R.id.couponCodeBtn);
        EtcouponCode = view.findViewById(R.id.EtcouponCode);
        next_ll.setOnClickListener(this);
        cob_rb.setOnClickListener(this);
        couponCodeBtn.setOnClickListener(this);
        total_tv.setText(Utility.getCartTotal(databaseCart));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_payment_next:
              /*  sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "PayPal");
                SharedPreferences.Editor editor1 = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                editor1.putString("total_price", total_tv.getText().toString());
                editor1.putString("offer_price", tv_payment_offer.getText().toString());
                editor1.apply();*/
                // ((CheckOutActivity) getActivity()).setPosition(2);
                ConfirmationFragment fragment = new ConfirmationFragment(ctx);
                Utility.setFragment1(fragment, ctx, ConstantData.HOME);

                break;
            case R.id.rb_payment_cod:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "PayPal");
                //((CheckOutActivity) getActivity()).setPosition(2);

                break;
           /* case R.id.rb_payment_credit:
                sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "online");
                //((CheckOutActivity) getActivity()).setPosition(2);
                break;*/

        }
    }


}
