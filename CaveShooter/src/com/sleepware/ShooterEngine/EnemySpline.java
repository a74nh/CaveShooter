package com.sleepware.ShooterEngine;

//import java.util.List;

//import android.graphics.Canvas;

import com.badlogic.gdx.math.Vector3;

public class EnemySpline extends Enemy{
	
	MovementTypeSpline mMovementType;

	double mSpeed;
	 
    int mSplineIndex;
    Vector3 mDestination; //Where the next point is
    float mDistanceToNext; //Distance to the next point
    
    //Drawable fooimage;

    
    //TODO: Add in additional speeds?  (for when being fired from a gun)  (If we want this...)
    //TODO: Add in an additional direction  (for mapping the spline along) (If we want this...)
     
    public EnemySpline(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType type, MovementTypeSpline movementType) {
        
    	super(shooterEngineContext, now, x, y, type);
    	mMovementType = movementType;
    	
        //sort out the initial speed
        mSpeed = (double)type.getInitialSpeed();
        
        mSplineIndex=0;
		mDestination = mMovementType.getPoint(mSplineIndex);
        mDistanceToNext = mMovementType.getDistanceToFirstPoint(x, y);

		//fooimage = mShooterEngineContext.mContext.getResources().getDrawable(R.drawable.spot);
    }
    
    /*
    public void doDraw(Canvas canvas) {

		List<Vector3> list = mSpline.getPath();

		for(int i = mSpline.mCurrentPoint; i < list.size(); i++) {
			Vector3 point = list.get(i);
			fooDraw(canvas, mType.getImage(), (int)point.x, (int)point.y );
		}
    	
		internalDoDraw(canvas, mType.getImage());
    }
    
    
    //grumble grumble hate hate
    protected void fooDraw(Canvas canvas, Drawable image, int x, int y) {

    	int imageWidth = fooimage.getIntrinsicHeight();
    	int imageHeight = fooimage.getIntrinsicHeight();
    	
        int xLeft = (int) x - (imageWidth / 2);
    	int yTop = (int) y - (imageHeight / 2);

    	fooimage.setBounds(xLeft, yTop, xLeft + imageWidth, yTop + imageHeight);
    	fooimage.draw(canvas);
    }
    */
 
    public boolean updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
		if(mDestination!=null) {

	    	final double additionalSpeed = ((mType.getAcceleration() * elapsed)/1000.0);
	
			mSpeed +=((additionalSpeed * elapsed)/1000.0);
			
			final double distanceMoved = ((mSpeed * elapsed)/1000.0);
	
			if(distanceMoved>=mDistanceToNext) {
				
				mX += ((mDestination.x-mX) / mDistanceToNext * distanceMoved);
				mY += ((mDestination.y-mY) / mDistanceToNext * distanceMoved);
				
				mDistanceToNext=mDestination.z;
	
				mSplineIndex++;
				mDestination = mMovementType.getPoint(mSplineIndex);
			
			} else {
				
				mX += ((mDestination.x-mX) / mDistanceToNext * distanceMoved);
				mY += ((mDestination.y-mY) / mDistanceToNext * distanceMoved);
				
				mDistanceToNext-=distanceMoved;
	
			}
		}
	    
		return super.updatePhysics(now, elapsed, canvasWidth, canvasHeight);    	
    }

}
