package com.yonyou.sh.common.bean;

/**
 * Created by LZJ on 2018/10/12.
 */
public class AccountInfo extends CommonBean {
    /**
     * jwt : eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNTgzNTcwODk3MiIsInVzZXJJZCI6IjI1IiwibmFtZSI6IiIsInJlbWFyayI6IiIsImtpY2tPdXQiOnRydWUsImV4cCI6MTU0NDI3NDA1Nn0.uVH3iqsWqgBN2Q9Fri5vq6cEeAbhWBuEFEPM1OcPD1mHYbG3N8ASDYC4n2YRl9xjr0pxnzSlIeS00N206rB4ciKVYLPiw0Hkju3PYob7pdnt8OD4vE8u2R9kJ8RFh8k7XVGfsS1dDqWaqhLDd1KejvhsORAHdurIY7cLaj0qlw8
     * rData : {"phone":"15835708972","userName":"15835708972","userId":25,"logTime":"2018-11-08 20:25:49"}
     */

    private String jwt;
    private RDataBean rData;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public RDataBean getRData() {
        return rData;
    }

    public void setRData(RDataBean rData) {
        this.rData = rData;
    }

    public static class RDataBean {
        /**
         * phone : 15835708972
         * userName : 15835708972
         * userId : 25
         * logTime : 2018-11-08 20:25:49
         */

        private String phone;
        private String userName;
        private long userId;
        private String logTime;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getLogTime() {
            return logTime;
        }

        public void setLogTime(String logTime) {
            this.logTime = logTime;
        }
    }

}
