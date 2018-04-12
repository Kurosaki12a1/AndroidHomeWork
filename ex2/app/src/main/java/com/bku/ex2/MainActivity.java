package com.bku.ex2;


import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private String TAG = MainActivity.class.getSimpleName();
    String arr[]={
           "Bạt Thái Lan (THB)",
            "Bảng Anh (GBP)",
            "Dollar Singapore (SGD)",
            "Dollar Úc (AUD)",
            "EURO (EUR)",
            "KIP Lào (LAK)",
            "Nhân Dân Tệ (CNY)",
            "Peso Philip (PHP)",
            "Riel Campuchia (KHR)",
            "Ringgit Malaysia (MYR)",
            "Rupi Ấn Độ (INR)",
            "Ruphiah Indo (IDR)",
            "Rúp Nga (RUB)",
            "Tân Đài Tệ (TWD)",
            "Việt Nam Đồng (VND)",
            "Won Hàn Quốc (KRW)",
            "Yên Nhật (JPY)",
            "Đôla Canada (CAD)",
            "Đôla Hồng Kông (HKD)",
            "Đôla Mỹ (USD)"

    };

    private Spinner  fromSpin,toSpin;
    private Button btnReset,btnOk;
    private EditText inputText,outputText;
    private TextView tvDetail_1,tvDetail_2;
    public double usdTovnd=22800;
    public double eurTovnd=27800;
    public double eurToUsd=1.22;
    List<String> ratio= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromSpin=(Spinner)findViewById(R.id.fromSpinner);
        toSpin=(Spinner)findViewById(R.id.toSpinner);
        btnReset=(Button)findViewById(R.id.Clear);
        btnOk=(Button)findViewById(R.id.Calculate);

        inputText=(EditText)findViewById(R.id.editText);
        tvDetail_1=(TextView)findViewById(R.id.detail1);
        tvDetail_2=(TextView)findViewById(R.id.detail2);
        outputText=(EditText)findViewById(R.id.EditTextAnswer);

        outputText.setEnabled(false);


        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        fromSpin.setAdapter(adapter);
        toSpin.setAdapter(adapter);




        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText.setText("");
                fromSpin.setSelection(0);
                toSpin.setSelection(0);
                tvDetail_1.setText("");
                tvDetail_2.setText("");
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new getRatio().execute();
            }
        });
    }

    private void exchange(int from,int to){
        /*if(from==to){
            outputText.setText(inputText.getText().toString());
            tvDetail_1.setText("1 "+fromSpin.getSelectedItem().toString()+" = "+
            "1 " + toSpin.getSelectedItem().toString());
        }
        else if(from=="VND"){
            ratioVN(to);
        }
        else if(from=="USD")
        {
            ratioUSD(to);
        }
        else if(from=="EUR"){
            ratioEUR(to);
        }*/
        double rat=Double.valueOf(ratio.get(to))/Double.valueOf(ratio.get(from));
        setDetail(rat);
    }

    private void ratioVN(String to){
        switch(to){
            case "USD":
               setDetail(1/usdTovnd);
                break;
            case "EUR":
                setDetail(1/eurTovnd);
                break;

        }
    }

    private void ratioUSD(String to){
        switch(to){
            case "VND":
                setDetail(usdTovnd);
                break;
            case "EUR":
                setDetail(1/eurToUsd);
                break;

        }
    }

    private void ratioEUR(String to){
        switch(to){
            case "VND":
                setDetail(eurTovnd);
                break;
            case "USD":
                setDetail(eurToUsd);
                break;

        }
    }

    private void setDetail(double ratio){
        outputText.setText(String.valueOf(Double.valueOf(inputText.getText().toString())*ratio));
        tvDetail_1.setText("1 "+fromSpin.getSelectedItem().toString() + " = "+ratio + " " + toSpin.getSelectedItem().toString() );
        tvDetail_2.setText("1 "+toSpin.getSelectedItem().toString() + " = "+1/ratio + " " + fromSpin.getSelectedItem().toString() );
    }

    private class getRatio extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected Void doInBackground(Void... arg0) {
            ratio.clear();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://data.fixer.io/api/latest?access_key=30f9ff0b21e97ae4c8973e55ffd2ee49&format=1";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject Rate=jsonObj.getJSONObject("rates");
                    ratio.add(Rate.getString("THB"));
                    ratio.add(Rate.getString("GBP"));
                    ratio.add(Rate.getString("SGD"));
                    ratio.add(Rate.getString("AUD"));
                    ratio.add(Rate.getString("EUR"));
                    ratio.add(Rate.getString("LAK"));
                    ratio.add(Rate.getString("CNY"));
                    ratio.add(Rate.getString("PHP"));
                    ratio.add(Rate.getString("KHR"));

                    ratio.add(Rate.getString("MYR"));
                    ratio.add(Rate.getString("INR"));
                    ratio.add(Rate.getString("IDR"));
                    ratio.add(Rate.getString("RUB"));
                    ratio.add(Rate.getString("TWD"));
                    ratio.add(Rate.getString("VND"));
                    ratio.add(Rate.getString("KRW"));
                    ratio.add(Rate.getString("JPY"));
                    ratio.add(Rate.getString("CAD"));
                    ratio.add(Rate.getString("HKD"));
                    ratio.add(Rate.getString("USD"));

                }
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            exchange(fromSpin.getSelectedItemPosition(),toSpin.getSelectedItemPosition());
        }
    }

}
