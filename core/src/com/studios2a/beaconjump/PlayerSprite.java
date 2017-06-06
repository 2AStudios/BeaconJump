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

public class PlayerSprite extends Actor {
    public final Rectangle bounds;
    Sprite sprite = new Sprite(Assets.rubySpritesAtlas.findRegion("rubyjump0"));
    float actorX = 100, actorY = 400;
    float velocityX = 0, velocityY = 3;
    boolean isFloating = false;
    int animationstate = 0;
    Animation spriteAnim;

    boolean isLeft = false;

    //private ShapeRenderer shapeRenderer;


    public PlayerSprite(){
        setBounds(getX(),getY(),sprite.getWidth(),sprite.getHeight());
        setTouchable(Touchable.enabled);
        setOrigin(getWidth()/2,getHeight()/2);
        sprite.setScale(3f);
        spriteAnim  = new Animation(1/10f,Assets.rubySpritesAtlas.getRegions());

        this.bounds = new Rectangle(this.actorX - getWidth() / 2, this.actorY - getHeight() / 2, this.getWidth(), this.getHeight());

        //shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void act(float delta) {
        sprite.setPosition(getX(),getY()); //Move Image to Actor
        //setBounds(actorX, actorY, this.getHeight(), this.getWidth());
        this.bounds.setPosition(this.actorX - getWidth() / 2, this.actorY - getHeight() / 2);
        actorX += velocityX;
        actorY += velocityY;
        sprite.setPosition(actorX,actorY);
        sprite.setRegion(Assets.rubySpritesAtlas.findRegion("rubyjump0"));
        if(velocityY <= -12) velocityY = -12;
        if(actorX < 0) actorX = 1080;
        if(actorX > 1080) actorX = 0;
        if(actorY <= 20) isFloating = false; else isFloating = true;
        if(isFloating){ //If floating
            velocityY-=0.3;
            if(velocityY>0.4){
                sprite.setRegion(Assets.rubySpritesAtlas.findRegion("rubyjump4"));
            }else if(velocityY<-0.2){
                //System.out.println("rubyjump"+(int)(Math.abs(velocityY)+6));
                sprite.setRegion(Assets.rubySpritesAtlas.findRegion("rubyjump"+(int)(Math.abs(velocityY/4)+6)));
            }else{
                sprite.setRegion(Assets.rubySpritesAtlas.findRegion("rubyjump5"));
            }
        }

        if(!isFloating && velocityY != 0){ //Landing Detection TODO: replace actorY with landed on platform
            actorY = 20;
            velocityY=0;
            if(animationstate == 0)
                animationstate = 15;
        }
        //System.out.println(velocityY + ", " + actorY + ", " +animationstate);
        if(velocityY == 0 && !isFloating && animationstate != 0){ //Landing Animation
            TextureRegion spriteTexture = sprite;
            switch ((int)(animationstate/5)) {
                case 2: {
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubycrouch0");
                }
                break;
                case 1: {
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubycrouch1");
                }
                break;
                case 0: {
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubyjump0");
                    jump();
                }
                break;
                default: {

                }
            }
            animationstate--;
            sprite.setRegion(spriteTexture);
        }
        if(velocityY >= 3 && animationstate != 0){ //Jumping Animation
            TextureRegion spriteTexture = sprite;
            switch ((int)(animationstate/3)){
                case 2:{
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubyjump1");
                }break;
                case 1:{
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubyjump2");
                }break;
                case 0:{
                    spriteTexture = Assets.rubySpritesAtlas.findRegion("rubyjump3");
                }break;
                default:{

                }

            }
            animationstate--;
            sprite.setRegion(spriteTexture);
        }
        if(animationstate <= 0){ //Lock Animation State
            animationstate = 0;
        }
        if(velocityX < -0.2){ //Flips Sprite
            isLeft = true;
        }
        if(velocityX > 0.2){
            isLeft = false;
        }
        if(isLeft){
            if(!sprite.isFlipX())
                sprite.flip(true, false);
        }
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        //super.draw(batch, parentAlpha);
    }

    public void jump() {
        velocityY = 18;
        animationstate = 9;
    }
}
