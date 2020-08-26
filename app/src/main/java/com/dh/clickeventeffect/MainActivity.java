package com.dh.clickeventeffect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dh.clickevent.ClickEventsManager;
import com.dh.clickevent.factory.ClickEventsFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ClickEventsManager.buildDefault();
        ClickEventsFactory.initClickEventEffects(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("dhdhdh", "111");
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("dhdhdh", "222");

                    }
                });
            }
        });
    }
}