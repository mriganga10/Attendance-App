package com.example.android.android_attendance_system;

/**
 * Created by LOKESH on 14-08-2017.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.android_attendance_system.data.Dbhelper;
import com.example.android.android_attendance_system.model.Batch;

import java.util.ArrayList;

import static android.R.id.edit;


public class MyCursorAdapter extends ArrayAdapter<Batch> {

    MyCursorAdapter(Context context, ArrayList<Batch> a){
        super(context,0,a);
    }
    // Find individual views that we want to modify in the list item layout
    //TextView nameTextView = (TextView) findViewById(R.id.batch_in_list);
    //TextView summaryTextView = (TextView) findViewById(R.id.subjec);
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitem=convertView;
        if(listitem==null){
            listitem= LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item,parent,false);
        }
        Batch b=getItem(position);
        TextView nameTextView = (TextView) listitem.findViewById(R.id.batch_in_list);
        TextView subTextView = (TextView) listitem.findViewById(R.id.subjec);
        TextView branch=(TextView) listitem.findViewById(R.id.branch_item);
        TextView sem=(TextView) listitem.findViewById(R.id.sem_item);
        nameTextView.setText("Batch: "+b.getBatchName());
        subTextView.setText("Sub: "+b.getSubject());
        branch.setText("Branch: "+b.getBatchBranch());
        sem.setText("Sem: "+b.getSemester());


        return listitem;
    }


}

