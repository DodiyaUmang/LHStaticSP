package com.likeheart.lhstaticsp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class myAdapter extends RecyclerView.Adapter<myAdapter.viewHolder> {
    ArrayList<DataModel> list;
    Context context;
    int id;

    public myAdapter(ArrayList<DataModel> list, Context context, int id) {
        this.list = list;
        this.context = context;
        this.id = id;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        viewHolder holder = new viewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        if(id==0){
            holder.cb_like.setChecked(list.get(position).isLike());
            holder.cb_heart.setVisibility(View.GONE);
            holder.tv_id.setText(list.get(position).getId());
        }

        if(id==1){
            holder.cb_like.setVisibility(View.GONE);
            holder.cb_heart.setChecked(list.get(position).isHeart());
            holder.tv_id.setText(list.get(position).getId());
        }

        if(id==2){
            holder.cb_like.setChecked(list.get(position).isLike());
            holder.cb_heart.setChecked(list.get(position).isHeart());
            holder.tv_id.setText(list.get(position).getId());
        }

        holder.cb_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("check",true);
                editor.apply();

                boolean like = holder.cb_like.isChecked();
                boolean heart = holder.cb_heart.isChecked();

                if(like==true){
                    holder.cb_like.setChecked(true);
                    if(heart==false){
                        holder.cb_heart.setChecked(false);
                    }
                    list.get(position).setLike(true);
                    list.get(position).setHeart(false);
                    saveData();
//                    holder.cb_like.setChecked(list.get(position).isLike());
//                    holder.cb_heart.setChecked(list.get(position).isHeart());
                } else{
                    holder.cb_like.setChecked(false);
                    holder.cb_heart.setChecked(true);
                    list.get(position).setLike(false);
                    list.get(position).setHeart(true);
                    notifyDataSetChanged();
                    saveData();
                }
            }
        });

        holder.cb_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                SharedPreferences sharedPreferences = context.getSharedPreferences("shared", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("check",true);
                editor.apply();

                boolean like = holder.cb_like.isChecked();
                boolean heart = holder.cb_heart.isChecked();

                if(heart==true){
                    holder.cb_heart.setChecked(true);
                    if(like==false){
                        holder.cb_like.setChecked(false);
                    }
                    list.get(position).setHeart(true);
                    list.get(position).setLike(false);
                    saveData();
//                    holder.cb_like.setChecked(list.get(position).isLike());
//                    holder.cb_heart.setChecked(list.get(position).isHeart());
                } else{
                    holder.cb_heart.setChecked(false);
                    holder.cb_like.setChecked(true);
                    list.get(position).setHeart(false);
                    list.get(position).setLike(true);
                    notifyDataSetChanged();
                    saveData();
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("courses", json);
        editor.apply();
//        Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return(null != list?list.size():0);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView tv_id;
        CheckBox cb_like,cb_heart;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            cb_heart = itemView.findViewById(R.id.cb_heart);
            cb_like = itemView.findViewById(R.id.cb_like);
        }
    }
}
