package org.example;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem es = new ElevatorSystem(2, 11);

        es.pickup(6, Direction.UPWARD);

        simulation(es, 5);

        es.pickup(8, Direction.DOWNWARD);
        es.pickup(4, Direction.UPWARD);

        simulation(es, 3);

        es.pickup(10, Direction.UPWARD);
        es.pickup(1, Direction.DOWNWARD);

        simulation(es, 3);

        es.pickup(5, Direction.UPWARD);

        simulation(es, 5);
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