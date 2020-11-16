package edu.austral.dissis.starship;

import processing.core.PGraphics;
import processing.core.PImage;

public class GalaxyDrawer {
    private final PImage image;
    private final PImage goImage;

    public GalaxyDrawer(PImage image, PImage goImage) {
        this.image = image;
        this.goImage = goImage;
    }

    public void draw(PGraphics graphics) {
        graphics.pushMatrix();
        graphics.noFill();
        graphics.noStroke();
        graphics.image(image, 0, 0);
        graphics.popMatrix();
    }

    public void drawGameOver(PGraphics graphics){
        graphics.pushMatrix();
        graphics.noFill();
        graphics.noStroke();
        graphics.image(goImage, 0, 0);
        graphics.popMatrix();
    }

}


