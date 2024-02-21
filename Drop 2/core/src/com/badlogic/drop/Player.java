package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Player {
    // Remember to do the javadoc stuff
    public Rectangle sprite;
    final Drop2 game;
    public int direction = 2; // 0 = up, 1 = right, 2 = down, 3 = left (like a clock)

    private TextureRegion currentFrame;
    private float stateTime = 0;
    private Array<Animation<TextureRegion>> walkingAnimation;
    private Array<Animation<TextureRegion>> idleAnimation;

    public Player (final Drop2 game) {
        this.game = game;

        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("Sprites/Atlas/Player.atlas"));

        walkingAnimation = new Array<Animation<TextureRegion>>();
        idleAnimation = new Array<Animation<TextureRegion>>();

        // Load walking animation from Sprite atlas
        walkingAnimation.add(
                new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_back"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_right"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_front"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_left"), Animation.PlayMode.LOOP));
        // Load idle animations
        idleAnimation.add(
                new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_back"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_right"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_front"), Animation.PlayMode.LOOP),
                new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_left"), Animation.PlayMode.LOOP)
        );


//        walkForward = new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_front"), Animation.PlayMode.LOOP);
//        walkBack = new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_back"), Animation.PlayMode.LOOP);
//        walkLeft = new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_left"), Animation.PlayMode.LOOP);
//        walkRight = new Animation<TextureRegion>(0.25f, playerAtlas.findRegions("walk_right"), Animation.PlayMode.LOOP);
//
//        idleForward = new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_front"), Animation.PlayMode.LOOP);
//        idleBack = new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_back"), Animation.PlayMode.LOOP);
//        idleLeft = new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_left"), Animation.PlayMode.LOOP);
//        idleRight = new Animation<TextureRegion>(0.40f, playerAtlas.findRegions("idle_right"), Animation.PlayMode.LOOP);

        sprite = new Rectangle();
        sprite.x = 0;
        sprite.y = 0;
        sprite.height = 28*4;
        sprite.width = 17*4;

    }

    public void move () {
        // Updates the player's position based on keys being pressed
        // Also updates the direction they are facing, and whether they are currently moving
        boolean moving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.x -= 200 * Gdx.graphics.getDeltaTime();
            direction = 3;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.x += 200 * Gdx.graphics.getDeltaTime();
            direction = 1;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprite.y += 200 * Gdx.graphics.getDeltaTime();
            direction = 0;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.y -= 200 * Gdx.graphics.getDeltaTime();
            direction = 2; 
            moving = true;
        }

        // Increment the animation
        stateTime += Gdx.graphics.getDeltaTime();

        // Get the current frame of the animation
        // Show a different animation if the player is moving vs idling
        if (moving) {
            currentFrame = walkingAnimation.get(direction).getKeyFrame(stateTime);
        } else {
            currentFrame = idleAnimation.get(direction).getKeyFrame(stateTime);
        }

    }



    public TextureRegion getCurrentFrame () {
            // Returns the current frame the player animation is on
            return currentFrame;
        }


}
