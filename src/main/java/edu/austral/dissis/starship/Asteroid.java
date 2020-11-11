package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

import java.util.Random;

public class Asteroid {
    private final Vector2 position;
    private final Vector2 direction;
    boolean active;
    float speed;

    public Asteroid(Vector2 position, Vector2 direction, boolean active, float speed) {
        this.position = position;
        this.direction = direction.asUnitary();
        this.active = active;
        this.speed =speed;
    }

    public Asteroid moveForward() {
        return new Asteroid(position.add(direction.multiply(speed)), direction, active,speed);
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getDirection() { return direction; }

    public void destroy() {
        active = false;
    }
}
