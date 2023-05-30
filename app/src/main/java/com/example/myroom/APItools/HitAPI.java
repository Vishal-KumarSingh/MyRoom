package com.example.myroom.APItools;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myroom.PermanantStorage.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class HitAPI {
    public static final String url = "https://room-manager-api.000webhostapp.com/";
    public void sendJsonPostRequest(String path , Context context , JSONObject jsonobj ,APICallback callback, boolean Addtoken) {

        try {

            // Make new json object and put params in it

            if(Addtoken){
                jsonobj.put("token", Session.getToken(context));
            }



            // Building a request
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    // Using a variable for the domain is great for testing
                    url+path,
                    jsonobj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("SelfDebug" , "Response from Server: " + response.toString());
                            try {
                                if(!response.get("token").toString().equals(Session.getToken(context))){
                                    Session.setToken(context , response.get("token").toString());
                                }
                                if(response.get("toast").toString().equals("")){
                                    Toast.makeText(context, response.get("toast").toString(), Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            callback.response(response);
                            // Handle the response


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("SelfDebug" , "Error from Server: " + error.toString() + " "+String.valueOf(error.networkResponse.statusCode));
                            if(error.networkResponse.data != null){
                                try {
                                    String body = new String(error.networkResponse.data,"UTF-8");
                                    Log.d("SelfDebug" , "Error Data : "+body);

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }

                            }
                            // Handle the error

                        }
                    });

            /*

              For the sake of the example I've called newRequestQueue(getApplicationContext()) here
              but the recommended way is to create a singleton that will handle this.

              Read more at : https://developer.android.com/training/volley/requestqueue

              Category -> Use a singleton pattern

            */
            Log.d("SelfDebug" , "New Request to "+url+path);
            Log.d("SelfDebug" , "Raw Request data "+jsonobj.toString());
            Volley.newRequestQueue(context).
                    add(request);

        } catch (JSONException ex) {
            // Catch if something went wrong with the params
        }

    }
}
