package com.mygdx.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.helper.constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.lang.Math;

public class Player extends Entities {
    /**
     * Class who extends Entities and allow the player to do special things
     */

    public Player(World world, Vector2 spawnPosition) {
        super(new Texture("player\\standing-right.png"),new Texture("player\\standing-left.png"), world, spawnPosition);
        definePlayer();
    }

    /**
     * A method to define the player body and probably some attribute later
     */
    private void definePlayer() {
        // Definition of the body of the player
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.getX(), this.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        // Definition of the fixture apply to the player
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        /**
         * Definition of the vertices of the polygon used to create the fixture, can be
         * modified in the future to simplify the part to adapt the fixture to the
         * sprite.
         * the sprite is 41 pixel per 49 pixel but the sprite doesn't take 41*49, he
         * take 40*48 and start at pixel 1*1 for the bottom left
         */
        float startx = 1 / constants.pixelPerMeter;
        float starty = 1 / constants.pixelPerMeter;
        float spriteWidth = 41 / constants.pixelPerMeter;
        float spriteHeight = 49 / constants.pixelPerMeter;

        Vector2[] shapeVertices = new Vector2[4];
        Vector2 bottomLeft = new Vector2(startx, starty);
        shapeVertices[0] = bottomLeft;
        Vector2 bottomRight = new Vector2(spriteWidth, starty);
        shapeVertices[1] = bottomRight;
        Vector2 topLeft = new Vector2(startx, spriteHeight);
        shapeVertices[2] = topLeft;
        Vector2 topRight = new Vector2(spriteWidth, spriteHeight);
        shapeVertices[3] = topRight;

        shape.set(shapeVertices);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        shape.dispose();
    }

    /**
     * Method to handle all the input of the player and reajust the position of the
     * sprite to but it in his Phiysical body
     * 
     * TODO : (optional) Handle controller input.
     * TODO : Change jump to make it variable if user release jump button.
     * 
     * "Z" or "Spacebar" : jump
     * "Q" or "left arrow" : go to left
     * "D" or "right arrow" : go to right
     * 
     * @param deltaTime The amount of time between two call of update method
     */
    public void update(float deltaTime) {
        if ((Gdx.input.isKeyJustPressed(Keys.Z) || Gdx.input.isKeyJustPressed(Keys.SPACE)) && canJump()) {
            this.b2body.applyLinearImpulse(new Vector2(0, 0.130f * constants.pixelPerMeter),
                    new Vector2(this.getX(), this.getY()), true);
        }
        if (Gdx.input.isKeyPressed(Keys.Q) || Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.moveLeft(0.1f);
        }
        if (Gdx.input.isKeyPressed(Keys.D) || Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.moveRight(0.1f);
        }
        this.setPosition(b2body.getPosition().x, b2body.getPosition().y);

    }

    /**
     * This method check if the player is jumping or falling to see if he can jump
     * 
     * @return A boolean to check if the player can jump or not
     */
    public boolean canJump() {
        if (this.b2body.getLinearVelocity().y <= 1)
            return true;
        return false;
    }
}
