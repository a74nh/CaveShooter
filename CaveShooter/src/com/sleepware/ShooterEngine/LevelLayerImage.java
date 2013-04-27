package com.sleepware.ShooterEngine;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class LevelLayerImage extends LevelLayer {
	
    private Bitmap mImage;   //TODO: OUCH! into resources please!!
    
	public LevelLayerImage(ShooterEngineContext shooterEngineContext, double relativeSpeed, int start, int finish, Bitmap image)
	{
		super(shooterEngineContext, relativeSpeed, start, finish);
		mImage =image;
	}

	
	public void doStart(long now, int canvasWidth, int canvasHeight) {
		
		super.doStart(now, canvasWidth, canvasHeight);
	}

    
    public void doDraw(Canvas canvas) {
    	
    	if (isNotActive()) return;

    	int xOffset = ((int)mXPosition) % mImage.getWidth();
    	int yOffset = Math.abs((int)mYPosition) % mImage.getHeight();
    	
    	
    	//mShooterEngineContext.mDebug1 =  yOffset;
    	    	
    	//&& ypos>=canvas.getHeight()-((int)mYPosition)
    	
        for (int ypos=canvas.getHeight()-yOffset; ypos>-mImage.getHeight(); ypos-=mImage.getHeight()) {

    		for (int xpos=canvas.getWidth()-xOffset; xpos>-mImage.getWidth() && xpos>=canvas.getWidth()-((int)mXPosition); xpos-=mImage.getWidth()) {
    		
        	    canvas.drawBitmap(mImage, (float)xpos, (float)ypos, null);
    		}
    	}
    }

}
