package com.beitone.signup.entity.response;

public class SessionResponse {


    /**
     * id : 12
     * sessionId : EDCD2E07774BA6F1708585CCA52B513E
     * token : HRjzviPvn5hPDGRobN9E
     * token_invalid_timer : 2020-07-03 14:37:00
     */

    private String id;
    private String sessionId;
    private String token;
    private String token_invalid_timer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_invalid_timer() {
        return token_invalid_timer;
    }

    public void setToken_invalid_timer(String token_invalid_timer) {
        this.token_invalid_timer = token_invalid_timer;
    }
}
