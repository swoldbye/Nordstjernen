//package com.example.skibslogapp.Model.DB;
//
//import android.app.ProgressDialog;
//import android.widget.EditText;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.skibslogapp.Model.LogInstans;
//import com.example.skibslogapp.viewControl.AddItem;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//public class DBservices {
//    public void addItemToSheet(EditText editTextItemName, EditText editTextBrand, final LogData_frag logData) {
//
//
//        final String name = editTextItemName.getText().toString().trim();
//        final String brand = editTextBrand.getText().toString().trim();
//        final AddItem GuiResponse = new AddItem(logData);
//        final ProgressDialog Loading = GuiResponse.load();
//        //TODO: Loading is not ideal as we pass something which should be in front-end trough backend.
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, "The Deploied URL goes here",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        GuiResponse.response(response, Loading);
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> parmas = new HashMap<>();
//
//                //here we are passing the params
//                parmas.put("action","addItem");
//                parmas.put("itemName",name);
//                parmas.put("brand",brand);
//
//                return parmas;
//            }
//        };
//        int socketTimeOut = 50000;// Change as you please .. time atm: 50 seconds
//
//        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
//        stringRequest.setRetryPolicy(retryPolicy);
//
////        RequestQueue queue = Volley.newRequestQueue(logData);
//
////        RequestQueue queue = Volley.newRequestQueue(log);
//
////        queue.add(stringRequest);
//
//
//    }
//}
