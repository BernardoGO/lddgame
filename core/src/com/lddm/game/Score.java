package com.lddm.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {

    BitmapFont font;
    public int myScore; 
    public int highScore = 0;
    public int vidas = 1;
    
    public Score(){
        font = new BitmapFont(false);
            font.setColor(0.5f,1f,0.5f,1);   //Brown is an underated Colour
            font.setScale(2f);
    }
    
    public void addScore()
    {
    	myScore++;
    	if(myScore % 10 == 0) vidas++;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
    	if(vidas < 0) vidas = 0;
         font.draw(batch, "Score: 0" + myScore + "   High Score: " + highScore + "   Satellites: " + lddgame.satt+ "   Vidas: " + vidas, 20, 710);
         //Also remember that an actor uses local coordinates for drawing within
         //itself!
    }



}