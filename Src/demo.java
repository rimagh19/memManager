/*
 * demo() class
 * 
 * 
 * @authors Rima Ali (2005617),  Wajd Bandar Alharbi (2007057)
 * Arwa Mohammed Kulib (2019574), Ghaida Ahmed Aleryani  (1906952) A
 * King Abdulaziz university - FCIT - CS department - CPCS361 project - Part#1
 */

import java.util.Scanner;

public class demo {

    public static void main(String[] args) {

        int MemorySize;
        System.out.print("\n\n----Virtual memory size is 65536----\n");
        memManager Mem = new memManager(65536);
        Mem.Run();

    }

}
