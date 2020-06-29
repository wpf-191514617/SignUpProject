package com.beitone.signup.model;

public class TabItem {

    public String tabText;

    public int mSelect;

    public int mDefault;

    public TabItem(String tabText, int mSelect, int mDefault) {
        this.tabText = tabText;
        this.mSelect = mSelect;
        this.mDefault = mDefault;
    }
}
