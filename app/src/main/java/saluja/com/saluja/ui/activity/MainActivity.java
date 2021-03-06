package saluja.com.saluja.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import saluja.com.saluja.AppPreference;
import saluja.com.saluja.BuildConfig;
import saluja.com.saluja.R;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.ui.fragment.HomeFragment;
import saluja.com.saluja.ui.fragment.MobileFragment;
import saluja.com.saluja.ui.fragment.AppleFragment;
import saluja.com.saluja.ui.fragment.CartFragment;
import saluja.com.saluja.ui.fragment.MiFragment;
import saluja.com.saluja.ui.fragment.OppoFragment;
import saluja.com.saluja.ui.fragment.SamsungFragment;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.Utility;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    Fragment fragment;
    Context context;
    LinearLayout btnHome,btnMobile,btnVivo,btnSamsung,btnApple,btnOppo,btnMoto,btnMi,btnLenovo;
    RelativeLayout rlCart;
    public static TextView cart_number;
    public static int cart_count = 0;
    public ImageView btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new HomeFragment();
        Utility.setFragment(fragment, context, ConstantData.HOME);
        cart_count = AppPreference.getIntegerPreference(context, Constant.CART_ITEM_COUNT); //0 is the default value.

        init();

    }


    private void init(){
        cart_number = findViewById(R.id.cart_number);
        btnSearch = findViewById(R.id.btnSearch);
        cart_number.setText("" + cart_count);
        btnHome = (LinearLayout)findViewById(R.id.btnHome);
        btnMobile = (LinearLayout)findViewById(R.id.btnMobile);
        btnVivo = (LinearLayout)findViewById(R.id.btnVivo);
        btnSamsung = (LinearLayout)findViewById(R.id.btnSamsung);
        btnApple = (LinearLayout)findViewById(R.id.btnApple);
        btnMoto = (LinearLayout)findViewById(R.id.btnMoto);
        btnOppo = (LinearLayout)findViewById(R.id.btnOppo);
        btnMi = (LinearLayout)findViewById(R.id.btnMi);
        btnLenovo = (LinearLayout)findViewById(R.id.btnLenovo);
        rlCart = (RelativeLayout) findViewById(R.id.rlCart);

        btnHome.setOnClickListener(this);
        btnMobile.setOnClickListener(this);
        btnVivo.setOnClickListener(this);
        btnSamsung.setOnClickListener(this);
        btnApple.setOnClickListener(this);
        btnMoto.setOnClickListener(this);
        btnOppo.setOnClickListener(this);
        btnMi.setOnClickListener(this);
        btnLenovo.setOnClickListener(this);
        rlCart.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem itemLogin = menu.findItem(R.id.action_settings);
        MenuItem itemLogout = menu.findItem(R.id.action_logout);

        if (AppPreference.getBooleanPreference(context, Constant.IS_LOGIN)){
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        }else{
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }else if (id == R.id.action_logout){

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            //webView.loadUrl("http://salujacart.com/index.php/product-category/clothing/accessories/");
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, CartFragment.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            //webView.loadUrl("http://salujacart.com/index.php/product-category/mobile/");
        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Saluja Cart");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                Toast.makeText(MainActivity.this," "+e.toString(),Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnHome :
                fragment = new HomeFragment();
                Utility.setFragment(fragment, context, ConstantData.HOME);
                break;
            case R.id.btnMobile :
                fragment = new MobileFragment();
                Utility.setFragment(fragment, context, ConstantData.MOBILE);
                break;
            case R.id.btnSamsung :
                fragment = new SamsungFragment();
                Utility.setFragment(fragment, context, ConstantData.SAMSUNG);
                break;
            case R.id.btnVivo :
                fragment = new MobileFragment();
                Utility.setFragment(fragment, context, ConstantData.MOBILE);
                break;
            case R.id.btnApple :
                fragment = new AppleFragment();
                Utility.setFragment(fragment, context, ConstantData.APPLE);
                break;
            case R.id.btnMoto :
                fragment = new HomeFragment();
                Utility.setFragment(fragment, context, ConstantData.HOME);
                break;
            case R.id.btnMi :
                fragment = new MiFragment();
                Utility.setFragment(fragment, context, ConstantData.MI);
                break;
            case R.id.btnOppo :
                fragment = new OppoFragment();
                Utility.setFragment(fragment, context, ConstantData.OPPO);
                break;
            case R.id.btnLenovo :
                fragment = new HomeFragment();
                Utility.setFragment(fragment, context, ConstantData.MOBILE);
                break;

            case R.id.rlCart :
                Intent intent1 = new Intent(this, CartFragment.class);
                startActivity(intent1);
                break;

            case R.id.btnSearch :
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cart_count = AppPreference.getIntegerPreference(context, Constant.CART_ITEM_COUNT); //0 is the default value.
        cart_number.setText("" + cart_count);
    }

    private void doLogout(){
        new AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppPreference.setBooleanPreference(context, Constant.IS_LOGIN, false);
                        Intent intent = new Intent(context, MainActivity.class);
                        /*intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);*/
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("NO", null)
                .create()
                .show();
    }
}
