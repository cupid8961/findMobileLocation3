package com.example.igsjinhyung.findmobilelocation3;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by iGSjaehun on 2017-01-19.
 */

public class InsertData extends AsyncTask<String, Void, String> {
    ProgressDialog loading;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            String phone_num = (String) params[0];
            String lat = (String) params[1];
            String lon = (String) params[2];
            String b_per = (String) params[3];

            /*
            String link = "http://52.79.164.222/i_parser.php";
            String data = URLEncoder.encode("phone_num", "UTF-8") + "=" + URLEncoder.encode(phone_num, "UTF-8");
            data += "&" + URLEncoder.encode("lat", "UTF-8") + "=" + URLEncoder.encode(lat, "UTF-8");
            data += "&" + URLEncoder.encode("lon", "UTF-8") + "=" + URLEncoder.encode(lon, "UTF-8");
            data += "&" + URLEncoder.encode("b_per", "UTF-8") + "=" + URLEncoder.encode(b_per, "UTF-8");
*/


            String link = "http://52.79.164.222/i_parser.php/?phone_num="+phone_num+"&lat="+lat+"&lon="+lon+"&b_per="+b_per;

            Log.i(MainActivity.TAG, "link:"+link );
            //http://52.79.164.222/i_parser.php?phone_num=010-1234-4444&lat=33.33&lon=44.44&b_per=666

            URL url = new URL(link);
            URLConnection conn = url.openConnection();

            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write("");
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());


        }
    }
}
