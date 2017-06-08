package com.studios2a.beaconjump;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
        platfromList = new ArrayList<PlatformSprite>();
        gameY = 0;
        platformSpacing = (Gdx.graphics.getHeight()/8);
        for(int i = 0; i < 8;i++){
            platfromList.add(new PlatformSprite(platformSpacing*i,100,(float)(Math.random()*Gdx.graphics.getWidth())));
            //System.out.println(platfromList.get(i).actorX + "," + (float)(Math.random()*Gdx.graphics.getWidth()));
            gameStage.addActor(platfromList.get(i));
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    public void act(){
        float accelX = Gdx.input.getAccelerometerX();
        player.velocityX = -accelX;
        if(player.actorY > Gdx.graphics.getHeight()/2){
            /*if(gameY%platformSpacing < (gameY+player.actorY-(Gdx.graphics.getHeight()/2))%platformSpacing){
                gameY += player.actorY - (Gdx.graphics.getHeight() / 2);
                platfromList.add(new PlatformSprite(platformSpacing*(gameY%platformSpacing),100,(float)(Math.random()*Gdx.graphics.getWidth())));
            }*/
            gameY += player.actorY - (Gdx.graphics.getHeight() / 2);
            System.out.println(platfromList.get(platfromList.size()-1).gameY  + platformSpacing - Gdx.graphics.getHeight()+ ", " + gameY);
            if(platfromList.get(platfromList.size()-1).gameY + platformSpacing - Gdx.graphics.getHeight() < gameY){
                platfromList.add(new PlatformSprite(platfromList.get(platfromList.size()-1).gameY + platformSpacing,100,(float)(Math.random()*Gdx.graphics.getWidth())));
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
    }

    @Override
    public void render(float delta) {
        act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameStage.act(Gdx.graphics.getDeltaTime());
        gameStage.draw();
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
