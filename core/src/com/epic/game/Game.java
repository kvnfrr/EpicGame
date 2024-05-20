package com.epic.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	private Texture player;
	private SpriteBatch batch;
	private ShapeRenderer wall;
	private OrthographicCamera camera;
	//test variables atm, nothing final or fully implemented
	//wanted to make something with movement like Asteroids from Atari, seems a bit difficult but not unrealistic
	//for now will implement simple movement rhough
	private float acceleration = 200f;
	private float maxSpeed = 400f;
	private float deceleration = 200f;
	//simple movement variables, must be float because of batch
	private float xAxis = 600f;
	private float yAxis = 350f;
    private float rotationAngle = 90f; // starting angle (90 degrees is north)

	@Override
	public void create () {

		//load images (SPRITE MUST BE FACING RIGHT) sprite and rotaion not linked otherwise
		player = new Texture("player.png");

		//instance of sprite batch (used to make character a rectangle p much)
		batch = new SpriteBatch();

		//create shapes
		wall = new ShapeRenderer();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0,1);	//sets background back to black after input, prevents after image

		userInput();	//handles player movement

		batch.begin();
		// Reminder: creates player (player(image), xAxis, yAxis (for starting position), xOrigin, yOrigin (for centering rotation), xScale, yScale (for size), rotationAngle (value for direction to face), srcX, srY (no idea),
		batch.draw(player, xAxis - player.getWidth() / 2, yAxis - player.getHeight() / 2, player.getWidth() / 2, player.getHeight() / 2, player.getWidth(), player.getHeight(), 1f, 1f, rotationAngle, 0, 0, player.getWidth(), player.getHeight(), false, false);
		batch.end();

		wall.begin(ShapeRenderer.ShapeType.Filled);
		wall.rectLine(0, 0, 50, 50, 3);
		wall.end();


	}

	@Override
	public void dispose() {
		player.dispose();
		batch.dispose();
		wall.dispose();
	}

	private void userInput() {
		// Trig used for getting W to move forward based on position looking
		float radianAngle = (float) Math.toRadians(rotationAngle);

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			xAxis += Math.cos(radianAngle) * 4.5;
			yAxis += Math.sin(radianAngle) * 4.5;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			rotationAngle += 4;
			if (rotationAngle >= 360) {
				rotationAngle -= 360;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			// Plan to add momentum and this would be like a brake button, if not just use W key code and convert + to - for reverse.

		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			rotationAngle -= 4;
			if (rotationAngle < 0) {
				rotationAngle += 360;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
}