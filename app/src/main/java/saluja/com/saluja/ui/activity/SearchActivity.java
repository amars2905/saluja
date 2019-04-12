package saluja.com.saluja.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import saluja.com.saluja.Api.HttpHandler;
import saluja.com.saluja.ProductModel;
import saluja.com.saluja.R;
import saluja.com.saluja.adapter.SearchListAdapter;
import saluja.com.saluja.utilit.BaseActivity;

import static saluja.com.saluja.Api.URLs.URL_PRODUCT_LIST;


public class SearchActivity extends BaseActivity implements View.OnClickListener, SearchListAdapter.SearchAdapterListener {

    private List<ProductModel> allUserLists = new ArrayList<>();
    private RecyclerView gridDetailrclv;
    private SearchListAdapter searchListAdapter;
    private EditText edtSearch;
    private String strUserId = "";
    private ImageView backActivity;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();

    }


    private void init() {
        //strUserId = AppPreference.getStringPreference(mContext, Constant.User_Id);
        edtSearch = findViewById(R.id.et_search);
        //findViewById(R.id.imgBack).setOnClickListener(this);

        gridDetailrclv = findViewById(R.id.gridDetailrclv);
        backActivity = findViewById(R.id.ic_back_search);
        backActivity.setOnClickListener(this);
        gridDetailrclv.setHasFixedSize(true);

        searchListAdapter = new SearchListAdapter(allUserLists, mContext, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        gridDetailrclv.setLayoutManager(gridLayoutManager);
        gridDetailrclv.setItemAnimator(new DefaultItemAnimator());
        gridDetailrclv.setAdapter(searchListAdapter);

        new GetContacts().execute();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            allUserLists.clear();
            // Showing progress dialog
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(URL_PRODUCT_LIST);
            //Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                //Log.e(TAG, "Json not empity " );
                try {
                    //converting response to json object
                    JSONArray obj = new JSONArray(jsonStr);
                    for (int i = 0; i < obj.length(); i++) {
                           /* JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                            for (int j = 0; j < jsonArray1.length(); j++) {*/
                        JSONObject object = obj.getJSONObject(i);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String date_modified = object.getString("date_modified");
                        String description = object.getString("description");
                        String price = object.getString("price");
                        String regular_price = object.getString("regular_price");
                        String sale_price = object.getString("sale_price");
                        String price_html = object.getString("price_html");
                        String image1 = null;
                        JSONArray image_array = object.getJSONArray("images");
                        if (image_array.length() > 0) {
                            JSONObject objectimg = image_array.getJSONObject(0);
                            image1 = objectimg.getString("src");
                        }

                        ProductModel productModel = new ProductModel();
                        productModel.setPro_id(id);
                        productModel.setPro_name(name);
                        productModel.setPro_price(sale_price);
                        productModel.setOld_price(regular_price);
                        productModel.setPro_image(image1);
                        allUserLists.add(productModel);
                        Log.e("title " + name, "image_url " + description);
                        Log.e("music_link " + date_modified, "Img" + price);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
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
           /* ProductAdapter adapter = new ProductAdapter(productModelArrayList, getActivity());
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rv_home_recyclerview.setLayoutManager(layoutManager);
            rv_home_recyclerview.setItemAnimator(new DefaultItemAnimator());
            rv_home_recyclerview.setAdapter(adapter);*/
            searchListAdapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_product1:
                int position = Integer.parseInt(v.getTag().toString());

                break;
            case R.id.ic_back_search:
                finish();
                break;
        }

    }

    @Override
    public void onSearchSelected(ProductModel contact) {
        Intent intent = new Intent(mContext , ProductDetailActivity.class);
        intent.putExtra("Product_ID", contact.getPro_id());
        startActivity(intent);

        /*Intent postUserId = new Intent(mContext, UserProfileActivity.class);
        postUserId.putExtra("fan_id", contact.getUserId());
        startActivity(postUserId);*/
    }
}
