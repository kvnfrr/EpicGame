package com.epic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements com.badlogic.gdx.Screen {
    private Game game;
    private Texture background;
    private Texture playButton;
    private Texture exitButton;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private int selectedOption = 0; // 0: Play, 1: Exit

    public MenuScreen(Game game) {
        this.game = game;
        background = new Texture("menu_background.png"); // Your background image
        playButton = new Texture("play_button.png");
        exitButton = new Texture("exit_button.png");
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        handleInput(); // Handle navigation input

        batch.begin();
        // Draw background image
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Draw buttons
        batch.draw(playButton, Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 50);
        batch.draw(exitButton, Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 50);
        batch.end();

        // Draw white border around the selected option
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        if (selectedOption == 0) {
            shapeRenderer.rect(Gdx.graphics.getWidth() / 2 - playButton.getWidth() / 2 - 5, Gdx.graphics.getHeight() / 2 + 45, playButton.getWidth() + 10, playButton.getHeight() + 10);
        } else if (selectedOption == 1) {
            shapeRenderer.rect(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2 - 5, Gdx.graphics.getHeight() / 2 - 55, exitButton.getWidth() + 10, exitButton.getHeight() + 10);
        }
        shapeRenderer.end();
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.W) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedOption = (selectedOption == 0) ? 1 : 0;
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedOption = (selectedOption == 1) ? 0 : 1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (selectedOption == 0) {
                game.setScreen(new GameScreen(game));
            } else if (selectedOption == 1) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        playButton.dispose();
        exitButton.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }
}
