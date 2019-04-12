package saluja.com.saluja.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import saluja.com.saluja.R;
import saluja.com.saluja.database.DatabaseHandler;
import saluja.com.saluja.ui.fragment.ShoppingFragment;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.Utility;

public class CheckOutActivity extends AppCompatActivity {
    Context ctx;
    ImageView back_iv;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private FrameLayout viewPager;

    public DatabaseHandler databaseCart;
    //private GoogleApiClient googleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        initXml();
    }
    private void initXml() {
        ctx = this;
        back_iv = findViewById(R.id.iv_chekout_back);
        toolbar = (Toolbar) findViewById(R.id.toolbar_checkout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setFragment();
    }

    private void setFragment() {
        ShoppingFragment fragment = new ShoppingFragment(ctx);
        Utility.setFragment1(fragment, ctx, ConstantData.HOME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 567) {
            setFragment();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
