package com.beitone.signup.entity.response;

public class TeamResponse {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        TeamResponse that = (TeamResponse) o;
        return getId().equals(that.getId());
    }

}
