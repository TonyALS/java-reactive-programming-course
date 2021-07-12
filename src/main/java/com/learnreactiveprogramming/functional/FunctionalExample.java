package com.learnreactiveprogramming.functional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunctionalExample {
    public static void main(String[] args) {
        var namesList = List.of("alex", "ben", "chloe", "adam");
        var namesGreaterThanSizeThree = namesList.stream()
                .filter(filterNames(3))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

        System.out.println("newNamesList: " + namesGreaterThanSizeThree);
    }

    public static Predicate<String> filterNames(int size) {
        return (string) -> string.length() > size;
    }
}
