package com.example.gradecalculator.HelperClass;

public class ComputationClass {
    private String subject;
    private double prelimGrade;
    private double midtermGrade;
    private double prefinalGrade;
    private double finalGrade;
    private double computedGrade;

    public ComputationClass(String subject, double prelimGrade, double midtermGrade,
                            double prefinalGrade, double finalGrade) {
        this.subject = subject;
        this.prelimGrade = prelimGrade;
        this.midtermGrade = midtermGrade;
        this.prefinalGrade = prefinalGrade;
        this.finalGrade = finalGrade;
        this.computedGrade = prelimGrade + midtermGrade + prefinalGrade + finalGrade;
    }

    // Getters and Setters
    public String getSubject() { return subject; }
    public double getPrelimGrade() { return prelimGrade; }
    public double getMidtermGrade() { return midtermGrade; }
    public double getPrefinalGrade() { return prefinalGrade; }
    public double getFinalGrade() { return finalGrade; }
    public double getComputedGrade() { return computedGrade; }
}