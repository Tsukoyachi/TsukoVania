package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Player;
import com.mygdx.helper.TileMapHelper;
import com.mygdx.helper.constants;
import com.badlogic.gdx.Input.Keys;

public class GameScreen extends ScreenAdapter {
    /**
     * Class who manage the display of the game
     */
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Player player;

    public GameScreen(OrthographicCamera camera) {
        this.camera = camera;
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, -10), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        tileMapHelper = new TileMapHelper(this);
        orthogonalTiledMapRenderer = tileMapHelper.setupMap();

        // The player doesn't spawn on the ground but a little bit higher to not clip in
        // it and fall
        player = new Player(world, new Vector2(1 / constants.pixelPerMeter, 1 / constants.pixelPerMeter));
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        player.update(1 / 60f);

        handleSpecialInput();

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

    }

    /**
     * This method will handle the input to change menu or respawn to last
     * checkpoint because for example the respawn button destroy the instance of
     * player to create an other one so we can't do it from the player class.
     * 
     *  "/" : respawn to spawn position (later on will be last checkpoint)
     */
    private void handleSpecialInput() {
        if (Gdx.input.isKeyJustPressed(Keys.ALT_LEFT)) {
            world.destroyBody(this.player.b2body);
            this.player = new Player(world, new Vector2(1 / constants.pixelPerMeter, 1 / constants.pixelPerMeter));
        }
    }

    /**
     * Method used to allow the camera to follow the player
     */
    private void cameraUpdate() {
        camera.position.set(new Vector3(player.getX(), player.getY(), 0));
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render();

        batch.begin();

        player.draw(batch);

        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(constants.pixelPerMeter));
    }

    /**
     * @return The world of the game.
     */
    public World getWorld() {
        return world;
    }
}
