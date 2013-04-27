package com.sleepware.ShooterEngine;

import android.graphics.Canvas;

public class LevelLayer {
	
	protected ShooterEngineContext mShooterEngineContext;
	
	protected double mRelativeSpeed;
	private int mStart;
	private int mFinish;
	
	protected double mXPosition=0; //Progress through level layer. Equal to right side of screen
	protected double mYPosition=0; //Progress through level layer. Equal to bottom of screen

	protected double mLeftPos;
	protected double mTopPos;

	public final static int SECTION_START_LEFT = -1;
	public final static int SECTION_FOREVER = 666666;
	
	private boolean mStarted;
	private boolean mFinished;
	
	
	public LevelLayer(ShooterEngineContext shooterEngineContext, double relativeSpeed, int start, int finish)
	{
		mShooterEngineContext = shooterEngineContext;
		mRelativeSpeed =relativeSpeed;
		mStart =start;
		mFinish =finish;
		mStarted = false;
		mFinished = false;
	}

	
	public void doStart(long now, int canvasWidth, int canvasHeight) {		
		
		mXPosition=0;
		mYPosition=canvasHeight; //TODO: Do we need a SECTION_START_BOTTOM and SECTION_START_LEFT_AND_BOTTOM ?
		
		if(SECTION_START_LEFT==mStart) {
			mXPosition=canvasWidth;
		}
		
		mLeftPos = mXPosition-canvasWidth;
		mTopPos = mYPosition-canvasHeight;
	}

	//returns true if not active
    public boolean updatePhysics(double xMovement, double yMovement, LevelSection levelSection, int canvasWidth, int canvasHeight ) {
    	
    	if(mFinished) return true;
    	
    	if(!mStarted && levelSection.mId>=mStart) {
    		mStarted=true;
    	}
    	
    	if (mStarted && levelSection.mId>=mFinish) {
    		mFinished=true;
    		return true;
    	}
    	
    	if(mStarted) {
    		mXPosition+=xMovement;
    		mYPosition+=yMovement;
    		mLeftPos = mXPosition-canvasWidth;
    		mTopPos = mYPosition-canvasHeight;
    	}
    	
    	return !mStarted;
    }
    
    public void doDraw(Canvas canvas) {
    	
    }

	//returns true if not active
    protected boolean isNotActive() {
    	return (!mStarted || mFinished);
    }
}
