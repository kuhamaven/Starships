package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class Projectile {
    private final Vector2 position;
    private final Vector2 direction;
    boolean active;
    String shipName;
    int scoreToAdd;

    public Projectile(Vector2 position, Vector2 direction, boolean active, String shipName, int scoreToAdd) {
        this.position = position;
        this.direction = direction.asUnitary();
        this.active = active;
        this.shipName = shipName;
        this.scoreToAdd = scoreToAdd;
    }

    public Projectile moveForward(float speed) {
        return new Projectile(position.add(direction.multiply(speed)), direction, active, shipName, scoreToAdd);
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getDirection() { return direction; }

    public void destroy() {
        active = false;
    }
}
