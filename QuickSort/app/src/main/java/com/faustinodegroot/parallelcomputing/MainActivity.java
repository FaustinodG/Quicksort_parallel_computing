package com.faustinodegroot.parallelcomputing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private List<Student> mStudentList = new ArrayList<>();
    private int studentNumber = 5006000;
    private static final int STUDENT_COUNT = 1000000;

    private Button mButton;
    private TextView mResultView;
    private TextView mUserInput;
    private Quicksort mQuickSort = new Quicksort();
    private MergeSort mMergeSort = new MergeSort();
    private Pattern number = Pattern.compile("^(-?)\\d+$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button_generate);
        mResultView = (TextView) findViewById(R.id.title_results);
        mUserInput = (TextView) findViewById(R.id.input_students);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUserInput.length() != 0 && !mUserInput.getText().equals("")) {
                    mButton.setEnabled(false);
                    generateStudents(Integer.parseInt(mUserInput.getText().toString()));
                } else {
                    Toast.makeText(MainActivity.this, "Input cannot be empty", Toast.LENGTH_SHORT).show();
                }

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
        mMergeSort.parallelMergeSort(mStudentList, 4);
        long endTime = System.currentTimeMillis();

        mResultView.append("\n Merge sort " + mUserInput.getText() + " students in " + (endTime - startTime) + "ms");

        mButton.setEnabled(true);
        mStudentList.clear();
    }

    private double generateGrade() {
        Random r = new Random();
        int lowestGrade = 10;
        int highestGrade = 101;
        int result = r.nextInt(highestGrade - lowestGrade) + lowestGrade;
        return (double) result / 10;
    }
}
