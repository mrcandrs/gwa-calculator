package com.example.gradecalculator.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

        root.btnViewGrade.setOnClickListener(view -> {
            Intent intent = new Intent(HomePage.this, ComputationPage.class);
            startActivity(intent);

            loadGradesFromDB();
            finish();
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
        String subject = root.txtSubject.getText().toString().trim();
        String prelims = root.txtPrelims.getText().toString().trim();
        String midterm = root.txtMidterm.getText().toString().trim();
        String prefinals = root.txtPrefinals.getText().toString().trim();
        String finals = root.txtFinals.getText().toString().trim();

        boolean isValid = true; // Track validity

        // Validate Subject (only letters)
        if (subject.isEmpty()) {
            root.txtSubject.setError("Subject field is required.");
            isValid = false;
        } else if (!subject.matches("[a-zA-Z ]+")) {
            root.txtSubject.setError("Subject should contain only letters.");
            isValid = false;
        } else {
            root.txtSubject.setError(null);
        }

        // Validate Grades (only numbers)
        if (!isValidGrade(prelims, root.txtPrelims, "Prelims")) isValid = false;
        if (!isValidGrade(midterm, root.txtMidterm, "Midterm")) isValid = false;
        if (!isValidGrade(prefinals, root.txtPrefinals, "Prefinals")) isValid = false;
        if (!isValidGrade(finals, root.txtFinals, "Finals")) isValid = false;

        return isValid;
    }

    private boolean isValidGrade(String input, android.widget.EditText editText, String fieldName) {
        if (input.isEmpty()) {
            editText.setError(fieldName + " field is required.");
            return false;
        }
        try {
            float value = Float.parseFloat(input);
            if (value < 0 || value > 100) {
                editText.setError(fieldName + " should be between 0 and 100.");
                return false;
            }
        } catch (NumberFormatException e) {
            editText.setError(fieldName + " should be a valid number.");
            return false;
        }
        editText.setError(null);
        return true;
    }

    private float calculateGrade(String input, float weight) {
        try {
            return Float.parseFloat(input) * weight;
        } catch (NumberFormatException e) {
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
