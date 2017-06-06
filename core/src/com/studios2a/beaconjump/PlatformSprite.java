package com.studios2a.beaconjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by SysAdmin on 5/26/2017.
 */

public class PlatformSprite extends Actor {

    Sprite sprite = new Sprite(Assets.platformAtlas.findRegion("platform0"));
    float actorX = 100, actorY = 400;
    int animationstate = 0;
    Animation spriteAnim;


    public PlatformSprite(){
        setBounds(getX(),getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setOrigin(getWidth()/2,getHeight()/2);
        sprite.setScale(3f);
        spriteAnim  = new Animation(1/10f,Assets.rubySpritesAtlas.getRegions());


    }

    @Override
    public void act(float delta) {
        sprite.setPosition(getX(),getY()); //Move Image to Actor
        sprite.setPosition(actorX,actorY);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        //super.draw(batch, parentAlpha);
    }
}
