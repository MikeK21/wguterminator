package com.example.wguterminator.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterminator.Entities.Assessment;
import com.example.wguterminator.Entities.Course;
import com.example.wguterminator.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessItemView;
        private final TextView assessItemView2;
        //private final TextView assessItemView3;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessItemView = itemView.findViewById(R.id.assessName);
            //courseItemView2 = itemView.findViewById(R.id.textViewCourseStartDate);
            //courseItemView3 = itemView.findViewById(R.id.textViewCourseEndDate);
            assessItemView2 = itemView.findViewById(R.id.assessType);
            //courseItemView5 = itemView.findViewById(R.id.textViewCourseNote);
            //assessItemView3 = itemView.findViewById(R.id.assessEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessId", current.getAssessmentId());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("endDate", current.getAssessmentEndDate());
                    intent.putExtra("assessType", current.getAssessmentType());
                    context.startActivity(intent);
                }
            });
        }
    }
    public AssessmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {

        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            //String endDate = current.getAssessmentEndDate();
            String assessType = current.getAssessmentType().toString();
            holder.assessItemView.setText(name);
            holder.assessItemView2.setText(assessType);
            //holder.assessItemView3.setText(endDate);
        }
        else {
            holder.assessItemView.setText("no name");
            holder.assessItemView2.setText("no type");
            //holder.assessItemView3.setText("no date");
        }
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }
    public void setAssessments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
