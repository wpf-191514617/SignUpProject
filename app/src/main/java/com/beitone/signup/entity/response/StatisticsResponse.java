package com.beitone.signup.entity.response;

public class StatisticsResponse {


    /**
     * worker : {"phone":"15529000519","workerConstructionRecord":{"id":3,
     * "b_project_team_name_new":"一区一队","status_name":"待审核","status":"1","opt_type_name":"注册加入",
     * "out_time":null,"is_clear_wages":null,"is_clear_wages_name":null,"join_time":null,
     * "opt_type":"1","b_project_name_new":"引江济淮9-2标","audit_memo":null},"face_photo":"/upload
     * /2020-06-28/22df5f56-84c1-4db9-825d-33e619ad27e1.jpeg","regist_step":"2",
     * "card_num":"610323199112255514","type":"3","password":"ff92aaa244440d11b05ebd392348c35f",
     * "createtime":"2020-06-18 18:04:07","id":9,"sys_users_username":null,
     * "card_photo":"/upload/2020-06-28/1c028f05-367b-422c-9fca-cabba17a7a50.jpg",
     * "token":"CNkRqhJtmgVeWQP4tCME","b_project_team_id":"1","name":"王鹏飞","study_rate":"-100%",
     * "head_photo":"/upload/2020-07-01/9ade7ea9-eede-407b-8ac9-b7d9ecb2a5e7.jpeg",
     * "b_project_name":"引江济淮9-2标","job":null,"b_project_id":"id7942781577",
     * "b_project_team_name":"一区一队","job_name":null,"sign_rate":"50%","type_of_work":"1",
     * "sys_users_id":null,"type_name":"协作队伍","type_of_work_name":"队伍负责人",
     * "token_invalid_timer":"2020-07-13 15:53:25"}
     * sum_rate : {"sign_rate":50,"study_rate":4.17}
     * fail_rate : {"num":0,"rate":0}
     */

    private WorkerBean worker;
    private SumRateBean sum_rate;
    private FailRateBean fail_rate;

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

    public FailRateBean getFail_rate() {
        return fail_rate;
    }

    public void setFail_rate(FailRateBean fail_rate) {
        this.fail_rate = fail_rate;
    }

    public static class WorkerBean {
        /**
         * phone : 15529000519
         * workerConstructionRecord : {"id":3,"b_project_team_name_new":"一区一队",
         * "status_name":"待审核","status":"1","opt_type_name":"注册加入","out_time":null,
         * "is_clear_wages":null,"is_clear_wages_name":null,"join_time":null,"opt_type":"1",
         * "b_project_name_new":"引江济淮9-2标","audit_memo":null}
         * face_photo : /upload/2020-06-28/22df5f56-84c1-4db9-825d-33e619ad27e1.jpeg
         * regist_step : 2
         * card_num : 610323199112255514
         * type : 3
         * password : ff92aaa244440d11b05ebd392348c35f
         * createtime : 2020-06-18 18:04:07
         * id : 9
         * sys_users_username : null
         * card_photo : /upload/2020-06-28/1c028f05-367b-422c-9fca-cabba17a7a50.jpg
         * token : CNkRqhJtmgVeWQP4tCME
         * b_project_team_id : 1
         * name : 王鹏飞
         * study_rate : -100%
         * head_photo : /upload/2020-07-01/9ade7ea9-eede-407b-8ac9-b7d9ecb2a5e7.jpeg
         * b_project_name : 引江济淮9-2标
         * job : null
         * b_project_id : id7942781577
         * b_project_team_name : 一区一队
         * job_name : null
         * sign_rate : 50%
         * type_of_work : 1
         * sys_users_id : null
         * type_name : 协作队伍
         * type_of_work_name : 队伍负责人
         * token_invalid_timer : 2020-07-13 15:53:25
         */

        private String phone;
        private WorkerConstructionRecordBean workerConstructionRecord;
        private String face_photo;
        private String regist_step;
        private String card_num;
        private String type;
        private String password;
        private String createtime;
        private int id;
        private Object sys_users_username;
        private String card_photo;
        private String token;
        private String b_project_team_id;
        private String name;
        private String study_rate;
        private String head_photo;
        private String b_project_name;
        private Object job;
        private String b_project_id;
        private String b_project_team_name;
        private Object job_name;
        private String sign_rate;
        private String type_of_work;
        private Object sys_users_id;
        private String type_name;
        private String type_of_work_name;
        private String token_invalid_timer;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public WorkerConstructionRecordBean getWorkerConstructionRecord() {
            return workerConstructionRecord;
        }

        public void setWorkerConstructionRecord(WorkerConstructionRecordBean workerConstructionRecord) {
            this.workerConstructionRecord = workerConstructionRecord;
        }

        public String getFace_photo() {
            return face_photo;
        }

        public void setFace_photo(String face_photo) {
            this.face_photo = face_photo;
        }

        public String getRegist_step() {
            return regist_step;
        }

        public void setRegist_step(String regist_step) {
            this.regist_step = regist_step;
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

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getSys_users_username() {
            return sys_users_username;
        }

        public void setSys_users_username(Object sys_users_username) {
            this.sys_users_username = sys_users_username;
        }

        public String getCard_photo() {
            return card_photo;
        }

        public void setCard_photo(String card_photo) {
            this.card_photo = card_photo;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getB_project_team_id() {
            return b_project_team_id;
        }

        public void setB_project_team_id(String b_project_team_id) {
            this.b_project_team_id = b_project_team_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStudy_rate() {
            return study_rate;
        }

        public void setStudy_rate(String study_rate) {
            this.study_rate = study_rate;
        }

        public String getHead_photo() {
            return head_photo;
        }

        public void setHead_photo(String head_photo) {
            this.head_photo = head_photo;
        }

        public String getB_project_name() {
            return b_project_name;
        }

        public void setB_project_name(String b_project_name) {
            this.b_project_name = b_project_name;
        }

        public Object getJob() {
            return job;
        }

        public void setJob(Object job) {
            this.job = job;
        }

        public String getB_project_id() {
            return b_project_id;
        }

        public void setB_project_id(String b_project_id) {
            this.b_project_id = b_project_id;
        }

        public String getB_project_team_name() {
            return b_project_team_name;
        }

        public void setB_project_team_name(String b_project_team_name) {
            this.b_project_team_name = b_project_team_name;
        }

        public Object getJob_name() {
            return job_name;
        }

        public void setJob_name(Object job_name) {
            this.job_name = job_name;
        }

        public String getSign_rate() {
            return sign_rate;
        }

        public void setSign_rate(String sign_rate) {
            this.sign_rate = sign_rate;
        }

        public String getType_of_work() {
            return type_of_work;
        }

        public void setType_of_work(String type_of_work) {
            this.type_of_work = type_of_work;
        }

        public Object getSys_users_id() {
            return sys_users_id;
        }

        public void setSys_users_id(Object sys_users_id) {
            this.sys_users_id = sys_users_id;
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

        public String getToken_invalid_timer() {
            return token_invalid_timer;
        }

        public void setToken_invalid_timer(String token_invalid_timer) {
            this.token_invalid_timer = token_invalid_timer;
        }

        public static class WorkerConstructionRecordBean {
            /**
             * id : 3
             * b_project_team_name_new : 一区一队
             * status_name : 待审核
             * status : 1
             * opt_type_name : 注册加入
             * out_time : null
             * is_clear_wages : null
             * is_clear_wages_name : null
             * join_time : null
             * opt_type : 1
             * b_project_name_new : 引江济淮9-2标
             * audit_memo : null
             */

            private int id;
            private String b_project_team_name_new;
            private String status_name;
            private String status;
            private String opt_type_name;
            private Object out_time;
            private Object is_clear_wages;
            private Object is_clear_wages_name;
            private Object join_time;
            private String opt_type;
            private String b_project_name_new;
            private Object audit_memo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getB_project_team_name_new() {
                return b_project_team_name_new;
            }

            public void setB_project_team_name_new(String b_project_team_name_new) {
                this.b_project_team_name_new = b_project_team_name_new;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getOpt_type_name() {
                return opt_type_name;
            }

            public void setOpt_type_name(String opt_type_name) {
                this.opt_type_name = opt_type_name;
            }

            public Object getOut_time() {
                return out_time;
            }

            public void setOut_time(Object out_time) {
                this.out_time = out_time;
            }

            public Object getIs_clear_wages() {
                return is_clear_wages;
            }

            public void setIs_clear_wages(Object is_clear_wages) {
                this.is_clear_wages = is_clear_wages;
            }

            public Object getIs_clear_wages_name() {
                return is_clear_wages_name;
            }

            public void setIs_clear_wages_name(Object is_clear_wages_name) {
                this.is_clear_wages_name = is_clear_wages_name;
            }

            public Object getJoin_time() {
                return join_time;
            }

            public void setJoin_time(Object join_time) {
                this.join_time = join_time;
            }

            public String getOpt_type() {
                return opt_type;
            }

            public void setOpt_type(String opt_type) {
                this.opt_type = opt_type;
            }

            public String getB_project_name_new() {
                return b_project_name_new;
            }

            public void setB_project_name_new(String b_project_name_new) {
                this.b_project_name_new = b_project_name_new;
            }

            public Object getAudit_memo() {
                return audit_memo;
            }

            public void setAudit_memo(Object audit_memo) {
                this.audit_memo = audit_memo;
            }
        }
    }

    public static class SumRateBean {
        /**
         * sign_rate : 50
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

    public static class FailRateBean {
        /**
         * num : 0
         * rate : 0
         */

        private String num;
        private String rate;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }
    }
}
