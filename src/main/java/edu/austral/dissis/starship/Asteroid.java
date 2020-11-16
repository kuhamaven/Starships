package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;

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

    public Asteroid screenWarp(int width, int height){
        Vector2 current = position;
        if( current.getX()>width){
            return new Asteroid(vector(0,current.getY()),direction,active,speed);
        }
        else if(current.getX()<0){
            return new Asteroid(vector(width,current.getY()),direction,active,speed);
        }
        else if(current.getY()>height){
          return new Asteroid(vector(current.getX(),0),direction,active,speed);
        }
        else if(current.getY()<0){
            return new Asteroid(vector(current.getX(),height),direction,active,speed);
        }
        return this;
    }

    public float getSpeed() {
        return speed;
    }
}
