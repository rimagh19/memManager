import java.util.Comparator;

public class addressComparator implements Comparator<address>  {
    public int compare(address o1, address o2) {
        return o1.getPhysicalAddress() - o2.getPhysicalAddress();
    }
}
