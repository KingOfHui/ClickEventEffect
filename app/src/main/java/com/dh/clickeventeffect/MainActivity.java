package com.dh.clickeventeffect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        final TextView tv2 = findViewById(R.id.tv2);
        tv.setOnClickListener(v -> Log.e("dhdhdh", "111"));
        tv.performClick();
        tv2.setOnClickListener(v -> tv.performClick());
    }
}