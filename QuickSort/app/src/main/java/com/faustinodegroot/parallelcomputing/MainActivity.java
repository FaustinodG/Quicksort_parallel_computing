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
    private TextView mStudentCountInput;
    private TextView mThreadCountInput;
    private int mThreadCount = 1; //Default = 1
    private MergeSort mMergeSort = new MergeSort();
    private Pattern number = Pattern.compile("^(-?)\\d+$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button_generate);
        mResultView = (TextView) findViewById(R.id.title_results);
        mStudentCountInput = (TextView) findViewById(R.id.input_students);
        mThreadCountInput = (TextView) findViewById(R.id.input_threads);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThreadCountInput.length() != 0 && !mThreadCountInput.getText().equals("") &&
                        mThreadCountInput.getText().toString().matches(number.pattern())) {
                    mThreadCount = Integer.parseInt(mThreadCountInput.getText().toString());
                } else {
                    mThreadCount = 1;
                }
                if (mStudentCountInput.length() != 0 && !mStudentCountInput.getText().equals("")) {
                    if (mStudentCountInput.getText().toString().matches(number.pattern())) {

                        mButton.setEnabled(false);
                        generateStudents(Integer.parseInt(mStudentCountInput.getText().toString()));
                    } else {
                        Toast.makeText(MainActivity.this, "'Amount of students' has to be a number", Toast.LENGTH_SHORT).show();
                    }
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
        mMergeSort.parallelMergeSort(mStudentList, mThreadCount);
        long endTime = System.currentTimeMillis();

        mResultView.append("\nMerge sorted " + mStudentCountInput.getText() + " students in " + (endTime - startTime) + "ms with " + mThreadCount + " threads.");

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
