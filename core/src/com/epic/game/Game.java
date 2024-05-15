package com.epic.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private Rectangle player;
	private Texture playerImage;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	@Override
	public void create () {

		playerImage = new Texture("player-temp.png");

		batch = new SpriteBatch();

		player = new Rectangle();
		player.x = 100;
		player.y = 100;
		player.width = 30;
		player.height = 50;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(playerImage, player.x, player.y);
		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
	}

	private void playerMovement(){

		//tbd aka idk yet

	}

}