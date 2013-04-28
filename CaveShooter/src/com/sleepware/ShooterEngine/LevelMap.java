package com.sleepware.ShooterEngine;

import android.graphics.Canvas;

public class LevelMap {

	private int mMapData[][];
	
	//In num of tiles
	//private int mMapWidth;
	//private int mMapHeight;
	
	private LevelTiles mTiles;
	
	//Size of each tile
	private int mTileSizeWidth;
	private int mTileSizeHeight;
	
	
	public LevelMap(ShooterEngineContext shooterEngineContext, String mapDataString, int width, int height, int tilesId, int tilewidth, int tileheight) {
		
		//Convert mapData
		
		mMapData = new int[height+3][width+3];
		
		String temp[] = mapDataString.split(",");
		
		int i=0;
		for(int y=1; y<=height; y++ ) {
		
			for(int x=1; x<=width; x++ ) {
				
				setMapValue(x, y, Integer.parseInt(temp[i]));
				i++;
			}
		}
		
		//mMapWidth = width;
		//mMapHeight = height;
		
		mTiles = new LevelTiles (shooterEngineContext, tilesId, tilewidth, tileheight);
		
		mTileSizeWidth = tilewidth;
		mTileSizeHeight = tileheight;
	}
	
	public int getTileSizeWidth() {
		return mTileSizeWidth;
	}
	
	public int getTileSizeHeight() {
		return mTileSizeHeight;
	}
	
	
	private void setMapValue(int x, int y, int value) {
		mMapData[y+1][x+1] = value;
	}
	
	private int getMapValue(int x, int y) {
		return mMapData[y+1][x+1];
	}
	
	
	public void doDraw(Canvas canvas, int xLeftVisible, int yTopVisible) {
		
		final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		
		final int mapStartX = (xLeftVisible / mTileSizeWidth) +1;
		final int firstTileStartOffsetX = xLeftVisible % mTileSizeWidth;
		final int numTilesX = (width / mTileSizeWidth) +2;
		
		final int mapStartY = (yTopVisible / mTileSizeHeight) +1;
		final int firstTileStartOffsetY = yTopVisible % mTileSizeHeight;
		final int numTilesY = (height / mTileSizeHeight) +1;
		
		int ypos = 0 - firstTileStartOffsetY;
		int mapY =mapStartY;

		for (int iy=0; iy<numTilesY ; iy++) {

			int xpos = 0 - firstTileStartOffsetX;
			int mapX =mapStartX;

			for (int ix=0; ix<numTilesX ; ix++) {
				 
				mTiles.doDraw(canvas, getMapValue(mapX, mapY), xpos, ypos);
				
				xpos+=mTileSizeWidth;
				mapX++;
			}
			
			ypos+=mTileSizeHeight;
			mapY++;
		}
		
	}

}
