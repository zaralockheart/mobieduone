package com.something.mobieduone;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundLogin extends AsyncTask<String, String, String> {
    Context context;
    BackgroundLogin(Context ctx){
        this.context = ctx;
    }

    @Override
    protected String doInBackground(String... strings) {
        String type = strings[0];
        String user = strings[1];
        String pass = strings[2];
        String email = strings[3];
        String fname = strings[4];
        String lname = strings[5];

        String regURL = "https://mobieduone.000webhostapp.com/register.php";
        if (type.equals("req")){
            try {
                URL url = new URL(regURL);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoOutput(true);
                    OutputStream outputstream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputstream, "UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    String insert_data = URLEncoder.encode("logu", "UTF-8")+"="+URLEncoder.encode(user, "UTF-8")
                            +"&"+URLEncoder.encode("logp", "UTF-8")+"="+URLEncoder.encode(pass, "UTF-8")
                            +"="+URLEncoder.encode("loge", "UTF-8")+"="+URLEncoder.encode(email, "UTF-8")
                            +"="+URLEncoder.encode("logf", "UTF-8")+"="+URLEncoder.encode(fname, "UTF-8")
                            +"="+URLEncoder.encode("logl", "UTF-8")+"="+URLEncoder.encode(lname,"UTF-8");

                    bufferedWriter.write(insert_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "ISO-8859-1");
                    BufferedReader  bufferedReader = new BufferedReader(inputStreamReader);
                    String result = "";
                    String line = "";
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine())!=null) {
                        stringBuilder.append(line).append("\n");
                    }
                    result=stringBuilder.toString();
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        //super.onPostExecute(s);
    }

}
