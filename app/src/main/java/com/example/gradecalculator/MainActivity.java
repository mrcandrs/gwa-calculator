package com.example.gradecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gradecalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //root binding
    private ActivityMainBinding root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(root.getRoot());

        // Position
        root.image.setX(1300);
        root.txtTitle.setX(1300);
        root.txtQuote.setX(1300);

        // Animate the splash
        root.image.animate()
                .translationXBy(-1300)
                .setDuration(250)
                .setStartDelay(500)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        root.image.animate()
                                .rotationBy(90)
                                .setDuration(250)
                                .setStartDelay(250)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        root.txtTitle.animate()
                                                .translationXBy(-1300)
                                                .setDuration(200)
                                                .setStartDelay(250)
                                                .withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        root.txtQuote.animate()
                                                                .translationXBy(-1300)
                                                                .setDuration(250)
                                                                .withEndAction(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        // Delay before going to the next activity
                                                                        new Handler().postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                Intent intent = new Intent(MainActivity.this, HomePage.class);
                                                                                startActivity(intent);
                                                                                finish();
                                                                            }
                                                                        }, 1000); // 1000 milliseconds = 1 second delay
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}