package com.example.ryhanahmedtamim.ccps;

import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ContractOfStaffActivity extends AppCompatActivity {


    MainUrl Url = new MainUrl();
    String mainUrl = Url.getUrl();


    ListView listView;
   // EditText editText;
    String url =mainUrl +"contractualcompanypaymentsystem/public/api/all/contract_details";
   ArrayList<String> sendId;

    JSONObject jsonObject1= new JSONObject();

    JSONObject[] jsonObjects = new JSONObject[100];
   // String[] contract = {""};
    ArrayList<String> contract;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_of_staff);


       // editText = (EditText) findViewById(R.id.editText);
        listView = (ListView) findViewById(R.id.contractOfStaff);


        contract = new ArrayList<String>();
        sendId = new ArrayList<String>();
        String massage = "ERROR";
        JSONObject jsonObject = null;
        String URL = url;


        String js = getIntent().getStringExtra("JSON_OBJECT").toString();

        try {
            jsonObject = new JSONObject(js);
        }catch (Exception e){

        }

        if(Build.VERSION.SDK_INT>=  10){

            StrictMode.ThreadPolicy policy = StrictMode.ThreadPolicy.LAX;

            StrictMode.setThreadPolicy(policy);
        }


        try {


            HttpParams params = new BasicHttpParams();

            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

            HttpClient httpClient = new DefaultHttpClient(params);

            HttpGet httpGet = new HttpGet(URL);





            // 6. set httpPost Entity



            // httpPost.setEntity();



            HttpResponse response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();


            String json_string = EntityUtils.toString(response.getEntity());


           // JSONObject jsonObject1 = new JSONObject(json_string);

            JSONArray jsonArray = new JSONArray(json_string);

          //  System.out.print(jsonArray);


            //editText.setText(jsonArray.getJSONObject(0).getString("staff_id").toString());


           // String rolename = jsonObject.getString("rolename");

            JSONObject jsonObject2 = new JSONObject();

            String id = jsonObject.getString("id");

            int indx=0;


            for(int i=0; i<jsonArray.length(); i++)
            {

                // String s = jsonArray.get();

                // editText.setText("Hello");


                String s =  jsonArray.getJSONObject(i).toString();

                jsonObject1 = new  JSONObject (s);


                if(id.equals(jsonObject1.getString("staff_id")) && jsonObject1.getString("Active").equals("1")){

                         String user = jsonObject1.getString("client_id");

                        // editText.setText(user);


                        String usrUrl = mainUrl+"contractualcompanypaymentsystem/public/api/get/user/"+user;

                    //editText.setText(usrUrl);
                        try{

                            HttpParams httpParams = new BasicHttpParams();

                            httpParams.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_0);

                            HttpClient httpClient1 = new DefaultHttpClient(httpParams);

                            HttpGet httpGet1 = new HttpGet(usrUrl);


                            HttpResponse response1 = httpClient1.execute(httpGet1);

                            HttpEntity httpEntity1 = response1.getEntity();


                            String json_string1 = EntityUtils.toString(response1.getEntity());
                            // JSONObject jsonObject1 = new JSONObject(json_string);

                           jsonObject2 = new JSONObject(json_string1);


                        }catch (Exception e){

                        }


                    contract.add( "Contract Id No: "+jsonObject1.getString("id")+" Client Name : " + jsonObject2.getString("name"));

                    sendId.add(jsonObject1.getString("id"));
                    indx++;
                }

            }






            ListAdapter listAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,contract);

            listView.setAdapter(listAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(ContractOfStaffActivity.this, Middle_Activity_Staff.class);

                    intent.putExtra("id", sendId.get(position));
                    startActivity(intent);

                }
            });





        } catch (Exception e) {

        }












    }
}
