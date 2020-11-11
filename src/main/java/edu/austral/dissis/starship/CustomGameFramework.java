package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.collision.CollisionEngine;
import edu.austral.dissis.starship.base.framework.GameFramework;
import edu.austral.dissis.starship.base.framework.ImageLoader;
import edu.austral.dissis.starship.base.framework.WindowSettings;
import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.event.KeyEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static edu.austral.dissis.starship.base.vector.Vector2.vector;
import static java.util.Arrays.asList;

public class CustomGameFramework implements GameFramework {

    private StarshipDrawer starshipDrawer;
    private Starship starship1 = new Starship(vector(200, 200), vector(0, -1));
    private Starship starship2 = new Starship(vector(400, 400), vector(0, -1));

    private final CollisionEngine engine = new CollisionEngine();

    @Override
    public void setup(WindowSettings windowsSettings, ImageLoader imageLoader) {
        windowsSettings
            .setSize(500, 500);

        starshipDrawer = new StarshipDrawer(imageLoader.load("spaceship.png"));
    }

    @Override
    public void draw(PGraphics graphics, float timeSinceLastDraw, Set<Integer> keySet) {
        updateStarship(keySet);
        drawStarships(graphics);
        checkCollisions();
    }

    private void checkCollisions() {
        final List<SquareCollisionable> collisionables = asList(
                starshipDrawer.getCollisionable(starship1),
                starshipDrawer.getCollisionable(starship2)
        );

        engine.checkCollisions(collisionables);
    }

    private void drawStarships(PGraphics graphics) {
        starshipDrawer.draw(graphics, starship1);
        starshipDrawer.draw(graphics, starship2);
    }

    private void updateStarship(Set<Integer> keySet) {
        if (keySet.contains(PConstants.UP)) {
            starship1 = starship1.moveForward(2);
        }

        if (keySet.contains(PConstants.DOWN)) {
            starship1 = starship1.moveBackwards(2);
        }

        if (keySet.contains(PConstants.LEFT)) {
            starship1 = starship1.rotate(-1 * PConstants.PI / 60);
        }

        if (keySet.contains(PConstants.RIGHT)) {
            starship1 = starship1.rotate(PConstants.PI / 60);
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {

    }

    @Override
    public void keyReleased(KeyEvent event) {

    }
}
