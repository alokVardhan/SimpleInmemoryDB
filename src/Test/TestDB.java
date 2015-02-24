/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import simpleinMemorydb.InmemoryDB;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 *
 * @author alok
 */
public class TestDB {

    /**
     * @param args the command line arguments
     * This class runs the different testfiles and displays the output on console. 
     */
    public static void main(String[] args) {
//        please change the file names to testFile2 ,testFile3 etc for running different files 
        File file = new File("Resources/testFile5");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.out.println("File not found...please check the path again...");
            System.exit(0);
        }
       InmemoryDB.runShell(scanner,1);
    }
}
