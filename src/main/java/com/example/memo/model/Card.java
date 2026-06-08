package com.example.memo.model;

public class Card {
    private final int id;
    private boolean isOpened = false;
    private boolean isMatched = false;

    public Card(int id) {
        this.id = id;
    }

    public int getId() { return id; }
    public boolean isOpened() { return isOpened; }
    public void setOpened(boolean opened) { isOpened = opened; }
    public boolean isMatched() { return isMatched; }
    public void setMatched(boolean matched) { isMatched = matched; }
}