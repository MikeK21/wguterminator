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
        private final TextView courseItemView4;
        private final TextView courseItemView6;
        private final TextView courseItemView7;
        private final TextView courseItemView8;
        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.textView2);
            courseItemView4 = itemView.findViewById(R.id.textViewCourseStatus);
            courseItemView6 = itemView.findViewById(R.id.assignedInstructor);
            courseItemView7 = itemView.findViewById(R.id.courseInstructorEmail);
            courseItemView8 = itemView.findViewById(R.id.courseInstructorPhone);
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
                    intent.putExtra("status", current.getStatus().toString());
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
            holder.courseItemView4.setText(status);
        }
        else {
            holder.courseItemView.setText("No Course Name");
            holder.courseItemView4.setText("No Status");
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
