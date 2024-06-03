package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ElevatorSystemTest {

    private ElevatorSystem elevatorSystem;

    @BeforeEach
    public void setUp() {
        elevatorSystem = new ElevatorSystem(2, 11);
    }

    @Test
    public void testOneScenario() {
        elevatorSystem.pickup(5, Direction.UPWARD);
        steps(4);
        List<Elevator> elevators = elevatorSystem.status();

        assertEquals(4, elevators.get(0).getCurrentFloor(), "Elevator 0 should be on floor 4");
        assertEquals(0, elevators.get(1).getCurrentFloor(), "Elevator 1 should be on floor 0");
    }

    @Test
    public void testTwoScenario() {
        elevatorSystem.pickup(3, Direction.UPWARD);
        elevatorSystem.pickup(2, Direction.UPWARD);
        steps(1);
        List<Elevator> elevators = elevatorSystem.status();

        assertEquals(2, elevators.get(0).getTargetFloor(), "Elevator 0 should be going to floor 2");
        assertTrue(elevators.get(0).containsUpwardRequest(3));
        assertEquals(0, elevators.get(1).getTargetFloor(), "Elevator 1 should be staying at floor 0");
    }

    @Test
    public void testThreeScenario() {
        elevatorSystem.clickFloorButton(0, 8);
        elevatorSystem.clickFloorButton(0, 7);
        elevatorSystem.clickFloorButton(1, 8);
        steps(5);
        elevatorSystem.pickup(3, Direction.DOWNWARD);
        List<Elevator> elevators = elevatorSystem.status();

        assertTrue(elevators.get(1).containsDownwardRequest(3));
    }

    @Test
    public void testFourScenario() {
        elevatorSystem.clickFloorButton(0, 6);
        elevatorSystem.clickFloorButton(1, 7);
        steps(3);
        elevatorSystem.pickup(5, Direction.DOWNWARD);
        steps(1);
        List<Elevator> elevators = elevatorSystem.status();

        assertEquals(6, elevators.get(0).getTargetFloor());
        assertTrue(elevators.get(0).containsDownwardRequest(5));
        assertFalse(elevators.get(1).containsDownwardRequest(5));
    }

    @Test
    public void testFiveScenario() {
        elevatorSystem.clickFloorButton(0, 4);
        elevatorSystem.clickFloorButton(1, 5);
        steps(4);
        elevatorSystem.clickFloorButton(0, 0);
        elevatorSystem.clickFloorButton(1, 0);
        steps(1);
        elevatorSystem.pickup(1, Direction.UPWARD);
        List<Elevator> elevators = elevatorSystem.status();

        assertEquals(0, elevators.get(0).getTargetFloor());
        assertTrue(elevators.get(0).containsUpwardRequest(1));
    }

    @Test
    public void testSixScenario() {
        elevatorSystem.clickFloorButton(0, 5);
        elevatorSystem.clickFloorButton(1, 6);
        steps(4);
        elevatorSystem.clickFloorButton(0, 1);
        elevatorSystem.clickFloorButton(1, 1);
        elevatorSystem.pickup(2, Direction.UPWARD);
        steps(3);
        List<Elevator> elevators = elevatorSystem.status();

        assertEquals(2, elevators.get(0).getTargetFloor());
        assertEquals(1, elevators.get(1).getTargetFloor());
    }

    private void steps(int n) {
        for (int i = 0; i < n; i ++) {
            elevatorSystem.step();
        }
    }
}
