package com.example.ryhanahmedtamim.ccps;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PendingActivityClient extends AppCompatActivity {


    MainUrl Url = new MainUrl();


    String mainUrl = Url.getUrl();
    ArrayList<String> contract;
    ArrayList<String> sendId;
    ListView listView;
    TextView textView;
    JSONObject jsonObject1 = new JSONObject();
    String url =mainUrl+"contractualcompanypaymentsystem/public/api/all/pendingduty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_activity_client);

        listView = (ListView) findViewById(R.id.pendingDuty);
        textView = (TextView) findViewById(R.id.textView6);
        final String dutyId = getIntent().getStringExtra("id");
        contract = new ArrayList<String>();
        sendId = new ArrayList<String>();

        String massage = "ERROR";

        String URL = url;




        if(Build.VERSION.SDK_INT>=  10){

            StrictMode.ThreadPolicy policy = StrictMode.ThreadPolicy.LAX;

            StrictMode.setThreadPolicy(policy);
        }

        try {

           // textView.setText(dutyId);
            HttpParams params = new BasicHttpParams();

            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

            HttpClient httpClient = new DefaultHttpClient(params);

            HttpGet httpGet = new HttpGet(URL);

            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();

            String json_string = EntityUtils.toString(response.getEntity());

            JSONArray jsonArray = new JSONArray(json_string);

            for(int i=0; i<jsonArray.length(); i++)
            {

                String s =  jsonArray.getJSONObject(i).toString();

                jsonObject1 = new  JSONObject (s);

                if(dutyId.equals(jsonObject1.getString("contract_id")) && (jsonObject1.getString("approved_by_client").equals("0"))){
                    contract.add("Contract Id No: "+jsonObject1.getString("contract_id")+" Duty Date : " + jsonObject1.getString("duty_date"));
                    sendId.add(jsonObject1.getString("id"));

                }

            }

            ListAdapter listAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,contract);

            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String posturl = mainUrl + "contractualcompanypaymentsystem/public/api/stuff_duty/store/client/"+sendId.get(position);

                    try {

                        HttpParams params = new BasicHttpParams();

                        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

                        HttpClient httpClient = new DefaultHttpClient(params);

                       // HttpPost httpPost = new HttpPost(loginURL);

                        HttpGet httpGet1 = new HttpGet(posturl);



                        HttpResponse response = httpClient.execute(httpGet1);

                        HttpEntity httpEntity = response.getEntity();

                        String message = EntityUtils.toString(response.getEntity());

                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();


                    }catch (Exception e){

                    }

                    Intent intent = new Intent(PendingActivityClient.this,PendingActivityClient.class);
                    intent.putExtra("id", dutyId);
                    startActivity(intent);

                }
            });
        } catch (Exception e) {

        }











    }
}
