package com.example.android.android_attendance_system.model;

/**
 * Created by LOKESH on 14-08-2017.
 */

public class Students {
    public long primaryID;
    public int classesPlus=0;
    public int preClicked=0;
    public int abClicked=0;
    public String rollNo;
    public String nameStudent;
    public String email_id;
    public String contactNo;
    public String classesattended;
    public String attendancePercent;
    public String midSem;
    public String endSem;
    //public long fkk;
    public Students(){

    }

    public Students(String roll,String name,String email,String contact,String classattend,String percentAttendance,String mid,String end){
        rollNo=roll;
        nameStudent=name;
        email_id=email;
        contactNo=contact;
        classesattended=classattend;
        attendancePercent=percentAttendance;
        midSem=mid;
        endSem=end;
        //fkk=f;
    }
    public void setPrimaryID(long id){
        primaryID=id;
    }
    public void setClassesattended(int c){
        classesattended=Integer.toString(c);
    }
    public void setattendedPercent(int c){
        attendancePercent=Integer.toString(c);
    }
    public long getPrimaryID(){
        return primaryID;
    }
    public String getClasses(){
        return classesattended;
    }
    public String getID(){
        return rollNo;
    }
    public String getName(){
        return nameStudent;
    }
    public String getEmail_id(){
        return email_id;
    }
    public String getContactNo(){
        return contactNo;
    }
    public String getAttendancePercent(){
        return attendancePercent;
    }
    public String getMidSem(){
        return midSem;
    }
    public String getEndSem(){
        return endSem;
    }
    public int getClassesPlus(){
        return classesPlus;
    }
    public void setClassesPlus(int b){
        classesPlus=b;
    }
    public void setPreClicked(int p){
        preClicked=p;
    }
    public int getPreClicked(){
        return preClicked;
    }
    public void setabClicked(int p){
        abClicked=p;
    }
    public int getabClicked(){
        return abClicked;
    }
}
