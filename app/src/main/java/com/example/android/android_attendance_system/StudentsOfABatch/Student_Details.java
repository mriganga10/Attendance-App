package com.example.android.android_attendance_system.StudentsOfABatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.android_attendance_system.R;
import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Students;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by LOKESH on 20-08-2017.
 */

public class Student_Details extends AppCompatActivity {
    String studentName;
    long batchId;
    Dbhelper d;
    Students s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_details);
        setTitle("Student Details");
        String studentID= getIntent().getExtras().getString("StuID");
        studentName= getIntent().getExtras().getString("StuName");
        batchId=getIntent().getExtras().getLong("BatchI");
        String batchName=getIntent().getExtras().getString("BatchNam").toString();
        Intent intent=new Intent(this,StudentsList.class);
        intent.putExtra("BatchName", batchName);
        intent.putExtra("BatchID", batchId);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        d=new Dbhelper(this.getApplicationContext());
        s=d.getStudent(studentID,studentName);
        Log.i("primary",""+s.getPrimaryID());
        displayStudent(s);
        Button call_button=(Button) findViewById(R.id.call_button);
        Button mail_button=(Button) findViewById(R.id.email_button);

        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+s.getContactNo()));
                startActivity(i);
            }
        });
        mail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Intent.ACTION_SENDTO);
                in.setData(Uri.parse("mailto:"+s.getEmail_id()));
                startActivity(in);
            }
        });
        //startActivity(intent);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void displayStudent(Students s){
        TextView schID=(TextView) findViewById(R.id.schid);
        schID.setText(s.getID());
        TextView nameee=(TextView) findViewById(R.id.nameee);
        nameee.setText(s.getName());
        TextView contact=(TextView) findViewById(R.id.textView3);
        contact.setText("Contact No. :"+s.getContactNo());
        TextView email=(TextView) findViewById(R.id.textView4);
        email.setText("Email Id :"+s.getEmail_id());
        TextView classes=(TextView) findViewById(R.id.textView5);
        classes.setText("Classes Attended :"+s.getClasses());
        Log.i("mmmmm",s.getClasses());
        TextView tot=(TextView) findViewById(R.id.totalClasses);
        int totClasses=d.updateBatchTotClasses(batchId,0);
        tot.setText("Total classes taken :"+(totClasses-1));
        //Log.i("mmmmm",s.getClasses());
        TextView apercent=(TextView) findViewById(R.id.textView6);
        apercent.setText("Attendendnce % : "+s.getAttendancePercent()+"%");
        TextView mid=(TextView) findViewById(R.id.textView7);
        mid.setText("Midsem :"+s.getMidSem());
        TextView end=(TextView) findViewById(R.id.textView8);
        end.setText("Endsem :"+s.getEndSem());
    }
}
