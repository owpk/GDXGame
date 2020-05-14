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

    public boolean contain(int key) {
        return key == keycode[0] || key == keycode[1];
    }
}
