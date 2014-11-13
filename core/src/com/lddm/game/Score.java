package com.lddm.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Score extends Actor {

    BitmapFont font;
    public int myScore;      //I assumed you have some object 
                        //that you use to access score.
                        //Remember to pass this in!  
    public Score(){
        font = new BitmapFont(false);
            font.setColor(0.5f,1f,0.5f,1);   //Brown is an underated Colour
            font.setScale(2f);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
         font.draw(batch, "Score: 0" + myScore, 20, 710);
         //Also remember that an actor uses local coordinates for drawing within
         //itself!
    }



}