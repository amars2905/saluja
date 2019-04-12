package saluja.com.saluja.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import saluja.com.saluja.R;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.Utility;
import saluja.com.saluja.utilit.WebApi;

import static saluja.com.saluja.utilit.ConstantData.Login_Fragment;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_signUp;
    EditText et_fname, etlname, etemail, etpassword;
    Context ctx;

    String getUserName, getdisplayName, getEmailId, getPassword, getMobileNumber, getConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        setListeners();

    }

    // Initialize all views
    private void initViews() {
        et_fname = (EditText) findViewById(R.id.et_fname);
        etlname = (EditText) findViewById(R.id.et_lname);
        etemail = (EditText) findViewById(R.id.et_email);
        etpassword = (EditText) findViewById(R.id.et_password);
        btn_signUp = (Button) findViewById(R.id.btn_signUp);


        // Setting text selector over textviews

    }

    private void setListeners() {
        btn_signUp.setOnClickListener(this);
        //login_tv.setOnClickListener(this);
    }

    private void regsiterUser() {
        Utility.showLoader(ctx);
        AndroidNetworking.post(WebApi.API_REGISTER)
                .addBodyParameter("email", getEmailId)
                .addBodyParameter("password", getPassword)
                .addBodyParameter("first_name", getdisplayName)
                .addBodyParameter("last_name", getUserName)
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
                        Utility.toastView(ctx, error.toString());
                    }
                });
    }

    private void setResponse(JSONObject response) {

        Utility.toastView(ctx, "Successfully Register please login");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void clear() {
        etemail.setText("");
        et_fname.setText("");
        etlname.setText("");
        etpassword.setText("");
    }

    private void checkValidation() {
        getUserName = et_fname.getText().toString();
        getdisplayName = etlname.getText().toString();
        getEmailId = etemail.getText().toString();
        getPassword = etpassword.getText().toString();

        Pattern p = Pattern.compile(ConstantData.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getUserName.equals("") || getUserName.length() == 0
                || getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0)


            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();


            // Else do signup or do your stuff
        else {
                regsiterUser();


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signUp:
                checkValidation();
                break;

            /*case R.id.already_user:

                break;*/
        }

    }
}
