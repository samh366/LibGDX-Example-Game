package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import org.w3c.dom.Text;

import java.util.List;

import java.util.Iterator;

public class GameScreen implements Screen {
    final Drop2 game;
    private Texture rainDropImg;
    private Texture bucketImg;


    private Sound dropSound;
    private Music rainMusic;
    private OrthographicCamera camera;

    // The libgdx arrayclass is far better at garbage collection
    private Array<Rectangle> raindrops;
    private long lastDropTime;

    private int score = 0;

    public Player player;




    public GameScreen(final Drop2 game) {
        this.game = game;
        player = new Player(game);

        // Load textures
        rainDropImg = new Texture(Gdx.files.internal("droplet.png"));
        bucketImg = new Texture(Gdx.files.internal("bucket.png"));


        // Load sounds
        dropSound = Gdx.audio.newSound(Gdx.files.internal("dropSound.wav"));
        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rainfall.wav"));

        // Play the rain music
        rainMusic.setLooping(true);
        rainMusic.setVolume(0.1f);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Make the array of rectangles
        raindrops = new Array<Rectangle>();
        spawnRaindrop();

    }

    @Override
    public void show() {
        rainMusic.play();
    }

    @Override
    public void render (float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();

        // Set batch to use the same coordinate system as te camera
        game.batch.setProjectionMatrix(camera.combined);
        // Create a bach of images to send to openGL


        // Spawn raindrops
        if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        // Move each drop
        for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext();) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();
            // Check for hit with bucket
            if(raindrop.overlaps(player.sprite)) {
                dropSound.play();
                iter.remove();
                score += 1;
            }
        }

        player.move();
        // Create a bach of images to send to openGL
        game.batch.begin();


        game.batch.draw(player.getCurrentFrame(), player.sprite.x, player.sprite.y, 0, 0, player.sprite.width, player.sprite.height, 1, 1, 1);
        for(Rectangle raindrop: raindrops) {
            game.batch.draw(rainDropImg, raindrop.x, raindrop.y);
        }
        game.infoFont.draw(game.batch, "Take a shower!", 0f, 470);
        game.smallinfoFont.draw(game.batch, String.format("Score: %d", score), 0f, 430);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800-64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

//    public BitmapFont generateFont(String path, double size)
//    {
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        parameters.genMipMaps = true;
//        parameters.color = Color.WHITE;
//        parameters.size = (int) Math.ceil(size);
////		parameters.magFilter = TextureFilter.Linear;
////		parameters.minFilter = TextureFilter.Linear;
//        generator.scaleForPixelHeight((int) size);
//
//        return generator.generateFont(parameters);
//    }

    @Override
    public void dispose () {
        rainDropImg.dispose();
        bucketImg.dispose();
        dropSound.dispose();
        rainMusic.dispose();
    }
}
