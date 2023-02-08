import java.util.Comparator;

public class address {
    private int pageNum;
    private int frameNum;
    private int signedNUm;
    private int LogicalAddress;
    private int physicalAddress;
    private int offset;

    public address(int pageNum, int frameNum, int offset, int signedNUm, int logicalAddress, int physicalAddress) {
        this.pageNum = pageNum;
        this.frameNum = frameNum;
        this.signedNUm = signedNUm;
        LogicalAddress = logicalAddress;
        this.physicalAddress = physicalAddress;
        this.offset=offset;
    }

    @Override
    public String toString() {
        return "address [pageNum=" + pageNum + ", frameNum=" + frameNum + ", offset=" + offset + ", signedNUm=" + signedNUm
                + ", LogicalAddress=" + LogicalAddress + ", physicalAddress=" + physicalAddress + "]\n";
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getFrameNum() {
        return frameNum;
    }

    public int getSignedNUm() {
        return signedNUm;
    }

    public int getLogicalAddress() {
        return LogicalAddress;
    }

    public int getPhysicalAddress() {
        return physicalAddress;
    }



}