package Task;

import java.util.Hashtable;

public class InstanceType {
    private Hashtable<Integer, Host> hosts;

    private int empHostCount, fullHostCount;
    private int mstFilledHost, filledHostEmpCount;
    private String id;

    public InstanceType(String id) {
        hosts = new Hashtable<>();
        this.id = id;
        empHostCount = 0;
        fullHostCount = 0;
        mstFilledHost = 0;
        filledHostEmpCount = Integer.MAX_VALUE;
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

        if (host.getNumEmpSlots() > 0 && host.getNumEmpSlots() <= filledHostEmpCount && host.getNumFullSlots() > mstFilledHost){
            mstFilledHost = host.getNumFullSlots();
            filledHostEmpCount = host.getNumEmpSlots();
        }
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
        if (mstFilledHost ==  Integer.MAX_VALUE)
            return 0;

        return mstFilledHost;
    }

    public int getFilledHostEmpCount() {
        return filledHostEmpCount;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
