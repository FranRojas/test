package com.example.cardstore;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<CardData> cardDataArrayList;

    public CardAdapter(Context context, ArrayList<CardData> cardDataArrayList) {
        this.context = context;
        this.cardDataArrayList = cardDataArrayList;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        CardData item = cardDataArrayList.get(position);
        holder.cardName.setText(item.getName());
        holder.cardStore.setText(item.getStore());
        holder.cardImage.setImageResource(item.getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = item.getStore().toString();
                v.getContext().startActivity(new Intent(v.getContext(), MapsActivity.class).putExtra("message_key", str));

            }
        });


    }

    @Override
    public int getItemCount() {
        return cardDataArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView cardImage;
        private final TextView cardName;
        private final TextView cardStore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardName = itemView.findViewById(R.id.name);
            cardStore = itemView.findViewById(R.id.store);
        }

    }


}
