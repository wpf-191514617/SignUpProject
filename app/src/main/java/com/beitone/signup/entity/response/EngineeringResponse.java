package com.beitone.signup.entity.response;

public class EngineeringResponse {


    /**
     * id : id2094666594
     * name : 门源县浩门水库工程
     */

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
        EngineeringResponse that = (EngineeringResponse) o;
        return getId().equals(that.getId());
    }

}
