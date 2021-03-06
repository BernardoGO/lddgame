package com.lddm.game;

import java.util.ArrayList;
import java.util.Random;

import sun.security.ssl.Debug;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class lddgame extends ApplicationAdapter implements ApplicationListener, GestureListener{
private SpriteBatch batch;
    
    
	public static Score score;
	int satelites = 0;
	
	//receber satelites do android
    public lddgame(int satelites)
    {
    	this.satelites = satelites;
    }
    
	BitmapFont font;
    Skin skin;
	TextButton button;
    TextButtonStyle textButtonStyle;
    TextureAtlas buttonAtlas;	
    
    public static boolean paused = false;
    public static Stage stage;
    public static MyActor Jet;
    public static Wall oWall;
    Camera  camera;
    private Viewport viewport;
    @Override
    public void create() {   
    	//camera = new OrthographicCamera(1280, 720);
    	
    	//inicializa cenário, nave e missel
    	stage = new Stage(new StretchViewport(1280, 720));
    	Jet = new MyActor();
    	oWall = new Wall(); //wall = missel
    	
    	//fontes para textos
    	font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("spritesheet2.atlas"));
        skin.addRegions(buttonAtlas);
        
        textButtonStyle = new TextButtonStyle();
        font.setScale(3f);
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("001");
        textButtonStyle.down = skin.getDrawable("001");
        
        //textButtonStyle.checked = skin.getDrawable("checked-button");
        button = new TextButton("Restart", textButtonStyle);
        button.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
            	
                oWall.restart();
                Jet.restart();
                button.setVisible(false);
                score.myScore = 0;
                navedestruida = false;
                terminou = false;
            }
        });
        
        button.addListener( new ClickListener() {             
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Preferences prefs = Gdx.app.getPreferences("PreferenceName");// We store the value 10 with the key of "highScore"
            	score.highScore = prefs.getInteger("highScore");
            	 oWall.restart();
            	 
            	 //Recuperar satelites removidos na partida anterior
            	 for(int yss=0; yss< satts.size(); yss++)
            	 {
            		 satts.get(yss).restart();
            	 }
            	 
            	 //recuperar nave
                 Jet.restart();
                 
                 //esconder botao
                 button.setVisible(false);
                 
                 //zerar pontuações
                 score.myScore = 0;
                 score.vidas = 1;
                 navedestruida = false;
                 terminou = false;
                 
            };
        });
        
    	button.setX(550);
    	button.setY(300);
    	button.setVisible(false);
    	  
    	//stage.setCamera(new OrthographicCamera(1280, 720));
    	//stage.getCamera().position.set(1280/2, 720/2, 0f); 
    	
    			
        //Gdx.input.setInputProcessor(stage);
        
        oWall.setTouchable(Touchable.enabled);
        //Jet.setTouchable(Touchable.enabled);
        Gdx.input.setInputProcessor(stage);
    	
    	Jet.setTouchable(Touchable.enabled);
        for(int x = 0; x < 100; x++)
        	stage.addActor(new Star());
        
        
        score = new Score();
        Preferences prefs = Gdx.app.getPreferences("PreferenceName");// We store the value 10 with the key of "highScore"
    	score.highScore = prefs.getInteger("highScore");
    	
    	stage.addActor(score);
        
        
        stage.addActor(Jet);
        stage.addActor(oWall);
        stage.addActor(button);
        //camera = stage.getCamera();
        //viewport = new StretchViewport(1280, 720, camera);
        
        //Gdx.input.setInputProcessor(new GestureDetector(this));
        gameStarted = true;
    }

    @Override
    public void dispose() {
    	 stage.dispose();

    }
    
    
    //informaçoes dos satelites
    public boolean gameStarted = false;
    public static int satt = 0;
    public static ArrayList<Float> Azimuths;
    public static ArrayList<Float> Elevations;
    public static boolean addedSatts = false;
    public static ArrayList<Satellite> satts = new ArrayList<Satellite>();
    
    //função chamada pelo listener do android
    public void setSattelites(int satt, ArrayList<Float> Azimuths, ArrayList<Float> Elevations)
    {
    	lddgame.Azimuths = Azimuths;
		lddgame.satt = satt;
    	lddgame.Elevations = Elevations;
    	
    	//setSatts();
    }
    
    //ainda não usei mas pretendo
    public static void setSatts()
    {
    	
    }
    
    
    boolean navedestruida = false;
    boolean terminou = false;
    int times = 10;
    
    //cores do fundo
    public static float r=0.3f, g= 0.3f, b=0.5f;
    
    
    //chamado em loop para renderizar os quadros da tela
    @Override
    public void render() {        
        Gdx.gl.glClearColor(r, g, b, 1);
        
        if(paused == true && r >= 0.1f)
        {
        	r = r-0.01f;
        	g = g-0.01f;
        	b = b-0.01f;
        	
        }
        else if (paused == false && r <= 0.3f)
        {
        	r = r+0.01f;
        	g = g+0.01f;
        	b = b+0.01f;
        	
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(camera.combined);
        //stage.setCamera(camera);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        
        //verificar colisão
        Rectangle rJet = new Rectangle();
        
        rJet.height = Jet.getHeight();
        rJet.width = Jet.getWidth();
        rJet.x = Jet.actorX- rJet.width/2f;
        rJet.y = Jet.actorY - rJet.height/2f;
        
        //java não tem Goto, ai já viu
        huuu:
        {
        	
	        if(rJet.overlaps(new Rectangle(oWall.actorX, oWall.actorY, oWall.getWidth(), oWall.getHeight())))
	        {
	        	//reduzir as vidas e verificar se é menor que um para terminar o jogo
	        	score.vidas--;
	        	if(score.vidas >= 1)
	        	{
	        		oWall.restore();
	        		break huuu;
	        	}
	        	
	        	//fim de jogo
	        	navedestruida = true;
	        	//oWall.started = false;
	        	button.setVisible(true);
	        	oWall.collided = true;
	        	Jet.collided = true;
	        	Jet.Effects(); 
	        	
	        	
	        	//atualizar highscore ao fim do jogo
	        	if(score.myScore > score.highScore)
	        	{
	        		Preferences prefs = Gdx.app.getPreferences("PreferenceName");// We store the value 10 with the key of "highScore"
	        	
	        		prefs.putInteger("highScore", score.myScore); 
	        		prefs.flush(); 
	        	}
	        	
	        }
        }
        
        //animações para fim de jogo
        if(navedestruida && !terminou)
        {
        	oWall.multiplier = 1;
        	r=0.3f;
        	g= 0.3f;
        	b=0.5f;
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
        
        //adicionar satelites
        if(satt > 8 && stage != null && addedSatts == false)
    	{
    		for(int x = 0; x < satt; x++)
    		{
    			Satellite newSat = new Satellite();
    			
    			newSat.angle = Azimuths.get(x);
    			newSat.distance = ((int)(Math.round( Elevations.get(x))))*2 + 120;
    			satts.add(newSat);
    			lddgame.stage.addActor(satts.get(x));
    			lddgame.stage.addActor(new Explosion(500, 500, 10, false));
    		}
    		
    		addedSatts = true;
    		
    	}
    }

    
    
    float increaseX = 1;
    float increaseY = 1;
    
    
    //corrigir o aspecto da tela para diferentes resoluções é o que faz este metodo
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
