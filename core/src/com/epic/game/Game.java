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
	private Texture projectilePlayer;
	private SpriteBatch batch;
	private ShapeRenderer wall;
	private OrthographicCamera camera;

    private float xAxis = 600f;
	private float yAxis = 350f;
    private float rotationAngle = 90f; // starting angle (90 degrees is north)
	private float velocityX = 0f;
	private float velocityY = 0f;

	//screen size for border
	private float screenWidth;
	private float screenHeight;


	@Override
	public void create () {

		//load images (SPRITE MUST BE FACING RIGHT) sprite and rotaion not linked otherwise
		player = new Texture("player.png");

		//instance of sprite batch (used to make character a rectangle p much)
		batch = new SpriteBatch();

		//create shapes
		wall = new ShapeRenderer();

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

	}

	@Override
	public void render () {
		ScreenUtils.clear(0,0,0,1);	//sets background back to black after input, prevents after image

		userInput();	//handles player movement

		// player pos
		xAxis += velocityX * Gdx.graphics.getDeltaTime();
		yAxis += velocityY * Gdx.graphics.getDeltaTime();

		// reduce speed after w key momentum, value 0.9 and 1 are the difference between isnta stop and no friction
		float friction = 0.99f;
		velocityX *= friction;
		velocityY *= friction;

		//code for border
		float halfWidth = player.getWidth() * 0.7f / 2;
		float halfHeight = player.getHeight() * 0.7f / 2;

		if (xAxis - halfWidth < 0) {
			xAxis = halfWidth;
			velocityX = 0;
		}
		if (xAxis + halfWidth > screenWidth) {
			xAxis = screenWidth - halfWidth;
			velocityX = 0;
		}
		if (yAxis - halfHeight < 0) {
			yAxis = halfHeight;
			velocityY = 0;
		}
		if (yAxis + halfHeight > screenHeight) {
			yAxis = screenHeight - halfHeight;
			velocityY = 0;
		}

		batch.begin();
		// Reminder: creates player (player(image), xAxis, yAxis (for starting position), xOrigin, yOrigin (for centering rotation), xScale, yScale (for size), rotationAngle (value for direction to face), srcX, srY (no idea),
		batch.draw(player, xAxis - player.getWidth() / 2, yAxis - player.getHeight() / 2, player.getWidth() / 2, player.getHeight() / 2, player.getWidth(), player.getHeight(), 0.7f, 0.7f, rotationAngle, 0, 0, player.getWidth(), player.getHeight(), false, false);
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
		// deltaTime used for momentum
		float deltaTime = Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            float acceleration = 600f;
            velocityX += Math.cos(radianAngle) * acceleration * deltaTime;
			velocityY += Math.sin(radianAngle) * acceleration * deltaTime;

		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			rotationAngle += 2;
			if (rotationAngle >= 360) {
				rotationAngle -= 360;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			// 0.9 and 1 is the difference between nearly insta stop and very loose brakes, like friction
			velocityX *= 0.97f;
			velocityY *= 0.97f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			rotationAngle -= 3;
			if (rotationAngle < 0) {
				rotationAngle += 360;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}
	}
}