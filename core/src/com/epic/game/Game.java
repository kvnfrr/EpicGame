package com.epic.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends ApplicationAdapter {

	private Texture player;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	//test variables atm, nothing final or fully implemented
	//wanted to make something with movement like Asteroids from Atari, seems a bit difficult but not unrealistic
	//for now will implement simple movement rhough
	private float acceleration = 200f;
	private float maxSpeed = 400f;
	private float deceleration = 200f;
	private float rotationSpeed = 100f;
	//simple movement variables, must be float because of batch
	private float xAxis = 0f;
	private float yAxis = 0f;

	@Override
	public void create () {

		//load images
		player = new Texture("player.png");

		//instance of sprite batch (used to make character a rectangle p much)
		batch = new SpriteBatch();

		//player movement
		userInput();
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(player, xAxis, yAxis);
		batch.end();

		userInput();
		//leaves after image atm, update image issue ig
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void userInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			yAxis += 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)){
			xAxis -= 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)){
			yAxis -= 10;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
			xAxis += 10;
		}

	}


}