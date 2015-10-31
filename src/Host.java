public class Host {

    private int  id;
    private String instanceType;
    private int slotCount, emptySlots, fullSlots;

    public Host(int id, String instanceType, int slotCount, int emptySlots, int fullSlots) {
        this.id = id;
        this.instanceType = instanceType;
        this.slotCount = slotCount;
        this.emptySlots = emptySlots;
        this.fullSlots = fullSlots;
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

    public int getEmptySlots() {
        return emptySlots;
    }

    public int getFullSlots() {
        return fullSlots;
    }
}
