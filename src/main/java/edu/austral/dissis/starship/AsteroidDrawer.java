package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class AsteroidDrawer {

    private final PImage image;

    public AsteroidDrawer(PImage image) {
        this.image = image;
    }

    private float getImageCenter() {
        return image.pixelHeight / -2f;
    }

    public void draw(PGraphics graphics, Asteroid asteroid) {
        final Vector2 position = asteroid.getPosition();
        final float angle = calculateRotation(asteroid);

        graphics.pushMatrix();

        graphics.translate(position.getX(), position.getY());
        graphics.rotate(angle);

        graphics.noFill();
        graphics.noStroke();
        graphics.rect(image.pixelHeight/-2f,image.pixelHeight/-2f,image.pixelHeight,image.pixelHeight);
        graphics.image(image, getImageCenter(), getImageCenter());

        graphics.popMatrix();
    }

    private float calculateRotation(Asteroid asteroid) {
        return asteroid.getDirection().rotate(PConstants.PI / 2).getAngle();
    }

    public SquareCollisionable getCollisionable(Asteroid asteroid) {
        return new AsteroidCollisionable(
                image.pixelHeight,
                calculateRotation(asteroid),
                asteroid.getPosition(),
                asteroid
        );
    }
}

