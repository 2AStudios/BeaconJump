package com.studios2a.beaconjump;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;


/**
 * Created by SysAdmin on 5/26/2017.
 */

public class MainGame extends ScreenAdapter {

    private Stage gameStage;
    BeaconJump game;
    PlayerSprite player;
    ArrayList<PlatformSprite> platfromList;
    float platformSpacing;
    float gameY;
    BitmapFont scoreFont;

    int skywrap = 10;
    int score = 0;

    public static final boolean HitboxMode = false;

    public MainGame(BeaconJump game){
        this.game = game;
        gameStage = new Stage();
        Gdx.input.setInputProcessor(gameStage);
        player = new PlayerSprite();
        gameStage.addActor(player);
        /*Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                player.jump();
                return true;
            }

        });*/
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/RWBY Font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;
        scoreFont = generator.generateFont(parameter);

        platfromList = new ArrayList<PlatformSprite>();
        gameY = 0;
        platformSpacing = (Gdx.graphics.getHeight()/8);
        for(int i = 0; i < 8;i++){
            platfromList.add(new PlatformSprite(platformSpacing*i,100,(float)(Math.random()*(Gdx.graphics.getWidth()-100))));
            //System.out.println(platfromList.get(i).actorX + "," + (float)(Math.random()*Gdx.graphics.getWidth()));
            gameStage.addActor(platfromList.get(i));
        }
        player.actorX = platfromList.get(0).actorX;


    }

    @Override
    public void resize(int width, int height) {

    }

    public void act(){
        float accelX = Gdx.input.getAccelerometerX();
        if(platformSpacing < (Gdx.graphics.getHeight()/8)*2) platformSpacing = (Gdx.graphics.getHeight()/8) + (int)(score);
        player.velocityX = -accelX;
        if(player.actorY > Gdx.graphics.getHeight()/2){
            /*if(gameY%platformSpacing < (gameY+player.actorY-(Gdx.graphics.getHeight()/2))%platformSpacing){
                gameY += player.actorY - (Gdx.graphics.getHeight() / 2);
                platfromList.add(new PlatformSprite(platformSpacing*(gameY%platformSpacing),100,(float)(Math.random()*Gdx.graphics.getWidth())));
            }*/
            gameY += player.actorY - (Gdx.graphics.getHeight() / 2);
            //System.out.println(platfromList.get(platfromList.size()-1).gameY  + platformSpacing - Gdx.graphics.getHeight()+ ", " + gameY);
            if(platfromList.get(platfromList.size()-1).gameY + platformSpacing - Gdx.graphics.getHeight() < gameY){
                platfromList.add(new PlatformSprite(platfromList.get(platfromList.size()-1).gameY + platformSpacing,100,(float)(Math.random()*(Gdx.graphics.getWidth()-100))));
                gameStage.addActor(platfromList.get(platfromList.size()-1));
            }

            player.actorY = Gdx.graphics.getHeight()/2;
        }
        for(PlatformSprite platform : platfromList){
            platform.actorY = platform.gameY - this.gameY;
            if(platform.bounds.overlaps(player.bounds) && player.velocityY < 0){
                player.jump();
            }
        }
        if(gameY > 15 && player.actorY <= 20){
            System.out.println("Game Over");
            player.remove();
            game.setScreen(new MainMenu(game));
        }
    }

    @Override
    public void render(float delta) {
        act();
        Gdx.gl.glClearColor(132/255f, 190/255f, 228/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStage.getBatch().begin();
        gameStage.getBatch().draw(Assets.backgroundsky, 0, 1000, skywrap, 500, 1080, 650); skywrap++; if(skywrap == 1055)skywrap=10;
        gameStage.getBatch().draw(Assets.backgroundRegion, 0, 0, 1080,1920);
        ParticleEffect splash = Assets.splashParticle;
        splash.start();
        splash.setPosition(0,652);
        splash.draw(gameStage.getBatch(),delta);
        gameStage.getBatch().end();

        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();

        gameStage.getBatch().begin();
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        if(score < (int)((gameY + player.actorY)/100)) score = (int)((gameY + player.actorY)/100);
        scoreFont.draw(gameStage.getBatch(), "Score: " + score, 25, Gdx.graphics.getHeight()-100);
        gameStage.getBatch().end();


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        gameStage.dispose();
    }
}
