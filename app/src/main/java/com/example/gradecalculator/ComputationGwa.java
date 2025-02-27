package com.example.gradecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gradecalculator.databinding.ActivityComputationGwaBinding;

public class ComputationGwa extends AppCompatActivity {
    private TextView txtGwa;

    private ActivityComputationGwaBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computation_gwa);

        txtGwa = findViewById(R.id.txtGwa);

        // Retrieve the GWA from the Intent
        float gwa = getIntent().getFloatExtra("computed_GWA", 0);

        // Display the GWA value in the TextView
        txtGwa.setText(String.format("%.2f", gwa));


        root.btnAnotherCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputationGwa.this, ComputationPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
