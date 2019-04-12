package saluja.com.saluja.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import saluja.com.saluja.AppPreference;
import saluja.com.saluja.R;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.SessionManager;
import saluja.com.saluja.utilit.Utility;
import saluja.com.saluja.utilit.WebApi;

import static saluja.com.saluja.SplashScreen.mypreference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static View view;
    private static EditText emailid_et, password_et;
    private static Button loginButton;
    private static TextView forgotPassword;
    private static TextView signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
   ProgressBar loginProgress;
    String getEmailId, getPassword;

    Context ctx;
    //ConnectionDetector connectionDetector;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;
        initViews();
        setListeners();

    }

    private void initViews()
    {
        emailid_et = (EditText) findViewById(R.id.login_emailid);
        password_et = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.loginBtn);
        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        signUp = (TextView) findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) findViewById(R.id.login_layout);
        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            //signUp.setBackgroundResource(csl);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                signUp.setBackgroundTintList(csl);
            }

        } catch (Exception e) {
        }

    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password_et
        show_hide_password
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {

                        // If it is checkec then show password_et else hide
                        // password_et
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password_et.setInputType(InputType.TYPE_CLASS_TEXT);
                            password_et.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password_et
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password_et.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password_et.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password_et

                        }

                    }
                });
    }


    private void checkValidation() {

        getEmailId = emailid_et.getText().toString();
        getPassword = password_et.getText().toString();

        Pattern p = Pattern.compile(ConstantData.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);

            Toast.makeText(this,"Enter both credentials.", Toast.LENGTH_SHORT).show();

        } else if (!m.find()) {

            Toast.makeText(this,"Your Email Id is Invalid.", Toast.LENGTH_SHORT).show();

        } else {
            //boolean internet = connectionDetector.isConnected();
            //if (internet) {
                loginUser();
           /* } else {
                Toast.makeText(this,"No Internet Connection", Toast.LENGTH_SHORT).show();
            }*/
        }
    }

    private void loginUser() {

        Utility.showLoader(ctx);
        AndroidNetworking.get(WebApi.API_LOGIN)
                .addPathParameter("email", getEmailId)
                .addPathParameter("password", getPassword)
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utility.hideLoader();
                        setResponse(response);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utility.hideLoader();
                    }
                });
    }

    private void setResponse(JSONObject response) {

        try {
            String status = response.getString("status");
            Log.e("response", response.toString() );
            if (status.equals("ok")) {

                JSONObject user_obj = response.getJSONObject("user");
                String user_id = user_obj.getString("id");
                String username = user_obj.getString("username");
                String user_email = user_obj.getString("email");
                String diaplay_name = user_obj.getString("displayname");
                String fname = user_obj.getString("firstname");
                String lname = user_obj.getString("lastname");

                AppPreference.setStringPreference(ctx, Constant.USERNAME, fname + " " + lname);
                AppPreference.setBooleanPreference(ctx, Constant.IS_LOGIN, true);

                SharedPreferences.Editor editor = getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                editor.putString("user_id", user_id);
                editor.apply();

                sessionManager.createLoginSession(user_id, username, diaplay_name, user_email);
                startActivity(new Intent(ctx, MainActivity.class));
                finish();

            } else if (status.equals("error")) {
                String error = response.getString("error");
                Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(LoginActivity.this, "ok", Toast.LENGTH_SHORT).show();

            }
            clear();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        emailid_et.setText("");
        password_et.setText("");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.forgot_password:

                break;
            case R.id.createAccount:

                break;
        }

    }
}
