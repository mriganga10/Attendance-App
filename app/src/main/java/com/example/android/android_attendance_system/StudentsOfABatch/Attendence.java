package com.example.android.android_attendance_system.StudentsOfABatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.android_attendance_system.MainActivity;
import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Students;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.android.android_attendance_system.R.id.branch;

/**
 * Created by LOKESH on 21-08-2017.
 */

public class Attendence extends AppCompatActivity{
    long id;
    int totClasses;
    ArrayList<Students> s;
    Dbhelper db;

    String batchName;
    String branchName;
    String batchSem;
    String batchSub;
    String monthYearForPdf;
    int classAttendence[];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Intent intent=getIntent();
        id=intent.getExtras().getLong("BatchID");
        batchName=intent.getExtras().getString("BatchName");
        branchName=intent.getExtras().getString("BatchBranch");
        batchSub=intent.getExtras().getString("BatchSub");
        batchSem=intent.getExtras().getString("BatchSem");
        //Log.i("zzzz",""+intent.getExtras().getLong("BatchID"));
        ListView attendanceListView=(ListView) findViewById(R.id.listAttendance);
        db=new Dbhelper(this.getApplicationContext());
        s=db.getAllStudentsByBatch(intent.getExtras().getLong("BatchID"));
        StudentListAdapter myAdapter=new StudentListAdapter(this,R.layout.attendance_list_item,s);
        attendanceListView.setAdapter(myAdapter);
        classAttendence=myAdapter.getAttendArray();

        Button b=(Button) findViewById(R.id.confirm_attendence);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                showAttendConfirmationDialog();
            }
        });

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //Log.i("date",dateFormat.format(cal.getTime()));
        TextView date=(TextView) findViewById(R.id.date);
        date.setText("Date : "+dateFormat.format(cal.getTime()));


        monthYearForPdf=dateFormat.format(cal.getTime());

    }
    private void showAttendConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("NOTE: Taking attendence increases total classes by +1");
        builder.setPositiveButton("Take Attendance", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int idd) {


                //Dbhelper d=new Dbhelper(Attendence.this);
                totClasses=db.updateBatchTotClasses(id,1);
                float t=(float) totClasses;
                for(int j=0;j<s.size();j++) {
                    Students current = db.getStudent(s.get(j).getID(), s.get(j).getName());
                    //Students current=s.get(j);
                    long stuID = current.getPrimaryID();
                    int cl = Integer.parseInt(current.getClasses());
                    //int classesPlus=db.getStudentClassesPlus(s.get(j).getID(), s.get(j).getName());
                    int classesPlus=current.getClassesPlus();
                    current.setClassesattended(cl+classesPlus);
                    Log.i("classes",""+current.getID()+" "+cl+"+"+classesPlus);
                    db.updateStudent(current,2);
                    float updatedClasses=(float) (cl+classesPlus);
                    float i = (updatedClasses / t) * 100.00f;
                    db.updateStuAttendPercent(i, stuID);
                    db.updateStudent(current,4);
                    Log.i("RaTot", "up" + totClasses);


                }

                //getbatchpdf(id);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // Close the activity
        //finish();
    }



}
