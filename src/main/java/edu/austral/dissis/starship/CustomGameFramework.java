package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.GlobalDrawer;
import edu.austral.dissis.starship.base.collision.CollisionEngine;
import edu.austral.dissis.starship.base.framework.GameFramework;
import edu.austral.dissis.starship.base.framework.ImageLoader;
import edu.austral.dissis.starship.base.framework.WindowSettings;
import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;

public class CustomGameFramework implements GameFramework {

    private final int height=720;
    private final int width=1280;

    private GlobalDrawer globalDrawer;

    private AsteroidSpawner asteroidSpawner;
    private Starship starship1;
    private final StarshipController starshipController1 = new StarshipController(0x26,0x28,0x25,0x27, 0x6B);
    private Starship starship2;
    private final StarshipController starshipController2 = new StarshipController( 0x57,0x53,0x41,0x44,0x20);

    private List<Projectile> projectiles = new ArrayList<>();
    private List<Projectile> projectiles2 = new ArrayList<>();
    private List<Asteroid> asteroids = new ArrayList<>();

    private final CollisionEngine engine = new CollisionEngine();

    @Override
    public void setup(WindowSettings windowsSettings, ImageLoader imageLoader) {
        windowsSettings
            .setSize(width, height);

        globalDrawer = new GlobalDrawer(imageLoader);

        String player1Name;
        String player2Name;
        player1Name = JOptionPane.showInputDialog("What is player 1's name? ");
        player2Name = JOptionPane.showInputDialog("What is player 2's name? ");
        starship1 = new Starship(vector(200, 200), vector(0, 1),true, player1Name,0,5);
        starship2 = new Starship(vector(200, 520), vector(0, -1),true,player2Name,0,5);

        asteroidSpawner = new AsteroidSpawner(width,height);
        for (int i = 0; i < 5; i++) {
            asteroids.add(asteroidSpawner.spawnAsteroid());
        }

    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        updateStarship(keySet);
        updateProjectiles();
        updateAsteroids(timeSinceLastDraw);
        globalDrawer.draw(graphics,asteroids,projectiles,projectiles2,starship1,starship2);
        checkCollisions();
    }

    private void checkCollisions() {
        List<SquareCollisionable> collisionables = globalDrawer.getCollisionables(asteroids,projectiles,projectiles2,starship1,starship2);
        engine.checkCollisions(collisionables);
    }

    private void updateAsteroids(float timeSinceLastDraw){
        asteroids = asteroidSpawner.AsteroidCheck(timeSinceLastDraw,asteroids);
        for (int i = asteroids.size()-1; i >= 0; i--) {
            if (!asteroids.get(i).active) asteroids.remove(i);
        }
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid current = asteroids.get(i).moveForward();
            asteroids.set(i, current.screenWarp(width,height));
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
            if( !projectileList.get(i).active || current.getX()>width || current.getX()<0 || current.getY()>height || current.getY()<0){
                updateStarshipScore(projectileList.get(i));
                projectileList.remove(i);
            }
        }
    }

    private void updateStarshipScore(Projectile projectile){
        if(projectile.scoreToAdd!=0){
            if (starship1.getShipName().equals(projectile.shipName)) starship1.score+=projectile.scoreToAdd;
            else starship2.score+=projectile.scoreToAdd;
        }
    }

    private void updateStarship(Set<Integer> keySet) {

        if(starship1.getLives()>0){
            if(!starship1.active){
                starship1 =  new Starship(vector(200, 200), vector(0, 1),true,starship1.shipName,starship1.score,starship1.lives);
            }else {
                starship1 = starshipController1.keyHandle(keySet,starship1,projectiles);
            }
            starship1 = starshipController1.starshipWarpEdge(starship1,width,height);
        }

        if(starship2.getLives()>0){
            if(!starship2.active){
                starship2 = new Starship(vector(200,520),vector(0,-1),true,starship2.shipName,starship2.score,starship2.lives);
            } else {
                starship2 = starshipController2.keyHandle(keySet,starship2,projectiles2);
            }
            starship2 = starshipController2.starshipWarpEdge(starship2,width,height);
        }





    }

    @Override
    public void keyPressed(KeyEvent event) {}

    @Override
    public void keyReleased(KeyEvent event) {}
}
