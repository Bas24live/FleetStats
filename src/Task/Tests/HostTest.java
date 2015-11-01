package Task.Tests;

import Task.Host;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;


public class HostTest {

    public Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @Test
    public void testAddSlot() throws Exception {
        Host host = new Host();

        assertEquals("Check that 0 is accepted as a empty slot", true, host.addSlot(0));
        assertEquals("Check that the number of empty slots has been increased to 1", 1, host.getNumEmpSlots());
        for (int i = 0; i < 9; i++) {
            host.addSlot(0);
        }
        assertEquals("Check that the number of empty slots has been increased to 10", 10, host.getNumEmpSlots());

        assertEquals("Check that 1 is accepted as a filled slot", true, host.addSlot(1));
        assertEquals("Check that the number of full slots has been increased to 1", 1, host.getNumFullSlots());
        for (int i = 0; i < 9; i++) {
            host.addSlot(1);
        }
        assertEquals("Check that the number of full slots has been increased to 10", 10, host.getNumFullSlots());

        //Check that any value outside of 1 and 0 are not accepted
        assertEquals("Make sure that 1 is accepted as a filled slot", true, host.addSlot(1));
        assertEquals("Make sure that 1 is accepted as a filled slot", true, host.addSlot(1));
    }
}