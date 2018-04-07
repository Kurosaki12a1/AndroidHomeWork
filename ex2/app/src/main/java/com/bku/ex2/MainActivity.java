package com.bku.ex2;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    String arr[]={
            "VND",
            "USD",
            "EUR"
    };

    private Spinner  fromSpin,toSpin;
    private Button btnReset,btnOk;
    private EditText inputText,outputText;
    private TextView tvDetail_1,tvDetail_2;
    public double usdTovnd=22800;
    public double eurTovnd=27800;
    public double eurToUsd=1.22;

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
                exchange(fromSpin.getSelectedItem().toString(),toSpin.getSelectedItem().toString());
            }
        });
    }

    private void exchange(String from,String to){
        if(from==to){
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
        }
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


}
