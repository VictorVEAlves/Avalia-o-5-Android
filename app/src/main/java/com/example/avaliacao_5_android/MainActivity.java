package com.example.avaliacao_5_android;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    public static final String REQUEST_TAG = "MainVolleyActivity";

    private TextView iptNome;
    private TextView iptIdade;
    private TextView iptCidade;
    private EditText rgInput;
    private Button btn;
    private RequestQueue mQueue;

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgInput = (EditText)findViewById(R.id.rgInput);
        btn = (Button)findViewById(R.id.btnBuscar);
        iptNome = (TextView)findViewById(R.id.iptNome);
        iptIdade = (TextView)findViewById(R.id.iptIdade);
        iptCidade = (TextView)findViewById(R.id.iptCidade);

    }

    @Override
    protected void onStart(){
        super.onStart();

        mQueue = CustomVolleyRequestQueue.getInstance(ctx.getApplicationContext()).getRequestQueue();

        btn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               String rg = rgInput.getText().toString();
               String URL2 = "https://codewars.up.edu.br/exercicios/users.php?RG="+rg;
               CustomJSONObjectRequest json1 = new CustomJSONObjectRequest(Request.Method.GET, URL2, new JSONObject(), MainActivity.this,MainActivity.this);
               json1.setTag(REQUEST_TAG);
               mQueue.add(json1);
           }
        });
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mQueue != null){
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error){
        System.out.println("zikou");
    }

    @Override
    public void onResponse(Object response){
        try{
            iptNome.setText(((JSONObject)response).getString("name"));
            iptIdade.setText(String.valueOf(((JSONObject)response).getInt("age")));
            iptCidade.setText(((JSONObject)response).getString("city"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }


}
