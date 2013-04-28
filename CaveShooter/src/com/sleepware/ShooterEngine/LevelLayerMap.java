package com.sleepware.ShooterEngine;

import android.graphics.Canvas;

public class LevelLayerMap extends LevelLayer {
	
	private int mMapData[][];
	
	//In num of tiles
	//private int mMapWidth;
	//private int mMapHeight;
	
	private LevelTiles mTiles;
	
	//Size of each tile
	private int mTileSizeWidth;
	private int mTileSizeHeight;

	
	public LevelLayerMap(ShooterEngineContext shooterEngineContext, double relativeSpeed, int start, int finish, String mapDataString, int width, int height, int tilesId, int tilewidth, int tileheight)
	{
		super(shooterEngineContext, relativeSpeed, start, finish);
		
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
	
	
	//public void doStart(long now, int canvasWidth, int canvasHeight) {

	
	//returns true if not active
    //public boolean updatePhysics(double xMovement, double yMovement, LevelSection levelSection, int canvasWidth, int canvasHeight ) {
   
    
    public void doDraw(Canvas canvas) {
    	
    	if (isNotActive()) return;

    	final int width = canvas.getWidth();
		final int height = canvas.getHeight();
		
		final int mapStartX = (int) ((mLeftPos / mTileSizeWidth) +1);
		final int firstTileStartOffsetX = (int) (mLeftPos % mTileSizeWidth);
		final int numTilesX = (width / mTileSizeWidth) +2;
		
		final int mapStartY = (int) ((mYPosition / mTileSizeHeight) +1);
		final int firstTileStartOffsetY = (int) (mYPosition % mTileSizeHeight);
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
