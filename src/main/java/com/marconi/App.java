package com.marconi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Input source is not present.");
            return;
        }

        switch (defineOperationType(args)) {
            case FROM_USER_INPUT_TO_TERMINAL -> processFromUserToTerminalCase(args);
            case FROM_FILE_TO_TERMINAL -> processFromFileToTerminalCase(args);
            case FROM_FILE_TO_FILE -> processFromFileToFileCase(args);
        }
    }


    private static void processFromUserToTerminalCase(String[] args) {
        System.out.println(doSorting(validateInput(List.of(args))));
    }

    private static void processFromFileToTerminalCase(String[] args) {
        System.out.println(readFromFile(args));
    }

    private static void processFromFileToFileCase(String[] args) {
        List<Integer> integers = readFromFile(args);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[1]))) {
            for (Integer number : integers) {
                writer.write(number.toString() + " ");
            }
            System.out.println("Data successfully written to file.");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to file.");
        }
    }

    private static List<Integer> readFromFile(String[] args) {
        List<String> elementsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(" ");
                elementsList.addAll(Arrays.asList(elements));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file.");
        }
        return doSorting(validateInput(elementsList));
    }

    private static List<Integer> doSorting(List<Integer> integers) {
        return integers.stream()
                .filter(number -> (integers.size() % 2 == 0) == (number % 2 == 0))
                .collect(Collectors.toList());
    }

    private static List<Integer> validateInput(List<String> numbers) {
        try {
            return numbers.stream()
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Сan not be converted to integer: " + e.getMessage());
        }
    }

    private static Operation defineOperationType(String[] args) {
        if (args.length == 2 && args[0].endsWith(".txt") && args[1].endsWith(".txt")) {
            return Operation.FROM_FILE_TO_FILE;
        } else if (args.length == 1 && args[0].endsWith(".txt")) {
            return Operation.FROM_FILE_TO_TERMINAL;
        } else {
            return Operation.FROM_USER_INPUT_TO_TERMINAL;
        }
    }

    enum Operation {
        FROM_USER_INPUT_TO_TERMINAL,
        FROM_FILE_TO_TERMINAL,
        FROM_FILE_TO_FILE
    }
}
