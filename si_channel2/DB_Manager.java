package cairo_university.si_channel2;

import android.os.AsyncTask;

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

/**
 * Created by AMR on 5/2/2016.
 */
public class DB_Manager extends AsyncTask<String,Void,String> {
    private String Insert_url;
    private String Select_json_url;
    private String Delete_url;
    private String json_row;


    @Override
    protected void onPreExecute(){
        Insert_url = "http://192.168.1.5/Insert.php";
        Select_json_url = "http://192.168.1.5/json_select.php";
        Delete_url = "http://192.168.1.5/Delete.php";
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        try {
            URL url;

            if(params[1]=="select")
                url = new URL(Select_json_url);
            else if(params[1] == "insert")
                url = new URL(Insert_url);
            else
                url = new URL(Delete_url);


            HttpURLConnection connection = (HttpURLConnection)url.openConnection();


            connection.setRequestMethod("POST");
            
            connection.setDoOutput(true);
            connection.setDoInput(true);


            OutputStream out = connection.getOutputStream();
            BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(out,"utf-8"));

            String Query = params[0];
            String Data = URLEncoder.encode("Query", "utf-8")+ "=" +URLEncoder.encode(Query, "utf-8");

            if(params[1] == "select") {
                String table_name = params[2];
                Data += "&" + URLEncoder.encode("TABLE", "utf-8") + "=" + URLEncoder.encode(table_name, "utf-8");
            }
            Writer.write(Data);
            Writer.flush();
            Writer.close();

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));

            StringBuilder stringBuilder = new StringBuilder();

            while ((json_row = reader.readLine())!= null)
            {
                stringBuilder.append(json_row+"\n");
            }

            reader.close();
            in.close();
            connection.disconnect();

            System.out.println( "Gowa el async task "+stringBuilder.toString());

            return stringBuilder.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
