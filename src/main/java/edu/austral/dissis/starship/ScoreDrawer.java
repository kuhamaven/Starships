package edu.austral.dissis.starship;

import processing.core.PGraphics;

public class ScoreDrawer {

    public ScoreDrawer() {
    }

    public void draw(PGraphics graphics, Starship starship, float ypos) {
        graphics.pushMatrix();
        String text;
        if(starship.getLives()<1) text=starship.shipName+ " GAME OVER! Final score: "+starship.getScore();
            else text=starship.getShipName() + " Lives: " + starship.getLives() + " Score: " + starship.getScore();
        graphics.textSize(22);
        graphics.text(text,0,ypos);
        graphics.popMatrix();
    }

    public void drawGameOver(PGraphics graphics, Starship starship, float ypos, boolean winner) {
        graphics.pushMatrix();
        String text=starship.shipName+ " scored "+starship.getScore()+ " points!";
        if(winner) text += " WINNER!";
        graphics.textSize(32);
        if(winner) graphics.text(text,380,ypos);
        else graphics.text(text,450,ypos);
        graphics.popMatrix();
    }

}



