package com.faustinodegroot.parallelcomputing;

import java.util.List;

/**
 * Created by Faustino on 8-4-2017.
 */

public class MergeSort {


    public void parallelMergeSort(List<Student> students, int threadCount)
    {
        if(threadCount <= 1)
        {
            mergeSort(students);
            return;
        }

        int mid = students.size() / 2;

        List<Student> left = students.subList(0,mid);
        List<Student> right = students.subList(mid, students.size());

        Thread leftSorter = mergeSortThread(left, threadCount);
        Thread rightSorter = mergeSortThread(right, threadCount);

        leftSorter.start();
        rightSorter.start();

        try {
            leftSorter.join();
            rightSorter.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        merge(left, right, students);
    }

    private Thread mergeSortThread(final List<Student> a, final int NUM_THREADS)
    {
        return new Thread()
        {
            @Override
            public void run()
            {
                parallelMergeSort(a, NUM_THREADS / 2);
            }
        };
    }

    public void mergeSort(List<Student> a)
    {
        if(a.size() <= 1) return;

        int mid = a.size() / 2;

        List<Student> left = a.subList(0,mid);
        List<Student> right = a.subList(mid, a.size());

        mergeSort(left);
        mergeSort(right);

        merge(left, right, a);
    }


    private static void merge(List<Student> a, List<Student> b, List<Student> r)
    {
        int i = 0, j = 0, k = 0;
        while(i < a.size() && j < b.size())
        {
            if(a.get(i).getGrade() < b.get(j).getGrade())
                r.set(k++,a.get(i++));
            else
                r.set(k++,b.get(j++));
        }

        while(i < a.size())
            r.set(k++,a.get(i++));

        while(j < b.size())
            r.set(k++,b.get(j++));
    }
}
