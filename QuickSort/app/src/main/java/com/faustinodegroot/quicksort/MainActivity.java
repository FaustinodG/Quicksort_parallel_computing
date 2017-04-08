package com.faustinodegroot.quicksort;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Student> mStudentList = new ArrayList<>();
    private int studentNumber = 5006000;
    private static final int STUDENT_COUNT = 1000000;

    private Button mButton;
    private TextView mResultView;
    private Quicksort mQuickSort = new Quicksort();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button_generate);
        mResultView = (TextView) findViewById(R.id.title_results);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateStudents(STUDENT_COUNT);
            }
        });
    }

    private void generateStudents(int amount) {
        for (int i = 0; i < amount; i++) {
            Student student = new Student(studentNumber++, generateGrade());
            mStudentList.add(student);
        }
        Collections.shuffle(mStudentList);

        long startTime = System.currentTimeMillis();
        mQuickSort.sort(mStudentList);
        long endTime = System.currentTimeMillis();

        mResultView.append("\n Quicksorting " + STUDENT_COUNT + " students in " + (endTime - startTime) + "ms");

    }

    private double generateGrade() {
        Random r = new Random();
        int lowestGrade = 10;
        int highestGrade = 101;
        int result = r.nextInt(highestGrade - lowestGrade) + lowestGrade;
        return (double) result / 10;
    }
}
