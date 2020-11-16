package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

import java.util.Random;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;

public class AsteroidSpawner {

    public AsteroidSpawner(){}

    public Asteroid spawnAsteroid(int width,int height){
        Random random = new Random();
        Vector2 position = vector(random.nextFloat()*width,random.nextFloat()*height);
        Vector2 direction = vector(random.nextFloat(),random.nextFloat()*-1);
        return new Asteroid(position,direction,true,random.nextFloat()*4);
    }

}
