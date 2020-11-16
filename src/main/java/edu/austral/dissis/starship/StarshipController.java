package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;

import java.util.List;
import java.util.Set;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;

public class StarshipController {
    private int upKey;
    private int downKey;
    private int leftKey;
    private int rightKey;
    private int shootKey;

    public StarshipController(int upKey, int downKey, int leftKey, int rightKey, int shootKey) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.shootKey = shootKey;
    }

    public Starship keyHandle(Set<Integer> keySet, Starship starship, List<Projectile> projectiles){
        Starship newStarship = starship;
        if (keySet.contains(upKey)) {
            newStarship = newStarship.moveForward(4);
        }

        if (keySet.contains(downKey)) {
            newStarship = newStarship.moveBackwards(4);
        }

        if (keySet.contains(leftKey)) {
            newStarship = newStarship.rotate(-1 * PConstants.PI / 60);
        }

        if (keySet.contains(rightKey)) {
            newStarship = newStarship.rotate(PConstants.PI / 60);
        }

        if (keySet.contains(shootKey)) {
            if(projectiles.size()<4) projectiles.add(newStarship.shoot());
            keySet.remove(shootKey);
        }

        return newStarship;
    }

    public Starship starshipWarpEdge(Starship starship,int width, int height){
        Vector2 current = starship.getPosition();
        if( current.getX()>width){
            return new Starship(vector(0,current.getY()),starship.getDirection(),starship.active,starship.shipName,0);
        }
        else if(current.getX()<0){
            return new Starship(vector(width,current.getY()),starship.getDirection(),starship.active,starship.shipName,0);
        }
        else if(current.getY()>height){
            return new Starship(vector(current.getX(),0),starship.getDirection(),starship.active,starship.shipName,0);
        }
        else if(current.getY()<0){
            return new Starship(vector(current.getX(), height),starship.getDirection(),starship.active,starship.shipName,0);
        }
        return starship;
    }


}
