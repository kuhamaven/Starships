package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;

import java.util.List;
import java.util.Random;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;

public class AsteroidSpawner {
    float tick;
    int width;
    int height;

    public AsteroidSpawner(int width, int height){
        tick =0;
        this.width=width;
        this.height=height;
    }

    public List<Asteroid> AsteroidCheck(float timeSinceLastDraw, List<Asteroid> asteroids){
        tick = tick + timeSinceLastDraw/100;
        if( tick>300) {
            asteroids.add(spawnAsteroid());
            tick=0;
        }
        return asteroids;
    }

    public Asteroid spawnAsteroid(){
        Random random = new Random();
        Vector2 position;
        if(random.nextBoolean()) position = vector(random.nextFloat()*width,random.nextBoolean()? 0:height);
        else position = vector(random.nextBoolean()? 0:width,random.nextFloat()*height);
        Vector2 direction = vector(random.nextFloat(),random.nextFloat()*-1);
        return new Asteroid(position,direction,true,random.nextFloat()*4);
    }

}
