package com.example.android.android_attendance_system.model;

import static android.R.attr.id;

/**
 * Created by LOKESH on 14-08-2017.
 */

public class Batch {
    public long batch_pri_ID;
    public String batchName;
    public String branchName;
    public String baseScholarId;
    public String semester;
    public String subject;
    public int totClasses;

    public Batch(){

    }
    public Batch(String batch,String sub){
        batchName=batch;
        subject=sub;
    }
    public void setBatch_pri_ID(long id){
        batch_pri_ID=id;
    }
    public long getBatch_pri_ID(){
        return batch_pri_ID;
    }

    public void setTotClasses(int totClasses){
        this.totClasses=totClasses;
    }
    public int getTotClasses(){
        return totClasses;
    }

    public Batch(String batch,String branch,String sem,String sub){
        batchName=batch;
        branchName=branch;
        //baseScholarId=scholarID;
        semester=sem;
        subject=sub;
    }

    public String getBaseScholarID(){
        return baseScholarId;
    }
    public String getBatchName(){
        return batchName;
    }
    public String getBatchBranch(){
        return branchName;
    }
    public String getSemester(){
        return semester;
    }
    public String getSubject(){
        return subject;
    }
}
