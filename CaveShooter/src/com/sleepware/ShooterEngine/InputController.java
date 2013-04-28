package com.sleepware.ShooterEngine;

import com.badlogic.gdx.math.Vector2;

import android.view.KeyEvent;
import android.view.MotionEvent;

public class InputController {

	private ShooterEngineContext mShooterEngineContext;

    private boolean mTouched;

    private double mXFinger;
    private double mYFinger;
    
    private double mXPlayerLast;
    private double mYPlayerLast;
    private double mXRelativeOffset;
    private double mYRelativeOffset;
    
    Vector2 mDestination;
    
    private int mMode;
    final static int MODE_ABSOLUTE=1;
    final static int MODE_RELATIVE=2;
    
    private int mOffset;

	InputController(ShooterEngineContext shooterEngineContext) {
		
		mShooterEngineContext =shooterEngineContext;
		mTouched=false;
		
		mDestination = new Vector2();
		
		mMode = MODE_RELATIVE;
		mOffset = 0;
	}
    
	
	void setMode(int mode) {
		mMode=mode;
	}
	
	void setOffset(int offset) {
		mOffset=offset;
		//+ (mType.getImageWidth() *1.5));
	}
	
    /**
     * Handles a key-down event.
     */
    public boolean doKeyDown(int keyCode, KeyEvent msg) {
        return false;
    }

    /**
     * Handles a key-up event.
     */
    public boolean doKeyUp(int keyCode, KeyEvent msg) {
        return false;
    }

    /**
     * Handles a touch event.
     */
    public boolean doTouchEvent (MotionEvent event) {
        
        if (mShooterEngineContext.mStateRunning) {

	    	int count = event.getPointerCount();
	        
		    switch(event.getAction()) {
		    
		    case MotionEvent.ACTION_UP:
		    case MotionEvent.ACTION_CANCEL:
			    mTouched=false;
			    break;
			  
		    case MotionEvent.ACTION_DOWN:
		    	
		    	mXRelativeOffset = mXPlayerLast - event.getX(count-1);
		    	mYRelativeOffset = mYPlayerLast - event.getY(count-1);
		    	
		    	//FALL THROUGH...
		    	
		    default:
		        mXFinger = event.getX(count-1);
		        mYFinger = event.getY(count-1);
		      
		        mTouched = true; 
		    }
		    
		    return true;
        }    
	    return false;
    }
    
    
	Vector2 getFingerDestination(double playerX, double playerY) {
		
		mXPlayerLast = playerX;
		mYPlayerLast = playerY;
		
		if(!mTouched) return null;
		
		switch(mMode) {
		
		case MODE_ABSOLUTE:
			mDestination.x=(float) (mXFinger + mOffset );
			mDestination.y=(float) mYFinger;
			break;
			
		case MODE_RELATIVE:
			mDestination.x=(float) (mXRelativeOffset + mXFinger);
			mDestination.y=(float) (mYRelativeOffset + mYFinger);
			break;
		}
		
		return mDestination;
	}
	
	
	boolean getIsTouched() {
		return mTouched;
	}
	
}
