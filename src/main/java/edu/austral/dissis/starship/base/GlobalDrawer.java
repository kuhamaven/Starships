package edu.austral.dissis.starship.base;

import edu.austral.dissis.starship.*;
import edu.austral.dissis.starship.base.framework.ImageLoader;
import processing.core.PGraphics;

import javax.swing.plaf.basic.BasicTextAreaUI;
import java.util.ArrayList;
import java.util.List;

public class GlobalDrawer {
    private StarshipDrawer starshipDrawer1;
    private StarshipDrawer starshipDrawer2;
    private ProjectileDrawer projectileDrawer;
    private AsteroidDrawer asteroidDrawer1;
    private AsteroidDrawer asteroidDrawer2;
    private BackgroundDrawer backgroundDrawer;
    private ScoreDrawer scoreDrawer;

    public GlobalDrawer(ImageLoader imageLoader){
        scoreDrawer = new ScoreDrawer();
        starshipDrawer1 = new StarshipDrawer(imageLoader.load("spaceship.png"));
        starshipDrawer2 = new StarshipDrawer(imageLoader.load("spaceship2.png"));
        projectileDrawer = new ProjectileDrawer(imageLoader.load("bullet.png"));
        asteroidDrawer1 = new AsteroidDrawer(imageLoader.load("asteroid1.png"));
        asteroidDrawer2 = new AsteroidDrawer(imageLoader.load("asteroid2.png"));
        backgroundDrawer = new BackgroundDrawer(imageLoader.load("galaxy.png"),imageLoader.load("gameover.png"));
    }

    public void draw(PGraphics graphics,List<Asteroid> asteroids, List<Projectile> projectiles1, List<Projectile> projectiles2, Starship starship1, Starship starship2){
        if(starship1.getLives()<1 && starship2.getLives()<1) backgroundDrawer.drawGameOver(graphics);
        else backgroundDrawer.draw(graphics);
        drawStarships(graphics, starship1, starship2);
        drawProjectiles(graphics, projectiles1, projectiles2);
        drawAsteroids(graphics,asteroids);
        drawScores(graphics, starship1, starship2);
    }

    private void drawAsteroids(PGraphics graphics, List<Asteroid> asteroids){
        for (int i = 0; i <asteroids.size() ; i++) {
            if(asteroids.get(i).getSpeed()<2) asteroidDrawer1.draw(graphics,asteroids.get(i));
            else asteroidDrawer2.draw(graphics,asteroids.get(i));
        }
    }

    private void drawStarships(PGraphics graphics, Starship starship1, Starship starship2) {
        starshipDrawer1.draw(graphics, starship1);
        starshipDrawer2.draw(graphics, starship2);
    }

    private void drawProjectiles(PGraphics graphics, List<Projectile> projectiles, List<Projectile> projectiles2){
        for (int i = 0; i < projectiles.size(); i++) {
            projectileDrawer.draw(graphics,projectiles.get(i));
        }
        for (int i = 0; i < projectiles2.size(); i++) {
            projectileDrawer.draw(graphics,projectiles2.get(i));
        }
    }

    private void drawScores(PGraphics graphics, Starship starship1, Starship starship2){
        if(starship1.getLives()<1 && starship2.getLives()<1) {
            scoreDrawer.drawGameOver(graphics,starship1,350,starship1.getScore()>starship2.getScore());
            scoreDrawer.drawGameOver(graphics,starship2,390,starship2.getScore()>starship1.getScore());
        }
        else{
            scoreDrawer.draw(graphics,starship1,20);
            scoreDrawer.draw(graphics,starship2, 45);
        }
    }

    public List<SquareCollisionable> getCollisionables(List<Asteroid> asteroids, List<Projectile> projectiles1, List<Projectile> projectiles2, Starship starship1, Starship starship2){
        List<SquareCollisionable> collisionables = new ArrayList<>();
        collisionables.add(starshipDrawer1.getCollisionable(starship1));
        collisionables.add(starshipDrawer2.getCollisionable(starship2));
        for (Projectile projectile : projectiles1) {
            collisionables.add(projectileDrawer.getCollisionable(projectile));
        }
        for (Projectile projectile : projectiles2) {
            collisionables.add(projectileDrawer.getCollisionable(projectile));
        }
        for (Asteroid asteroid: asteroids) {
            if(asteroid.getSpeed()<2) collisionables.add(asteroidDrawer1.getCollisionable(asteroid));
            else collisionables.add(asteroidDrawer2.getCollisionable(asteroid));
        }
        return collisionables;
    }

}
