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
    private float scale;

    public Projectile(Texture texture, Vector2 position, Vector2 velocity) {
        this(texture, position, velocity, 2f, false);
    }

    public Projectile(Texture texture, Vector2 position, Vector2 velocity, float scale, boolean isCharged) {
        this.texture = texture;
        this.position = new Vector2(position);
        this.velocity = new Vector2(velocity);
        this.angle = velocity.angleDeg();
        this.scale = scale;
    }

    public void update(float deltaTime) {
        position.x += velocity.x * deltaTime * 4.5f;
        position.y += velocity.y * deltaTime * 4.5f;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - (texture.getWidth() * scale) / 2, position.y - (texture.getHeight() * scale) / 2,
                texture.getWidth() / 2, texture.getHeight() / 2, texture.getWidth(), texture.getHeight(), scale, scale,
                angle, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public Vector2 getPosition() {
        return position;
    }
}