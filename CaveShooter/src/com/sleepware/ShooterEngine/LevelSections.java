package com.sleepware.ShooterEngine;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.Canvas;


public class LevelSections {

	//Level As a whole

    private int mLevelLength;

	
    private double mXMovement;
    private double mYMovement;
    
    
	//Level Sections
	
    private ArrayList<LevelSection> mSectionList;
    private int mLevelSectionCurrent;
    
    private double mLastTimeJumpedSection;

    
    //Level Layers
    
    private ArrayList<LevelLayer> mLayersList;

    
    //Stuff to move.... ?
    
    private LevelMap mLevelMap;
    
    
    boolean mStarted = false;
    
    //In tiles
    private int mXStartRightWallPos;
    private int mYStartBottomWallPos;
    
    public double mXRightWallPos;
    public double mYBottomWallPos;
    
    private double mXLeftWallPos;
    private double mYTopWallPos;

    

        
    public int mRemaining;
    
	public int mCurrentPositionInLevel;

	
    public LevelSections(ShooterEngineContext shooterEngineContext) {

    	mLevelLength =0;

    	mSectionList = new ArrayList<LevelSection>();
    	mSectionList.clear();
    	mLevelSectionCurrent =0;
    	
    	mLayersList = new ArrayList<LevelLayer>();
    	mLayersList.clear();
    }
    
    
	public void doStart(long now, int canvasWidth, int canvasHeight) {
				
		for(int i=0; i<mLayersList.size(); i++) {
			mLayersList.get(i).doStart(now, canvasWidth, canvasHeight);
		}
		
        mXRightWallPos = mXStartRightWallPos;
        mYBottomWallPos = mYStartBottomWallPos;		
        
        mXLeftWallPos = mXRightWallPos - canvasWidth;
        mYTopWallPos = mYBottomWallPos - canvasHeight;
        
        mLastTimeJumpedSection = now;
        
        //TODO: only needed because doDraw is being called all the damn time...
        mStarted = true;
	}
    

    
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    
    	mXMovement=0;
    	mYMovement=0;
    	
    	LevelSection section = getCurrentLevelSection();

    	double overflow = updatePhysicsSection(section, now, elapsed, canvasWidth, canvasHeight);
    	
    	//It might have changed...
    	section = getCurrentLevelSection();

    	if(overflow>0) {
    		//We flowed into the next section - move that one too
    		updatePhysicsSection(section, now, overflow, canvasWidth, canvasHeight);
    	}
    	
    	
		switch(section.mType) {
    	
    	case LevelSection.SECTION_STILL:    		
    	case LevelSection.SECTION_TIMED_STILL:
    	case LevelSection.SECTION_BOSS_STILL:
    		mCurrentPositionInLevel = (int)(now-mLastTimeJumpedSection);
    		break;
    	
    	case LevelSection.SECTION_SCROLL_X:
    	case LevelSection.SECTION_SCROLL_X_UP:
    	case LevelSection.SECTION_SCROLL_X_DOWN:
    		mCurrentPositionInLevel = (int)mXRightWallPos-section.mStartPosition;
            break;
		}
		
		for(int i=0; i<mLayersList.size(); i++) {
			mLayersList.get(i).updatePhysics(mXMovement, mYMovement, section, canvasWidth, canvasHeight);
		}
	 
		section.spawnEnemies(now, mCurrentPositionInLevel);
    }
    
    
    private double updatePhysicsSection(LevelSection section, long now, double elapsed, int canvasWidth, int canvasHeight) {
    	    	
    	boolean jumpSection=false;
    	double overflowTime = 0;
        double underflowTime =elapsed;

    	//Move Wall
    	
    	switch(section.mType) {
    	
    	case LevelSection.SECTION_STILL:
    		break;
    		
    	case LevelSection.SECTION_TIMED_STILL:
    	    if(now>=mLastTimeJumpedSection+section.mTiming) {
            	jumpSection=true;
            	overflowTime = now - (mLastTimeJumpedSection+section.mTiming);
    	    }
    		break;
    	
    	case LevelSection.SECTION_SCROLL_X:
    	case LevelSection.SECTION_SCROLL_X_UP:
    	case LevelSection.SECTION_SCROLL_X_DOWN:

    		double newXRightWallPos = (mXRightWallPos + ((section.mTiming * elapsed)/1000.0));
            
            if(newXRightWallPos>=section.mEndPosition) {
            	
            	jumpSection=true;
            	
            	overflowTime = (newXRightWallPos - section.mEndPosition) *1000 / section.mTiming;
            	
            	underflowTime -= overflowTime;
            	
            	newXRightWallPos = section.mEndPosition;
            }
            
            mXMovement += newXRightWallPos - mXRightWallPos;
            mXRightWallPos = newXRightWallPos;
            
            double newYMovement =0;
        	switch(section.mType) {
        		case LevelSection.SECTION_SCROLL_X_UP:
        			newYMovement -= ((section.mTiming * underflowTime)/1000.0);
            		break;
        		case LevelSection.SECTION_SCROLL_X_DOWN:
        			newYMovement += ((section.mTiming * underflowTime)/1000.0);
            		break;
        	}
        	mYMovement += newYMovement;
        	mYBottomWallPos += newYMovement;
        	
            break;
            
    	case LevelSection.SECTION_BOSS_STILL:
    		//Could set overflow time here...?
    		if(section.mNumBossesRemaining<=0) jumpSection=true;
    		break;
    	}
    	
    	
    	//Change Section
    	
    	if(jumpSection) {
    		section = nextLevelSection();
	    	mLastTimeJumpedSection=now-overflowTime;
	    }
        
    	
    	//Set top and left
    	
    	mXLeftWallPos = mXRightWallPos-canvasWidth;
    	mYTopWallPos = mYBottomWallPos-canvasHeight;

    	//need this???
    	mRemaining = (int)(section.mEndPosition-mXRightWallPos);
    	    	
    	return overflowTime;
    }
    
	
    public void doDraw(Canvas canvas) {
    	
    	if(!mStarted) return;

		for(int i=0; i<mLayersList.size(); i++) {
			mLayersList.get(i).doDraw(canvas);
		}
    	
    	//From here on in, we're all about the ints...
    	//mLevelMap.doDraw(canvas, (int)mXLeftWallPos, (int)mYTopWallPos);
    }
    
    
	public LevelSection addLevelSectionToEnd (ShooterEngineContext shooterEngineContext, int type, int length, int timing, int numbosses) {
		
        int newLevelLength = mLevelLength + ( length * mLevelMap.getTileSizeWidth() );

		LevelSection wallSec = new LevelSection(shooterEngineContext, mSectionList.size(), mLevelLength, newLevelLength, type, timing, numbosses);
		
		mSectionList.add(wallSec);
		
        mLevelLength =newLevelLength;
        
        return wallSec;
	}
	
	
	public void addLevelLayer (LevelLayer layer) {
		
		mLayersList.add(layer);
	}
	
	
    private boolean doRowBackgroundCollision(int x, int y, int width) {
    	
    	for(int rowX=x; rowX<width+x; rowX++) {
    		
    		if(rowX<0) {
    			y++;
    		}
    		
    		if(mXLeftWallPos<0) {
    			y++;
    		}
    		
    		//TODO: !!!!!!!!!
    		//if(Color.TRANSPARENT!=mWallImage.getPixel((int)(rowX+mXWallCanvasLeftPos), (int)(y+mYWallCanvasTopPos))) return true;
    	}
    	return false;
    }
    
    public boolean doBackgroundCollisions(double x, double y, int width, int height) {
    	
    	if(doRowBackgroundCollision((int)(x - (width / 2)), (int)(y - (height / 2)), width)) return true;
    	
    	return doRowBackgroundCollision((int)(x - (width / 2)), (int)(y + (height / 2)), width);
    }
	
	
	
	private LevelSection nextLevelSection() {
    	if(mLevelSectionCurrent<mSectionList.size()) {
	    	mLevelSectionCurrent++;
    	}
    	return mSectionList.get(mLevelSectionCurrent);
    }
    
    private LevelSection getCurrentLevelSection() {
    	return mSectionList.get(mLevelSectionCurrent);
    }
    
	/*
    public LevelSection getPrevious() {
    	
    	if(mCurrent==0) return null;
    	
    	return mSectionList.get(mCurrent-1);
    }
    
    public LevelSection getNext() {
    	return mSectionList.get(mCurrent);
    }
    */
    
    public LevelMap setLevelMap(String mapDataString, int width, int height, Bitmap tilesImage, int tilewidth, int tileheight, int startxright, int startybottom) {
    	
    	mXStartRightWallPos = startxright * tilewidth;
    	mYStartBottomWallPos = startybottom * tileheight;
    	
    	mLevelLength = mXStartRightWallPos;
    	
    	mLevelMap = new LevelMap(mapDataString, width, height, tilesImage, tilewidth, tileheight);
    	return mLevelMap;
    }
 
    public void bossKilled() {
    	getCurrentLevelSection().mNumBossesRemaining--;
    }

}
