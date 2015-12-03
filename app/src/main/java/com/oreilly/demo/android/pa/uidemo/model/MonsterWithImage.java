package com.oreilly.demo.android.pa.uidemo.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Lucas on 11/29/2015.
 */
public class MonsterWithImage extends MonsterWithCoordinates {

    private File imageFile;
    private MonsterWithCoordinates monster;
    private Bitmap monsterSprite;

    public MonsterWithImage(MonsterWithCoordinates monster, String imageFile)
    {
        this.monster = monster;
        this.imageFile = new File(imageFile);
        this.configure();
    }

    public MonsterWithCoordinates getMonster() {return monster;}

    public void configure() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            monsterSprite = BitmapFactory.decodeStream(new FileInputStream(imageFile), null, options);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public Bitmap getMonsterSprite() { return monsterSprite;}

    public String getImageFile() {return imageFile.toString();}

    protected void setImageFile(String imageFile) {this.imageFile = new File(imageFile);}


}
