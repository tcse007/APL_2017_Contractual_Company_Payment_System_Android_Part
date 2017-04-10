package com.example.ryhanahmedtamim.ccps;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientActivity extends AppCompatActivity {



    MainUrl Url = new MainUrl();
    String mainUrl = Url.getUrl();


    Button submitButton;
    TextView textView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        submitButton = (Button) findViewById(R.id.clientSubmitButton);
        textView = (TextView) findViewById(R.id.textView4);
//        String date1 =  (new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime())) ;
//
//        textView.setText(date1);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                String date =  (new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));


                String id = getIntent().getStringExtra("id");

                String URL = mainUrl+"contractualcompanypaymentsystem/public/api/stuff_duty/store/client";


                String loginURL = URL;

                InputStream inputStream = null;
                String massage = "erro";

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

                    jsonObject.accumulate("contract_id", id);

                    jsonObject.accumulate("duty_date", date);

                    String json = jsonObject.toString();


                    StringEntity se = new StringEntity(json);

                    // 6. set httpPost Entity
                    httpPost.setEntity(se);


                    // httpPost.setEntity();
                    httpPost.setHeader("Content-type", "application/json");



                    HttpResponse response = httpClient.execute(httpPost);



                    HttpEntity httpEntity = response.getEntity();


                    String json_string = EntityUtils.toString(response.getEntity());

                    Toast.makeText(getApplicationContext(),json_string,Toast.LENGTH_SHORT).show();
//                    JSONObject jsonObject1 = new JSONObject(json_string);
//
//                    String massage1 = jsonObject1.toString();
//
//                    Toast.makeText(getApplicationContext(),massage1,Toast.LENGTH_LONG).show();





                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(),"Connection Error",Toast.LENGTH_LONG).show();

                }


            }
        });
    }
}
