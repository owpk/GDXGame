package com.mygdx.game.screen;

public enum KeySet {
    UP(51, 19),
    DOWN(47, 20),
    LEFT(29, 21),
    RIGHT(32, 22);

    private final int[] keycode;

    KeySet(int... keyCode) {
        this.keycode = keyCode;
    }

    //как то можно включить поддержку stream ?
    public boolean contain(int key) {
        for (int k : keycode) {
            if (k == key)
                return true;
        }
        return false;
    }
}
