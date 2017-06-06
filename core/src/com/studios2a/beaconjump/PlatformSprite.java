package com.studios2a.beaconjump;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
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
    float gameY;
    float actorX = 100, actorY = 400;
    int animationstate = 0;
    public Rectangle bounds;
    Animation spriteAnim;

    //private ShapeRenderer shapeRenderer;


    public PlatformSprite(float gameY, float setY, float setX){
        setBounds(getX(),getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setOrigin(getWidth()/2,getHeight()/2);
        sprite.setScale(2f);
        spriteAnim  = new Animation(1/10f,Assets.platformAtlas.getRegions());
        this.bounds = new Rectangle(this.actorX - getWidth() / 2, this.actorY - getHeight() / 2, this.getWidth(), this.getHeight());
        this.gameY = gameY;
        this.actorY = setY;
        this.actorX = setX;

        //shapeRenderer = new ShapeRenderer();
    }

    public void updateGameY(float setY){
        this.actorY = setY;
    }

    @Override
    public void act(float delta) {
        sprite.setPosition(getX(),getY()); //Move Image to Actor
        this.bounds.setPosition(this.actorX - getWidth() / 2, this.actorY - getHeight() / 2);
        sprite.setPosition(actorX,actorY);
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        //super.draw(batch, parentAlpha);
    }
}
