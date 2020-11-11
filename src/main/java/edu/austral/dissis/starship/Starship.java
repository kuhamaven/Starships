package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class Starship {
    private final Vector2 position;
    private final Vector2 direction;
    boolean active;
    String shipName;

    public Starship(Vector2 position, Vector2 direction, boolean active, String shipName) {
        this.position = position;
        this.direction = direction.asUnitary();
        this.active = active;
        this.shipName = shipName;
    }

    public Starship rotate(float angle) {
        return new Starship(position, direction.rotate(angle),active,shipName);
    }

    public Starship moveForward(float speed) {
        return new Starship(position.add(direction.multiply(speed)), direction,active,shipName);
    }

    public Starship moveBackwards(float speed) {
        return new Starship(position.subtract(direction.multiply(speed)), direction,active,shipName);
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getDirection() { return direction; }

    public Projectile shoot() {
        return new Projectile(this.position,this.direction, active,shipName);
    }

    public void destroy() {
        active = false;
    }
}
