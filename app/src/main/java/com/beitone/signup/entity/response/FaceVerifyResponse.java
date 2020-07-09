package com.beitone.signup.entity.response;

import java.util.List;

public class FaceVerifyResponse {


    /**
     * error_code : 0
     * error_msg : SUCCESS
     * log_id : 9445155525946
     * timestamp : 1594278482
     * cached : 0
     * result : {"score":97.51699066,"face_list":[{"face_token":"0e98358739914e4432a509968a25b5ff
     * "},{"face_token":"bab6ecaab461c771922d9e1e216f4c54"}]}
     */

    private int error_code;
    private String error_msg;
    private long log_id;
    private int timestamp;
    private int cached;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getCached() {
        return cached;
    }

    public void setCached(int cached) {
        this.cached = cached;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * score : 97.51699066
         * face_list : [{"face_token":"0e98358739914e4432a509968a25b5ff"},{"face_token
         * ":"bab6ecaab461c771922d9e1e216f4c54"}]
         */

        private double score;
        private List<FaceListBean> face_list;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public List<FaceListBean> getFace_list() {
            return face_list;
        }

        public void setFace_list(List<FaceListBean> face_list) {
            this.face_list = face_list;
        }

        public static class FaceListBean {
            /**
             * face_token : 0e98358739914e4432a509968a25b5ff
             */

            private String face_token;

            public String getFace_token() {
                return face_token;
            }

            public void setFace_token(String face_token) {
                this.face_token = face_token;
            }
        }
    }
}
