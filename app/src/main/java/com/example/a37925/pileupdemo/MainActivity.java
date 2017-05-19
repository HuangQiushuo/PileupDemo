package com.example.a37925.pileupdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    PileupView pileupView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pileupView = (PileupView) findViewById(R.id.pile);
        ArrayList<String> list = new ArrayList<>();
        list.add("#FF0000");
        list.add("#00FF00");
        list.add("#0000FF");
        list.add("#00FFFF");
        list.add("#FFFF00");
        pileupView.setImages(list);
    }
}
