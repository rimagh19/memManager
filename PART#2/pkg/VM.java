/*
 * @todo Page fault
 * @todo addresses strings length
 * @todo Test Cases
 */

import java.util.*;
import java.io.*;

/**
 *
 * @author wajda
 */
public class VM {
    // 2 ^ 8 entries in the page table
    public static final int PAGE_TABLE_ENTRIES = 255;
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
    public static File testCase1 = new File("../testCase#1.txt");
    public static File testCase2 = new File("../testCase#2.txt");

    public static ArrayList<address> physicalMem = new ArrayList<address>();
    public static int[][] pageTable = new int[255][2]; // ====
    public static int i = 0;

    // private static PrintWriter writer = new PrintWriter(LaddressFile);

    public static void main(String[] args) throws Exception {
        fillpagetable();
        try (
                Scanner scanner = new Scanner(LaddressFile);
                Scanner scanner2 = new Scanner(signedNumbersFile);

        ) {

            for (int i = 0; i < 80; i++) {

                // 1- read num
                LogicalAddress = scanner.nextInt();
                signedNUm = scanner2.nextInt();

                // 2- conver to binery
                LogicalAddress = Integer.parseInt(convertIntToBinary(LogicalAddress), 2);

                // 3- extract page num
                pageNum = getPage(LogicalAddress);

                // 4- extract offset
                offset = getOffset(LogicalAddress);

                // 5- test page number

                // ---------------------------- @todo if exists in table

                // 6- generate frame number
                if (!testPageNumber()) {
                    // ---------------------------- @todo check frame nuumber
                    randomFramenum();
                } else {
                    getFrameNumber();
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
                pageTable[i][0] = pageNum;
                pageTable[i][1] = frameNum;
                physicalMem.add(new address(pageNum, frameNum, offset, signedNUm, LogicalAddress, physicalAddress));

            }
            physicalMem.sort(new addressComparator());
            printReport();
            testCase_samePageNumber();
            test_diffPage();
            calcFaultRate(); // ===========================
            displayFaultRate(); // ===========================

        }

    }

    private static void testCase_samePageNumber() throws Exception {

        int randomPageNumber = 33800;

        try (PrintWriter out = new PrintWriter(testCase1)) {
            out.println("NUM = " + randomPageNumber);
            for (int i = 0; i < physicalMem.size(); i++) {
                if (physicalMem.get(i).getPageNum() == randomPageNumber) {
                    out.println(physicalMem.get(i).toString());
                }
            }
        }

    }

    private static int getFrameNumber() {
        for (int i = 0; i < pageTable.length; i++) {
            if (pageTable[i][0] == pageNum) {
                return pageTable[i][1];
            }
        }
        return -1;

    }

    private static void test_diffPage() throws FileNotFoundException {
        int randomPageNumb = (int) (Math.random() * 255);

        try (PrintWriter out = new PrintWriter(testCase2)) {
            out.println("NUM = " + randomPageNumb);
            for (int i = 0; i < physicalMem.size(); i++) {
                if (physicalMem.get(i).getPageNum() != randomPageNumb) {
                    out.println(physicalMem.get(i).toString());
                }
            }
        }

    }

    public static void fillpagetable() {
        for (int i = 0; i < pageTable.length - 1; i++) {
            pageTable[i][1] = -1;
        }
    }

    private static void displayFaultRate() {
    }

    private static void calcFaultRate() {
    }

    private static void printReport() throws Exception {
        try (PrintWriter writer = new PrintWriter(output)) {
            for (int i = 0; i < physicalMem.size() - 1; i++) {
                writer.print(physicalMem.get(i).toString());
            }
        }

    }

    private static boolean testPhysicalAddress() {
        for (int i = 0; i < physicalMem.size() - 1; i++) {
            if (physicalMem.get(i).getPhysicalAddress() == physicalAddress) {
                return true;
            }
        }
        i++;
        return false;

    }

    private static void generatePhysicalAddress() {
        physicalAddress = (frameNum * 256) + offset;

    }

    private static boolean testPageNumber() {
        for (int i = 0; i < pageTable.length - 1; i++) {
            if (pageTable[i][0] == pageNum) {
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
        return (LogicalAddress & 6528010);
    }

    public static int getOffset(int LogicalAddress) {
        return (LogicalAddress & 255);
    }

    public static void randomFramenum() {
        frameNum = (int) (Math.random() * 255);// max frame number is 255

    }

}
