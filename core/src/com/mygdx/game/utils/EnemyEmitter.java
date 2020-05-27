package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.mygdx.game.math.Rect;
import com.mygdx.game.math.Rnd;
import com.mygdx.game.pool.EnemyPool;
import com.mygdx.game.sprite.ship.EnemyShip;

public class EnemyEmitter {

    private static float generateInterval = 2f;
    private static final float SMALL_GRP_GEN = 0.5f;
    private static final float BIG_GRP_GEN = 4f;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final int ENEMY_SMALL_HP = 1;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.015f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 5;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 10f;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final int ENEMY_MEDIUM_HP = 30;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 15;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 9f;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private static final int ENEMY_BIG_HP = 70;
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 25;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;

    private Rect worldBounds;
    private float generateTimer;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;

    private final Vector2 enemySmallV;
    private final Vector2 enemyMediumV;
    private final Vector2 enemyBigV;

    private final TextureRegion bulletRegion;

    private final EnemyPool enemyPool;
    private EnemyShip enemy;
    private int level;
    private boolean flag;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool) {
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegions = Regions.split(enemy0, 1, 2, 2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegions = Regions.split(enemy1, 1, 2, 2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegions = Regions.split(enemy2, 1, 2, 2);
        this.enemySmallV = new Vector2(0, -0.2f);
        this.enemyMediumV = new Vector2(0, -0.03f);
        this.enemyBigV = new Vector2(0, -0.005f);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        level = 1;
    }

    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    public void generateSmallGroup(float delta) {
        generateTimer += delta;
        if (enemyPool.getActiveObjects().size() < 5) {
            if (generateTimer >= SMALL_GRP_GEN) {
                generateTimer = 0f;
                enemy = enemyPool.obtain();
                enemy.setBottom(worldBounds.getTop());
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_BULLET_DAMAGE,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        ENEMY_SMALL_HP,
                        ENEMY_SMALL_HEIGHT
                );
            }
        }

    }

    public void generateMedium(float delta) {
        generateInterval = 2f;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            enemy = enemyPool.obtain();
            enemy.setBottom(worldBounds.getTop());
            enemy.set(
                    enemyMediumRegions,
                    enemyMediumV,
                    bulletRegion,
                    ENEMY_MEDIUM_BULLET_HEIGHT,
                    ENEMY_MEDIUM_BULLET_VY,
                    ENEMY_MEDIUM_BULLET_DAMAGE,
                    ENEMY_MEDIUM_RELOAD_INTERVAL,
                    ENEMY_MEDIUM_HP,
                    ENEMY_MEDIUM_HEIGHT
            );
        }
    }

    public void generateBig(float delta) {
        generateTimer += delta;
        if (generateTimer >= BIG_GRP_GEN) {
            generateTimer = 0f;
            enemy = enemyPool.obtain();
            enemy.setBottom(worldBounds.getTop());
            enemy.set(
                    enemyBigRegions,
                    enemyBigV,
                    bulletRegion,
                    ENEMY_BIG_BULLET_HEIGHT,
                    ENEMY_BIG_BULLET_VY,
                    ENEMY_BIG_BULLET_DAMAGE,
                    ENEMY_BIG_RELOAD_INTERVAL,
                    ENEMY_BIG_HP,
                    ENEMY_BIG_HEIGHT
            );
        }
        if (generateTimer >= SMALL_GRP_GEN && flag) {
            generateTimer = 0f;
            enemy = enemyPool.obtain();
            enemy.setBottom(worldBounds.getTop());
            enemy.set(
                    enemySmallRegions,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_BULLET_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HP,
                    ENEMY_SMALL_HEIGHT
            );
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int l) {
        level = l;
    }
}
