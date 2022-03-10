package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Game;

public class Boot extends Game {
    /* Classe Permettant d'instancier les éléments du jeu */
    public static Boot INSTANCE;
    private int widthScreen, heightScreen;
    private OrthographicCamera orthographicCamera;

    public Boot() {
        INSTANCE = this;
    }

    @Override
    public void create() {
        this.widthScreen = Gdx.graphics.getWidth();
        this.heightScreen = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, widthScreen, heightScreen);
        setScreen(new GameScreen(orthographicCamera));
    }
}
