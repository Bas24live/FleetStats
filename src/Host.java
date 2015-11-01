public class Host {

    private int  id;
    private String instanceType;
    private int slotCount, numEmpSlots, numFullSlots;
    private final int empty = 0, full = 1;

    public Host() {
        numEmpSlots = 0;
        numFullSlots = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public void setSlotCount(int slotCount) {
        this.slotCount = slotCount;
    }

    public boolean addSlot(int value) {
        boolean result = true;
        if (value == empty)
            ++numEmpSlots;
        else if(value == full)
            ++numFullSlots;
        else
            result = false;

        return result;
    }

    public int getId() {
        return id;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public int getNumEmpSlots() {
        return numEmpSlots;
    }

    public int getNumFullSlots() {
        return numFullSlots;
    }
}
