package com.mygdx.game;

import java.util.Random;

import sun.security.ssl.Debug;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MyGdxGame extends ApplicationAdapter implements ApplicationListener, GestureListener{
private SpriteBatch batch;
    
    
    private float elapsedTime = 0;
    
    public class MyActor extends Actor{
        //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
        private TextureAtlas textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
        private Animation animation =  new Animation(1/15f, textureAtlas.getRegions());
        //batch = new SpriteBatch();
        //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
       // animation = new Animation(1/15f, textureAtlas.getRegions());

        float actorX = 500, actorY = 500;
        public boolean started = false;
        private float stateTime = 0;
        
        public MyActor(){
        	
            setBounds(actorX,actorY,animation.getKeyFrame(elapsedTime, true).getRegionWidth(),animation.getKeyFrame(elapsedTime, true).getRegionHeight());
            
            
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    //((MyActor)event.getTarget()).started = true;
                    return true;
                }
            });
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
        	batch.draw(region,actorX,actorY, (textureWidth) / 2f, (textureHeight) / 2f , textureWidth, textureHeight, 1, 1, rotationAngle);
        }
        
        @Override
        public void act(float delta){
        	stateTime += delta;
            if(started){
                actorX+=20;
            }
            
        }


		
    }
    
    public class Wall extends Actor {
        //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
        private Texture textureAtlas = new Texture(Gdx.files.internal("wall.png"));
        
        //batch = new SpriteBatch();
        //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
       // animation = new Animation(1/15f, textureAtlas.getRegions());
        
        float actorX = -100, actorY = 408;
        public boolean started = false;
        private float stateTime = 0;
        public Wall(){
        	Random rnd = new Random();
        	actorY = rnd.nextInt(208);
            setBounds(actorX,actorY,textureAtlas.getWidth(),textureAtlas.getHeight());
            addListener(new InputListener(){
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    ((Wall)event.getTarget()).started = true;
                    return true;
                }
            });
        }
        
        
        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.draw(textureAtlas,actorX,actorY);
        }
        
        @Override
        public void act(float delta){
            if(started){
                actorX+=20;
            }
            if(actorX > 1280)
            {
            	actorX = -100;
            	Random rnd = new Random();
            	actorY = rnd.nextInt(408);
            }
            
        }
    }
    
    public class Star extends Actor {
        //Texture texture = new Texture(Gdx.files.internal("data/spritesheet.png"));
        private Texture textureAtlas = new Texture(Gdx.files.internal("star.png"));
        
        //batch = new SpriteBatch();
        //textureAtlas = new TextureAtlas(Gdx.files.internal("data/spritesheet.atlas"));
       // animation = new Animation(1/15f, textureAtlas.getRegions());
        
        float actorX = -100, actorY = 408;
        public boolean started = false;
        private float stateTime = 0;
        public Star(){
        	Random rnd = new Random();
        	actorY = rnd.nextInt(800);
        	stateTime = rnd.nextInt(5)+1;
        	actorY -= 100;
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
            	count ++;
            //batch.draw(textureAtlas, (float)actorX, (float)actorY, (float)textureAtlas.getWidth()/2, (float)textureAtlas.getHeight()/2, (float)textureAtlas.getWidth(), (float)textureAtlas.getHeight(), (float)32, (float)32);
            //batch.draw(textureAtlas, actorX, actorY, (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(1), (float)(1), 180, (int)(actorX), (int)(actorY), (textureAtlas.getWidth()), (textureAtlas.getHeight()), false, false) ;
            
        	
        	
        	
        	textureAtlas.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        	int textureWidth = textureAtlas.getWidth();
        	int textureHeight = textureAtlas.getHeight();
        	float rotationAngle = count;

        	TextureRegion region = new TextureRegion(textureAtlas, 0, 0, textureWidth, textureHeight);


        	batch.draw(region, actorX, actorY, (textureWidth-reduce) / 2f, (textureHeight-reduce) / 2f, textureWidth-reduce, textureHeight-reduce, 1, 1, rotationAngle, false);

        	
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
    
    public class Explosion extends Actor {

        private Texture textureAtlas = new Texture(Gdx.files.internal("explode.png"));

        float actorX = 0, actorY = 0;
        public boolean started = false;
        private float stateTime = 0;
        public Explosion(float x, float y){
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
        int reduce = 0;
        float count =360.0f;
        @Override
        public void draw(Batch batch, float parentAlpha) {
            //batch.draw(textureAtlas,actorX,actorY);
            //batch.draw(textureAtlas,actorX,actorY,textureAtlas.getWidth()/2.0f,textureAtlas.getHeight()/2.0f, textureAtlas.getWidth(), textureAtlas.getHeight(), 1f, 1f,count, false);
            if(count >= 360.f)
            	count = 0.0f;
            else
            	count ++;
            //batch.draw(textureAtlas, (float)actorX, (float)actorY, (float)textureAtlas.getWidth()/2, (float)textureAtlas.getHeight()/2, (float)textureAtlas.getWidth(), (float)textureAtlas.getHeight(), (float)32, (float)32);
            //batch.draw(textureAtlas, actorX, actorY, (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(textureAtlas.getWidth()), (float)(textureAtlas.getHeight()), (float)(1), (float)(1), 180, (int)(actorX), (int)(actorY), (textureAtlas.getWidth()), (textureAtlas.getHeight()), false, false) ;
            
            reduce++;
        	
        	
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
    
    private Stage stage;
    MyActor Jet;
    Wall oWall;
    Camera  camera;
    private Viewport viewport;
    @Override
    public void create() {   
    	//camera = new OrthographicCamera(1280, 720);
    	stage = new Stage(new StretchViewport(1280, 720));
    	Jet = new MyActor();
    	oWall = new Wall();
    	
    	  
    	//stage.setCamera(new OrthographicCamera(1280, 720));
    	//stage.getCamera().position.set(1280/2, 720/2, 0f); 
    	
    			
        //Gdx.input.setInputProcessor(stage);
        
        oWall.setTouchable(Touchable.enabled);
        //Jet.setTouchable(Touchable.enabled);
        
        for(int x = 0; x < 100; x++)
        	stage.addActor(new Star());
        
        
        
        stage.addActor(Jet);
        stage.addActor(oWall);
        
        //camera = stage.getCamera();
        //viewport = new StretchViewport(1280, 720, camera);
        
        Gdx.input.setInputProcessor(new GestureDetector(this));
        
    }

    @Override
    public void dispose() {
    	 stage.dispose();
        
       
    }

    boolean navedestruida = false;
    boolean terminou = false;
    int times = 10;
    @Override
    public void render() {        
        Gdx.gl.glClearColor(0.6f, 0.6f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(camera.combined);
        //stage.setCamera(camera);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        Rectangle rJet = new Rectangle();
        rJet.height = Jet.getHeight();
        rJet.width = Jet.getWidth();
        rJet.x = Jet.actorX;
        rJet.y = Jet.actorY;
        
        if(rJet.overlaps(new Rectangle(oWall.actorX, oWall.actorY, oWall.getWidth(), oWall.getHeight())))
        {
        	navedestruida = true;
        	//oWall.started = false;
        	
        }
        if(navedestruida && !terminou)
        {
        	
        	if(times == 10) 
        		{
        			stage.addActor(new Explosion(rJet.x, rJet.y));
        			times = 0;
        		}
        	Jet.actorX -= 3;
        	Jet.actorY -= 3;
    		Jet.degrees += 17;
    		times++;
    		if(Jet.actorY < -260) terminou = true;
        }
    }

    float increaseX = 1;
    float increaseY = 1;
    
    @Override
    public void resize(int width, int height) {
    	//stage.setViewport(new StretchViewport(1280,728));
    	stage.getViewport().update(width, height, false);
    	increaseX = ((float)1280)/((float)width);
    	increaseY = ((float)720)/((float)height);
    	//viewport.update(width, height);
    	//Debug.println("aa", "width/1280="+increaseX);
    	
    	//stage.getCamera().position.set(1280/2, 720/2, 0);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
    
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
    }
     
    @Override
    public boolean tap(float x, float y, int count, int button) {
    	oWall.started = true;
    return false;
    }
     
    @Override
    public boolean longPress(float x, float y) {
    // TODO Auto-generated method stub
    return false;
    }
     
    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
    // TODO Auto-generated method stub
    return false;
    }
     
    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
       // TODO Auto-generated method stub
    	if(oWall.started == false) return false;
    	if(navedestruida)
    	{
    		 return false; 
    	}
    	
    	float newPosX = (float) (Jet.actorX + (deltaX*increaseX));
    	float newPosY = (float) (Jet.actorY - (deltaY*increaseY));
    	    	
    	if(!(newPosX < 0) && !(newPosX > 1280-256))
    	{
    		Jet.actorX = newPosX;
    		
    	}
    	if(!(newPosY < 0) && !(newPosY > 720-128))
    	{
    		Jet.actorY = newPosY;
    	}
    	
    	
    	
    	
    	//Debug.println("as", String.valueOf((deltaX*increaseX)));
    	
    	//marioX += deltaX;
    	//marioY -= deltaY;
       return false;
    }
    float marioX = 0;
    float marioY = 0;
     
    @Override
    public boolean zoom(float initialDistance, float distance) {
    // TODO Auto-generated method stub
    return false;
    }
     
    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
    Vector2 pointer1, Vector2 pointer2) {
    // TODO Auto-generated method stub
    return false;
    }

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
}