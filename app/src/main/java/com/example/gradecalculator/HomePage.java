package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.gradecalculator.Adapter.ComputationAdapter;
import com.example.gradecalculator.Database.GradeDatabase;
import com.example.gradecalculator.HelperClass.SubjectClass;
import com.example.gradecalculator.databinding.ActivityHomePageBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomePage extends AppCompatActivity {
    private ActivityHomePageBinding root;
    private ComputationAdapter adapter;
    private GradeDatabase db;
    private Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());

        db = GradeDatabase.getInstance(this);

        // Initialize Adapter
        adapter = new ComputationAdapter(new ArrayList<>());


        // Load data from Room database
        loadGradesFromDB();

        root.btnCalculaGrade.setOnClickListener(view -> {
            if (validateInputs()) {
                float prelims = calculateGrade(root.txtPrelims.getText().toString(), 0.20f);
                float midterm = calculateGrade(root.txtMidterm.getText().toString(), 0.20f);
                float prefinals = calculateGrade(root.txtPrefinals.getText().toString(), 0.20f);
                float finals = calculateGrade(root.txtFinals.getText().toString(), 0.40f);

                SubjectClass computation = new SubjectClass(
                        root.txtSubject.getText().toString(),
                        prelims, midterm, prefinals, finals
                );

                // Save to Room Database on a background thread
                executor.execute(() -> {
                    db.getSubjectDAO().addGrade(computation);

                    // Reload RecyclerView on the main thread
                    runOnUiThread(() -> {
                        loadGradesFromDB();
                        clearInputs();
                    });

                    // Open ComputationPage with the calculated data
                    Intent intent = new Intent(HomePage.this, ComputationPage.class);
                    intent.putExtra("subject_name", computation.getSubject_name());
                    intent.putExtra("prelims", computation.getPrelims());
                    intent.putExtra("midterm", computation.getMidterm());
                    intent.putExtra("prefinals", computation.getPrefinals());
                    intent.putExtra("finals", computation.getFinals());
                    startActivity(intent);
                });
            }
        });

        root.btnViewGrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ComputationPage.class);
                startActivity(intent);
                finish();;
            }
        });
    }

    private void loadGradesFromDB() {
        executor.execute(() -> {
            List<SubjectClass> gradeList = db.getSubjectDAO().getCalculate();
            runOnUiThread(() -> {
                adapter.updateData(gradeList); // Update the adapter with new data
            });
        });
    }

    private boolean validateInputs() {
        if (root.txtSubject.getText().toString().isEmpty() ||
                root.txtPrelims.getText().toString().isEmpty() ||
                root.txtMidterm.getText().toString().isEmpty() ||
                root.txtPrefinals.getText().toString().isEmpty() ||
                root.txtFinals.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private float calculateGrade(String input, float weight) {
        try {
            return Float.parseFloat(input) * weight;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for grades", Toast.LENGTH_SHORT).show();
            return 0.0f;
        }
    }

    private void clearInputs() {
        root.txtSubject.getText().clear();
        root.txtPrelims.getText().clear();
        root.txtMidterm.getText().clear();
        root.txtPrefinals.getText().clear();
        root.txtFinals.getText().clear();
    }
}