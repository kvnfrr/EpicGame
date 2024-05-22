package com.epic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;

    public Projectile(Texture texture, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
    }

    public void update(float deltaTime) {
        position.x += velocity.x * Gdx.graphics.getDeltaTime();
        position.y += velocity.y * Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 2, position.y - texture.getHeight() / 2);

    }

    public Vector2 getPosition() {
        return position;
    }

}

