package com.likeheart.lhstaticsp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class fav extends AppCompatActivity {
    Button btn_like,btn_heart,btn_all;
    RecyclerView rv_all;
    myAdapter adapter;
    ArrayList<DataModel> list = new ArrayList<DataModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        setTitle("Fav");

        loadData();
        btn_like = findViewById(R.id.btn_like);
        btn_all = findViewById(R.id.btn_all);
        btn_heart = findViewById(R.id.btn_heart);
        rv_all = findViewById(R.id.rv_all);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_all.setLayoutManager(layoutManager);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DataModel> filteredlist = new ArrayList<>();
                // running a for loop to compare elements.
                for (DataModel item : list) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.isLike()==true) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                adapter = new myAdapter(filteredlist,fav.this,0);
                rv_all.setAdapter(adapter);
            }
        });

        btn_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<DataModel> filteredlist = new ArrayList<>();
                // running a for loop to compare elements.
                for (DataModel item : list) {
                    // checking if the entered string matched with any item of our recycler view.
                    if (item.isHeart()==true) {
                        // if the item is matched we are
                        // adding it to our filtered list.
                        filteredlist.add(item);
                    }
                }
                adapter = new myAdapter(filteredlist,fav.this,1);
                rv_all.setAdapter(adapter);
            }
        });

        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter = new myAdapter(list,fav.this,2);
                rv_all.setAdapter(adapter);
            }
        });

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("courses", null);
        Type type = new TypeToken<ArrayList<DataModel>>() {}.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<>();
        }
    }
}