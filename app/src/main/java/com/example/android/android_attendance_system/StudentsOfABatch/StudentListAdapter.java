package com.example.android.android_attendance_system.StudentsOfABatch;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;
import com.example.android.android_attendance_system.model.Students;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.media.CamcorderProfile.get;
import static android.util.Log.i;
import static com.example.android.android_attendance_system.R.id.branch;
import static com.example.android.android_attendance_system.R.id.radioGroup;
import static com.example.android.android_attendance_system.R.id.radioabsent;
import static com.example.android.android_attendance_system.R.id.radiopresent;

/**
 * Created by LOKESH on 20-08-2017.
 */

public class StudentListAdapter extends ArrayAdapter<Students> {
    int resourceID;
    Students s;
    //int i=0;
    int []attendResults;
    //int []attendMemory;
    boolean[] pre;
    RadioButton present;
    RadioButton absent;
    //Boolean p,a;
    ArrayList<Students> arrayOfStudents;
    Dbhelper db;
    int totClasses;
    int attendedClasses;
    StudentListAdapter(Context context, int resourceid, ArrayList<Students> a) {
        super(context, 0, a);
        resourceID = resourceid;
        arrayOfStudents=new ArrayList<>();
        attendResults=new int[a.size()];
        //attendMemory=new int[a.size()];

        pre=new boolean[a.size()];
//        for (int i=0;i<a.size();i++){
//            arrayOfStudents.add(new Students());
//        }
        arrayOfStudents.addAll(a);
        //Log.i("SSS",""+a.size());
        //totClasses=totclasses;
        //attendedClasses=attendedclasses;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem = convertView;
        db=new Dbhelper(this.getContext());
        if (resourceID == R.layout.student_list_item) {
            if (listitem == null) {
                listitem = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
            }
            Students s = getItem(position);
            TextView iDTextView = (TextView) listitem.findViewById(R.id.student_scholarid);
            TextView nameTextView = (TextView) listitem.findViewById(R.id.student_name);
            TextView totClasses = (TextView) listitem.findViewById(R.id.student_total);
            TextView attendPercent = (TextView) listitem.findViewById(R.id.student_attendPercent);

            iDTextView.setText(s.getID());
            nameTextView.setText(s.getName());
            totClasses.setText("Total: "+s.getClasses());
            attendPercent.setText("Attend %: "+String.format("%.2f",Float.parseFloat(s.getAttendancePercent()))+"%");
            return listitem;

        }
        else{

            if (true ) {
            listitem = LayoutInflater.from(getContext()).inflate(R.layout.attendance_list_item, parent, false);
            }
            s = getItem(position);
            TextView iDTextView = (TextView) listitem.findViewById(R.id.attend_id);
            //TextView nameTextView = (TextView) listitem.findViewById(R.id.student_name);

            iDTextView.setText(arrayOfStudents.get(position).getID());
            //Log.i("ooo",""+s.getPrimaryID());
            //nameTextView.setText(s.getName());
            present=(RadioButton) listitem.findViewById(R.id.radiopresent);
            absent=(RadioButton) listitem.findViewById(R.id.radioabsent);
            RadioGroup radioGroup=(RadioGroup) listitem.findViewById(R.id.radioGroup);

            radioGroup.clearCheck();

            if(arrayOfStudents.get(position).getPreClicked()==1){
                radioGroup.check(radiopresent);
            }
            else if(arrayOfStudents.get(position).getabClicked()==1){
                radioGroup.check(radioabsent);
            }
            else{
                radioGroup.clearCheck();
            }


            //Students m=db.getStudent(s.getID(),s.getName());
            //Log.i("mnbvcx",arrayOfStudents.get(position).getClassesPlus()+" "+arrayOfStudents.get(position).getID()+"  "+position);
            i("mnbv",attendResults[position]+"  "+position);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {
                    //Log.i("mnbnn","mnb"+present.isChecked()+" "+absent.isChecked());
                    if(checkedId==R.id.radiopresent){
                        //if(!p || !a)
                        //{
                        //listitem.setBackgroundColor(Color.parseColor("#e8eaf6"));
                        attendResults[position]=1;
                        //att.setAttendStatus(position);
                        //attendMemory[position]=1;
                        pre[position]=true;
                        Students current=db.getStudent(arrayOfStudents.get(position).getID(),arrayOfStudents.get(position).getName());
                        //long stuID=current.getPrimaryID();
                        //int cl=Integer.parseInt(current.getClasses());
                        i("classesID",""+current.getPrimaryID());
                        current.setClassesPlus(1);
                        current.setPreClicked(1);
                        arrayOfStudents.set(position,current);
                        //current.setClassesattended(cl+1);
                        db.updateStudent(current,3);
                        //}
                    }
                    else if(checkedId==R.id.radioabsent){
                        //Students current=db.getStudent(arrayOfStudents.get(position).getID(),arrayOfStudents.get(position).getName());
                       if(pre[position])
                       {
                           pre[position]=false;
                           //Log.i("mnbnn","mnba");
                           attendResults[position]=0;
                           //att.resetAttendStatus(position);
                           //attendMemory[position]=0;
                           Students current=db.getStudent(arrayOfStudents.get(position).getID(),arrayOfStudents.get(position).getName());
                           //long stuID=current.getPrimaryID();
                           //int cl=Integer.parseInt(current.getClasses());
                           //Log.i("Radio",""+attendResults[position]+" "+position+"    "+cl);
                           current.setClassesPlus(0);
                            current.setabClicked(1);
                           arrayOfStudents.set(position,current);
                           db.updateStudent(current,4);
                       //    a=true;

                       }
                        Students current=db.getStudent(arrayOfStudents.get(position).getID(),arrayOfStudents.get(position).getName());
                        current.setClassesPlus(0);
                        current.setabClicked(1);
                        arrayOfStudents.set(position,current);
                    }
                }
            });
            return listitem;
        }
        //Log.i("lllll",""+attendResults[0]+" "+attendResults[1]+" "+attendResults[2]+" "+attendResults[3]+" ");
        //return listitem;
    }
    public int[] getAttendArray(){
        return attendResults;
    }
    //Attendence a=new Attendence(attendResults);

}
