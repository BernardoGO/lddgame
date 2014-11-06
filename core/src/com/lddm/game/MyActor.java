package com.lddm.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.DragScrollListener;

public class MyActor extends Actor{
    //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
    private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
    private Animation animation =  new Animation(1/15f, textureAtlas.getRegions());
    //batch = new SpriteBatch();
    //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
   // animation = new Animation(1/15f, textureAtlas.getRegions());
    private float elapsedTime = 0;
    float actorX = 500, actorY = 500;
    public boolean started = false;
    private float stateTime = 0;
    public boolean collided = false;
    
    public MyActor(){
    	
        setBounds(actorX,actorY,animation.getKeyFrame(elapsedTime, true).getRegionWidth(),animation.getKeyFrame(elapsedTime, true).getRegionHeight());
        //setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
        
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //((MyActor)event.getTarget()).started = true;
            	lddgame.oWall.started = true;
            	started = true;
            	lddgame.oWall.multiplier = 1;
            	/*
            	lddgame.r = 0.3f;
                lddgame.g = 0.3f;
                lddgame.b = 0.5f;
                */
            	lddgame.paused = false;
                
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //((MyActor)event.getTarget()).started = true;
  
            	lddgame.oWall.multiplier = (float) 0.01;
                /*lddgame.r = 0.1f;
                lddgame.g = 0.1f;
                lddgame.b = 0.2f;*/
            	lddgame.paused = true;
                
            }
        });
        
        
        
        addListener(new DragScrollListener(null){
        	@Override
        public void drag(InputEvent event, float x, float y, int pointer) {
        		
        	// TODO Auto-generated method stub
        		//if(started== false) return;
        		if(started && !collided)
        		{
        			
	        		float newPosX = (float) (0+ ((float)x*(float)1));
	            	float newPosY = (float) (0 - ((float)y*(float)1));
	            	actorX = event.getStageX();
	            	actorY = event.getStageY();
	            	//Debug.println("", "dragged " +event.getStageY());
	            	move(actorX, actorY);
	        		//actorX = x;
	        		//actorY = y;
	            	
        		}
        		else return;
        		
        }
        });
    }
    
    float frozenX = 0;
    float frozenY = 0;
    
        public void move(float x, float y)
        {
        	
        	this.setPosition(x-this.getWidth()/2f, y-this.getHeight()/2f);
        	
        	
        	if( x+this.getWidth()/2f > 1280) 
        	{
        		this.setPosition(1280-this.getWidth(), y-this.getHeight()/2f);
        		if(y+this.getHeight()/2f > 720) 
            	{
            		this.setPosition(1280-this.getWidth(), 720-this.getHeight());
            		return;
            	}
            	if(y-this.getHeight()/2f < 0) 
            	{
            		this.setPosition(1280-this.getWidth(), 0);
            		return;
            	}
        		return;
        		
        	}
        	if(x-this.getWidth()/2f < 0 ) 
        	{
        		this.setPosition(0, y-this.getHeight()/2f);
        		if(y+this.getHeight()/2f > 720) 
            	{
            		this.setPosition(0, 720-this.getHeight());
            		return;
            	}
            	if(y-this.getHeight()/2f < 0) 
            	{
            		this.setPosition(0, 0);
            		return;
            	}
        		
        		return;
        	}
        	
        	if(y+this.getHeight()/2f > 720) 
        	{
        		this.setPosition(x-this.getWidth()/2f, 720-this.getHeight());
        		return;
        	}
        	if(y-this.getHeight()/2f < 0) 
        	{
        		this.setPosition(x-this.getWidth()/2f, 0);
        		return;
        	}
        	
        	
        }
        
        
    public void restart()
	{
    	collided = false;
    	actorX = 500;
    	actorY = 500;
    	started = false;
	}
    
    int reduce = 0;
    int degrees = 0;
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(animation.getKeyFrame(stateTime, true),actorX,actorY);
    	
    	//textureAtlas.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	int textureWidth = animation.getKeyFrame(stateTime, true).getRegionWidth();
    	int textureHeight = animation.getKeyFrame(stateTime, true).getRegionHeight();
    	float rotationAngle = degrees;

    	TextureRegion region = new TextureRegion(animation.getKeyFrame(stateTime, true), 0, 0, textureWidth, textureHeight);


    	//batch.draw(region, actorX, actorY, (textureWidth) / 2f, (textureHeight) / 2f, textureWidth, textureHeight, 1, 1, rotationAngle, false);
    	
    
    	if(!collided)
    		batch.draw(region,this.getX(),this.getY());
    	else
    		batch.draw(region,actorX,actorY, (textureWidth) / 2f, (textureHeight) / 2f , textureWidth, textureHeight, 1, 1, rotationAngle);
    }
    
    @Override
    public void act(float delta){
    	float count = 0;
    	if(lddgame.oWall.multiplier == 1)
    		count = count + 1;
    	else 
    		count = count + 0.1f;
    	stateTime += delta*count;

        
    }


	
}

