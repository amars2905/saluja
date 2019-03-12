package saluja.com.saluja.utilit;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class BaseFragment extends Fragment {

    /*public RetrofitApiClient retrofitApiClient;
    public RetrofitApiClient retrofitRxClient;
    public ConnectionDetector1 cd;*/
    public Context mContext;
    public Activity activity;

    public BaseFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = getActivity();
        activity = getActivity();
        /*cd = new ConnectionDetector1(mContext);
        retrofitRxClient = RetrofitService.getRxClient();
        retrofitApiClient = RetrofitService.getRetrofit();*/
        return null;
    }
}