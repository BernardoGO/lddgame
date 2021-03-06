package com.lddm.game;

import java.util.Random;

import sun.security.ssl.Debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;


public class Wall extends Actor {
    //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
    private Texture textureAtlas = new Texture(Gdx.files.internal("wall.png"));
    
    //batch = new SpriteBatch();
    //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
   // animation = new Animation(1/15f, textureAtlas.getRegions());
    
    public boolean collided = false;
    float actorX = -300, actorY = 408;
    public boolean started = false;
    private float stateTime = 0;
    int frame = 0;
    private Sound dropSound;
    
    public Wall(){
    	Random rnd = new Random();
    	dropSound = Gdx.audio.newSound(Gdx.files.internal("chk.mp3"));
    	actorY = rnd.nextInt(208);
        setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((Wall)event.getTarget()).started = true;
                return true;
            }
        });
    }
    
    float rotationAngle = -30;
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.draw(textureAtlas,actorX,actorY);
        
        

    	int textureWidth = textureAtlas.getWidth();
    	int textureHeight = textureAtlas.getHeight();
    	

    	TextureRegion region = new TextureRegion(textureAtlas, 0, 0, textureWidth, textureHeight);


    	//batch.draw(region, actorX, actorY, (textureWidth) / 2f, (textureHeight) / 2f, textureWidth, textureHeight, 1, 1, rotationAngle, false);
    	batch.draw(region,actorX,actorY, (textureWidth) / 2f, (textureHeight) / 2f , textureWidth, textureHeight, 1, 1, rotationAngle);
    
    }
    
    float xis = 1f;
    
    public void restart()
   	{
       	actorX = -500;
       	actorY = -500;
       	started = false;
       	rotationAngle = -27;
       	collided = false;
       	xis = 1;
   	}
    
    public float multiplier = 1;
    
    public boolean isLeft = false;
    
    public void move1()
    {
    	
    	//movimentar para cada lado
    	if(!!!isLeft)
    	{
    		actorY+=(5+Math.log(Math.pow(xis, 3)))*multiplier;
    		actorX+=(5+Math.log(Math.pow(xis, 3)))*multiplier;
    	}
    	else
    	{
    		actorY+=(5+Math.log(Math.pow(xis, 3)))*multiplier;
    		actorX-=(5+Math.log(Math.pow(xis, 3)))*multiplier;
    		
    	}
    }
    
    
    @Override
    public void act(float delta){
        if(started){
        	int flametime = 2;
        	int flames = 1;
        	frame++;
            if(collided)
            {
            	actorY-=8;
            	rotationAngle -= 2;
            	flametime = 4;
            	flames = 4;
            }
            else
            	move1();
            //Debug.println("das" , " das" + String.valueOf(5+Math.log(Math.pow(xis, 3))));
            
            int mod = 1;
            if(multiplier != 1) mod = 19;
            if(frame % mod == 0)
            {
            	//soltar fumaça de uma cor ou de outra, usando o mod 4 para deixar mais diferenciado
	            if(frame%4 == 0) 
	    		{
	            	int textureWidth = textureAtlas.getWidth();
	            	int textureHeight = textureAtlas.getHeight();
	            	for(int i = 0; i < 1; i++)
	            	{
	            		
	            		if(isLeft)lddgame.stage.addActor(new Explosion(actorX+(textureWidth), actorY-(textureHeight/2f), flametime, false));
	            		if(!isLeft)lddgame.stage.addActor(new Explosion(actorX-(textureWidth), actorY-(textureHeight/2f), flametime, false));
	            	}	
	    		}
	            else
	            {
	            	int textureWidth = textureAtlas.getWidth();
	            	int textureHeight = textureAtlas.getHeight();
	            	for(int i = 0; i < 1; i++)
	            	{
	            		if(isLeft)lddgame.stage.addActor(new Explosion(actorX+(textureWidth), actorY-(textureHeight/2f), flametime, true));
	            		if(!isLeft)lddgame.stage.addActor(new Explosion(actorX-(textureWidth), actorY-(textureHeight/2f), flametime, true));
	            	}
	  
	    		}
            }
        }
        if(actorY > 720 )
        {
        	restore();
        }
        
    }
    
    //recomeçar o movimento em caso de colisão sem game over 
    public void restore()
    {
    	isLeft = !isLeft;
    	if(isLeft)
    	{
    		rotationAngle = 27;
    		lddgame.score.addScore();
    		xis += 2;
        	actorY = -10;
        	Random rnd = new Random();
        	actorX = rnd.nextInt(1280);
        	dropSound.play();
    	}
    	else
    	{
    		rotationAngle = -27;
        	lddgame.score.addScore();
        	xis += 2;
        	actorY = -10;
        	Random rnd = new Random();
        	actorX = rnd.nextInt(1280);
        	dropSound.play();
    	}
    }
}


