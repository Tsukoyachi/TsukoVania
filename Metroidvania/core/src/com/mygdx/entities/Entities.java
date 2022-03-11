package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.constants;

public class Entities extends Sprite {

    protected Rectangle bottom;
    protected Texture texture;
    protected Sprite sprite;
    public World world;
    public Body b2body;

    public Entities(Texture texture, World world) {
        bottom = new Rectangle(0.0f, 0.0f, 128.0f, 128.0f);

        this.texture = texture;
        sprite = new Sprite(texture, 0, 0, 128, 128);
        this.setPosition(0, 0);
        this.world = world;
    }

    /*
     * Method to check if an entities as hitted an other one by checking the hitbox, not sure if I'll keep it like that
     */
    public int hit(Rectangle r) {
        if (bottom.overlaps(r)) {
            return 1;
        }
        return -1;
    }

    /* A setter for the position of the object */
    public void setPosition(float x, float y) {
        bottom.x = x * constants.pixelPerMeter;
        bottom.y = y * constants.pixelPerMeter;
        sprite.setPosition(x, y);
    }

    public float getX() {
        return bottom.x;
    }

    public float getY() {
        return bottom.y;
    }

    public float getWidth() {
        return bottom.width;
    }

    public float getHeight() {
        return bottom.height;
    }

    public Sprite getSprite() {
        return this.sprite;
    }

    /* A method to draw the sprite off an entities on his physical body */
    public void draw(Batch batch) {
        sprite.setPosition(this.b2body.getPosition().x*constants.pixelPerMeter,(this.b2body.getPosition().y-1)*constants.pixelPerMeter);
        sprite.draw(batch);
    }

    /* A method to apply a force on the physical body of an object to go to the left*/
    public void moveLeft(float delta) {
        this.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), this.b2body.getWorldCenter(), true);
    }

    /* A method to apply a force on the physical body of an object to go to the right*/
    public void moveRight(float delta) {
        this.b2body.applyLinearImpulse(new Vector2(0.1f, 0), this.b2body.getWorldCenter(), true);

    }

    public void update(float delta) {
        // TODO : method usable for every instance of ennemi or player to render it 60 time per second
    }
}
