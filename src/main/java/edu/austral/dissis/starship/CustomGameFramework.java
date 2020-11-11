package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.collision.CollisionEngine;
import edu.austral.dissis.starship.base.framework.GameFramework;
import edu.austral.dissis.starship.base.framework.ImageLoader;
import edu.austral.dissis.starship.base.framework.WindowSettings;
import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.*;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;
import static java.util.Arrays.asList;
import static java.util.Arrays.fill;

public class CustomGameFramework implements GameFramework {

    private StarshipDrawer starshipDrawer;
    private ProjectileDrawer projectileDrawer;
    private AsteroidDrawer asteroidDrawer1;
    private AsteroidDrawer asteroidDrawer2;
    private Starship starship1 = new Starship(vector(200, 200), vector(0, -1),true, "player1");
    private Starship starship2 = new Starship(vector(400, 400), vector(0, -1),true,"player2");
    private List<Projectile> projectiles = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();
    private final int height=720;
    private final int width=1280;

    private final CollisionEngine engine = new CollisionEngine();

    @Override
    public void setup(WindowSettings windowsSettings, ImageLoader imageLoader) {
        windowsSettings
            .setSize(width, height);

        starshipDrawer = new StarshipDrawer(imageLoader.load("spaceship.png"));
        projectileDrawer = new ProjectileDrawer(imageLoader.load("bullet.png"));
        asteroidDrawer1 = new AsteroidDrawer(imageLoader.load("asteroid1.png"));
        asteroidDrawer2 = new AsteroidDrawer(imageLoader.load("asteroid2.png"));
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            Vector2 position = vector(random.nextFloat()*width,random.nextFloat()*height);
            Vector2 direction = vector(random.nextFloat(),random.nextFloat()*-1);
            asteroids.add(new Asteroid(position,direction,true,random.nextFloat()*4));
        };
    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        updateStarship(keySet);
        updateProjectiles();
        updateAsteroids();
        drawStarships(graphics);
        drawProjectiles(graphics);
        drawAsteroids(graphics);
        checkCollisions();
    }

    private void updateAsteroids(){
        for (int i = asteroids.size()-1; i >= 0; i--) {
            if (!asteroids.get(i).active) asteroids.remove(i);
        }
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.set(i, asteroids.get(i).moveForward());
            Asteroid asteroid = asteroids.get(i);
            Vector2 current = asteroids.get(i).getPosition();
            if( current.getX()>width){
                asteroids.set(i,new Asteroid(vector(0,asteroid.getPosition().getY()),asteroid.getDirection(),asteroid.active,asteroid.speed));
            }
            else if(current.getX()<0){
                asteroids.set(i,new Asteroid(vector(width,asteroid.getPosition().getY()),asteroid.getDirection(),asteroid.active,asteroid.speed));
            }
            else if(current.getY()>height){
                asteroids.set(i,new Asteroid(vector(asteroid.getPosition().getX(),0),asteroid.getDirection(),asteroid.active,asteroid.speed));
            }
            else if(current.getY()<0){
                asteroids.set(i,new Asteroid(vector(asteroid.getPosition().getX(),height),asteroid.getDirection(),asteroid.active,asteroid.speed));
            }
        }
    }

    private void drawAsteroids(PGraphics graphics){
        for (int i = 0; i <asteroids.size() ; i++) {
            if(asteroids.get(i).speed<2) asteroidDrawer1.draw(graphics,asteroids.get(i));
            else asteroidDrawer2.draw(graphics,asteroids.get(i));
        }
    }

    private void checkCollisions() {
        List<SquareCollisionable> collisionables = new ArrayList<>();
        collisionables.add(starshipDrawer.getCollisionable(starship1));
        collisionables.add(starshipDrawer.getCollisionable(starship2));
        for (Projectile projectile : projectiles) {
            collisionables.add(projectileDrawer.getCollisionable(projectile));
        }
        for (Asteroid asteroid: asteroids) {
            if(asteroid.speed<2) collisionables.add(asteroidDrawer1.getCollisionable(asteroid));
            else collisionables.add(asteroidDrawer2.getCollisionable(asteroid));
        }
        engine.checkCollisions(collisionables);
    }

    private void drawStarships(PGraphics graphics) {
        starshipDrawer.draw(graphics, starship1);
        starshipDrawer.draw(graphics, starship2);
    }

    private void drawProjectiles(PGraphics graphics){
        for (int i = 0; i < projectiles.size(); i++) {
            projectileDrawer.draw(graphics,projectiles.get(i));
        }
    }

    private void updateProjectiles(){
        for (int i = 0; i < projectiles.size(); i++) {
            projectiles.set(i, projectiles.get(i).moveForward(8));
        }
        for (int i = projectiles.size()-1; i >=0 ; i--) {
            Vector2 current = projectiles.get(i).getPosition();
            if( !projectiles.get(i).active || current.getX()>width || current.getX()<0 || current.getY()>height || current.getY()<0)projectiles.remove(i);
        }
    }

    private void updateStarship(Set<Integer> keySet) {
        if(!starship2.active){
            starship2 = new Starship(vector(410,400),vector(0,-1),true,"player2");
        }
        if(!starship1.active){
            starship1 =  new Starship(vector(200, 200), vector(0, -1),true,"player1");
        }
        else {
            if (keySet.contains(PConstants.UP)) {
                starship1 = starship1.moveForward(4);
            }

            if (keySet.contains(PConstants.DOWN)) {
                starship1 = starship1.moveBackwards(4);
            }

            if (keySet.contains(PConstants.LEFT)) {
                starship1 = starship1.rotate(-1 * PConstants.PI / 60);
            }

            if (keySet.contains(PConstants.RIGHT)) {
                starship1 = starship1.rotate(PConstants.PI / 60);
            }
        }
        starship1 = starshipWarpEdge(starship1);
    }

    private Starship starshipWarpEdge(Starship starship){
        Vector2 current = starship.getPosition();
        if( current.getX()>width){
            return new Starship(vector(0,current.getY()),starship.getDirection(),starship.active,starship.shipName);
        }
        else if(current.getX()<0){
            return new Starship(vector(width,current.getY()),starship.getDirection(),starship.active,starship.shipName);
        }
        else if(current.getY()>height){
            return new Starship(vector(current.getX(),0),starship.getDirection(),starship.active,starship.shipName);
        }
        else if(current.getY()<0){
            return new Starship(vector(current.getX(), height),starship.getDirection(),starship.active,starship.shipName);
        }
        return starship;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKey()==' '){
                if(projectiles.size()<4){
                    projectiles.add(starship1.shoot());
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
