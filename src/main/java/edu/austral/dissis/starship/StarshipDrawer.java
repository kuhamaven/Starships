package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class StarshipDrawer {
    private static final float IMAGE_SIZE = 100;
    public static final int SQUARE_SIZE = 70;

    private final PImage image;

    public StarshipDrawer(PImage image) {
        this.image = image;
    }

    private float getImageCenter() {
        return IMAGE_SIZE / -2f;
    }

    public void draw(PGraphics graphics, Starship starship) {
        if(starship.getLives()<1) return;
        final Vector2 position = starship.getPosition();
        final float angle = calculateRotation(starship);

        graphics.pushMatrix();
        graphics.translate(position.getX(), position.getY());
        graphics.rotate(angle);
        graphics.noFill();
        graphics.noStroke();
        graphics.rect(SQUARE_SIZE/ -2f, SQUARE_SIZE / -2f, SQUARE_SIZE, SQUARE_SIZE);
        graphics.image(image, getImageCenter(), getImageCenter());
        graphics.popMatrix();
    }

    private float calculateRotation(Starship starship) {
        return starship.getDirection().rotate(PConstants.PI / 2).getAngle();
    }

    public SquareCollisionable getCollisionable(Starship starship) {
        if(starship.getLives()<1) return new NullCollisionable();
        return new ShipCollisionable(
                SQUARE_SIZE,
                calculateRotation(starship),
                starship.getPosition(),
                starship
        );
    }
}
