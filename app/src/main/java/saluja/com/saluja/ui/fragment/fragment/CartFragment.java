package saluja.com.saluja.ui.fragment.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import saluja.com.saluja.AppPreference;
import saluja.com.saluja.R;
import saluja.com.saluja.adapter.AdapterCart;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.database.DatabaseHandler;
import saluja.com.saluja.database.HelperManager;
import saluja.com.saluja.model.ProductDetail;
import saluja.com.saluja.ui.fragment.activity.CheckOutActivity;
import saluja.com.saluja.utilit.SessionManager;

import static saluja.com.saluja.ui.fragment.activity.MainActivity.cart_number;


@SuppressLint("ValidFragment")
public class CartFragment extends Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    Button place_bt;
    ArrayList<ProductDetail> list;

    HelperManager helperManager;
   // ConnectionDetector connectionDetector;
    SessionManager sessionManager;
    Activity activity;
    String user_id = "0";
    private String DATABASE_CART = "cart.db";
    public DatabaseHandler databaseCart;
    private ArrayList<ProductDetail> cartProductList = new ArrayList<>();
    private AdapterCart adapterCart;

    public CartFragment(Context ctx, Activity activity) {
        this.ctx = ctx;
        this.activity = activity;
        helperManager = new HelperManager(ctx);
        sessionManager = new SessionManager(ctx);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_addto_cart, container, false);
       // MainActivity.tooltext_tv.setText(ConstantData.CART);
       // SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
       // user_id = prefs.getString("user_id", "0");//"No name defined" is the default value.

        recyclerView = view.findViewById(R.id.rv_wishlist_recyclerview);
        place_bt = view.findViewById(R.id.bt_wishlist_placeorder);

        list = helperManager.readAllCart();
        place_bt.setVisibility(View.VISIBLE);
        place_bt.setOnClickListener(this);

        initDatabase();
        setTotal();
        return view;
    }

    private void initDatabase() {
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
        cartProductList.clear();
        if (databaseCart.getContactsCount()) {
            cartProductList.addAll(databaseCart.getAllUrlList());
        }

        adapterCart = new AdapterCart(cartProductList, ctx, CartFragment.this, this, databaseCart);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterCart);
    }

    public void setTotal() {
        float total = 0;
        ArrayList<ProductDetail> total_list = databaseCart.getAllUrlList();
        cart_number.setText("" + total_list.size());
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, total_list.size());
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
            total = Math.round(total);
        }
        place_bt.setText("Place this Order :   ₹" + total);
    }

    public String getTotal() {
        float total = 0;
        float round_total = 0;
        ArrayList<ProductDetail> total_list = databaseCart.getAllUrlList();
        cart_number.setText(total_list.size());
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, total_list.size());
        for (int i = 0; i < total_list.size(); i++) {
            float pr = Float.parseFloat(total_list.get(i).getPrice());
            int qty = total_list.get(i).getQuantity();

            float tot = pr * qty;
            total += tot;
            round_total = Math.round(total);
        }
        return String.valueOf(round_total);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_wishlist_placeorder:
                ArrayList<ProductDetail> cartlist = databaseCart.getAllUrlList();
                    if (cartlist.size() > 0) {
                        startActivity(new Intent(ctx, CheckOutActivity.class));
                        getActivity().finish();
                    }
                break;
            case R.id.iv_adpcart_minus:
                minusItem(view);
                break;
            case R.id.iv_adpcart_plus:
                plusItem(view);
                break;
        }
    }

    private void plusItem(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        ProductDetail productDetail = cartProductList.get(pos);
        View v = recyclerView.getChildAt(pos);
        TextView tvQty = (TextView) v.findViewById(R.id.tv_adpcart_qty);
        ImageView minus_iv = (ImageView) v.findViewById(R.id.iv_adpcart_minus);

        int qty = Integer.parseInt(tvQty.getText().toString());
        qty++;
        productDetail.setQuantity(qty);
        databaseCart.updateUrl(productDetail);
        tvQty.setText(qty + "");
        setTotal();
        if (qty > 1) {
            minus_iv.setImageResource(R.drawable.ic_minus);
        } else {
            minus_iv.setImageResource(R.drawable.ic_delete);
        }
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, cartProductList.size());
    }

    private void minusItem(View view) {
        int pos = Integer.parseInt(view.getTag().toString());
        ProductDetail productDetail = cartProductList.get(pos);
        View v = recyclerView.getChildAt(pos);
        TextView tvQty = (TextView) v.findViewById(R.id.tv_adpcart_qty);
        ImageView minus_iv = (ImageView) v.findViewById(R.id.iv_adpcart_minus);

        int qty = Integer.parseInt(tvQty.getText().toString());
        if (qty == 1) {
            databaseCart.deleteContact(productDetail);
            cartProductList.remove(pos);
            adapterCart.notifyDataSetChanged();
        } else {
            qty--;
            productDetail.setQuantity(qty);
            databaseCart.updateUrl(productDetail);
            tvQty.setText(qty + "");
        }
        if (qty > 1) {
            minus_iv.setImageResource(R.drawable.ic_minus);
        } else {
            minus_iv.setImageResource(R.drawable.ic_delete);
        }
        setTotal();
        AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, list.size());
    }
}
