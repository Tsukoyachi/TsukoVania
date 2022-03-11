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

public class GameScreen extends ScreenAdapter {
    /* Class who manage the display of the game */
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
        
        player = new Player(world);
    }

    private void update() {
        world.step(1 / 60f, 6, 2);
        player.update(1 / 60f);

        cameraUpdate();

        batch.setProjectionMatrix(camera.combined);
        orthogonalTiledMapRenderer.setView(camera);
        
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }


    }

    /* Method usefull for later to allow the camera to follow the player */
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

    public World getWorld() {
        return world;
    }
}
