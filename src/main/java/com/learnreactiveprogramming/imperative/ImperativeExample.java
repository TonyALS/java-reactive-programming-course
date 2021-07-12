package com.learnreactiveprogramming.imperative;

import java.util.ArrayList;
import java.util.List;

public class ImperativeExample {
    public static void main(String[] args) {
        List<String> namesList = List.of("alex", "ben", "chloe", "adam");
        List<String> namesGreaterThanSizeThree = namesGreaterThanSize(namesList, 3);

        System.out.println("newNamesList: " + namesGreaterThanSizeThree);
    }

    private static List<String> namesGreaterThanSize(List<String> namesList, int size) {
        var newNamesList = new ArrayList<String>();
        for (String name : namesList) {
            if (name.length() > size) {
                newNamesList.add(name);
            }
        }
        return newNamesList;
    }
}
