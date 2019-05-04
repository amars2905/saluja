package saluja.com.saluja.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import saluja.com.saluja.AppPreference;
import saluja.com.saluja.R;
import saluja.com.saluja.adapter.AdapterConfirmation;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.database.DatabaseHandler;
import saluja.com.saluja.model.ProductDetail;
import saluja.com.saluja.payUmoney.PayUmoneyActivity;
import saluja.com.saluja.retrofit_provider.RetrofitApiClient;
import saluja.com.saluja.retrofit_provider.RetrofitService;
import saluja.com.saluja.retrofit_provider.WebResponse;
import saluja.com.saluja.ui.activity.MainActivity;
import saluja.com.saluja.ui.activity.ThankYouActivity;
import saluja.com.saluja.utilit.Alerts;
import saluja.com.saluja.utilit.ConnectionDetector;
import saluja.com.saluja.utilit.SessionManager;
import saluja.com.saluja.utilit.Utility;
import saluja.com.saluja.utilit.WebApi;

import static android.content.Context.MODE_PRIVATE;
import static saluja.com.saluja.SplashScreen.mypreference;

@SuppressLint("ValidFragment")
public class ConfirmationFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    Context ctx;
    RecyclerView recyclerView;
    TextView total_tv,tv_payment;
    LinearLayout ordernow_ll;
    public static String Payment_Package = "";
    SessionManager sessionManager;
    public DatabaseHandler databaseCart;
    private String DATABASE_CART = "cart.db";

    private ConnectionDetector connectionDetector;
    private RetrofitApiClient retrofitApiClient;
    //ConnectionDetector connectionDetector;
    String totalAmount1 = "0";
    String Offer_Amount = "0";
    @SuppressLint("ValidFragment")
    public ConfirmationFragment(Context ctx) {
        this.ctx = ctx;
        sessionManager = new SessionManager(ctx);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_confirmation, null);

        connectionDetector = new ConnectionDetector(ctx);
        retrofitApiClient = RetrofitService.getRetrofit();

        initXml(view);
        setOrder();
        return view;
    }

    private void setOrder() {

        ArrayList<ProductDetail> orderlist = databaseCart.getAllUrlList();
        AdapterConfirmation adapter = new AdapterConfirmation(orderlist, ctx);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(ctx);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initXml(View view) {
        //cart_count = AppPreference.getIntegerPreference(ctx, Constant.TOTAL_AMOUNT); //0 is the default value.

        SharedPreferences prefs = getActivity().getSharedPreferences(Constant.TOTAL_AMOUNT, MODE_PRIVATE);
       /* totalAmount = prefs.getString("Total", "0");//"No name defined" is the default value.
        Offer_Amount = prefs.getString("Offer", "0");//"No name defined" is the default value.
        Log.e("Total ",".."+totalAmount);*/

       /* totalAmount = sessionManager.getData(SessionManager.KEY_ORDER_PRICE);
        Log.e("total ",".."+totalAmount);*/

      /*  SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
        totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.*/

        ordernow_ll = view.findViewById(R.id.ll_conforder_ordernow);
        recyclerView = view.findViewById(R.id.rv_conforder_recycler);
        total_tv = view.findViewById(R.id.tv_confirmation_total);
        tv_payment = view.findViewById(R.id.tv_payment);

        totalAmount1 = Utility.getCartTotal(databaseCart);
        ordernow_ll.setOnClickListener(this);
        total_tv.setText(totalAmount1);
        tv_payment.setText(totalAmount1);

        /*total_tv.setText(Utility.getCartTotal(databaseCart));
        Payment_Package = Utility.getCartTotal(databaseCart);*/
        Payment_Package = totalAmount1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_conforder_ordernow:
                String paytype = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_TYPE);
                if (paytype.equals("PAYUMONEY")) {
                    Intent i = new Intent(getActivity(), PayUmoneyActivity.class);
                    getActivity().startActivity(i);
                    getActivity().finish();
                    getData();

                }else {

                    getData();
                }
                //getData();
                break;
        }
    }

    private void getData() {

        String name = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_NAME);
        String mobile =  AppPreference.getStringPreference(ctx, Constant.PHONE);
        String address = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_ADDRESS);
        String city = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_CITY);
        String state = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_STATE);
        String country = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_CONTRY);
        String code = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_CODE);
        String paytype = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_TYPE);


        if (name.equals("") || mobile.equals("") || address.equals("") || city.equals("") ) {
            Utility.toastView(ctx, "Please enter Your all shipping Details");
        } else if (paytype.equals("")) {
            Utility.toastView(ctx, "Please Select your payment method");
        } else {
            sessionManager.setData(SessionManager.KEY_ORDER_NAME, "");
            sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, "");
            sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, "");
            sessionManager.setData(SessionManager.KEY_ORDER_CITY, "");
            sessionManager.setData(SessionManager.KEY_ORDER_STATE, "");
            sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, "");
            sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, "");
            sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "");
            submitData("abc", "mobile", "address", "city", "state", "country", "code", "paytype", "");
        }
    }

    private void submitData(String name, String mobile, String address, String city, String state, String country, String code, String paytype, String txnid) {

        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("total", totalAmount1);
           // paramObject.put("total", Utility.getCartTotal(databaseCart));
            paramObject.put("customer_id", sessionManager.getData(SessionManager.KEY_ID));
            JSONObject billing_obj = new JSONObject();
            billing_obj.put("first_name", name);
            billing_obj.put("last_name", "");
            billing_obj.put("company", "");
            billing_obj.put("address_1", address);
            billing_obj.put("address_2", "");
            billing_obj.put("city", city);
            billing_obj.put("state", state);
            billing_obj.put("postcode", code);
            billing_obj.put("country", country);
            billing_obj.put("email", sessionManager.getData(SessionManager.KEY_EMAIL));
            billing_obj.put("phone", mobile);
            paramObject.put("billing", billing_obj);
            paramObject.put("payment_method", paytype);
            paramObject.put("payment_method_title", "");
            paramObject.put("transaction_id", txnid);
            JSONArray line_item_array = new JSONArray();
            ArrayList<ProductDetail> list = databaseCart.getAllUrlList();
            for (int i = 0; i < list.size(); i++) {
                float tot = list.get(i).getQuantity() * Float.parseFloat(list.get(i).getPrice());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("product_id", Integer.parseInt(list.get(i).getId()));
                jsonObject.put("variation_id", 0);
                jsonObject.put("quantity", list.get(i).getQuantity());
                jsonObject.put("sku", "");
                jsonObject.put("total", String.valueOf(tot));
                line_item_array.put(jsonObject);
            }
            paramObject.put("line_items", line_item_array);
            JSONArray line_item_array1 = new JSONArray();
                //float tot = list1.get(i).getQuantity() * Float.parseFloat(list1.get(i).getPrice());
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("id", "1");
                jsonObject1.put("code", "ABC");
                jsonObject1.put("discount", "30");
                line_item_array1.put(jsonObject1);

            paramObject.put("coupon_lines", line_item_array1);
            String data = paramObject.toString();

            //shipping_address
            JSONObject shippingAddressObject = new JSONObject();

            shippingAddressObject.put("city",city);
            shippingAddressObject.put("country",country);
            shippingAddressObject.put("address_1",address);
            shippingAddressObject.put("last_name","");
            shippingAddressObject.put("company","");
            shippingAddressObject.put("postcode",code);
            shippingAddressObject.put("address_2","");
            shippingAddressObject.put("state",state);
            shippingAddressObject.put("first_name",name);

            //billing_address
            JSONObject billingAddressObject = new JSONObject();

            billingAddressObject.put("phone", mobile);
            billingAddressObject.put("city", city);
            billingAddressObject.put("country", country);
            billingAddressObject.put("address_1", address);
            billingAddressObject.put("first_name", name);
            billingAddressObject.put("last_name", "");
            billingAddressObject.put("company", "");
            billingAddressObject.put("postcode", code);
            billingAddressObject.put("email", sessionManager.getData(SessionManager.KEY_EMAIL));
            billingAddressObject.put("address_2", "");
            billingAddressObject.put("state", state);

            /*customer
            * */

            JSONObject customerJsonObject = new JSONObject();

            customerJsonObject.put("id",sessionManager.getData(SessionManager.KEY_ID));
            customerJsonObject.put("last_order_date","");
            customerJsonObject.put("avatar_url","");
            customerJsonObject.put("total_spent","");
            customerJsonObject.put("created_at","");
            customerJsonObject.put("orders_count","");
            customerJsonObject.put("billing_address",billingAddressObject);
            customerJsonObject.put("shipping_address",shippingAddressObject);
            customerJsonObject.put("first_name",name);
            customerJsonObject.put("username","");
            customerJsonObject.put("last_name","");
            customerJsonObject.put("last_order_id","");
            customerJsonObject.put("email",sessionManager.getData(SessionManager.KEY_EMAIL));

            /*********************************
            * */
            //shipping_lines
            JSONArray ja = new JSONArray();
            JSONObject jo1 = new JSONObject();
            jo1.put("method_title","Free Shipping");
            jo1.put("id","");
            jo1.put("method_id","free_shipping");
            jo1.put("total","0.00");

            ja.put(jo1);

            /*********************************
            * */
            //payment_details
            JSONObject pdObject = new JSONObject();
            jo1.put("method_title","Cheque Payment");
            jo1.put("method_id","cheque");
            jo1.put("paid",false);

            /*********************************
            * */
            //line_items
            JSONArray line_item_array2 = new JSONArray();
            ArrayList<ProductDetail> pList = databaseCart.getAllUrlList();
            for (int i = 0; i < pList.size(); i++) {
                float totl = pList.get(i).getQuantity() * Float.parseFloat(pList.get(i).getPrice());
                JSONObject jsonObject8 = new JSONObject();
                jsonObject8.put("product_id", Integer.parseInt(list.get(i).getId()));
                jsonObject8.put("variation_id", 0);
                jsonObject8.put("quantity", list.get(i).getQuantity());
                jsonObject8.put("sku", "");
                jsonObject8.put("total", String.valueOf(totl));
                line_item_array2.put(jsonObject8);
            }


            JSONObject orderOject = new JSONObject();
            orderOject.put("completed_at","");
            orderOject.put("tax_lines",new JSONArray());
            orderOject.put("status","processing");
            orderOject.put("total","");
            orderOject.put("cart_discount","");
            orderOject.put("customer_ip","");
            orderOject.put("total_discount","");
            orderOject.put("updated_at","");
            orderOject.put("currency","");
            orderOject.put("total_shipping","");
            orderOject.put("customer_user_agent","");
            orderOject.put("line_items",line_item_array2);
            orderOject.put("customer_id",sessionManager.getData(SessionManager.KEY_ID));
            orderOject.put("total_tax","");
            orderOject.put("order_number","");
            orderOject.put("shipping_methods","Free Shipping");
            orderOject.put("shipping_address",shippingAddressObject);
            orderOject.put("payment_details",pdObject);
            orderOject.put("id","");
            orderOject.put("shipping_tax","0.00");
            orderOject.put("cart_tax","0.00");
            orderOject.put("fee_lines",new JSONArray());
            orderOject.put("total_line_items_quantity","");
            orderOject.put("shipping_lines",ja);
            orderOject.put("customer",customerJsonObject);
            orderOject.put("note","");
            orderOject.put("coupon_lines",new JSONArray());
            orderOject.put("order_discount","0.00");
            orderOject.put("created_at","");
            orderOject.put("view_order_url","");
            orderOject.put("billing_address",billingAddressObject);

            JSONObject orderSubmitObject = new JSONObject();
            orderSubmitObject.put("order", orderOject);



          //  submitdata(paramObject);

            submitFinalJsonObject(orderSubmitObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void submitFinalJsonObject(JSONObject orderSubmitObject) {

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                ((orderSubmitObject)).toString());

        if (connectionDetector.isNetworkAvailable()){
            RetrofitService.getServerResponse(new Dialog(ctx), retrofitApiClient.submitOrder(body), new WebResponse() {
                @Override
                public void onResponseSuccess(Response<?> result) {
                    //Alerts.show(ctx, "Login Success!");
                    ResponseBody response = (ResponseBody) result.body();
                    try {
                        JSONObject loginObject = new JSONObject(response.string());

                        Toast.makeText(ctx, loginObject.toString(), Toast.LENGTH_SHORT).show();

                        Utility.toastView(ctx, "your order has been successfully done");
                        databaseCart.deleteallCart(databaseCart);
                        clear();

                        Intent i = new Intent(getActivity(), ThankYouActivity.class);
                        getActivity().startActivity(i);
                        getActivity().finish();

                        //Toast.makeText(ctx, loginObject.toString(), Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onResponseFailed(String error) {
                    Toast.makeText(ctx, error, Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    // post data
    private void submitdata(JSONObject paramObject) {
        Utility.showLoader(ctx);
        AndroidNetworking.post(WebApi.API_SUBMIT_ORDER)
                .addJSONObjectBody(paramObject)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, "your order has been successfully done");
                        databaseCart.deleteallCart(databaseCart);
                        clear();
                        //startActivity(new Intent(ctx, ThankyouActivity.class));

                        String paytype = AppPreference.getStringPreference(ctx, Constant.PAYMENTR_TYPE);

                        if (paytype.equals("PAYUMONEY")) {
                            Intent i = new Intent(getActivity(), PayUmoneyActivity.class);
                            getActivity().startActivity(i);

                        }else {
                            Intent i = new Intent(getActivity(), ThankYouActivity.class);
                            getActivity().startActivity(i);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Utility.hideLoader();
                        Utility.toastView(ctx, anError.toString());
                    }
                });
    }

    private void clear() {
        sessionManager.setData(SessionManager.KEY_ORDER_NAME, "");
        sessionManager.setData(SessionManager.KEY_ORDER_MOBILE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ADDRESS, "");
        sessionManager.setData(SessionManager.KEY_ORDER_CITY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_STATE, "");
        sessionManager.setData(SessionManager.KEY_ORDER_COUNTRY, "");
        sessionManager.setData(SessionManager.KEY_ORDER_ZIPCODE, "");
        sessionManager.setData(SessionManager.KEY_PAYMENT_TYPE, "");
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
        totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            SharedPreferences prefs = getActivity().getSharedPreferences(mypreference, MODE_PRIVATE);
            totalAmount1 = prefs.getString("total_price", "0");//"No name defined" is the default value.        }
        }
    }
}


/*{
  "order" : {
    "completed_at" : "2013-12-10T18:59:30Z",
    "tax_lines" : [],
    "status" : "processing",
    "total" : "20.00",
    "cart_discount" : "0.00",
    "customer_ip" : "127.0.0.1",
    "total_discount" : "0.00",
    "updated_at" : "2013-12-10T18:59:30Z",
    "currency" : "USD",
    "total_shipping" : "0.00",
    "customer_user_agent" : "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36",
    "line_items" : [
      {
        "product_id" : 31,
        "quantity" : 1,
        "id" : 7,
        "subtotal" : "20.00",
        "tax_class" : null,
        "sku" : "",
        "total" : "20.00",
        "name" : "Ninja Silhouette",
        "total_tax" : "0.00"
      }
    ],
    "customer_id" : "4",
    "total_tax" : "0.00",
    "order_number" : "#113",
    "shipping_methods" : "Free Shipping",
    "shipping_address" : {
      "city" : "New York",
      "country" : "US",
      "address_1" : "512 First Avenue",
      "last_name" : "Draper",
      "company" : "SDCP",
      "postcode" : "12534",
      "address_2" : "",
      "state" : "NY",
      "first_name" : "Don"
    },
    "payment_details" : {
      "method_title" : "Cheque Payment",
      "method_id" : "cheque",
      "paid" : false
    },
    "id" : 113,
    "shipping_tax" : "0.00",
    "cart_tax" : "0.00",
    "fee_lines" : [],
    "total_line_items_quantity" : 1,
    "shipping_lines" : [
      {
        "method_title" : "Free Shipping",
        "id" : 8,
        "method_id" : "free_shipping",
        "total" : "0.00"
      }
    ],
    "customer" : {
      "id" : 4,
      "last_order_date" : "2013-12-10T18:58:00Z",
      "avatar_url" : "https://secure.gravatar.com/avatar/ad516503a11cd5ca435acc9bb6523536?s=96",
      "total_spent" : "0.00",
      "created_at" : "2013-12-10T18:58:07Z",
      "orders_count" : 0,
      "billing_address" : {
        "phone" : "215-523-4132",
        "city" : "New York",
        "country" : "US",
        "address_1" : "512 First Avenue",
        "last_name" : "Draper",
        "company" : "SDCP",
        "postcode" : "12534",
        "email" : "thedon@mailinator.com",
        "address_2" : "",
        "state" : "NY",
        "first_name" : "Don"
      },
      "shipping_address" : {
        "city" : "New York",
        "country" : "US",
        "address_1" : "512 First Avenue",
        "last_name" : "Draper",
        "company" : "SDCP",
        "postcode" : "12534",
        "address_2" : "",
        "state" : "NY",
        "first_name" : "Don"
      },
      "first_name" : "Don",
      "username" : "thedon",
      "last_name" : "Draper",
      "last_order_id" : "113",
      "email" : "thedon@mailinator.com"
    },
    "note" : "",
    "coupon_lines" : [],
    "order_discount" : "0.00",
    "created_at" : "2013-12-10T18:58:00Z",
    "view_order_url" : "https://www.example.com/my-account/view-order/113",
    "billing_address" : {
      "phone" : "215-523-4132",
      "city" : "New York",
      "country" : "US",
      "address_1" : "512 First Avenue",
      "last_name" : "Draper",
      "company" : "SDCP",
      "postcode" : "12534",
      "email" : "thedon@mailinator.com",
      "address_2" : "",
      "state" : "NY",
      "first_name" : "Don"
    }
  }
}*/

/*{"order":{"completed_at":"","tax_lines":[],"status":"processing","total":"","cart_discount":"","customer_ip":"","total_discount":"","updated_at":"","currency":"","total_shipping":"","customer_user_agent":"","line_items":[{"product_id":401,"variation_id":0,"quantity":1,"sku":"","total":"13999.0"}],"total_tax":"","order_number":"","shipping_methods":"Free Shipping","shipping_address":{"city":"city","country":"country","address_1":"address","last_name":"","company":"","postcode":"code","address_2":"","state":"state","first_name":"abc"},"payment_details":{},"id":"","shipping_tax":"0.00","cart_tax":"0.00","fee_lines":[],"total_line_items_quantity":"","shipping_lines":[{"method_title":"Cheque Payment","id":"","method_id":"cheque","total":"0.00","paid":false}],"customer":{"last_order_date":"","avatar_url":"","total_spent":"","created_at":"","orders_count":"","billing_address":{"phone":"mobile","city":"city","country":"country","address_1":"address","first_name":"abc","last_name":"","company":"","postcode":"code","address_2":"","state":"state"},"shipping_address":{"city":"city","country":"country","address_1":"address","last_name":"","company":"","postcode":"code","address_2":"","state":"state","first_name":"abc"},"first_name":"abc","username":"","last_name":"","last_order_id":""},"note":"","coupon_lines":[],"order_discount":"0.00","created_at":"","view_order_url":"","billing_address":{"phone":"mobile","city":"city","country":"country","address_1":"address","first_name":"abc","last_name":"","company":"","postcode":"code","address_2":"","state":"state"}}}*/
