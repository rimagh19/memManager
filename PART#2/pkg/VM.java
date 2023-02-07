/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.io.*;

/**
 *
 * @author wajda
 */
public class VM {
    // 2 ^ 8 entries in the page table
    public static final int PAGE_TABLE_ENTRIES = 256;
    // Page size of 2 ^ 8 bytes
    public static final int PAGE_SIZE = 256;
    // frame size of 2 ^ 8 bytes
    public static final int FRAME_SIZE = 256;
    // 256 FRAMES
    public static final int NUM_OF_FRAMES = 256;
    // Physical memory of 65,536 bytes
    public static final int PHYSICAL_MEM_SIZE = 65536;
    // private PageTableEntry[] pageTable;
    // private Frame[] physicalMemory;

    public static int LogicalAddress; /* the virtual address being translated */
    public static int nextFrameNum;
    public static int pageNum; /* virtual page number */
    public static int frameNum;
    public static int offset;
    public static int physicalAddress; /* the physical address */
    public static int signedNUm; /* the physical address */

    public static boolean pgaeNumberExists;
    public static boolean PAExists;

    public static File LaddressFile = new File("../input/Laddress.txt");
    public static File signedNumbersFile = new File("../input/signedNumbers.txt");
    public static File output = new File("../output.txt");

    public static ArrayList<address> pageTable = new ArrayList<address>();

    // private static PrintWriter writer = new PrintWriter(LaddressFile);

    public static void main(String[] args) throws Exception {

        try (
                Scanner scanner = new Scanner(LaddressFile);
                Scanner scanner2 = new Scanner(signedNumbersFile);

        ) {

            for (int i = 0; i < 100; i++) {

                // 1- read num
                LogicalAddress = scanner.nextInt();
                signedNUm = scanner2.nextInt();

                // 2- conver to binery
                LogicalAddress = Integer.parseInt(convertIntToBinary(LogicalAddress), 2);

                System.out.println(LogicalAddress);
                // 3- extract page num
                pageNum = getPage(LogicalAddress);

                // 4- extract offset
                offset = getOffset(LogicalAddress);

                // 5- test page number
                pgaeNumberExists = testPageNumber(pageNum);

                // ---------------------------- @todo if exists in table

                // 6- generate frame number
                if (!pgaeNumberExists) {
                    // ---------------------------- @todo check frame nuumber
                    randomFramenum();
                }
                // 7- generate physical address
                generatePhysicalAddress();

                // 8- test physical address
                PAExists = testPhysicalAddress();

                while (PAExists) {
                    randomFramenum();
                    generatePhysicalAddress();
                    PAExists = testPhysicalAddress();
                }

                // 9- add to page table
                pageTable.add(new address(pageNum, frameNum, signedNUm, LogicalAddress, physicalAddress));

            }

            printReport();
        }

    }

    private static void printReport() throws Exception {
        try (PrintWriter writer = new PrintWriter(output)) {
            for (int i = 0; i < pageTable.size() - 1; i++) {
                writer.print(pageTable.get(i).toString());
            }
        }

    }

    private static boolean testPhysicalAddress() {
        for (int i = 0; i < pageTable.size() - 1; i++) {
            if (pageTable.get(i).getPhysicalAddress() == physicalAddress) {
                return true;
            }
        }
        return false;

    }

    private static void generatePhysicalAddress() {
        physicalAddress = (frameNum * 256) + offset;

    }

    private static boolean testPageNumber(int pageNumber) {
        for (int i = 0; i < pageTable.size() - 1; i++) {
            if (pageTable.get(i).getPageNum() == pageNumber) {
                return true;
            }
        }
        return false;
    }

    private static String convertIntToBinary(int num) {
        if (num == 0)
            return "0";

        StringBuilder ss = new StringBuilder();

        while (num > 0) {
            int rem = num % 2;
            ss.append(rem);
            num /= 2;
        }

        ss = ss.reverse();
        return ss.toString();
    }

    public static int getPage(int LogicalAddress) {
        return (LogicalAddress & 12);
    }

    public static int getOffset(int LogicalAddress) {
        return (LogicalAddress & 3);
    }

    public static void randomFramenum() {
        frameNum = (int) (Math.random() * 255);// max frame number is 255
    }

}
