package com.sleepware.ShooterEngine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class LevelTiles {

	private Bitmap mTiles;

	//Size of each tile
	private int mTileSizeWidth;
	private int mTileSizeHeight;
	
	//Size of the tiles image
	private int mTilesImageWidth;
//	private int mTilesImageHeight;
	
	//private int mNumTiles;
	private int mNumTilesX;
//	private int mNumTilesY;
	
	
	LevelTiles (ShooterEngineContext shooterEngineContext, int tilesId, int tilewidth, int tileheight) {

		mTiles =shooterEngineContext.mBitmapWarehouse.getImage(tilesId);

    	mTileSizeWidth = tilewidth;
    	mTileSizeHeight = tileheight;
		
    	mTilesImageWidth = mTiles.getWidth();
   // 	mTilesImageHeight = mTiles.getHeight();
    	
    	mNumTilesX = mTilesImageWidth/mTileSizeWidth;
   // 	mNumTilesY = mTilesImageHeight/mTileSizeHeight;
    	
  //  	mNumTiles = mNumTilesX * mNumTilesY;
    	
	}
	
	
	public void doDraw(Canvas canvas, int tile, int x, int y) {
		
		if(tile==0) return;
		
		tile--;
		
		int tileX = tile % mNumTilesX;
		int tileY = tile / mNumTilesX;
		
		Rect src = new Rect(tileX * mTileSizeWidth, tileY * mTileSizeHeight, (tileX+1) * mTileSizeWidth, (tileY+1) * mTileSizeHeight );
		Rect dest = new Rect(x, y, x + mTileSizeWidth, y + mTileSizeHeight );
		
		canvas.drawBitmap(mTiles, src, dest, null);
		
	}
	
	
}
