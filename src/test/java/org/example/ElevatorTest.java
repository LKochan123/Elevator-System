package org.example;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ElevatorTest {

    private Elevator elevator;

    @BeforeEach
    public void setUp() {
        elevator = new Elevator(1);
    }

    @Test
    public void testInitialConditions() {
        assertEquals(0, elevator.getCurrentFloor(), "Initial current floor should be 0");
        assertEquals(0, elevator.getTargetFloor(), "Initial target floor should be 0");
        assertEquals(Direction.NONE, elevator.getDirection(), "Initial direction should be NONE");
        assertTrue(elevator.isIdle(), "Elevator should initially be idle");
    }

    @Test
    public void testGetNumberOfStops() {
        elevator.addRequest(7, Direction.UPWARD);
        elevator.addRequest(5, Direction.UPWARD);
        elevator.addRequest(3, Direction.UPWARD);
        assertEquals(3, elevator.getNumberOfStops(), "Elevator should have 3 number od stops");
    }

    @Test
    public void testNextDestinationFloor() {
        elevator.addRequest(3, Direction.UPWARD);
        elevator.addRequest(5, Direction.UPWARD);
        assertEquals(Integer.valueOf(3), elevator.getNextDestinationFloor(), "Next destination should be 3");
        assertEquals(Integer.valueOf(5), elevator.getNextDestinationFloor(), "Next destination should be 5 after 3");
    }

    @Test
    public void testIsIdleAfterClearingRequests() {
        elevator.addRequest(3, Direction.UPWARD);
        elevator.getNextDestinationFloor();
        assertTrue(elevator.isIdle(), "Elevator should be idle after processing all requests");
    }

    @Test
    public void testLastStop() {
        elevator.addRequest(5, Direction.UPWARD);
        elevator.addRequest(7, Direction.UPWARD);
        elevator.addRequest(2, Direction.DOWNWARD);
        elevator.setDirection(Direction.UPWARD);
        assertEquals(Integer.valueOf(7), elevator.getLastStop(), "Last stop should be 7 in upward direction");
    }
}
