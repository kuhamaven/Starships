package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

public class Starship {
    private final Vector2 position;
    private final Vector2 direction;
    boolean active;
    String shipName;
    int score;
    int lives;

    public Starship(Vector2 position, Vector2 direction, boolean active, String shipName, int score, int lives) {
        this.position = position;
        this.direction = direction.asUnitary();
        this.active = active;
        this.shipName = shipName;
        this.score=score;
        this.lives=lives;
    }

    public Starship rotate(float angle) {
        return new Starship(position, direction.rotate(angle),active,shipName,score,lives);
    }

    public Starship moveForward(float speed) {
        return new Starship(position.add(direction.multiply(speed)), direction,active,shipName,score,lives);
    }

    public Starship moveBackwards(float speed) {
        return new Starship(position.subtract(direction.multiply(speed)), direction,active,shipName,score,lives);
    }

    public Vector2 getPosition() { return position; }

    public Vector2 getDirection() { return direction; }

    public Projectile shoot() {
        return new Projectile(this.position,this.direction, active,shipName,0);
    }

    public void destroy() {
        active = false;
        lives = lives-1;
    }

    public int getLives(){
        return lives;
    }

    public int getScore(){
        return score;
    }

    public String getShipName(){
        return shipName;
    }
}
