package com.oreilly.demo.android.pa.uidemo.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Lucas on 11/29/2015.
 */
public class MonsterWithImage extends MonsterWithCoordinates {

    private String imageFile;
    private MonsterWithCoordinates monster;
    private Bitmap monsterSprite;

    public MonsterWithImage(MonsterWithCoordinates monster, String imageFile)
    {
        this.monster = monster;
        this.imageFile = imageFile;
        this.configure();
    }

    public MonsterWithCoordinates getMonster() {return monster;}

    public void configure() {
        Bitmap monsterSprite = BitmapFactory.decodeFile("monster.png");
    }

    public Bitmap getMonsterSprite() { return monsterSprite;}

    public String getImageFile() {return imageFile;}

    protected void setImageFile(String imageFile) {this.imageFile = imageFile;}


}
