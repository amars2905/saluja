package saluja.com.saluja.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import saluja.com.saluja.Api.RequestHandler;
import saluja.com.saluja.Api.URLs;
import saluja.com.saluja.AppPreference;
import saluja.com.saluja.R;
import saluja.com.saluja.constant.Constant;
import saluja.com.saluja.utilit.ConstantData;
import saluja.com.saluja.utilit.Utility;
import saluja.com.saluja.utilit.WebApi;

import static saluja.com.saluja.SplashScreen.mypreference;
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
              //  regsiterUser();
            RegisterUser ru = new RegisterUser();
            ru.execute();
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


    class RegisterUser extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            RequestHandler requestHandler = new RequestHandler();
            //creating request parameters
            HashMap<String, String> params = new HashMap<>();
            params.put("email", getEmailId);
            params.put("password", getPassword);
            params.put("first_name", getdisplayName);
            params.put("last_name",getUserName);
            //returing the response
            return requestHandler.sendPostRequest(WebApi.API_REGISTER, params);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displaying the progress bar while user registers on the server
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //hiding the progressbar after completion
           // progressBar.setVisibility(View.GONE);

            try {
                //converting response to json object
                //JSONArray mJsonArray = new JSONArray(s);


                JSONObject movieObject = new JSONObject(s);
                String id = movieObject.getString("id");
                Log.e("ID ",id);

               /* JSONObject user_obj = new JSONObject(s);
                Log.e("Json ",user_obj.toString());

                String user_id = user_obj.getString("id");
                String user_email = user_obj.getString("email");

                //AppPreference.setStringPreference(ctx, Constant.USERNAME, fname + " " + lname);
                AppPreference.setBooleanPreference(ctx, Constant.IS_LOGIN, true);
                AppPreference.setStringPreference(ctx, Constant.USER_ID, user_id);

                SharedPreferences.Editor editor = getSharedPreferences(mypreference, MODE_PRIVATE).edit();
                editor.putString("user_id", user_id);
                editor.apply();*/

               // sessionManager.createLoginSession(user_id, username, diaplay_name, user_email);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
