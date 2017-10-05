/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nus.pgdb;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dineli
 */
public class Test1 {

    public static void main(String[] args) {
        Integer[] numbers = new Integer[]{1, 2, 3, 3, 1, 6, 2, 10};
//        System.out.println(Arrays.toString(removeDuplicates(numbers)));
//        System.out.println(getMaxValue(numbers));
        printValues();
    }

    public static void removeDuplicatesWithSet(Integer[] arrWithDuplicates) {
        List<Integer> list = Arrays.asList(arrWithDuplicates);
        Set uniqueSet = new HashSet<>(list);

        Iterator it = uniqueSet.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public static int[] removeDuplicates(Integer[] arrWithDuplicates) {
        int[] newArray = new int[arrWithDuplicates.length];

//        Arrays.sort(arrWithDuplicates);
        int tempValue = arrWithDuplicates[0];

        for (int i = 1; i < arrWithDuplicates.length; i++) {
            int newValue = arrWithDuplicates[i];

            if (tempValue != newValue) {
                newArray[i] = newValue;
            }
            tempValue = newValue;
        }

        return newArray;
    }

    public static int getMaxValue(Integer[] arrWithDuplicates) {
        int maxValue = arrWithDuplicates[0];
        
         for (int i = 1; i < arrWithDuplicates.length; i++) {
            int newValue = arrWithDuplicates[i];

            if (maxValue < newValue) {
                maxValue = newValue;
            }
        }
        return maxValue;
    }
    
    public static void printValues() {
        
        for(int i = 1; i <=50; i++){
            if(i % 3 == 0) System.out.println("Frizz");
            else if(i % 5 == 0) System.out.println("Buzz");
            else if(i % (3 * 5) == 0) System.out.println("FrizzBuzz");
            else
                System.out.println(i);
        }
        
    }
    
}
