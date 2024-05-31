package com.epic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen extends ScreenAdapter {
	private Game game;
	private Texture player;
	private Texture projectilePlayer;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;

	private float xAxis = 2500f; // character start pos (X)
	private float yAxis = 500f; // character start pos (Y)
	private float rotationAngle = 90f; // starting angle (90 degrees is north)
	private float velocityX = 0f;
	private float velocityY = 0f;
	// screen size for border
	private final float WORLD_WIDTH = 5000f, WORLD_HEIGHT = 5000f;
	// for tracking projectiles, cooldowns, and trail
	private ArrayList<Projectile> projectiles;
	private float playerProjectileCooldown = 0f; // set to 0.5 after every shot
	private float playerProjectileCooldownTime = 0.5f; // time between normal shots

	private Sound projectileSound;

	public GameScreen(Game game) {
		this.game = game;
		// load images (SPRITE MUST BE FACING RIGHT) sprite and rotation not linked otherwise
		player = new Texture("player.png");
		projectilePlayer = new Texture("projectilePlayer.png");

		projectileSound = Gdx.audio.newSound(Gdx.files.internal("playerProjectileSound.mp3"));

		// instance of sprite batch (used to make character a rectangle p much)
		batch = new SpriteBatch();

		// create shapes
		shapeRenderer = new ShapeRenderer();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.update();

		projectiles = new ArrayList<>();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1); // sets background back to black after input, prevents after image

		userInput(); // handles player inputs

		// player pos
		xAxis += velocityX * Gdx.graphics.getDeltaTime();
		yAxis += velocityY * Gdx.graphics.getDeltaTime();

		// reduce speed after w key momentum, value 0.9 and 1 are the difference between insta stop and no friction
		float friction = 0.99f;
		velocityX *= friction;
		velocityY *= friction;

		// code for border
		float halfWidth = player.getWidth() * 0.7f / 2;
		float halfHeight = player.getHeight() * 0.7f / 2;

		if (xAxis - halfWidth < 0) {
			xAxis = halfWidth;
			velocityX = 0;
		}
		if (xAxis + halfWidth > WORLD_WIDTH) {
			xAxis = WORLD_HEIGHT - halfWidth;
			velocityX = 0;
		}
		if (yAxis - halfHeight < 0) {
			yAxis = halfHeight;
			velocityY = 0;
		}
		if (yAxis + halfHeight > WORLD_HEIGHT) {
			yAxis = WORLD_HEIGHT - halfHeight;
			velocityY = 0;
		}

		// camera (update to player location)
		camera.position.set(xAxis, yAxis, 0);
		camera.update();

		// projectile location updating
		float deltaTime = Gdx.graphics.getDeltaTime();
		Iterator<Projectile> iter = projectiles.iterator();
		while (iter.hasNext()) {
			Projectile projectile = iter.next();
			projectile.update(deltaTime);
			Vector2 pos = projectile.getPosition();
			if (pos.x < 0 || pos.x > WORLD_WIDTH || pos.y < 0 || pos.y > WORLD_HEIGHT) {
				iter.remove();
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin(); // begin rendering images (where created objects should go)

		// Reminder: creates player (player(image), xAxis, yAxis (for starting position), xOrigin, yOrigin (for centering rotation), xScale, yScale (for size), rotationAngle (value for direction to face), srcX, srY (no idea),
		batch.draw(player, xAxis - player.getWidth() / 2, yAxis - player.getHeight() / 2,
				player.getWidth() / 2, player.getHeight() / 2, player.getWidth(), player.getHeight(),
				0.7f, 0.7f, rotationAngle, 0, 0, player.getWidth(), player.getHeight(), false, false);

		// renders projectiles
		for (Projectile projectile : projectiles) {
			projectile.render(batch);
		}

		batch.end(); // end rendering

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(0, 0, WORLD_WIDTH, 3); // bottom border
		shapeRenderer.rect(0, 0, 3, WORLD_HEIGHT); // left border
		shapeRenderer.rect(0, WORLD_HEIGHT - 3, WORLD_WIDTH, 3); // top border
		shapeRenderer.rect(WORLD_WIDTH - 3, 0, 3, WORLD_HEIGHT); // right border
		shapeRenderer.end();
	}

	@Override
	public void dispose() {
		player.dispose();
		projectilePlayer.dispose();
		batch.dispose();
		shapeRenderer.dispose();
	}

	//keyboard inputs
	private void userInput() {
		// Trig used for getting W to move forward based on position looking
		float radianAngle = (float) Math.toRadians(rotationAngle);
		// deltaTime used for momentum
		float deltaTime = Gdx.graphics.getDeltaTime();

		// Update cooldowns
		if (playerProjectileCooldown > 0) {
			playerProjectileCooldown -= deltaTime;
		}

		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
			float acceleration = 975f;
			velocityX += Math.cos(radianAngle) * acceleration * deltaTime;
			velocityY += Math.sin(radianAngle) * acceleration * deltaTime;
		}
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)) {
			rotationAngle += 3.5f;
			if (rotationAngle >= 360) {
				rotationAngle -= 360;
			}
		}
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
			// 0.9 and 1 is the difference between nearly insta stop and very loose brakes, like friction
			velocityX *= 0.97f;
			velocityY *= 0.97f;
		}
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.D)) {
			rotationAngle -= 3.5f;
			if (rotationAngle < 0) {
				rotationAngle += 360;
			}
		}
		if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.SPACE) && playerProjectileCooldown <= 0) {
			// Calculate the tip offset from the center of the sprite
			float spriteWidth = player.getWidth();
			float spriteHeight = player.getHeight();
			float scale = 0.7f; // Scale of the sprite

			// Offset to the tip of the triangle (east-most point when facing right)
			float tipOffsetX = spriteWidth * scale / 2;
			float tipOffsetY = 0; // Since the tip is horizontally aligned

			// Rotate this offset based on the current rotation angle
			float tipX = xAxis + (float) (Math.cos(radianAngle) * tipOffsetX - Math.sin(radianAngle) * tipOffsetY);
			float tipY = yAxis + (float) (Math.sin(radianAngle) * tipOffsetX + Math.cos(radianAngle) * tipOffsetY);

			// Shoot a normal projectile
			Vector2 position = new Vector2(tipX, tipY);
			Vector2 velocity = new Vector2((float) Math.cos(radianAngle) * 600, (float) Math.sin(radianAngle) * 600);
			projectiles.add(new Projectile(projectilePlayer, position, velocity));
			playerProjectileCooldown = playerProjectileCooldownTime;

			projectileSound.play();
		}
		if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
}