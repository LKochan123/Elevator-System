package org.example;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

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

    public void addRequest(int floor, Direction direction) {
        boolean isUpward = direction == Direction.UPWARD;
        Queue<Integer> requests = isUpward ? this.upwardRequests : this.downwardRequests;

        if ((isUpward && floor < this.targetFloor) || (!isUpward && floor > this.targetFloor)) {
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
            return this.currentFloor;
        }
    }

    public Integer getNumberOfStops() {
        int penaltyForHavingTarget = (this.direction == Direction.NONE) ? 0 : 1;
        return this.upwardRequests.size() + this.downwardRequests.size() + penaltyForHavingTarget;
    }

    @Override
    public String toString() {
        int upQueueSize = this.upwardRequests.size();
        int downQueueSize = this.downwardRequests.size();

        return String.format(
            "Elevator %d - currently on floor %d going %s to floor %d. Up-Queue size %d. Down-Queue size %d.",
            this.id + 1, this.currentFloor, this.direction.getName(), this.targetFloor, upQueueSize, downQueueSize
        );
    }
}
