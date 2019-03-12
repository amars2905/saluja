package saluja.com.saluja.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import saluja.com.saluja.Api.HttpHandler;
import saluja.com.saluja.ProductModel;
import saluja.com.saluja.R;
import saluja.com.saluja.adapter.ProductAdapter;
import saluja.com.saluja.adapter.SlidingImage_Adapter1;

import static android.content.ContentValues.TAG;
import static saluja.com.saluja.Api.URLs.URL_PRODUCT_LIST;

public class HomeFragment extends Fragment {
    CirclePageIndicator indicator;
    private static ViewPager mPager;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<ProductModel> productModelArrayList = new ArrayList<>();
    RecyclerView rv_home_recyclerview;
    ProgressDialog pDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initXml(view);
        return view;
    }

    private void initXml(View view) {
        mPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        rv_home_recyclerview = (RecyclerView) view.findViewById(R.id.rv_home_recyclerview);
        ImagesArray.add("data:image/png;base64,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");
        ImagesArray.add("data:image/jpeg;base64,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");
        ImagesArray.add("https://rukminim1.flixcart.com/flap/960/960/image/4b756a94aac75049.jpg?q=50");

        init(3);
        new GetContacts().execute();
    }

    private void init(int bannerLength) {
        SlidingImage_Adapter1 image_adapter1 = new SlidingImage_Adapter1(getActivity(), ImagesArray);
        mPager.setAdapter(image_adapter1);
        indicator.setViewPager(mPager);
        image_adapter1.notifyDataSetChanged();
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(3 * density);
        NUM_PAGES = bannerLength;
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

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
           productModelArrayList.clear();
            // Showing progress dialog
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
                            productModelArrayList.add(productModel);
                                Log.e("title " + name, "image_url " + description);
                                Log.e("music_link " + date_modified, "Img" + price);
                        }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
               // Log.e(TAG, "Couldn't get json from server.");
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
            rv_home_recyclerview.setLayoutManager(layoutManager);
            rv_home_recyclerview.setItemAnimator(new DefaultItemAnimator());
            rv_home_recyclerview.setAdapter(adapter);
        }
    }
}
