package com.example.cardstore;

import android.provider.ContactsContract;

public class CardData {
    private String name;
    private String store;
    private int image;

    public CardData(String name, String store, int image) {
        this.name = name;
        this.store = store;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
