package com.yelai.wearable.model;

import java.io.Serializable;

public  class UserInfo implements Serializable{

        private String memberId;//学员唯一编号
        private String schoolName;//学校名称
        private String schoolNo;//学生学号
        private String trueName;//学生姓名
        private int sex;//学生性别  1  男  2 女
        private String birthday;//学生身日

        private int weight;//体重
        private int height;//身高

        private String basis;//运动基础

        private String interest;//感兴趣的场所

        private String target;//运动目标

        private String sports;//感兴趣的类型

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public String getSchoolName() {
            return schoolName;
        }

        public void setSchoolName(String schoolName) {
            this.schoolName = schoolName;
        }

        public String getSchoolNo() {
            return schoolNo;
        }

        public void setSchoolNo(String schoolNo) {
            this.schoolNo = schoolNo;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getBasis() {
            return basis;
        }

        public void setBasis(String basis) {
            this.basis = basis;
        }

        public String getInterest() {
            return interest;
        }

        public void setInterest(String interest) {
            this.interest = interest;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        public String getSports() {
            return sports;
        }

        public void setSports(String sports) {
            this.sports = sports;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "memberId='" + memberId + '\'' +
                    ", schoolName='" + schoolName + '\'' +
                    ", schoolNo='" + schoolNo + '\'' +
                    ", trueName='" + trueName + '\'' +
                    ", sex=" + sex +
                    ", birthday='" + birthday + '\'' +
                    ", weight=" + weight +
                    ", height=" + height +
                    ", basis='" + basis + '\'' +
                    ", interest='" + interest + '\'' +
                    ", target='" + target + '\'' +
                    ", sports='" + sports + '\'' +
                    '}';
        }
    }