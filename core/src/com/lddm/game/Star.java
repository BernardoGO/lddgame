package com.lddm.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Star extends Actor {
    //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
    private Texture textureAtlas = new Texture(Gdx.files.internal("star.png"));
    boolean compassAvail = Gdx.input.isPeripheralAvailable(Peripheral.Compass);
    //batch = new SpriteBatch();
    //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
   // animation = new Animation(1/15f, textureAtlas.getRegions());
    
    float actorX = -100, actorY = 408;
    public boolean started = false;
    private float stateTime = 0;
    float firstAzimuth = 1;
    public Star(){
    	Random rnd = new Random();
    	actorY = rnd.nextInt(800);
    	stateTime = rnd.nextInt(5)+1;
    	actorY -= 100;
    	firstAzimuth = Gdx.input.getPitch();
    	
    	actorX = rnd.nextInt(600) * 1;
    	reduce = rnd.nextInt(10);
    	//this.rotate(rnd.nextInt(360));
    	//setOrigin(textureAtlas.getWidth()/2,textureAtlas.getHeight()/2);
        setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Star)event.getTarget()).started = true;
                return true;
            }
        });
    }
    int reduce = 0;
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
        
    	
    	
    	
    	textureAtlas.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	int textureWidth = textureAtlas.getWidth();
    	int textureHeight = textureAtlas.getHeight();
    	float rotationAngle = count;

    	TextureRegion region = new TextureRegion(textureAtlas, 0, 0, textureWidth, textureHeight);


    	batch.draw(region, actorX, actorY, (textureWidth-reduce) / 2f, (textureHeight-reduce) / 2f, textureWidth-reduce, textureHeight-reduce, 1, 1, rotationAngle, false);

    	
    }
    float azimuth = 1;
    float pitch = 1;
    float roll = 1;
    @Override
    public void act(float delta){
        //if(started){
    	
    	 float accelX = Gdx.input.getAccelerometerX();
    	    float accelY = Gdx.input.getAccelerometerY();
    	    float accelZ = Gdx.input.getAccelerometerZ();
    			
            actorX+=stateTime*lddgame.oWall.multiplier*((accelZ)*0.2);
            azimuth = Gdx.input.getPitch();
            //pitch = Gdx.input.getPitch();
            //roll = Gdx.input.getRoll();
            actorY+= (accelX)*0.2;
            //this.setRotation(this.getRotation() -30);
        //}
        if(actorX > 1280 )
        {
        	
        	actorX = -100;
        	Random rnd = new Random();
        	actorY = rnd.nextInt(800);
        	stateTime = rnd.nextInt(5);
        	actorY -= 100;
        	actorX = rnd.nextInt(600) * -1;
        }
        if(actorY > 740)
        {
        	actorY = -10;
        }
        if(actorY < -20)
        {
        	actorY = 720;
        }
    }
}
