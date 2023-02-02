/*
 * Process() class
 * 
 * 
 * @authors Rima Ali (2005617),  Wajd Bandar Alharbi (2007057)
 * Arwa Mohammed Kulib (2019574), Ghaida Ahmed Aleryani  (1906952) A
 * King Abdulaziz university - FCIT - CS department - CPCS361 project - Part#1
 */

public class Process {
    private String Pname;
    private int Paddress;
    private int Psize;
    private String Pstatus;

    public Process() {
        this.Paddress = -1;
        this.Pstatus = "";
        this.Psize = 0;
    }

    public Process(int Paddress, int Psize, String Pstatus) {
        this.Paddress = Paddress;
        this.Pstatus = Pstatus;
        this.Psize = Psize;
    }

    public Process(int Psize, String Pstatus) {
        this.Pstatus = Pstatus;
        this.Psize = Psize;
    }

    public Process(int Psize, String Pstatus, String Pname) {
        this.Paddress = -1;
        this.Pstatus = Pstatus;
        this.Psize = Psize;
        this.Pname = Pname.toUpperCase();
    }

    public void setPaddress(int Paddress) {
        this.Paddress = Paddress;
    }

    public void setPstatus(String Pstatus) {
        this.Pstatus = Pstatus;
    }

    public int getPaddress() {
        return this.Paddress;
    }

    public String getPstatus() {
        return this.Pstatus;
    }

    public void setPsize(int Psize) {
        this.Psize = Psize;
    }

    public int getpsize() {
        return this.Psize;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String pname) {
        Pname = pname;
    }

    public void incsize(int addsize) {
        this.Psize += addsize;
    }
}
