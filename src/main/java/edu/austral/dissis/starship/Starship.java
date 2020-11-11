package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class Starship {
    private final Vector2 position;
    private final Vector2 direction;

    public Starship(Vector2 position, Vector2 direction) {
        this.position = position;
        this.direction = direction.asUnitary();
    }

    public Starship rotate(float angle) {
        return new Starship(position, direction.rotate(angle));
    }

    public Starship moveForward(float speed) {
        return new Starship(position.add(direction.multiply(speed)), direction);
    }

    public Starship moveBackwards(float speed) {
        return new Starship(position.subtract(direction.multiply(speed)), direction);
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getDirection() { return direction; }
}
