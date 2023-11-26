package com.example.wguterminator.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wguterminator.Entities.Term;
import com.example.wguterminator.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;
        private final TextView termItemView2;
        private final TextView termItemView3;
        private final TextView termItemView4;
        private TermViewHolder(View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.textViewTermName);
            termItemView2 = itemView.findViewById(R.id.textViewTermDate);
            termItemView3 = itemView.findViewById(R.id.textViewTermEndDate);
            termItemView4 = itemView.findViewById(R.id.textViewTermId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent intent = new Intent(context, TermDetails.class);
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("name", current.getTermName());
                    intent.putExtra("date", current.getTermDate());
                    intent.putExtra("endDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    public TermAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            Term current = mTerms.get(position);
            String date = current.getTermDate();
            String name = current.getTermName();
            String endDate = current.getTermEndDate();
           // int termId = current.getTermId();
            holder.termItemView.setText(name);
            holder.termItemView2.setText(date);
            holder.termItemView3.setText(endDate);
           // holder.termItemView4.setText(termId);
        }
        else {
            holder.termItemView.setText("No Term Name");
        }

    }

    @Override
    public int getItemCount() {
        return mTerms.size();
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
}
