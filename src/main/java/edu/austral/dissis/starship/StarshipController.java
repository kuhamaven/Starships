package edu.austral.dissis.starship;

import processing.core.PConstants;

import java.util.List;
import java.util.Set;

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


}
