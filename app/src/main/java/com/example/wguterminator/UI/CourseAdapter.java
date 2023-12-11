package com.example.wguterminator.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.R;

import org.w3c.dom.Text;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;
        private final TextView courseItemView3;
        private final TextView courseItemView4;
        private final TextView courseItemView5;
        private final TextView courseInstructorItem;
        private final TextView courseItemView6;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            courseItemView2 = itemView.findViewById(R.id.textViewCourseStartDate);
            courseItemView3 = itemView.findViewById(R.id.textViewCourseEndDate);
            courseItemView4 = itemView.findViewById(R.id.textViewCourseStatus);
            courseItemView5 = itemView.findViewById(R.id.textViewCourseNote);
            courseInstructorItem = itemView.findViewById(R.id.courseInstructorName);
            courseItemView6 = itemView.findViewById(R.id.assignedInstructor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetails.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("name", current.getCourseName());
                    intent.putExtra("startDate", current.getStartDate());
                    intent.putExtra("endDate", current.getEndDate());
                    intent.putExtra("instructorName", current.getCourseInstructorName());
                    intent.putExtra("instructorEmail", current.getCourseInstructorEmail());
                    intent.putExtra("instructorPhone", current.getCourseInstructorNumber());
                    intent.putExtra("status", current.getStatus());
                    intent.putExtra("note", current.getCourseNote());
                    intent.putExtra("assignedInstructor", current.getCourseInstructorName());
                    context.startActivity(intent);
                }
            });
        }
    }

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    /*Testing
    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_course_details,parent,false);
        return new CourseViewHolder(itemView);
    }

     */


    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if (mCourses != null) {
            Course current = mCourses.get(position);
            String name = current.getCourseName();
            String startDate = current.getStartDate();
            String endDate = current.getEndDate();
            String status = current.getStatus().name();
            String note = current.getCourseNote();
            String instructorName = current.getCourseInstructorName();
            String instructorEmail = current.getCourseInstructorEmail();
            String instructorPhone = current.getCourseInstructorNumber();
            String assignedInstructor = current.getCourseInstructorName();
            holder.courseItemView.setText(name);
            holder.courseItemView2.setText(startDate);
            holder.courseItemView3.setText(endDate);
            holder.courseItemView4.setText(status);
            holder.courseItemView5.setText(note);
            holder.courseItemView6.setText(assignedInstructor);
        }
        else {
            holder.courseItemView.setText("No Course Name");
            holder.courseItemView2.setText("No Date");
            holder.courseItemView3.setText("No Date");
            holder.courseItemView4.setText("No Status");
            holder.courseItemView5.setText("Empty Note");
            holder.courseInstructorItem.setText("Empty Teacher");
            holder.courseItemView6.setText("No assignment");
        }

    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}
