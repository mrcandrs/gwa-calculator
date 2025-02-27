package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.gradecalculator.Adapter.ComputationAdapter;
import com.example.gradecalculator.Database.GradeDatabase;
import com.example.gradecalculator.HelperClass.SubjectClass;
import com.example.gradecalculator.databinding.ActivityComputationPageBinding;

import java.util.ArrayList;
import java.util.List;

public class ComputationPage extends AppCompatActivity {
    private ActivityComputationPageBinding root;
    private ComputationAdapter adapter;
    private GradeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityComputationPageBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());

        db = GradeDatabase.getInstance(this);

        // Setup RecyclerView
        root.recycleViewer.setLayoutManager(new LinearLayoutManager(this));

        // Load data from Room and set it to RecyclerView
        loadGradesFromDB();

        // Set up buttons
        root.btnCalculateSubject.setOnClickListener(view -> {
            Intent homeIntent = new Intent(ComputationPage.this, HomePage.class);
            startActivity(homeIntent);
            finish();
        });

        root.btnCalculateGWA.setOnClickListener(view -> calculateGWA());

        root.btnClear.setOnClickListener(view -> clearDatabase());
    }

    private void loadGradesFromDB() {
        List<SubjectClass> gradeList = db.getSubjectDAO().getCalculate();
        adapter = new ComputationAdapter(gradeList);
        root.recycleViewer.setAdapter(adapter);
    }

    private void calculateGWA() {
        List<SubjectClass> gradeList = db.getSubjectDAO().getCalculate();

        if (gradeList.isEmpty()) {
            Toast.makeText(this, "No subjects available to calculate GWA", Toast.LENGTH_SHORT).show();
            return;
        }

        float totalGrades = 0; // Sum of all final grades
        int totalSubjects = gradeList.size(); // Number of subjects

        for (SubjectClass subject : gradeList) {
            totalGrades += subject.getFinal_grade();  // Add each subject's grade
        }

        float exactGwa = totalGrades / totalSubjects; // Compute the average GWA

        // Pass the precise GWA (e.g., 1.02) to ComputationGwa activity
        Intent intent = new Intent(ComputationPage.this, ComputationGwa.class);
        intent.putExtra("GWA_RESULT", exactGwa); // Send precise GWA
        startActivity(intent);
    }



    private void clearDatabase() {
        db.getSubjectDAO().deleteAllGrades();
        adapter.updateData(new ArrayList<>());
        root.recycleViewer.setAdapter(adapter);
        Toast.makeText(this, "All records cleared", Toast.LENGTH_SHORT).show();
    }
}