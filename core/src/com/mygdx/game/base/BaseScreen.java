package com.mygdx.game.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;


    @Override
    public void show() {
        //init from here;
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        //render
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        //window resize
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        //keyboard key pressed;

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //keyboard key unpressed;
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("key "+character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //touch screen our mouse clicked;
        //System.out.println("tDwn "+screenX + " : " + screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //touch screen our mouse clicked up;
        //System.out.println("tUp "+screenX + " : " + screenY);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //mouse drag
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
