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

public class Player extends Entities {
    /* Class who extends Entities and allow the player to do special things */

    public Player(World world) {
        super(new Texture("player\\char_player.png"), world);
        definePlayer();
    }

    /* A method to define the player body and probably some attribute later */
    private void definePlayer() {
        // Definition of the body of the player
        BodyDef bdef = new BodyDef();
        bdef.position.set(this.getX(), this.getY());
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        // Definition of the fixture apply to the player
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        // Definition of the vertices of the polygon used to create the fixture
        Vector2[] shapeVertices = new Vector2[4];
        Vector2 bottomLeft = new Vector2(0, 0);
        shapeVertices[0] = bottomLeft;
        Vector2 bottomRight = new Vector2(this.getWidth() / 2 / constants.pixelPerMeter, 0);
        shapeVertices[1] = bottomRight;
        Vector2 topLeft = new Vector2(0, ((this.getHeight()) / 2) / constants.pixelPerMeter);
        shapeVertices[2] = topLeft;
        Vector2 topRight = new Vector2(((this.getWidth()) / 2) / constants.pixelPerMeter,
                ((this.getHeight()) / 2) / constants.pixelPerMeter);
        shapeVertices[3] = topRight;
        shape.set(shapeVertices);
        fdef.shape = shape;
        b2body.createFixture(fdef);

        shape.dispose();
    }

    /* Method to handle all the input of the player and reajust the position of the sprite to but it in his Phiysical body */
    public void update(float deltaTime) {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && canJump()) {
            this.b2body.applyLinearImpulse(new Vector2(0, 0.125f*constants.pixelPerMeter), new Vector2(this.getX(), this.getY()), true);
        }
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            this.moveLeft(10);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            this.moveRight(10);
        }
        this.setPosition(b2body.getPosition().x, b2body.getPosition().y);
         
    }

    /* This method check if the player is jumping or falling to see if he can jump */
    public boolean canJump() {
        if(this.b2body.getLinearVelocity().y==0)
            return true;
        return false;
    }
}
