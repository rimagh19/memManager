/*
 * mamManager() class
 * 
 * 
 * @authors Rima Ali (2005617),  Wajd Bandar Alharbi (2007057)
 * Arwa Mohammed Kulib (2019574), Ghaida Ahmed Aleryani  (1906952)
 * King Abdulaziz university - FCIT - CS department - CPCS361 project - Part#1
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class memManager {
    private int MemorySize; // size of the memory
    private ArrayList<Process> Processes; // list of process objects
    public static String option; // track the option command
    public Scanner scanner = new Scanner(System.in); // scanner

    /*
     * empty constructor
     */
    public memManager() {
        this.MemorySize = 0;
        this.Processes = new ArrayList<>();
    }

    /*
     * constructor with size parameter
     */
    public memManager(int MemorySize) {
        this.MemorySize = MemorySize;
        this.Processes = new ArrayList<>();
        this.Processes.add(new Process(0, MemorySize, "Free"));
    }

    /*
     * Run()
     * 
     * @return void
     * runs the program
     */
    public void Run() {
        do {
            menu();
            checkCommand();
        } while (!option.equalsIgnoreCase("e"));
    }

    /*
     * menu()
     * 
     * @return void
     * Display the commands menu
     */
    public void menu() {
        System.out.println("\n\n ----Welcome, please pick the operation----");
        System.out.println("");
        System.out.println("RQ: Request for a contiguous block of memory.");
        System.out.println("RL Release of a contiguous block of memory.");
        System.out.println("C: Compact unused holes of memory into one single block.");
        System.out.println("STAT: Report the regions of free and allocated memory.");
        System.out.println("E: exit.");
        System.out.println("");
        System.out.println("please enter your choice : ");
        option = scanner.next().toLowerCase().trim();

    }

    /*
     * checkCommand()
     * 
     * @return void
     * test the users command and implement the right method
     */
    public void checkCommand() {
        switch (option) {
            case "rq":
                if (!request()) {
                    System.err.println("\nPartition Ocuupation Failed");
                } else {
                    System.out.println("\nPartition Ocuupation Succeded");
                }
                break;
            case "rl":
                if (!release()) {
                    System.err.println("\nPartition Deallocation Faild");
                } else {
                    System.out.println("\nPartition Deallocation Succeded");
                }
                break;

            case "c":
                compact();
                break;
            case "stat":
                printStatus();
                break;
            case "e":
                System.out.println(
                        "\n\n----before saying goodbye.. do you want to review the memory content? (yes/no)----");
                option = scanner.next().toLowerCase().trim();
                if (option.equals("yes")) {
                    printStatus();
                }

                System.out.println("\n\n----bye!!----");

                System.exit(0);
            default:
                System.out.println("Sorry, wrong input...");
                break;
            // Statements
        }
    }

    /*
     * request()
     * 
     * @return boolean indicating whether the request done successfully or not
     * Allocate a process in the memory
     */
    private boolean request() {
        String Pname = scanner.next().trim();
        int Size = scanner.nextInt();

        char Palg = checAlg();

        System.out.print("\nEnter the Size of the Process : ");
        Process New = new Process(Size, "Occupied", Pname);

        int ind = -1;
        switch (Palg) {
            case 'f':
                ind = firstFit(New);
                break;
            case 'w':
                ind = worstFit(New);
                break;
            case 'b':
                ind = bestFit(New);
                break;
            default:
                System.out.println("Sorry, wrong algorithm, try again....");
                break;
        }

        if (ind == -1) { // no free partition satisfies the policy

            return false;

        } else {

            Process instant = this.Processes.get(ind); // get the chosen partition returned from the policy
            int start = instant.getPaddress();
            New.setPaddress(start);

            // eliminating internal fragmentation)

            this.Processes.remove(ind);
            this.Processes.add(ind, New);

            int remainingSize = instant.getpsize() - Size;
            if (remainingSize != 0) {
                int startFree = start + Size;
                this.Processes.add(ind + 1, new Process(startFree, remainingSize, "Free"));
            }

            return true;

        }

    }

    /*
     * checkAlg()
     * 
     * @return char: Palg
     * test weather the entered Algorithm is valid or not
     */
    private char checAlg() {
        char Palg = '-';

        boolean validAlg = false;
        while (!validAlg) {
            Palg = (scanner.next().trim()).toLowerCase().charAt(0);
            switch (Palg) {
                case 'f':
                case 'w':
                case 'b':
                    validAlg = true;
                    break;
                default:
                    System.out.println(
                            "Sorry, wrong algorithm (valid algorihms are:\nf: firstFit\nw: worstFit\nb: bestFit), try again....");
                    break;
            }
        }
        return Palg;
    }

    /*
     * release()
     * 
     * @return boolean indicating hether the redlase done successfully or not
     * release a process from the memory
     */
    private boolean release() {

        String Pname = scanner.next();

        for (int i = 0; i < Processes.size(); ++i) {
            System.out.println(1);

            if (Processes.get(i).getPname().equalsIgnoreCase(Pname) && Processes.get(i).getPstatus() != "Free") {
                System.out.println(2);

                Processes.get(i).setPstatus("Free");

                return true;

            }

        }

        return false;

    }

    /*
     * compact()
     * 
     * @return void
     * impelments the functions responsible for handeling the compaction
     */
    private void compact() {

        int freeSpacesCount = countFreeAddresses();
        removeFreeParitions();
        int start = shiftOcuupiedPartitions();

        this.Processes.add(new Process(start, freeSpacesCount, "Free"));

    }

    /*
     * countFreeAddresses()
     * 
     * @return int: # unused addresses
     */
    private int countFreeAddresses() {

        int sum = 0;

        for (int i = 0; i < this.Processes.size(); ++i) {

            if (this.Processes.get(i).getPstatus() == "Free") {
                sum += this.Processes.get(i).getpsize();
            }

        }

        return sum;

    }

    /*
     * removeFreeParitions()
     * 
     * @return void
     * removes unused processes from the array list
     */
    private void removeFreeParitions() {

        for (int i = 0; i < this.Processes.size(); ++i) {

            if (this.Processes.get(i).getPstatus() == "Free") {

                this.Processes.remove(i);
                i--;

            }

        }

    }

    /*
     * removeFreeParitions())
     * 
     * @return int: The first address of the new partition
     * removes unused processes from the array list
     */
    private int shiftOcuupiedPartitions() {

        int start = 0;

        for (int i = 0; i < this.Processes.size(); ++i) {

            if (this.Processes.get(i).getPaddress() != start) {
                this.Processes.get(i).setPaddress(start);
            }

            start = this.Processes.get(i).getPaddress() + this.Processes.get(i).getpsize();

        }
        return start;
    }

    /*
     * printStatusI()
     * 
     * @return void
     * Display the memory status
     */
    private void printStatus() {

        System.out.println("\n\n-----Memory Status----: ");

        for (int i = 0; i < this.Processes.size(); ++i) {

            int start = this.Processes.get(i).getPaddress();
            int size = this.Processes.get(i).getpsize();
            String status = this.Processes.get(i).getPstatus();
            String name = this.Processes.get(i).getPname();

            System.out.print(
                    "Addresses [" + start + ":"
                            + (start + size - 1) + "] ");

            if (status.equalsIgnoreCase("free")) {
                System.out.println("Unused");
            } else {
                System.out.println("Process " + name);
            }

        }

    }

    /*
     * bestFit() algorithm
     * 
     * @return int: -1 if not successful
     */
    private int bestFit(Process New) {
        int ind = -1, mn = 1000000;
        for (int i = 0; i < Processes.size(); ++i) {
            if (Processes.get(i).getPstatus().equals("Free")) {
                if (Processes.get(i).getpsize() >= New.getpsize()
                        && Processes.get(i).getpsize() < mn) {
                    mn = Processes.get(i).getpsize();
                    ind = i;
                }
            }
        }

        return ind;
    }

    /*
     * worstFit() algorithm
     * 
     * @return int: -1 if not successful
     */
    private int worstFit(Process New) {
        int ind = -1, mx = 0;
        for (int i = 0; i < Processes.size(); ++i) {
            if (Processes.get(i).getPstatus().equals("Free")) {
                if (Processes.get(i).getpsize() >= New.getpsize()
                        && Processes.get(i).getpsize() > mx) {
                    mx = Processes.get(i).getpsize();
                    ind = i;
                }
            }
        }

        return ind;
    }

    /*
     * firstfit() algorithm
     * 
     * @return int: -1 if not successful
     */
    private int firstFit(Process New) {
        for (int i = 0; i < Processes.size(); ++i) {
            if (Processes.get(i).getPstatus().equals("Free")) {
                if (Processes.get(i).getpsize() >= New.getpsize()) {
                    return i;
                }
            }
        }

        return -1;

    }

}
