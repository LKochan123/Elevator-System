package org.example;

public class Main {

    private static final int NUMBER_OF_ELEVATORS = 2;
    private static final int NUMBER_OF_FLOORS = 11;

    public static void main(String[] args) {
        // clickFloorButton - button inside the elevator (id)
        // pickup - button on the floor to call elevator with direction
        ElevatorSystem es = new ElevatorSystem(NUMBER_OF_ELEVATORS, NUMBER_OF_FLOORS);

        es.clickFloorButton(0, 5);
        simulation(es, 3);

        es.pickup(2, Direction.UPWARD);
        es.pickup(10, Direction.DOWNWARD);

        simulation(es, 1);

        es.clickFloorButton(1, 8);
        es.clickFloorButton(1, 5);

        es.pickup(7, Direction.DOWNWARD);

        simulation(es, 3);

        es.clickFloorButton(0, 2);
        es.clickFloorButton(0, 0);

        simulation(es, 4);
    }

    private static void simulation(ElevatorSystem es, int steps) {
        for (int i = 0; i < steps; i++) {
            es.step();
            printStatus(es);
        }
    }

    private static void printStatus(ElevatorSystem system) {
        System.out.println("--------");
        for (Elevator elevator : system.status()) {
            System.out.println(elevator);
        }
    }
}