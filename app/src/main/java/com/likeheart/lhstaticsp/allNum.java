package com.likeheart.lhstaticsp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class allNum extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<DataModel> list = new ArrayList<DataModel>();
    myAdapter adapter;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_num);
        setTitle("All Numbers");

        SharedPreferences sharedPreferences = getSharedPreferences("shared", MODE_PRIVATE);
        check = sharedPreferences.getBoolean("check", false);

        recyclerView = findViewById(R.id.recyclerView);

        adapter = new myAdapter(list, this, 2);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        if (!check) {
            for (int i = 1; i <= 20; i++) {
                DataModel mLog = new DataModel();
                mLog.setHeart(false);
                mLog.setLike(false);
                mLog.setId(String.valueOf(i));
                list.add(mLog);
            }
        } else {
            loadData();
            adapter = new myAdapter(list, this, 2);
            recyclerView.setAdapter(adapter);
        }

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("courses", null);
        Type type = new TypeToken<ArrayList<DataModel>>() {
        }.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
    }
}