package com.epic.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private Rectangle player;
	private Texture playerImage;
	private Texture tileImage;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	@Override
	public void create () {

		playerImage = new Texture("player-temp.png");
		tileImage = new Texture("tile-temp.png");

		batch = new SpriteBatch();

		player = new Rectangle();

		//centers player at start, but the sprite and player object are not allighned correctly so its wack
		//should make a method in future for centering player in tile
		player.x = 16;
		player.y = 5;

		//rounded size of player-temp.png
		player.width = 30;
		player.height = 50;

		//just copied it from GDX documentation, crashes without it
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		tilePattern();
		batch.draw(playerImage, player.x, player.y);
		batch.end();


	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerImage.dispose();
	}

	private void playerMovement(){

		// :D

	}
	private void tilePattern() {
		//should put variable for 64, but code was temporary from start
		for(int xAxis = 0; xAxis < Gdx.graphics.getWidth(); xAxis += 64){
			for(int yAxis = 0; yAxis < Gdx.graphics.getHeight(); yAxis += 64){
				batch.draw(tileImage, xAxis, yAxis, 64, 64);
			}
		}
	}

}