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
import com.example.wguterminator.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder>{


    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessItemView;
        private final TextView assessItemView2;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessItemView = itemView.findViewById(R.id.assessName);
            assessItemView2 = itemView.findViewById(R.id.assessType);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class);
                    intent.putExtra("assessId", current.getAssessmentId());
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("name", current.getAssessmentName());
                    intent.putExtra("startDate", current.getAssessmentStartDate());
                    intent.putExtra("endDate", current.getAssessmentEndDate());
                    intent.putExtra("assessType", current.getAssessmentType().toString());
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

    /**
     * View Holder to setup Assessment View
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {

        if (mAssessments != null) {
            Assessment current = mAssessments.get(position);
            String name = current.getAssessmentName();
            String assessType = current.getAssessmentType().toString();
            holder.assessItemView.setText(name);
            holder.assessItemView2.setText(assessType);
        }
        else {
            holder.assessItemView.setText("no name");
            holder.assessItemView2.setText("no type");
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
