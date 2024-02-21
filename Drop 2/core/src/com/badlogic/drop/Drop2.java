package com.badlogic.drop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop2 extends Game {
	public SpriteBatch batch;
	public BitmapFont titleFont;
	public BitmapFont infoFont;
	public BitmapFont smallinfoFont;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		titleFont.dispose();
		infoFont.dispose();
		smallinfoFont.dispose();
	}
}
