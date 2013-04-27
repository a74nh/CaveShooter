package com.sleepware.ShooterEngine;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.badlogic.gdx.math.Vector2;


public class HudEntryValue extends HudEntry {
	
	public static final int HUD_ENTRY_SCORE = 1;
	public static final int HUD_ENTRY_LEVEL_POS =2;
	public static final int HUD_ENTRY_FPS =3;
	public static final int HUD_ENTRY_LIVES =4;
	public static final int HUD_ENTRY_DEBUG = 5;
	
	private int mType;
	private int mSpeed;
    private Paint mPaint;
    
	private double mDisplayedValue;

    
	public HudEntryValue(ShooterEngineContext shooterEngineContext, Vector2 postion, int type, int speed, String font, int textSize, int color) {

		super(shooterEngineContext, postion);
		
		mType = type;
		mDisplayedValue=0;
		mSpeed = speed;
		
		mPaint = new Paint();
        mPaint.setColor(color);
        mPaint.setTextSize(textSize);
        mPaint.setTypeface(Typeface.createFromAsset(mShooterEngineContext.mContext.getAssets(), font));
	}
	
	
	
	public void doDraw(Canvas canvas) {
		
		String output = null;
		
		switch(mType) {
		
		case HUD_ENTRY_SCORE:
	        output = String.format("%07d", (int)mDisplayedValue );
	        break;

		case HUD_ENTRY_LEVEL_POS:
	        output = String.format("%d", (int)mDisplayedValue );
	        break;
	        
		case HUD_ENTRY_FPS:
	        output = String.format("%2d", (int)mDisplayedValue );
	        break;
	        
		case HUD_ENTRY_LIVES:
	        output = String.format("%1d", (int)mDisplayedValue );
	        break;
	        
		case HUD_ENTRY_DEBUG:
	        output = String.format("%d", (int)mDisplayedValue );
	        break;
		}

		canvas.drawText(output, mPosition.x, mPosition.y, mPaint);
	}
	
	
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
    	double currentValue =0;
    	
		switch(mType) {

		case HUD_ENTRY_SCORE:
			currentValue = mShooterEngineContext.mScore;
	        break;
	
		case HUD_ENTRY_LEVEL_POS:
			currentValue = mShooterEngineContext.mLevelSections.mCurrentPositionInLevel;
	        break;
	        
		case HUD_ENTRY_FPS:
			currentValue = mShooterEngineContext.mFPS;
	        break;
	    
		case HUD_ENTRY_LIVES:
			currentValue = mShooterEngineContext.mLives;
	        break;
	        
		case HUD_ENTRY_DEBUG:
			currentValue = mShooterEngineContext.mDebug1;
	        break;
		}
    			
    	if(mDisplayedValue<currentValue) mDisplayedValue += ((mSpeed * elapsed)/1000.0);

    	if(mSpeed==0 || mDisplayedValue>currentValue) mDisplayedValue=currentValue;
    }
    
}