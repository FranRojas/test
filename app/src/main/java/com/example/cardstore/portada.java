package com.example.cardstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.os.Bundle;

public class portada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);

    }
    public void Siguiente (View view) {

        view.getContext().startActivity(new Intent(view.getContext(), MainActivity2.class));

    }
}