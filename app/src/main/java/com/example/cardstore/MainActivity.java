package com.example.cardstore;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cardstore.CardAdapter;
import com.example.cardstore.CardData;
import com.example.cardstore.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView courseRV = findViewById(R.id.idRV);

        // Here, we have created new array list and added data to it
        ArrayList<CardData> cardDataArrayList = new ArrayList<CardData>();
        cardDataArrayList.add(new CardData("Mew 2 Pokemon", "Pokenono", R.drawable.mew2));
        cardDataArrayList.add(new CardData("Kid", "Wargaming", R.drawable.kid));
        cardDataArrayList.add(new CardData("Shere Khan", "USA", R.drawable.sherekhan));
        cardDataArrayList.add(new CardData("Law op", "Wargaming", R.drawable.law));
        cardDataArrayList.add(new CardData("Moonex", "Pokenono", R.drawable.moonex));
        cardDataArrayList.add(new CardData("Enel", "Wargaming", R.drawable.enel));
        cardDataArrayList.add(new CardData("The Queen", "USA", R.drawable.thequeen));
        cardDataArrayList.add(new CardData("Hallowed Fountain", "Wargaming", R.drawable.hallowed));
        // we are initializing our adapter class and passing our arraylist to it.
        CardAdapter cardAdapter = new CardAdapter(this, cardDataArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(cardAdapter);
    }

}
