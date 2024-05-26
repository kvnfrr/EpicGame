package com.epic.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private float angle;

    public Projectile(Texture texture, Vector2 position, Vector2 velocity) {
        this.texture = texture;
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
        this.angle = velocity.angleDeg();
    }

    public void update(float deltaTime) {
        position.x += velocity.x * Gdx.graphics.getDeltaTime() * 5;
        position.y += velocity.y * Gdx.graphics.getDeltaTime() * 5;
    }

    public void render(SpriteBatch batch) {
        float scale = 1.5f;
        batch.draw(texture, position.x - (texture.getWidth() * scale) / 2, position.y - (texture.getHeight() * scale) / 2,
                texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), scale, scale,
                angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public Vector2 getPosition() {
        return position;
    }

}

