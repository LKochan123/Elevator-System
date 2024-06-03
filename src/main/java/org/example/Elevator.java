package org.example;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

public class Elevator {
    private final int id;
    private int currentFloor;
    private int targetFloor;
    private Direction direction;
    private final Queue<Integer> upwardRequests;
    private final Queue<Integer> downwardRequests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.targetFloor = 0;
        this.direction = Direction.NONE;
        this.upwardRequests = new PriorityQueue<>();
        this.downwardRequests = new PriorityQueue<>((a, b) -> b - a);
    }

    public int getId() {
        return this.id;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public int getTargetFloor() {
        return this.targetFloor;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isIdle() {
        return this.direction == Direction.NONE;
    }

//    public void addRequest(int floor, Direction direction) {
//        boolean isUpward = isDirectionUpward(direction);
//        Queue<Integer> requests = isUpward ? this.upwardRequests : this.downwardRequests;
//
//        if ((isUpward && floor < this.targetFloor) || (!isUpward && floor > this.targetFloor)) {
//            requests.add(this.targetFloor);
//            this.targetFloor = floor;
//        } else {
//            requests.add(floor);
//        }
//    }

    public void addRequest(int floor, Direction direction, Direction requestDirection) {
        boolean isUpward = isDirectionUpward(direction);
        Queue<Integer> requests = isUpward ? this.upwardRequests : this.downwardRequests;
        boolean directionMatch = requestDirection == null || isDirectionUpward(requestDirection) == isUpward;

        if (directionMatch && ((isUpward && floor < this.targetFloor) || (!isUpward && floor > this.targetFloor))) {
            requests.add(this.targetFloor);
            this.targetFloor = floor;
        } else {
            requests.add(floor);
        }
    }

    public Integer getNextDestinationFloor() {
        if (!this.upwardRequests.isEmpty()) {
            return this.upwardRequests.poll();
        } else if (!this.downwardRequests.isEmpty()) {
            return this.downwardRequests.poll();
        } else {
            return null;
        }
    }

    public Integer getLastStop() {
        if (this.direction == Direction.UPWARD && !this.upwardRequests.isEmpty()) {
            return Collections.max(this.upwardRequests);
        } else if (this.direction == Direction.DOWNWARD && !this.downwardRequests.isEmpty()) {
            return Collections.min(this.downwardRequests);
        } else {
            return this.targetFloor;
        }
    }

    public Integer getNumberOfStopsBothWay(int requestFloor) {
        int penaltyForHavingTarget = this.isTargetFloorOnTheWay(requestFloor) ? 1 : 0;
        int firstWayTotalStops, secondWayStops;

        if (requestFloor > currentFloor) {
            firstWayTotalStops = this.downwardRequests.size();
            secondWayStops = (int) this.getRelevantUpwardRequests(requestFloor).count();
        } else {
            firstWayTotalStops = this.upwardRequests.size();
            secondWayStops = (int) this.getRelevantDownwardRequests(requestFloor).count();
        }

        return firstWayTotalStops + secondWayStops + penaltyForHavingTarget;
    }

    public Integer getNumberOfStopsOneWay(int requestFloor) {
        int penaltyForHavingTarget = this.isTargetFloorOnTheWay(requestFloor) ? 1 : 0;
        Stream<Integer> relevantRequests;

        if (requestFloor > currentFloor) {
            relevantRequests = this.getRelevantUpwardRequests(requestFloor);
        } else {
            relevantRequests = this.getRelevantDownwardRequests(requestFloor);
        }

        return (int) relevantRequests.count() + penaltyForHavingTarget;
    }

    public boolean containsUpwardRequest(int floor) {
        return this.upwardRequests.contains(floor);
    }

    public boolean containsDownwardRequest(int floor) {
        return this.downwardRequests.contains(floor);
    }

    @Override
    public String toString() {
        int upQueueSize = this.upwardRequests.size();
        int downQueueSize = this.downwardRequests.size();

        return String.format(
            "Elevator %d - currently on floor %d going %s to floor %d. Up-Queue size %d. Down-Queue size %d.",
            this.id, this.currentFloor, this.direction.getName(), this.targetFloor, upQueueSize, downQueueSize
        );
    }

    private boolean isDirectionUpward(Direction direction) {
        return direction == Direction.UPWARD;
    }

    private Stream<Integer> getRelevantUpwardRequests(int requestFloor) {
        Stream<Integer> relevantRequests;
        relevantRequests = this.upwardRequests.stream().filter(floor -> floor > currentFloor && floor <= requestFloor);
        return relevantRequests;
    }


    private Stream<Integer> getRelevantDownwardRequests(int requestFloor) {
        Stream<Integer> relevantRequests;
        relevantRequests = this.downwardRequests.stream().filter(floor -> floor < currentFloor && floor >= requestFloor);
        return relevantRequests;
    }

    private boolean isTargetFloorOnTheWay(int requestFloor) {
        if (this.direction == Direction.UPWARD) {
            return this.getCurrentFloor() < this.targetFloor && this.targetFloor < requestFloor;
        } else if (this.direction == Direction.DOWNWARD) {
            return this.getCurrentFloor() > this.targetFloor && this.targetFloor > requestFloor;
        } else  {
            return false;
        }
    }
}
