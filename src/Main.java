import model.Report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(args[1]), "UTF-16");
        PrintStream toFile = new PrintStream(new File(args[2]));
        System.setOut(toFile);
        Report report = new Report(args[0]);
        while (scan.hasNextLine()){
            report.fill(scan.nextLine());
        }
        report.print();
    }
}
