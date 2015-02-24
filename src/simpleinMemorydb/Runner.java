/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpleinMemorydb;

import java.util.Scanner;

/**
 *
 * @author alok
 * runner class
 */
public class Runner {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InmemoryDB.runShell(scanner,0);
    }
}
