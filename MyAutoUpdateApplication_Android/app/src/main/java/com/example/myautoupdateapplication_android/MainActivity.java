package com.example.myautoupdateapplication_android;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.example.myautoupdateapplication_android.App.CHANNEL_1_ID;
public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

        notificationManager = NotificationManagerCompat.from(this);
    }

    public void update_to_latest_version(final View view){
        final TextView textView = (TextView) findViewById(R.id.textView2);
        final String current_version = textView.getText().toString();
        String localhost = "http://10.0.2.2:5000/version?current_version="+current_version;
        System.out.println("URL="+localhost);
        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, localhost, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                String version = null;
                                String location = null;
                                try {
                                    version = response.getString("version");
                                    if(version.equalsIgnoreCase(current_version)){
                                        Context context = getApplicationContext();
                                        CharSequence text = "You are already on the latest version";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(context, text, duration);
                                        toast.show();
                                    }
                                    location = response.getString(("location"));
                                    textView.setText(version);

                                    // Method to show notifications
                                    //get_notification(view);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                        System.out.println("ERROR="+error);
                    }
                });
        queue.add(stringRequest);

    }

    public void get_notification(View view){
        String title = "Updates Available";
        String message = "New Versions of APP are available.";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManager.notify(1, notification);
    }

    public void get_new_versions(View view){
        final TextView textView = (TextView) findViewById(R.id.textView2);
        final Spinner sItems = (Spinner) findViewById(R.id.spinner);

        String current_version = textView.getText().toString();
        String localhost = "http://10.0.2.2:5000/newVersions?current_version="+current_version;
        System.out.println("URL="+localhost);
        JsonObjectRequest stringRequest =
                new JsonObjectRequest(Request.Method.GET, localhost, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                String version = null;
                                String location = null;
                                JSONObject versions;
                                ArrayList<String> newer_versions = new ArrayList<String>();
                                try {



                                    JSONArray array = response.getJSONArray("newer_versions");
                                    for(int i = 0 ; i < array.length() ; i++){
                                        newer_versions.add("version "+array.getJSONObject(i).getString("version"));
                                    }
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                            getApplicationContext(), android.R.layout.simple_spinner_item, newer_versions);

                                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    sItems.setAdapter(adapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    System.out.println(response);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.setText("That didn't work!");
                        System.out.println("ERROR="+error);
                    }
                });
        queue.add(stringRequest);

    }
}