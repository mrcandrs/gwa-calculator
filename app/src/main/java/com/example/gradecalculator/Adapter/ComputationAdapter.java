package com.example.gradecalculator.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gradecalculator.HelperClass.SubjectClass;
import com.example.gradecalculator.R;
import java.util.List;

public class ComputationAdapter extends RecyclerView.Adapter<ComputationAdapter.ViewHolder> {

    private List<SubjectClass> gradeList;

    public ComputationAdapter(List<SubjectClass> gradeList) {
        this.gradeList = gradeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_grades, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComputationAdapter.ViewHolder holder, int position) {
        SubjectClass compClass = gradeList.get(position);

        // Display subject name
        holder.tv_Subject.setText(compClass.getSubject_name());

        // Display weighted grades
        holder.tv_Prelims.setText(String.format("%.2f", compClass.getPrelims()));
        holder.tv_Midterm.setText(String.format("%.2f", compClass.getMidterm()));
        holder.tv_Prefinals.setText(String.format("%.2f", compClass.getPrefinals()));
        holder.tv_Finals.setText(String.format("%.2f", compClass.getFinals()));

        //Display original input values before weighting
        float originalPrelims = compClass.getPrelims() / 0.20f;
        float originalMidterm = compClass.getMidterm() / 0.20f;
        float originalPrefinals = compClass.getPrefinals() / 0.20f;
        float originalFinals = compClass.getFinals() / 0.40f;

        holder.tv_Converted_Prelims.setText(String.format("(%.2f * 0.20)", originalPrelims));
        holder.tv_Converted_Midterm.setText(String.format("(%.2f * 0.20)", originalMidterm));
        holder.tv_Converted_Prefinals.setText(String.format("(%.2f * 0.20)", originalPrefinals));
        holder.tv_Converted_Finals.setText(String.format("(%.2f * 0.40)", originalFinals));

        // Display the final grade (sum of all weighted grades)
        float finalGrade = compClass.getPrelims() + compClass.getMidterm() + compClass.getPrefinals() + compClass.getFinals();
        holder.tv_FinalGrade.setText(String.format("%.2f", finalGrade));

        // Display the converted final grade (grading scale)
        float convertedFinalGrade = (float) convertToGradeScale(finalGrade);
        holder.tv_Converted_FinalGrade.setText(String.format("(%.2f)", convertedFinalGrade));

        // Set the text color of tv_FinalGrade based on the final grade
        if (finalGrade > 89.50f) {
            holder.tv_FinalGrade.setTextColor(Color.BLUE);
        } else if (finalGrade < 69.50f) {
            holder.tv_FinalGrade.setTextColor(Color.RED);
        } else {
            holder.tv_FinalGrade.setTextColor(Color.BLACK);
        }

        if (convertedFinalGrade <= 1.75) {
            holder.tv_Converted_FinalGrade.setTextColor(Color.BLUE);
        } else if (convertedFinalGrade >= 3.00) {
            holder.tv_Converted_FinalGrade.setTextColor(Color.RED);
        } else {
            holder.tv_Converted_FinalGrade.setTextColor(Color.BLACK);
        }


    }

    @Override
    public int getItemCount() {
        return gradeList.size();
    }

    public void updateData(List<SubjectClass> newList) {
        gradeList.clear(); // Clear existing data
        gradeList.addAll(newList); // Add new list
        notifyDataSetChanged(); // Notify RecyclerView to refresh
    }

    // Grading scale conversion logic (same as in ComputationPage)
    private double convertToGradeScale(double gwa) {
        if (gwa >= 97.5) return 1.00;
        if (gwa >= 94.5) return 1.25;
        if (gwa >= 91.5) return 1.50;
        if (gwa >= 88.5) return 1.75;
        if (gwa >= 85.5) return 2.00;
        if (gwa >= 81.5) return 2.25;
        if (gwa >= 77.5) return 2.50;
        if (gwa >= 73.5) return 2.75;
        if (gwa >= 69.5) return 3.00;
        return 5.00; // If below passing, adjust accordingly
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Subject, tv_Prelims, tv_Midterm, tv_Prefinals, tv_Finals, tv_FinalGrade;
        TextView tv_Converted_Prelims, tv_Converted_Midterm, tv_Converted_Prefinals, tv_Converted_Finals, tv_Converted_FinalGrade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Subject = itemView.findViewById(R.id.tv_Subject);
            tv_Prelims = itemView.findViewById(R.id.tv_Prelims);
            tv_Midterm = itemView.findViewById(R.id.tv_Midterm);
            tv_Prefinals = itemView.findViewById(R.id.tv_Prefinals);
            tv_Finals = itemView.findViewById(R.id.tv_Finals);
            tv_FinalGrade = itemView.findViewById(R.id.tv_FinalGrade);

            // Find the new text views in the layout
            tv_Converted_Prelims = itemView.findViewById(R.id.tv_Converted_Prelims);
            tv_Converted_Midterm = itemView.findViewById(R.id.tv_Converted_Midterm);
            tv_Converted_Prefinals = itemView.findViewById(R.id.tv_Converted_Prefinals);
            tv_Converted_Finals = itemView.findViewById(R.id.tv_Converted_Finals);
            tv_Converted_FinalGrade = itemView.findViewById(R.id.tv_Converted_FinalGrade);
        }
    }
}