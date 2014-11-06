package com.lddm.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Explosion extends Actor {

    private Texture textureAtlas;

    float actorX = 0, actorY = 0;
    public boolean started = false;
    private float stateTime = 0;
    public int times = 4;
    
    
    public Explosion(float x, float y){
    	textureAtlas = new Texture(Gdx.files.internal("explode.png"));


    	Random rnd = new Random();
    	actorY = y - (rnd.nextInt(100)-50);
    	actorX = x - (rnd.nextInt(100)-50);
    	
    	reduce = rnd.nextInt(100)*-1;
    	//this.rotate(rnd.nextInt(360));
    	//setOrigin(textureAtlas.getWidth()/2,textureAtlas.getHeight()/2);
        setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Explosion)event.getTarget()).started = true;
                return true;
            }
        });
    }
    
    public Explosion(float x, float y, int times, boolean isGray){
    	this(x,y);
    	this.times = times;
    	
    	if(isGray)
    		textureAtlas = new Texture(Gdx.files.internal("explodegray.png"));


    }
    float reduce = 0;
    float count =360.0f;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(textureAtlas,actorX,actorY);
        //batch.draw(textureAtlas,actorX,actorY,textureAtlas.getWidth()/2.0f,textureAtlas.getHeight()/2.0f, textureAtlas.getWidth(), textureAtlas.getHeight(), 1f, 1f,count, false);
        if(count >= 360.f)
        	count = 0.0f;
        else
        {
        	if(lddgame.oWall.multiplier == 1)
        		count = count + 1;
        	else 
        		count = count + 0.1f;
        }
        //batch.draw(textureAtlas, (float)actorX, (float)actorY, (float)textureAtlas.getWidth()/2, (float)textureAtlas.getHeight()/2, (float)textureAtlas.getWidth(), (float)textureAtlas.getHeight(), (float)32, (float)32);
        //batch.draw(textureAtlas, actorX, actorY, (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(1), (float)(1), 180, (int)(actorX), (int)(actorY), (textureAtlas.getWidth()), (textureAtlas.getHeight()), false, false) ;
        
        //reduce++;
        
        
        	if(lddgame.oWall.multiplier == 1)
        		reduce = reduce + 1;
        	else 
        		reduce = reduce + 0.1f;
        
    	
        if((reduce > times && times != 0))
        {
        	try {
        		this.remove();
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    	textureAtlas.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	int textureWidth = textureAtlas.getWidth();
    	int textureHeight = textureAtlas.getHeight();
    	float rotationAngle = count;

    	TextureRegion region = new TextureRegion(textureAtlas, 0, 0, textureWidth, textureHeight);


    	batch.draw(region, actorX, actorY, (textureWidth-reduce) / 2f, (textureHeight-reduce) / 2f, textureWidth-reduce, textureHeight-reduce, 1, 1, rotationAngle, false);

    	if(reduce == textureHeight)
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    @Override
    public void act(float delta){
        //if(started){
            actorX+=stateTime;

            //this.setRotation(this.getRotation() -30);
        //}
        if(actorX > 1280)
        {
        	actorX = -100;
        	Random rnd = new Random();
        	actorY = rnd.nextInt(800);
        	stateTime = rnd.nextInt(5);
        	actorY -= 100;
        	actorX = rnd.nextInt(600) * -1;
        }
        
    }
}