

package com.lddm.game;

import java.awt.Point;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Satellite extends Actor {
    //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
    private Texture textureAtlas ;
    //boolean compassAvail = Gdx.input.isPeripheralAvailable(Peripheral.Compass);
    //batch = new SpriteBatch();
    //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
   // animation = new Animation(1/15f, textureAtlas.getRegions());
    boolean isCollided = false;
    
    float actorX = -100, actorY = 408;
    boolean started = false;
    public Float angle = 1f;
    public int distance = 180;
    public Satellite(){

    	textureAtlas = new Texture(Gdx.files.internal("satt.png"));
    	Random rnd = new Random();
    	actorY = 200 - (rnd.nextInt(100)-50);
    	actorX = 200 - (rnd.nextInt(100)-50);
    	
        setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Satellite)event.getTarget()).started = true;
                return true;
            }
        });
    }
    int reduce = 0;
    float count =360.0f;
    
   
    public void restart()
    {
    	isCollided = false;
    }
    
    public Vector2 GetPosition(Vector2 CurPos, float angle, float distance)
    {
        //Get SOH
        float op = (float)Math.sin(angle) * distance;
        //Get CAH
        float ad = (float)Math.cos(angle) * distance;
        //Add to old Vector
        return (new Vector2(ad, op)).add(CurPos.x, CurPos.y);
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	if(count >= 360.f)
        	count = 0.0f;
        else   
        {
        	if(lddgame.oWall.multiplier == 1)
        		count = count + 1;
        	else 
        		count = count + 0.1f;
        }
    	
    	//Vector2 aaa = new Vector2(825 * Math.sin(angle), 825 * Math.sin(angle));
    	
    	Vector2 pos = GetPosition(new Vector2(lddgame.Jet.actorX, lddgame.Jet.actorY), angle, distance);
    	
    	actorX = pos.x;
    	actorY = pos.y;
    	
    	//textureAtlas.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    	int textureWidth = textureAtlas.getWidth();
    	int textureHeight = textureAtlas.getHeight();
    	float rotationAngle = count;

    	TextureRegion region = new TextureRegion(textureAtlas, 0, 0, textureWidth, textureHeight);
    	if(!isCollided)
    	{
	    	Rectangle rJet = new Rectangle();
	        
	        rJet.height = this.getHeight();
	        rJet.width = this.getWidth();
	        rJet.x = this.actorX- rJet.width/2f;
	        rJet.y = this.actorY - rJet.height/2f;
	        
	        if(rJet.overlaps(new Rectangle(lddgame.oWall.actorX, lddgame.oWall.actorY, lddgame.oWall.getWidth(), lddgame.oWall.getHeight())))
	        {
	        	lddgame.oWall.restore();
	        	this.isCollided = true;
	        	Random rand = new Random();
	        	
	        	for(int i = 0; i < 15; i++)
	        	{
	        		lddgame.stage.addActor(new Explosion(actorX+(textureWidth), actorY-(textureHeight/2f), 2, false));
	
	        	}	
	        }
        
    	}
    	
    	if(!isCollided)
    		batch.draw(region, actorX, actorY, (textureWidth) / 2f, (textureHeight) / 2f, textureWidth, textureHeight, 1, 1, rotationAngle, false);

    	
    }

    @Override
    public void act(float delta){
        
    	 
       
    }
}
