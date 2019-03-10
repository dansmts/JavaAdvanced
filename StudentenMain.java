package be.studenten;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentenMain {

    public static void main(String[] args) {
    // write your code here

        List<Student> studentsList = new ArrayList<>();
        List<Path> pathsList = new ArrayList<>();

        // Find all files in the main directory
        try (Stream<Path> paths = Files.walk(Paths.get("alle-bestanden"))) {
            paths.filter(Files::isRegularFile).forEach(path -> pathsList.add(path));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Read the files and save data in studentsList
        for (Path file : pathsList) {
            Thread read = new Thread(() -> readStudent(file, studentsList));
            read.run();
            try {
                read.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        // Write studentsList to studenten.csv
        Thread writeStudents = new Thread(() -> writeStudent(studentsList));
        writeStudents.run();

        // Aangezien er geen werknemers.csv file is heb ik de data analyse op studenten.csv gedaan
        List<Student> studentsListDataAnalyse = new ArrayList<>();
        Path pathData = Paths.get("studenten.csv");
        Thread readStudentsDataAnalyse = new Thread(() -> readStudent(pathData, studentsListDataAnalyse));
        readStudentsDataAnalyse.run();

        // Werknemer met de langste voornaam


        // Aantal werknemers per stad
        Set<String> steden = studentsListDataAnalyse.stream().map(Student::getStad).collect(Collectors.toSet());
        for (String stad : steden) {
            int aantalWerknemersPerStad = (int) studentsListDataAnalyse.stream().filter(s -> stad.equals(s.getStad())).count();
            System.out.println(String.format("Aantal werknemers in %s: %d", stad, aantalWerknemersPerStad));
        }

        // Aantal werknemers ouder dan 60
        int aantalWerknemersOuderDan60 = (int) studentsListDataAnalyse.stream().filter(s -> s.getLeeftijd() > 60).count();
        System.out.println(String.format("Aantal werknemers ouder dan 60: %s\n", aantalWerknemersOuderDan60));

        // Totaal aantal werknemers
        int totaalAantalWerknemers = (int) studentsListDataAnalyse.stream().count();
        System.out.println(String.format("Totaal aantal werknemers: %s\n", totaalAantalWerknemers));

        // Werknemers gesorteerd op familienaam
        List<Student> listStudentsDataAnalysisSorted = studentsListDataAnalyse.stream().sorted(Comparator.comparing(Student::getNaam)).collect(Collectors.toList());
    }

   private static void readStudent(Path path, List<Student> studentsList) {
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                studentsList.add(new Student(line.split(";")));
            }
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    private static void writeStudent(List<Student> studentsList) {
        try (FileWriter file = new FileWriter("studenten.csv"))
        {
            for (Student student : studentsList) {
                file.write(student.toString() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
