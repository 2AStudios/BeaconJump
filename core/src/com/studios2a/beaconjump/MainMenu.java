package com.studios2a.beaconjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Rectangle;
import com.studios2a.beaconjump.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by SysAdmin on 3/12/2017.
 */

public class MainMenu extends ScreenAdapter {
    BeaconJump game;
    OrthographicCamera cam;
    Vector3 touchpoint;

    Rectangle soundBounds;
    Rectangle playbtnBounds;
    Rectangle creditsbtnBounds;

    int skywrap = 10;
    int introanim = 0;

    public MainMenu(BeaconJump game){
        this.game = game;

        cam = new OrthographicCamera(720,1280);
        cam.position.set(720/2,1280/2,0);

        soundBounds = new Rectangle(0, 0, 100, 100);
        playbtnBounds = new Rectangle(100,800,521,112);
        creditsbtnBounds = new Rectangle(100,650,521,112);

        touchpoint = new Vector3();
        if(Settings.soundEnabled){
            Assets.mmsound.play();
            Assets.birdsbg.play();
        }
    }

    public void update(){
        if(Gdx.input.justTouched()){
            cam.unproject(touchpoint.set(Gdx.input.getX(),Gdx.input.getY(),0));
            if (playbtnBounds.contains(touchpoint.x, touchpoint.y)) {
                Assets.playSound(Assets.clickSound);
                game.setScreen(new MainGame(game));
                return;
            }
            if (creditsbtnBounds.contains(touchpoint.x, touchpoint.y)) {
                Assets.playSound(Assets.clickSound);
                //game.setScreen(new GameScreen(game));
                return;
            }
            if (soundBounds.contains(touchpoint.x, touchpoint.y)) {
                Assets.playSound(Assets.clickSound);
                Settings.soundEnabled = !Settings.soundEnabled;
                if (Settings.soundEnabled) {
                    Assets.mmsound.play();
                    Assets.birdsbg.play();
                }
                else{
                    Assets.mmsound.pause();
                    Assets.birdsbg.pause();
                }
            }
        }
    }

    public void draw(float delta){
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(Assets.backgroundsky, 0, 830, skywrap, 0, 720, 450); skywrap++; if(skywrap == 1055)skywrap=10;
        game.batch.draw(Assets.backgroundRegion, 0, 0, 720,1280);
        ParticleEffect splash = Assets.splashParticle;
        splash.start();
        splash.setPosition(0,435);
        splash.draw(game.batch,delta);
        game.batch.end();

        if(introanim < 100){
            introanim++;
        }

        game.batch.begin();
        game.batch.draw(Assets.logoBeaconJumpBlank,60,923,600,357);
        TextureRegion logojump = Assets.logoBeaconAnim.keyFrames[4];
        if(introanim <= 12){
            logojump = Assets.logoBeaconAnim.keyFrames[(int)(introanim/3)];
        }else if(introanim >= 16){
            game.batch.draw(Assets.playbutton,100,800,521,112);
        }
        if(introanim >= 20){
            game.batch.draw(Assets.creditsbutton,100,650,521,112);
        }
        Texture sndbtn = Assets.sndbuttonon;
        if(!Settings.soundEnabled){
            sndbtn = Assets.sndbuttonoff;
        }
        game.batch.draw(sndbtn,0,0,100,100);
        game.batch.draw(logojump,60,0,600,1280);
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw(delta);
    }

    @Override
    public void pause() {
        super.pause(); //TODO: Settings.save();
    }
}
