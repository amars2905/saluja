package saluja.com.saluja.ui.fragment.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import saluja.com.saluja.utilit.SessionManager;

public class AddtoCart extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);

    }

}
