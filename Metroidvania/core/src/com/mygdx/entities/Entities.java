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
    /**
     * Class who manage the creation of each entities and allow them to move or make
     * action
     */

    protected Rectangle bottom;
    protected Texture textureRight;
    
    protected Texture textureLeft;
    protected Sprite sprite;
    protected Sprite spriteRight;
    protected Sprite spriteLeft;
    public World world;
    public Body b2body;
    protected boolean standRight = true;

    public Entities(Texture textureRight, Texture textureLeft, World world, Vector2 spawnPosition) {
        bottom = new Rectangle(0.0f, 0.0f, 128.0f, 128.0f);

        this.textureRight = textureRight;
        this.textureLeft = textureLeft;
        spriteRight = new Sprite(textureRight, 0, 0, 128, 128);
        spriteLeft = new Sprite(textureLeft, 0, 0, 128, 128);
        this.setPosition(spawnPosition.x, spawnPosition.y);
        this.world = world;
    }

    /**
     * Method to check if an entities as hitted an other one by checking the hitbox,
     * not sure if I'll keep it like that
     */
    public int hit(Rectangle r) {
        if (bottom.overlaps(r)) {
            return 1;
        }
        return -1;
    }

    /**
     * A setter for the position of the object
     * 
     * @param x The x coordinate in meter (I'll potentially put in in pixel later)
     * @param y The y coordinate in meter (I'll potentially put in in pixel later)
     */
    public void setPosition(float x, float y) {
        bottom.x = x * constants.pixelPerMeter;
        bottom.y = y * constants.pixelPerMeter;
        if(standRight){
            sprite = spriteRight;
        }
        else {
            sprite = spriteLeft;
        }
        sprite.setPosition(x, y);
    }

    /**
     * @return The x coordinate of the entity
     */
    public float getX() {
        return bottom.x;
    }

    /**
     * @return The y coordinate of the entity
     */
    public float getY() {
        return bottom.y;
    }

    /**
     * @return The width of the entity
     */
    public float getWidth() {
        return bottom.width;
    }

    /**
     * @return The Height of the entity
     */
    public float getHeight() {
        return bottom.height;
    }

    /**
     * @return The sprite of the entity
     */
    public Sprite getSprite() {
        return this.sprite;
    }

    /**
     * A method to draw the sprite off an entities on his physical body
     * 
     * @param batch The batch used to draw the sprite
     */
    public void draw(Batch batch) {
        sprite.setPosition(this.b2body.getPosition().x * constants.pixelPerMeter,
                (this.b2body.getPosition().y - 1) * constants.pixelPerMeter);
        sprite.draw(batch);
    }

    /**
     * A method to apply a force on the physical body of an object to go to the
     * left, later it will load a special sprite of character going left
     * 
     * @param delta This represent the "intensity" of the movement
     */
    public void moveLeft(float delta) {
        this.b2body.applyLinearImpulse(new Vector2(-delta, 0), this.b2body.getWorldCenter(), true);
        standRight = false;
    }

    /**
     * A method to apply a force on the physical body of an object to go to the
     * right, later it will load a special sprite of character going right
     * 
     * @param delta This represent the "intensity" of the movement
     */
    public void moveRight(float delta) {
        this.b2body.applyLinearImpulse(new Vector2(delta, 0), this.b2body.getWorldCenter(), true);
        standRight = true;
    }
}
