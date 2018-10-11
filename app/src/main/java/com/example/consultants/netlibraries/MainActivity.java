package com.example.consultants.netlibraries;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.awt.font.TextAttribute;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";
    private OkHttpClient okHttpClient;
    private TextView resultTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTV = findViewById(R.id.resultTV);

        okHttpClient = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url("http://www.google.com").build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            //no internet
            @Override
            public void onFailure(Call call, IOException e) {
                //retry call.enqueue.();
                //or log error
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String result = response.body().string();

                    resultTV.post(new Runnable() {
                        @Override
                        public void run() {
                            resultTV.setText(result);
                        }
                    });
                    Log.d(TAG, "onResponse: "+ result);
                }else {
                    //retry
                    Log.d(TAG, "onResponse: error "+ response.code());
                }
            }
        });
    }
}
