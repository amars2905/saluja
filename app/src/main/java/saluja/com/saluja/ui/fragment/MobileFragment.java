package saluja.com.saluja.ui.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import saluja.com.saluja.Api.HttpHandler;
import saluja.com.saluja.ProductModel;
import saluja.com.saluja.R;
import saluja.com.saluja.adapter.ProductAdapter;

import static android.support.constraint.Constraints.TAG;
import static saluja.com.saluja.Api.URLs.URL_PRODUCT_LIST;

public class MobileFragment extends Fragment {
    private View view;
    RecyclerView rv_mobilelist;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    ProgressDialog pDialog;
    public MobileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_mobile, container, false);

        init();
        return view;
    }

    public void init(){
        rv_mobilelist = (RecyclerView)view.findViewById(R.id.rv_mobilelist);
        new GetContacts().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            productModelArrayList.clear();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(URL_PRODUCT_LIST);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                Log.e(TAG, "Json not empity " );
                try {
                    //converting response to json object
                    JSONArray obj = new JSONArray(jsonStr);
                    for (int i = 0; i < obj.length(); i++) {
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
                        productModelArrayList.add(productModel);
                        Log.e("title " + name, "image_url " + description);
                        Log.e("music_link " + date_modified, "Img" + price);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
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
            ProductAdapter adapter = new ProductAdapter(productModelArrayList, getActivity());
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            rv_mobilelist.setLayoutManager(layoutManager);
            rv_mobilelist.setItemAnimator(new DefaultItemAnimator());
            rv_mobilelist.setAdapter(adapter);
        }
    }
}
