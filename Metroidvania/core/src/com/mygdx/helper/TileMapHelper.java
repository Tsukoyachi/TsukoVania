package com.mygdx.helper;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.GameScreen;

public class TileMapHelper {
    /*
     * Classe permettant de charger les différentes TiledMap ainsi que de gérer les
     * polygones du jeu.
     */

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    /*
     * This method initialise the tiledMap with a tmx file and take the list of
     * Polygon we want
     */
    public OrthogonalTiledMapRenderer setupMap() {

        tiledMap = new TmxMapLoader().load("maps\\map0.tmx");
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    /**
     * This method parse all the mapObject to instance each one
     */
    private void parseMapObjects(MapObjects mapObjects) {

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
        }
    }

    /**
     * Method who allow a polygonMapObject to be instancied and to draw them.
     * 
     * @param polygonMapObject The polygon who will use the static body we're creating.
     */
    private void createStaticBody(PolygonMapObject polygonMapObject) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    /**
     * Method who allow a polygonMapObject to create a shape to be drawed later.
     * 
     * @param polygonMapObject The polygon who will use the shape we're drawing for his fixture.
     * @return The shape of the fixture of our polygon.
     */
    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {

        // Contain x and y position of all vertices of a shape
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();

        // Contain at the end Vector of the vertices to build a shape
        Vector2[] coordVertices = new Vector2[vertices.length / 2];
        for (int i = 0; i < vertices.length / 2; i++) {
            // Every odd indices are for x value and non odd one are for y values so we take
            // them and put them on the coordVertices
            Vector2 currentVertice = new Vector2(vertices[2 * i] / constants.pixelPerMeter,
                    vertices[2 * i + 1] / constants.pixelPerMeter);
            coordVertices[i] = currentVertice;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(coordVertices);

        return shape;
    }
}
