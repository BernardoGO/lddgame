package com.lddm.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {

    BitmapFont font;
    public int myScore; 
    public int highScore = 0;
    
    public Score(){
        font = new BitmapFont(false);
            font.setColor(0.5f,1f,0.5f,1);   //Brown is an underated Colour
            font.setScale(2f);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
         font.draw(batch, "Score: 0" + myScore + "   High Score: " + highScore + "   Satellites: " + lddgame.satt, 20, 710);
         //Also remember that an actor uses local coordinates for drawing within
         //itself!
    }



}