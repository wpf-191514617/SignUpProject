package com.beitone.signup.model;

public class EventData<T> {

    public int CODE;

    public T data;

    public EventData(int CODE) {
        this.CODE = CODE;
    }

    public EventData(int CODE, T data) {
        this.CODE = CODE;
        this.data = data;
    }
}
