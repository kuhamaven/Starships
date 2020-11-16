package edu.austral.dissis.starship;

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

    private StarshipDrawer starshipDrawer1;
    private StarshipDrawer starshipDrawer2;
    private ProjectileDrawer projectileDrawer;
    private AsteroidDrawer asteroidDrawer1;
    private AsteroidDrawer asteroidDrawer2;
    private BackgroundDrawer backgroundDrawer;
    private AsteroidSpawner asteroidSpawner;
    private ScoreDrawer scoreDrawer;

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

        String player1Name;
        String player2Name;
        player1Name = JOptionPane.showInputDialog("What is player 1's name? ");
        player2Name = JOptionPane.showInputDialog("What is player 2's name? ");
        starship1 = new Starship(vector(200, 200), vector(0, 1),true, player1Name,0,5);
        starship2 = new Starship(vector(200, 520), vector(0, -1),true,player2Name,0,5);

        scoreDrawer = new ScoreDrawer();

        asteroidSpawner = new AsteroidSpawner(width,height);
        starshipDrawer1 = new StarshipDrawer(imageLoader.load("spaceship.png"));
        starshipDrawer2 = new StarshipDrawer(imageLoader.load("spaceship2.png"));
        projectileDrawer = new ProjectileDrawer(imageLoader.load("bullet.png"));
        asteroidDrawer1 = new AsteroidDrawer(imageLoader.load("asteroid1.png"));
        asteroidDrawer2 = new AsteroidDrawer(imageLoader.load("asteroid2.png"));
        backgroundDrawer = new BackgroundDrawer(imageLoader.load("galaxy.png"),imageLoader.load("gameover.png"));
        for (int i = 0; i < 5; i++) {
            asteroids.add(asteroidSpawner.spawnAsteroid());
        }

    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        updateStarship(keySet);
        updateProjectiles();
        updateAsteroids(timeSinceLastDraw);
        if(gameOver()) backgroundDrawer.drawGameOver(graphics);
        else backgroundDrawer.draw(graphics);
        drawStarships(graphics);
        drawProjectiles(graphics);
        drawAsteroids(graphics);
        drawScores(graphics);
        checkCollisions();
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

    private void drawScores(PGraphics graphics){
        if(gameOver()) {
            scoreDrawer.drawGameOver(graphics,starship1,350,starship1.getScore()>starship2.getScore());
            scoreDrawer.drawGameOver(graphics,starship2,390,starship2.getScore()>starship1.getScore());
        }
            else{
            scoreDrawer.draw(graphics,starship1,20);
            scoreDrawer.draw(graphics,starship2, 45);
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

    private boolean gameOver(){
        return starship1.getLives()<1 && starship2.getLives()<1;
    }
}
