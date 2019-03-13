package saluja.com.saluja.ui.fragment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import saluja.com.saluja.Api.HttpHandler;
import saluja.com.saluja.AppPreference;
import saluja.com.saluja.R;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.database.DatabaseHandler;
import saluja.com.saluja.database.HelperManager;
import saluja.com.saluja.model.ProductDetail;
import saluja.com.saluja.utilit.Alerts;

import static saluja.com.saluja.Api.URLs.URL_PRODUCT_DETAIL;
import static saluja.com.saluja.ui.fragment.activity.MainActivity.cart_count;
import static saluja.com.saluja.ui.fragment.activity.MainActivity.cart_number;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvDetail, productName, productOldPrice, productNewPrice , tvPriceDetail;
    ImageView productImage;
    ProgressDialog pDialog;
    String id,name,description,date_modified,regular_price,sale_price,price,image1,price_html;
    Button btnAddtoCart;
    ProductDetail productDetail;
    private DatabaseHandler databaseCart;
    HelperManager helperManager;
    private String DATABASE_CART = "cart.db";
    Context ctx;
    private ArrayList<ProductDetail> cartProductList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        ctx = this;
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        productName = (TextView) findViewById(R.id.productName);
        productOldPrice = (TextView) findViewById(R.id.productOldPrice);
        productNewPrice = (TextView) findViewById(R.id.productnewPrice);
        tvPriceDetail = (TextView) findViewById(R.id.tvPriceDetail);
        productImage = (ImageView) findViewById(R.id.productImage);
        btnAddtoCart = (Button) findViewById(R.id.btnAddtoCart);
        id = getIntent().getStringExtra("Product_ID");
        Log.e("Product_ID" , id);
        databaseCart = new DatabaseHandler(ctx, DATABASE_CART);
        cartProductList.clear();
        if (databaseCart.getContactsCount()) {
            cartProductList = databaseCart.getAllUrlList();
        }
        new GetContacts().execute();

        btnAddtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });
    }

    // Async task class to get json by making HTTP call
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(ProductDetailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(URL_PRODUCT_DETAIL+id);
            //Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    //converting response to json object
                    JSONObject object = new JSONObject(jsonStr);
                    id = object.getString("id");
                    name = object.getString("name");
                    date_modified = object.getString("date_modified");
                    description = object.getString("description");
                    price = object.getString("price");
                    regular_price = object.getString("regular_price");
                    sale_price = object.getString("sale_price");
                    price_html = object.getString("price_html");
                    image1 = null;
                    JSONArray image_array = object.getJSONArray("images");
                    if (image_array.length() > 0) {
                        JSONObject objectimg = image_array.getJSONObject(0);
                        image1 = objectimg.getString("src");
                    }
                    Log.e("title " + name, "image_url " + description);
                    Log.e("music_link " + date_modified, "Img" + price);

                    productDetail = new ProductDetail();
                    productDetail.setId(id);
                    productDetail.setImage(image1);
                    productDetail.setDescription(description);
                    productDetail.setPrice(sale_price);
                    productDetail.setName(name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ProductDetailActivity.this, "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDetail.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
            } else {
                tvDetail.setText(Html.fromHtml(description));
            }
            productName.setText("" + name);
            productOldPrice.setText(regular_price);
            productNewPrice.setText(sale_price);
            Picasso.with(ProductDetailActivity.this).load(image1).placeholder(R.drawable.mobile_icon).error(R.drawable.mobile_icon).into(productImage);

        }
    }


    private void addtoCart() {
        /*********************************************************************************************************/
        if (databaseCart.getContactsCount()) {
            cartProductList = databaseCart.getAllUrlList();
        }

        if (cartProductList.size() > 2) {
            Alerts.show(ctx, "Cart full");
        } else {
            if (cartProductList.size() > 0) {
                if (databaseCart.verification(productDetail.getId())) {
                    Alerts.show(ctx, "Already added to Cart");
                } else {
                  //  productDetail.setSelected_size(selected_size);
                  //  productDetail.setSelected_color(selected_color);
                    cart_count = cart_count + 1;
                   cart_number.setText("" + cart_count);
                    AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, cart_count);
                    Alerts.show(ctx, "Added to Cart");
                    databaseCart.addItemCart(productDetail);
                }
            } else {
               // productDetail.setSelected_size(selected_size);
              //  productDetail.setSelected_color(selected_color);
                cart_count = cart_count + 1;
                cart_number.setText("" + cart_count);
               AppPreference.setIntegerPreference(ctx, Constant.CART_ITEM_COUNT, cart_count);
                Alerts.show(ctx, "Added to Cart");
                databaseCart.addItemCart(productDetail);
            }
        }
    }
}
