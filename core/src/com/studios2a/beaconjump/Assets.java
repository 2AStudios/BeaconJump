package com.studios2a.beaconjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.studios2a.beaconjump.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by SysAdmin on 3/12/2017.
 */

public class Assets {
    //Main Menu Resources
    public static Texture background;
    public static Texture backgroundsky;
    public static Texture backgroundRegion;
    public static ParticleEffect splashParticle = new ParticleEffect();
    public static Texture logoBeaconJumpBlank;
    public static Texture logoBeaconJumpJump;
    public static Texture logoBeaconJumpAnimFrames;
    public static Animation logoBeaconAnim;

    public static Texture playbutton;
    public static Texture creditsbutton;
    public static Texture sndbuttonon;
    public static Texture sndbuttonoff;
    public static Music mmsound;
    public static Music birdsbg;
    public static Sound clickSound;

    //Game Resources
    public static TextureAtlas rubySpritesAtlas;
    public static TextureAtlas platformAtlas;

    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }
    public static void load(){
        background = loadTexture("data/background.png");
        backgroundsky = loadTexture("data/backgroundsky.png");
        backgroundsky.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundRegion = loadTexture("data/backgroundresized.png");
        splashParticle.load(Gdx.files.internal("data/effects/splashparticles.p"), Gdx.files.internal("data/effects/"));
        logoBeaconJumpBlank = loadTexture("data/logoBeaconJump/logoblank.png");
        logoBeaconJumpJump = loadTexture("data/logoBeaconJump/logojump.png");
        logoBeaconJumpAnimFrames = loadTexture("data/logoBeaconJump/logoJumpFrames.png");
        logoBeaconAnim = new Animation(0.2f,new TextureRegion(logoBeaconJumpAnimFrames,0,0,600,1280), new TextureRegion(logoBeaconJumpAnimFrames,600,0,600,1280), new TextureRegion(logoBeaconJumpAnimFrames,1200,0,600,1280), new TextureRegion(logoBeaconJumpAnimFrames,1800,0,600,1280), new TextureRegion(logoBeaconJumpAnimFrames,2400,0,600,1280));

        rubySpritesAtlas = new TextureAtlas(Gdx.files.internal("data/characterspritesheets/ruby.atlas"));
        platformAtlas = new TextureAtlas(Gdx.files.internal("data/characterspritesheets/platform.atlas"));

        playbutton = loadTexture("data/buttons/playbtn.png");
        creditsbutton = loadTexture("data/buttons/creditsbtn.png");

        sndbuttonon = loadTexture("data/buttons/sndon.png");
        sndbuttonoff = loadTexture("data/buttons/sndoff.png");

        mmsound = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/redlikerosesremix.mp3"));
        mmsound.setLooping(true);
        mmsound.setVolume(0.5f);
        birdsbg = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/birds.mp3"));
        birdsbg.setLooping(true);
        birdsbg.setVolume(0.2f);

        clickSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.mp3"));
    }

    public static void playSound (Sound sound) {
        if (Settings.soundEnabled) sound.play(1);
    }
}
