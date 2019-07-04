package com.codebulls.MeritQ.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import android.view.View;
import android.widget.*;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.mobileconnectors.apigateway.ApiRequest;
import com.amazonaws.mobileconnectors.apigateway.ApiResponse;
import com.codebulls.MeritQ.NewActivity;
import meritQ.request.clientsdk.MeritCheckClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class helperAPIGood extends AsyncTask<String, Void, String> {

    private Context mContext;
    private Boolean responseSuccess = false;

    public helperAPIGood(Context context){

        this.mContext = context;

    }

    @Override
    protected String doInBackground(String... params) {
        String passCreditAmount = params[0];
        String passCreditDuration = params[1];
        String passCreditPurpose = params[2];
        String passGender = params[3];
        String passHouseType = params[4];
        String passSavingsType = params[5];
        String passCheckingsType = params[6];
        String passAge = params[7];
        String passJobType = params[8];
        String passEmail = params[9];
        //gateway
        ApiClientFactory factory = new ApiClientFactory();

        final MeritCheckClient meritcheckClient = factory.build(MeritCheckClient.class);

        JSONObject rootObject = new JSONObject();
        try {
            rootObject.put("cr_amt", passCreditAmount);
            rootObject.put("cr_dur", passCreditDuration);
            rootObject.put("cr_pur", passCreditPurpose);
            rootObject.put("gender", passGender);
            rootObject.put("house_type", passHouseType);
            rootObject.put("savings_type", passSavingsType);
            rootObject.put("checkings_type", passCheckingsType);
            rootObject.put("age", passAge);
            rootObject.put("job_type", passJobType);
            rootObject.put("EmailID", passEmail);
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

        android.util.Log.v("Request: ", request.getHeaders().toString());
        //android.util.Log.v("Response Code: ", String.valueOf(responseCode));
        ApiResponse apiResponse = meritcheckClient.execute(request);

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
        String finalResult = "";
        String finalScore = "";
        if (responseSuccess) {
            try {
                JSONObject responseObject = new JSONObject(result);
                String bodyResponse = "Empty Response";
                bodyResponse = responseObject.getString("body");
                JSONObject bodyObject = new JSONObject(bodyResponse);
                finalResult = bodyObject.getString("result");
                finalScore = bodyObject.getString("score");
                Log.d("FINAL", finalResult);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(mContext, finalResult + "|" + finalScore, Toast.LENGTH_LONG).show();

        AlertDialog.Builder goAlert = new AlertDialog.Builder(mContext);
        goAlert.setTitle("Response from AI Model");
        goAlert.setMessage("Request for this Customer is " + finalResult + " and the Eligibility Score is " + finalScore);
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