package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class ElevatorSystem {
    private final int numberOfFloors;
    private final List<Elevator> elevators;
    private final int TIME_TO_STOP_AT_FLOOR_AS_ITERATIONS = 3;

    public ElevatorSystem(int numberOfElevators, int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
        this.elevators = new ArrayList<>();

        for (int i = 0; i < numberOfElevators; i++) {
            this.elevators.add(new Elevator(i));
        }
    }

    public void pickup(int requestFloor, Direction requestDirection) {
        Elevator closestElevator = this.findOptimalElevator(requestFloor, requestDirection);
        if (closestElevator != null) {
            this.processElevatorAction(closestElevator, requestFloor);
        }
    }

    public void step() {
        for (Elevator elevator : this.elevators) {
            Direction direction = elevator.getDirection();
            int floorChange = direction == Direction.UPWARD ? 1 : direction == Direction.DOWNWARD ? -1 : 0;

            if (floorChange != 0) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() + floorChange);
                this.update(elevator.getId(), elevator.getCurrentFloor(), elevator.getTargetFloor());
            }
        }
    }

    public List<Elevator> status() {
        return this.elevators;
    }

    private void update(int elevatorID, int currentFloor, int targetFloor) {
        Elevator elevator = this.elevators.get(elevatorID);

        if (currentFloor == targetFloor) {
            Integer nextFloor = elevator.getNextDestinationFloor();
            if (nextFloor != null) {
                Direction direction = this.calcDirection(elevator.getCurrentFloor(), nextFloor);
                elevator.setTargetFloor(nextFloor);
                elevator.setDirection(direction);
            } else {
                elevator.setDirection(Direction.NONE);
            }
        }
    }

    private Elevator findOptimalElevator(int requestFloor, Direction requestDirection) {
        Elevator closestElevator = null;

        BiFunction<Elevator, Integer, Integer> priorityDistance = this.getIdleAndOnTheWayDistance(requestDirection);
        BiFunction<Elevator, Integer, Integer> secondaryDistance = this.getLastStopDistance(requestDirection);
        BiFunction<Elevator, Integer, Integer>[] strategies = new BiFunction[]{priorityDistance, secondaryDistance};

        if (this.isRequestValid(requestFloor, requestDirection)) {
            for (BiFunction<Elevator, Integer, Integer> strategy : strategies) {
                closestElevator = findBestElevator(this.elevators, requestFloor, strategy);
                if (closestElevator != null) {
                    break;
                }
            }
        } else {
            System.out.println("*** Invalid request ***");
        }

        return closestElevator;
    }

    private Elevator findBestElevator(
            List<Elevator> elevators, int requestFloor,
            BiFunction<Elevator, Integer, Integer> distanceCalculator
    ) {
        Elevator closestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = distanceCalculator.apply(elevator, requestFloor);
            if (distance < minDistance) {
                minDistance = distance;
                closestElevator = elevator;
            }
        }

        return closestElevator;
    }

    private void processElevatorAction(Elevator elevator, int requestFloor) {
        Direction currentDirection = this.calcDirection(elevator.getCurrentFloor(), requestFloor);
        if (elevator.isIdle()) {
            elevator.setTargetFloor(requestFloor);
            elevator.setDirection(currentDirection);
        } else {
            elevator.addRequest(requestFloor, currentDirection);
        }
    }

    private Direction calcDirection(int currentFloor, int targetFloor) {
        if (currentFloor < targetFloor) {
            return Direction.UPWARD;
        } else if (currentFloor > targetFloor) {
            return Direction.DOWNWARD;
        } else {
            return Direction.NONE;
        }
    }

    private boolean isRequestValid(int requestFloor, Direction requestDirection) {
        if (!this.isValidateNumberOfFloor(requestFloor)) {
            System.out.println("The number of floor is greater than the maximum. This request will be skipped.");
            return false;
        } else if (this.isDownwardFromFloorZero(requestFloor, requestDirection)) {
            System.out.println("You can't go downward from floor zero. This request will be skipped.");
            return false;
        } else if (this.isUpwardFromMaxFloor(requestFloor, requestDirection)) {
            System.out.println("You can't go upward from max zero. This request will be skipped.");
            return false;
        } else {
            return true;
        }
    }

    private boolean isValidateNumberOfFloor(int requestFloor) {
        return 0 <= requestFloor && requestFloor <= this.numberOfFloors;
    }

    private boolean isDownwardFromFloorZero(int requestFloor, Direction requestDirection) {
        return requestFloor == 0 && requestDirection == Direction.DOWNWARD;
    }

    private boolean isUpwardFromMaxFloor(int requestFloor, Direction requestDirection) {
        return requestFloor == this.numberOfFloors && requestDirection == Direction.UPWARD;
    }

    private boolean isRequestOnTheWay(Elevator elevator, int floor, Direction requestDirection) {
        if (elevator.isIdle()) {
            return false;
        }

        return switch (requestDirection) {
            case UPWARD -> elevator.getDirection() == Direction.UPWARD && elevator.getCurrentFloor() <= floor;
            case DOWNWARD -> elevator.getDirection() == Direction.DOWNWARD && elevator.getCurrentFloor() >= floor;
            default -> false;
        };
    }

    private BiFunction<Elevator, Integer, Integer> getLastStopDistance(Direction requestDirection) {
        return (elevator, floor) -> {
            if (elevator.isIdle() || isRequestOnTheWay(elevator, floor, requestDirection)) {
                return Integer.MAX_VALUE;
            } else {
                int distance = Math.abs(elevator.getLastStop() - floor);
                int stops = elevator.getNumberOfStops();
                int totalStopTime = stops * TIME_TO_STOP_AT_FLOOR_AS_ITERATIONS;
                return distance + totalStopTime;
            }
        };
    }

    private BiFunction<Elevator, Integer, Integer> getIdleAndOnTheWayDistance(Direction requestDirection) {
        return (elevator, floor) -> {
            int distance = Math.abs(elevator.getCurrentFloor() - floor);
            int stops = elevator.getNumberOfStops();
            int totalStopTime = stops * TIME_TO_STOP_AT_FLOOR_AS_ITERATIONS;

            if (elevator.isIdle() || isRequestOnTheWay(elevator, floor, requestDirection)) {
                return distance + totalStopTime;
            } else {
                return Integer.MAX_VALUE;
            }
        };
    }
}
