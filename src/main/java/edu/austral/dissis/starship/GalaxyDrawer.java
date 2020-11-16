package edu.austral.dissis.starship;

import edu.austral.dissis.starship.base.vector.Vector2;
import processing.core.PConstants;
import processing.core.PGraphics;
import processing.core.PImage;

public class GalaxyDrawer {
    private final PImage image;

    public GalaxyDrawer(PImage image) {
        this.image = image;
    }

    private float getImageCenter() {
        return image.pixelHeight / -2f;
    }

    public void draw(PGraphics graphics) {
        final Vector2 position = Vector2.vector(0,360);
        final float angle = 0;

        graphics.pushMatrix();

        graphics.translate(position.getX(), position.getY());
        graphics.rotate(angle);
        graphics.noFill();
        graphics.noStroke();
        graphics.rect(image.pixelHeight/-2f,image.pixelWidth/-2f,image.pixelHeight,image.pixelWidth);
        graphics.image(image, getImageCenter(), getImageCenter());
        graphics.popMatrix();
    }

}


