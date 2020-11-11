package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class ProjectileDrawer {
    private static final float IMAGE_SIZE = 10;
    public static final int SQUARE_SIZE = 10;

    private final PImage image;

    public ProjectileDrawer(PImage image) {
        this.image = image;
    }

    private float getImageCenter() {
        return IMAGE_SIZE / -2f;
    }

    public void draw(PGraphics graphics, Projectile projectile) {
        final Vector2 position = projectile.getPosition();
        final float angle = calculateRotation(projectile);

        graphics.pushMatrix();

        graphics.translate(position.getX(), position.getY());
        graphics.rotate(angle);

//        graphics.fill(255, 0, 0);
        graphics.noFill();
        graphics.noStroke();
        graphics.rect(SQUARE_SIZE/-2f,SQUARE_SIZE/-2f,SQUARE_SIZE,SQUARE_SIZE);
        graphics.image(image, getImageCenter(), getImageCenter());


        graphics.popMatrix();

//        graphics.fill(0, 255, 0);
    }

    private float calculateRotation(Projectile projectile) {
        return projectile.getDirection().rotate(PConstants.PI / 2).getAngle();
    }

    public SquareCollisionable getCollisionable(Projectile projectile) {
        return new ProjectileCollisionable(
                SQUARE_SIZE,
                calculateRotation(projectile),
                projectile.getPosition(),
                projectile
        );
    }
}
