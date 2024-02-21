package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import org.w3c.dom.Text;

public class MainMenuScreen implements Screen{

    final Drop2 game;
    private Stage stage;

    OrthographicCamera camera;

    public MainMenuScreen(final Drop2 game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Make table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Make skin and buttons
        Skin skin = new Skin(Gdx.files.internal("Sprites/uiskin/rainbow-ui.json"));

        game.titleFont = skin.getFont("title");
        game.infoFont = skin.getFont("button");
        game.smallinfoFont = skin.getFont("font");
        game.infoFont.getData().setScale(0.8f);


        TextButton startButton = new TextButton("Start Game", skin);
        TextButton exitButton = new TextButton("Exit", skin);
        table.row().pad(100, 0, 0, 0);
        table.add(startButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(exitButton).fillX().uniformX();
        table.row();

        // Add listeners
        startButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    game.setScreen(new GameScreen(game));
                }
            }
        );

        exitButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
                }
            }
        );



    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.3f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        // Make stage follow actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        game.batch.begin();
        game.titleFont.draw(game.batch, "Heslington Hustle", 80, 400);
        game.batch.end();

//        if (Gdx.input.isTouched()) {
//            game.setScreen(new GameScreen(game));
//            dispose();
//        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}
