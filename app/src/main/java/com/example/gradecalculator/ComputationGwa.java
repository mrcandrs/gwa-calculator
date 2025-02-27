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
        root = ActivityComputationGwaBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());


        float gwa = getIntent().getFloatExtra("GWA_RESULT", -1.00f); // Default is -1.00 (debugging)
    // Display exact GWA (e.g., 1.02, 1.13)
        root.txtGwa.setText(String.format("%.2f", gwa));



        root.btnAnotherCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputationGwa.this, HomePage.class);
                startActivity(intent);
                finish();
            }
        });

        root.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComputationGwa.this, ComputationPage.class);
                startActivity(intent);
                finish();
            }
        });
}
}
