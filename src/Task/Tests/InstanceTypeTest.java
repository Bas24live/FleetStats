package Task.Tests;

import Task.Host;
import Task.InstanceType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InstanceTypeTest {

    @Test
    public void testAddHost() throws Exception {
        InstanceType instanceType = new InstanceType("M1");
        Host host = new Host();
        host.setId(1);
        host.setSlotCount(1);
        host.addSlot(0);

        assertEquals("Check that getFilledHostEmpCount return 0 and not MaxValue when there are is not host that fills the criteria for a most filled host", 0, instanceType.getFilledHostEmpCount());

        instanceType.addHost(host);
        assertEquals("Check that empHostCount increases when a host with all empty slots is added the M1", 1, instanceType.getEmpHostCount());

        for (int i = 0; i < 9; i++) {
            instanceType.addHost(host);
            assertEquals("Check that empHostCount increases when a host with all empty slots is added the M1", i+2, instanceType.getEmpHostCount());

        }

        Host host1 = new Host();
        host1.setId(2);
        host1.setSlotCount(1);
        host1.addSlot(1);

        instanceType.addHost(host1);
        assertEquals("Check that fullHostCount increases when a host with all full slots is added the M1", 1, instanceType.getFullHostCount());

        for (int i = 0; i < 9; i++) {
            instanceType.addHost(host1);
            assertEquals("Check that fullHostCount increases when a host with all full slots is added the M1", i+2, instanceType.getFullHostCount());

        }

        Host host2 = new Host();
        host2.setId(2);
        host2.setSlotCount(5);
        host2.addSlot(0);
        for (int i = 0; i < 4; i++) {
            host2.addSlot(1);
        }

        instanceType.addHost(host2);
        assertEquals("Check that mstFilledHost changes when a new host that contains the same amount of empty slots as current host but more filled slots is added to M1", 4, instanceType.getMstFilledHosts());
        assertEquals("Check that filledHostEmpCount changes when a new host that contains the same amount of empty slots as current host but more filled slots is added to M1", 1, instanceType.getFilledHostEmpCount());

        host2.setSlotCount(6);
        host2.addSlot(1);
        instanceType.addHost(host2);
        assertEquals("Check that mstFilledHost changes when a new host that contains the same amount of empty slots as current host but more filled slots is added to M1", 5, instanceType.getMstFilledHosts());
        assertEquals("Check that filledHostEmpCount changes when a new host that contains the same amount of empty slots as current host but more filled slots is added to M1", 1, instanceType.getFilledHostEmpCount());

        host2.setSlotCount(10);
        host2.addSlot(1);
        host2.addSlot(1);
        host2.addSlot(1);
        host2.addSlot(0);
        instanceType.addHost(host2);
        assertEquals("Check that mstFilledHost does not change when a new host that contains more empty slots than the current host and has more filled slots is added to M1", 5, instanceType.getMstFilledHosts());
        assertEquals("Check that filledHostEmpCount does not change when a new host that contains more empty slots than the current host and has more filled slots is added to M1", 1, instanceType.getFilledHostEmpCount());
    }
}