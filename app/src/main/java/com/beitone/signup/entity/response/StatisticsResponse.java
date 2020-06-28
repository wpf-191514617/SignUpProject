package com.beitone.signup.entity.response;

public class StatisticsResponse {


    /**
     * worker : {"phone":"15529000512","head_photo":null,"b_project_name":"引江济淮9-2标",
     * "regist_step":"2","face_photo":"/upload/2020-06-28/22df5f56-84c1-4db9-825d-33e619ad27e1
     * .jpeg","job":null,"card_num":"610323199112255514","type":"3",
     * "b_project_id":"id7942781577","password":"ff92aaa244440d11b05ebd392348c35f",
     * "sys_users_username":null,"id":9,"createtime":"2020-06-18 18:04:07",
     * "b_project_team_name":"一区一队","card_photo":"/upload/2020-06-28/1c028f05-367b-422c-9fca
     * -cabba17a7a50.jpg","sign_rate":"100%","job_name":null,"token":"uwWUWIIYdlTgQV3evC3g",
     * "type_of_work":"1","b_project_team_id":"1","sys_users_id":null,"name":"王鹏飞",
     * "type_name":"协作队伍","type_of_work_name":"队伍负责人","study_rate":"-100%",
     * "token_invalid_timer":"2020-07-08 18:06:29"}
     * sum_rate : {"sign_rate":57.1429,"study_rate":4.17}
     */

    private WorkerBean worker;
    private SumRateBean sum_rate;

    public WorkerBean getWorker() {
        return worker;
    }

    public void setWorker(WorkerBean worker) {
        this.worker = worker;
    }

    public SumRateBean getSum_rate() {
        return sum_rate;
    }

    public void setSum_rate(SumRateBean sum_rate) {
        this.sum_rate = sum_rate;
    }

    public static class WorkerBean {
        /**
         * phone : 15529000512
         * head_photo : null
         * b_project_name : 引江济淮9-2标
         * regist_step : 2
         * face_photo : /upload/2020-06-28/22df5f56-84c1-4db9-825d-33e619ad27e1.jpeg
         * job : null
         * card_num : 610323199112255514
         * type : 3
         * b_project_id : id7942781577
         * password : ff92aaa244440d11b05ebd392348c35f
         * sys_users_username : null
         * id : 9
         * createtime : 2020-06-18 18:04:07
         * b_project_team_name : 一区一队
         * card_photo : /upload/2020-06-28/1c028f05-367b-422c-9fca-cabba17a7a50.jpg
         * sign_rate : 100%
         * job_name : null
         * token : uwWUWIIYdlTgQV3evC3g
         * type_of_work : 1
         * b_project_team_id : 1
         * sys_users_id : null
         * name : 王鹏飞
         * type_name : 协作队伍
         * type_of_work_name : 队伍负责人
         * study_rate : -100%
         * token_invalid_timer : 2020-07-08 18:06:29
         */

        private String phone;
        private Object head_photo;
        private String b_project_name;
        private String regist_step;
        private String face_photo;
        private Object job;
        private String card_num;
        private String type;
        private String b_project_id;
        private String password;
        private Object sys_users_username;
        private int id;
        private String createtime;
        private String b_project_team_name;
        private String card_photo;
        private String sign_rate;
        private Object job_name;
        private String token;
        private String type_of_work;
        private String b_project_team_id;
        private Object sys_users_id;
        private String name;
        private String type_name;
        private String type_of_work_name;
        private String study_rate;
        private String token_invalid_timer;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(Object head_photo) {
            this.head_photo = head_photo;
        }

        public String getB_project_name() {
            return b_project_name;
        }

        public void setB_project_name(String b_project_name) {
            this.b_project_name = b_project_name;
        }

        public String getRegist_step() {
            return regist_step;
        }

        public void setRegist_step(String regist_step) {
            this.regist_step = regist_step;
        }

        public String getFace_photo() {
            return face_photo;
        }

        public void setFace_photo(String face_photo) {
            this.face_photo = face_photo;
        }

        public Object getJob() {
            return job;
        }

        public void setJob(Object job) {
            this.job = job;
        }

        public String getCard_num() {
            return card_num;
        }

        public void setCard_num(String card_num) {
            this.card_num = card_num;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getB_project_id() {
            return b_project_id;
        }

        public void setB_project_id(String b_project_id) {
            this.b_project_id = b_project_id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Object getSys_users_username() {
            return sys_users_username;
        }

        public void setSys_users_username(Object sys_users_username) {
            this.sys_users_username = sys_users_username;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getB_project_team_name() {
            return b_project_team_name;
        }

        public void setB_project_team_name(String b_project_team_name) {
            this.b_project_team_name = b_project_team_name;
        }

        public String getCard_photo() {
            return card_photo;
        }

        public void setCard_photo(String card_photo) {
            this.card_photo = card_photo;
        }

        public String getSign_rate() {
            return sign_rate;
        }

        public void setSign_rate(String sign_rate) {
            this.sign_rate = sign_rate;
        }

        public Object getJob_name() {
            return job_name;
        }

        public void setJob_name(Object job_name) {
            this.job_name = job_name;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getType_of_work() {
            return type_of_work;
        }

        public void setType_of_work(String type_of_work) {
            this.type_of_work = type_of_work;
        }

        public String getB_project_team_id() {
            return b_project_team_id;
        }

        public void setB_project_team_id(String b_project_team_id) {
            this.b_project_team_id = b_project_team_id;
        }

        public Object getSys_users_id() {
            return sys_users_id;
        }

        public void setSys_users_id(Object sys_users_id) {
            this.sys_users_id = sys_users_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getType_of_work_name() {
            return type_of_work_name;
        }

        public void setType_of_work_name(String type_of_work_name) {
            this.type_of_work_name = type_of_work_name;
        }

        public String getStudy_rate() {
            return study_rate;
        }

        public void setStudy_rate(String study_rate) {
            this.study_rate = study_rate;
        }

        public String getToken_invalid_timer() {
            return token_invalid_timer;
        }

        public void setToken_invalid_timer(String token_invalid_timer) {
            this.token_invalid_timer = token_invalid_timer;
        }
    }

    public static class SumRateBean {
        /**
         * sign_rate : 57.1429
         * study_rate : 4.17
         */

        private String sign_rate;
        private String study_rate;

        public String getSign_rate() {
            return sign_rate;
        }

        public void setSign_rate(String sign_rate) {
            this.sign_rate = sign_rate;
        }

        public String getStudy_rate() {
            return study_rate;
        }

        public void setStudy_rate(String study_rate) {
            this.study_rate = study_rate;
        }
    }
}
