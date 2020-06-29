package com.beitone.signup.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class WebEntity implements Parcelable {

    public String title;

    public String url;

    public Map<String, String> head;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.url);
        dest.writeInt(this.head.size());
        for (Map.Entry<String, String> entry : this.head.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
    }

    public WebEntity() {
    }

    protected WebEntity(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
        int headSize = in.readInt();
        this.head = new HashMap<String, String>(headSize);
        for (int i = 0; i < headSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.head.put(key, value);
        }
    }

    public static final Creator<WebEntity> CREATOR = new Creator<WebEntity>() {
        @Override
        public WebEntity createFromParcel(Parcel source) {
            return new WebEntity(source);
        }

        @Override
        public WebEntity[] newArray(int size) {
            return new WebEntity[size];
        }
    };
}
