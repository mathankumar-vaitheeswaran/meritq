package com.codebulls.MeritQ.helpers;

import List.Invoker.ListRequestAPIClient;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import meritQ.request.clientsdk.MeritCheckClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class helperAPIDynamo extends AsyncTask<String, Void, String> {

    private Context mContext;
    private Boolean responseSuccess = false;

    public helperAPIDynamo(Context context){

        this.mContext = context;

    }

    @Override
    protected String doInBackground(String... params) {
        String passRequestType = params[0];
        String passRequestId = params[1];
        String passStatus = params[2];
        //gateway
        ApiClientFactory factory = new ApiClientFactory();

        final ListRequestAPIClient listRequestAPIClient = factory.build(ListRequestAPIClient.class);

        JSONObject rootObject = new JSONObject();
        try {
            rootObject.put("LRequestType", passRequestType);
            rootObject.put("LRequestId", passRequestId);
            rootObject.put("LStatus", passStatus);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest request = new ApiRequest();
        request.addHeader("x-API-key", "PiixubgHvU2tXsYXO2TgP2IbGlaaxfcX2ZKBFBcY");
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Content-Length", String.valueOf(rootObject.toString().length()));
        //request.addHeader("Host", "osf22bt6r5.execute-api.us-east-2.amazonaws.com");
        //request.addHeader("x-Amz-Date", "20181207T002146Z");
        //request.addHeader("Authorization", "AWS4-HMAC-SHA256 Credential=/20181207/us-east-2/execute-api/aws4_request, SignedHeaders=content-length;content-type;host;x-amz-date;x-api-key, Signature=ca88c461f452b9e6666d713377f1116bdeb6869f4f9d6376021e552dc456c9a4");
        request.withBody(rootObject.toString());

        Log.v("Request: ", request.getHeaders().toString());
        //android.util.Log.v("Response Code: ", String.valueOf(responseCode));
        ApiResponse apiResponse = listRequestAPIClient.execute(request);

        int responseCode = apiResponse.getStatusCode();

        String responseBody = "Empty Response";
        responseSuccess = false;
        try {
            responseBody = convertStreamToString(apiResponse.getContent());
            responseSuccess = true;
        } catch (Exception e){
            responseBody = "Failed to parse response";
            Log.d("ERROR: ", "Failed reading response");
        }
        Log.d("RESPONSE STATUS", responseBody);


        return responseBody;
    }

    @Override
    public void onPostExecute(String result) {
        String recordCount = "";
        if (responseSuccess) {
            try {
                JSONObject responseObject = new JSONObject(result);
                String bodyResponse = "Empty Response";
                recordCount = responseObject.getString("count");
                bodyResponse = responseObject.getString("body");
                JSONObject bodyObject = new JSONObject(bodyResponse);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (Integer.parseInt(recordCount) > 0) {
            AlertDialog.Builder goAlert = new AlertDialog.Builder(mContext);
            goAlert.setTitle("Response from Dynamo API");
            goAlert.setMessage("Number of Requests Fetched is " + recordCount);
            goAlert.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            goAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alertLogin = goAlert.create();
            alertLogin.show();
        }

        //Toast.makeText(mContext, "JSON: " + finalJSON, Toast.LENGTH_LONG).show();
        Log.d("OUTPUT", result);
        Log.d("onPostExecute", "complete");
    }

    private String convertStreamToString(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reader.close();
        }
        return sb.toString();
    }

}