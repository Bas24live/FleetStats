import java.util.Hashtable;

public class InstanceType {
    private Hashtable<Integer, Host> hosts;

    private int empHostCount, fullHostCount;
    private int mstFilledHost, filledHostEmpCount;
    private String id;

    public InstanceType(String id) {
        hosts = new Hashtable<>();
        empHostCount = 0;
        fullHostCount = 0;
        mstFilledHost = 0;
        filledHostEmpCount = 0;
    }

    public void addHost(Host host) {
        hosts.put(host.getId(), host);
        calcHosts(host);
    }


    private void calcHosts(Host host) {
        int slotCount = host.getSlotCount();

        if (host.getNumEmpSlots() == slotCount)
            ++empHostCount;
        else if (host.getNumFullSlots() ==  slotCount)
            ++fullHostCount;
    }

    /////////////////////////////////////////////////////Getters////////////////////////////////////////////////////////

    public String getId() {
        return id;
    }

    public Host getHost(int id) {
        if (hosts.containsKey(id))
            return hosts.get(id);
        else
            return null;
    }

    public int getEmpHostCount() {
        return empHostCount;
    }

    public int getFullHostCount() {
        return fullHostCount;
    }

    public int getMstFilledHosts() {
        return mstFilledHost;
    }

    public int getFilledHostEmpCount() {
        return filledHostEmpCount;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
