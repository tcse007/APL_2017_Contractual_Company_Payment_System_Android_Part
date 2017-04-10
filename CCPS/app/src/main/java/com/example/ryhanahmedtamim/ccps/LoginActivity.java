package com.example.ryhanahmedtamim.ccps;

import android.app.DownloadManager;
import android.bluetooth.le.AdvertiseData;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.*;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    EditText userName;
    EditText password;
    MainUrl Url = new MainUrl();
    String mainUrl = Url.getUrl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);

        final String URL = mainUrl+"contractualcompanypaymentsystem/public/admin/login";

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginURL = URL;

                String massage = "ERROR";

                if(Build.VERSION.SDK_INT>=  10){

                    StrictMode.ThreadPolicy policy = StrictMode.ThreadPolicy.LAX;

                    StrictMode.setThreadPolicy(policy);
                }

                try {

                    HttpParams params = new BasicHttpParams();

                    params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

                    HttpClient httpClient = new DefaultHttpClient(params);

                    HttpPost httpPost = new HttpPost(loginURL);

                    JSONObject jsonObject = new JSONObject();

                    jsonObject.accumulate("username", userName.getText().toString());

                    jsonObject.accumulate("password", password.getText().toString());

                    String json = jsonObject.toString();


                    StringEntity se = new StringEntity(json);

                    httpPost.setEntity(se);

                    httpPost.setHeader("Content-type", "application/json");

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity httpEntity = response.getEntity();


                    String json_string = EntityUtils.toString(response.getEntity());
                    JSONObject jsonObject1 = new JSONObject(json_string);

                    String rolename = jsonObject1.getString("rolename");

                    if(rolename.equals("Staff")){


                        Intent intent = new Intent(LoginActivity.this,ContractOfStaffActivity.class);

                        intent.putExtra("JSON_OBJECT",json_string);

                        startActivity(intent);
                        //Toast.makeText(getApplicationContext(),rolename,Toast.LENGTH_SHORT).show();
                    }
                    else if(rolename.equals("Client")){

                       // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,ContractOfClientActivity.class);

                        intent.putExtra("JSON_OBJECT",json_string);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(getApplicationContext(),massage,Toast.LENGTH_SHORT).show();
                    }




                } catch (Exception e) {

                }



            }

        });




    }


}
