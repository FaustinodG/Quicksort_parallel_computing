package com.faustinodegroot.parallelcomputing;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * http://www.vogella.com/tutorials/JavaAlgorithmsQuicksort/article.html
 * Created by Faustino on 8-4-2017.
 */

public class Quicksort {

    private List<Student> mStudentList;
    private StudentGradeComparator mStudentGradeComparator = new StudentGradeComparator();
    private StudentNumberComparator mStudentNumberComparator = new StudentNumberComparator();

    public void sort(List<Student> students) {
        if (students == null || students.size() == 0) {
            return;
        }
        mStudentList = students;

        quickSort(0, mStudentList.size() - 1);
    }

    private void quickSort(int low, int high) {
        int i = low;
        int j = high;

        Student pivot = mStudentList.get(low + (high - low) / 2);

        while (i <= j) {
            while (mStudentGradeComparator.compare(mStudentList.get(i), pivot) == 1) {
                i++;
            }
            while (mStudentGradeComparator.compare(mStudentList.get(j), pivot) == -1) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(low, j);
        }
        if (i < high) {
            quickSort(i, high);
        }
    }

    private void swap(int i, int j) {
        Collections.swap(mStudentList, i, j);
    }

    private class StudentGradeComparator implements Comparator<Student> {

        @Override
        public int compare(Student student, Student student1) {
            if (student.getGrade() != student1.getGrade()) {
                return Double.valueOf(student.getGrade()).compareTo(student1.getGrade());
            } else return mStudentNumberComparator.compare(student1, student);
        }

    }

    private class StudentNumberComparator implements Comparator<Student> {

        @Override
        public int compare(Student student, Student student1) {
            return Integer.compare(student.getStudentNumber(), student1.getStudentNumber());
        }
    }
}
