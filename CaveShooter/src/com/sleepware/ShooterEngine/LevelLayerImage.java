package com.sleepware.ShooterEngine;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class LevelLayerImage extends LevelLayer {
	
    private Bitmap mImage;   //TODO: OUCH! into resources please!!
    
    
    class VisableImage {
    	public boolean mVisable;
    	public double mXImagePos;
    	public double mYImagePos;
    	public boolean mUpdated;
    	
    	//TODO: don't think the logic quite works here if level is jittering...
    	public final static int SPAWNED_NONE=0;
    	public final static int SPAWNED_POSITIVE=1;
    	public final static int SPAWNED_NEGATIVE=2;
    	public int mXLastSpawned;
    	public int mYLastSpawned;
    	    	
    	VisableImage() {
        	mVisable =false;
        	mXImagePos =0;
        	mYImagePos =0;
        	mUpdated = false;
        	mXLastSpawned= SPAWNED_NONE;
        	mYLastSpawned= SPAWNED_NONE;
    	}
    }
    
    private ArrayList<VisableImage> mVisableImages;
    
    
	public LevelLayerImage(ShooterEngineContext shooterEngineContext, double relativeSpeed, int start, int finish, Bitmap image)
	{
		super(shooterEngineContext, relativeSpeed, start, finish);
		mImage =image;
	}

	
	public void doStart(long now, int canvasWidth, int canvasHeight) {
		
		super.doStart(now, canvasWidth, canvasHeight);
		
		//TODO: Here we are assuming size of image > canvas in X and Y.
		//      IF it was smaller, array would need to be bigger
		
		mVisableImages = new ArrayList<VisableImage>(4);
		for(int i=0; i<4; i++) {
			mVisableImages.add(new VisableImage());
		}
		mVisableImages.get(0).mVisable=true;
		mVisableImages.get(0).mXImagePos=canvasWidth-mXPosition;
		mVisableImages.get(0).mYImagePos=canvasHeight-mYPosition;
	}

	
	private void addNewVisibleImage(double x, double y) {
		for(int i=0; i<mVisableImages.size(); i++) {
    		VisableImage visableImage = mVisableImages.get(i);
    		
    		if(!visableImage.mVisable) {
    			visableImage.mVisable=true;
    			visableImage.mXImagePos=x;
    			visableImage.mYImagePos=y;
    			visableImage.mUpdated=true;
    			visableImage.mXLastSpawned=VisableImage.SPAWNED_NONE;
    			visableImage.mYLastSpawned=VisableImage.SPAWNED_NONE;
    			return;
    		}
		}
		//TODO!! If get here .. then erk!!!
	}
	
	
	//returns true if not active
    public boolean updatePhysics(double xMovement, double yMovement, LevelSection levelSection, int canvasWidth, int canvasHeight ) {
    	
    	if (super.updatePhysics(xMovement, yMovement, levelSection, canvasWidth, canvasHeight)) return true;
    	
    	
    	for(int i=0; i<mVisableImages.size(); i++) {
    		
    		VisableImage visableImage = mVisableImages.get(i);
    		if(visableImage.mVisable && !visableImage.mUpdated) {
    			
    			visableImage.mXImagePos -= (xMovement*mRelativeSpeed);
    			visableImage.mYImagePos -= (yMovement*mRelativeSpeed);
    			
    			
    			if( xMovement>0 &&
    				visableImage.mXLastSpawned!=VisableImage.SPAWNED_POSITIVE &&
    			    mImage.getWidth() < (Math.abs(visableImage.mXImagePos) + canvasWidth) ) {
    				
    				addNewVisibleImage(visableImage.mXImagePos+mImage.getWidth(), visableImage.mYImagePos);
    				visableImage.mXLastSpawned=VisableImage.SPAWNED_POSITIVE;
    			}
    			else if( xMovement<0 &&
        			     visableImage.mXLastSpawned!=VisableImage.SPAWNED_NEGATIVE &&
        			     visableImage.mXImagePos>0 ) {
        				
    				addNewVisibleImage(visableImage.mXImagePos-mImage.getWidth(), visableImage.mYImagePos);
        			visableImage.mXLastSpawned=VisableImage.SPAWNED_NEGATIVE;

        		}
    			else if(Math.abs(visableImage.mXImagePos)>mImage.getWidth()) {
    				visableImage.mVisable=false;
    			}
    			
    			
    			if( yMovement>0 &&
        				visableImage.mYLastSpawned!=VisableImage.SPAWNED_POSITIVE &&
        			    mImage.getHeight() < (Math.abs(visableImage.mYImagePos) + canvasHeight) ) {
        				
        			addNewVisibleImage(visableImage.mXImagePos, visableImage.mYImagePos+mImage.getHeight());
        			visableImage.mYLastSpawned=VisableImage.SPAWNED_POSITIVE;
        		}
        		else if( yMovement<0 &&
            			 visableImage.mYLastSpawned!=VisableImage.SPAWNED_NEGATIVE &&
            		     visableImage.mYImagePos>0 ) {
            				
        			addNewVisibleImage(visableImage.mXImagePos, visableImage.mYImagePos-mImage.getHeight());
            		visableImage.mYLastSpawned=VisableImage.SPAWNED_NEGATIVE;

            	}
        		else if(Math.abs(visableImage.mYImagePos)>mImage.getHeight()) {
    				visableImage.mVisable=false;
    			}
    			
    			
    		}
    		visableImage.mUpdated=true;
    	}
    	
    	for(int i=0; i<mVisableImages.size(); i++) {
    		mVisableImages.get(i).mUpdated=false;
    	}
    	
    	return false;
    }
    
    
    public void doDraw(Canvas canvas) {
    	
    	if (isNotActive()) return;

    	for(int i=0; i<mVisableImages.size(); i++) {
    		
    		VisableImage visableImage = mVisableImages.get(i);
    		
    		if(visableImage.mVisable && !visableImage.mUpdated) {
            	canvas.drawBitmap(mImage, (float)visableImage.mXImagePos, (float)visableImage.mYImagePos, null);
    		}
    			
    	}
    }

}
