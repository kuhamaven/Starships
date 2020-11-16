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

    private final int height=720;
    private final int width=1280;
    private float asteroidTick=0;

    private StarshipDrawer starshipDrawer1;
    private StarshipDrawer starshipDrawer2;
    private ProjectileDrawer projectileDrawer;
    private AsteroidDrawer asteroidDrawer1;
    private AsteroidDrawer asteroidDrawer2;
    private GalaxyDrawer galaxyDrawer;
    private AsteroidSpawner asteroidSpawner;

    private Starship starship1 = new Starship(vector(100, 100), vector(0, 1),true, "player1");
    private StarshipController starshipController1 = new StarshipController(0x26,0x28,0x25,0x27, 0x6B);
    private Starship starship2 = new Starship(vector(100, 620), vector(0, -1),true,"player2");
    private StarshipController starshipController2 = new StarshipController( 0x57,0x53,0x41,0x44,0x20);

    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projectiles2 = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();


    private final CollisionEngine engine = new CollisionEngine();

    @Override
    public void setup(WindowSettings windowsSettings, ImageLoader imageLoader) {
        windowsSettings
            .setSize(width, height);
        windowsSettings.fullScreen();

        asteroidSpawner = new AsteroidSpawner();
        starshipDrawer1 = new StarshipDrawer(imageLoader.load("spaceship.png"));
        starshipDrawer2 = new StarshipDrawer(imageLoader.load("spaceship2.png"));
        projectileDrawer = new ProjectileDrawer(imageLoader.load("bullet.png"));
        asteroidDrawer1 = new AsteroidDrawer(imageLoader.load("asteroid1.png"));
        asteroidDrawer2 = new AsteroidDrawer(imageLoader.load("asteroid2.png"));
        galaxyDrawer = new GalaxyDrawer(imageLoader.load("galaxy.png"));
        for (int i = 0; i < 5; i++) {
            asteroids.add(asteroidSpawner.spawnAsteroid(width,height));
        }

    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        updateStarship(keySet);
        updateProjectiles();
        updateAsteroids(timeSinceLastDraw);
        galaxyDrawer.draw(graphics);
        drawStarships(graphics);
        drawProjectiles(graphics);
        drawAsteroids(graphics);
        checkCollisions();
    }

    private void updateAsteroids(float timeSinceLastDraw){
        asteroidTick = asteroidTick + timeSinceLastDraw/100;
        if( asteroidTick>300) {
            asteroids.add(asteroidSpawner.spawnAsteroid(width,height));
            asteroidTick=0;
        }
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
        collisionables.add(starshipDrawer1.getCollisionable(starship1));
        collisionables.add(starshipDrawer2.getCollisionable(starship2));
        for (Projectile projectile : projectiles) {
            collisionables.add(projectileDrawer.getCollisionable(projectile));
        }
        for (Projectile projectile : projectiles2) {
            collisionables.add(projectileDrawer.getCollisionable(projectile));
        }
        for (Asteroid asteroid: asteroids) {
            if(asteroid.speed<2) collisionables.add(asteroidDrawer1.getCollisionable(asteroid));
            else collisionables.add(asteroidDrawer2.getCollisionable(asteroid));
        }
        engine.checkCollisions(collisionables);
    }

    private void drawStarships(PGraphics graphics) {
        starshipDrawer1.draw(graphics, starship1);
        starshipDrawer2.draw(graphics, starship2);
    }

    private void drawProjectiles(PGraphics graphics){
        for (int i = 0; i < projectiles.size(); i++) {
            projectileDrawer.draw(graphics,projectiles.get(i));
        }
        for (int i = 0; i < projectiles2.size(); i++) {
            projectileDrawer.draw(graphics,projectiles2.get(i));
        }
    }

    private void updateProjectiles(){
        updateProjectilesHandler(projectiles);
        updateProjectilesHandler(projectiles2);
    }

    private void updateProjectilesHandler(List<Projectile> projectileList){
        for (int i = 0; i < projectileList.size(); i++) {
            projectileList.set(i, projectileList.get(i).moveForward(8));
        }
        for (int i = projectileList.size()-1; i >=0 ; i--) {
            Vector2 current = projectileList.get(i).getPosition();
            if( !projectileList.get(i).active || current.getX()>width || current.getX()<0 || current.getY()>height || current.getY()<0)projectileList.remove(i);
        }
    }

    private void updateStarship(Set<Integer> keySet) {
        if(!starship2.active){
            starship2 = new Starship(vector(100,620),vector(0,-1),true,starship2.shipName);
        }
        if(!starship1.active){
            starship1 =  new Starship(vector(100, 100), vector(0, 1),true,starship1.shipName);
        }
        else {
            starship1 = starshipController1.keyHandle(keySet,starship1,projectiles);
            starship2 = starshipController2.keyHandle(keySet,starship2,projectiles2);
        }
        starship1 = starshipWarpEdge(starship1);
        starship2 = starshipWarpEdge(starship2);
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
    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
