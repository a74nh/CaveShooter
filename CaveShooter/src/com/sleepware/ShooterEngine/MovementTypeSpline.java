package com.sleepware.ShooterEngine;

import java.util.List;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector3;


public class MovementTypeSpline extends MovementType {

	private CatmullRomSpline mSpline;
	
	private List<Vector3> mSplinePoints;
	
	public MovementTypeSpline(String splineString) {
		
    	super(MOVEMENT_SPLINE);

		mSpline = new CatmullRomSpline();
		
		String temp[] = splineString.split(",");
		
		Vector3 extracted = null;
		for(int i=0; i<temp.length; i+=2 ) {
		
			extracted = new Vector3(Integer.parseInt(temp[i]), Integer.parseInt(temp[i+1]), 0);
			mSpline.add(extracted);
		}
		mSpline.add(extracted);

		mSplinePoints = mSpline.getPath(5);
				
		//naughty. use z for the distance between current entry and the next one
		for(int i=0; i<mSplinePoints.size()-1; i++) {
			
			Vector3 point = mSplinePoints.get(i);
			Vector3 nextPoint = mSplinePoints.get(i+1);
			
			final float xdist = nextPoint.x - point.x;
			final float ydist = nextPoint.y - point.y;
			
			point.z = (float) Math.sqrt((xdist*xdist)+(ydist*ydist));
		}
	}
    
	
	private Vector3 getPoint(int index) {
		
		if(index>=mSplinePoints.size()) return null;

		return mSplinePoints.get(index);
	}

    
	private float getDistanceToFirstPoint(double x, double y) {
		
		Vector3 point = mSplinePoints.get(0);
		
		final float xdist = (float) (point.x - x);
		final float ydist = (float) (point.y - y);
		return (float) Math.sqrt((xdist*xdist)+(ydist*ydist));		
	}
	
    //TODO: Add in additional speeds?  (for when being fired from a gun)  (If we want this...)
    //TODO: Add in an additional direction  (for mapping the spline along) (If we want this...)
	
    public void OnEnemyConstruction(Enemy enemy, double xAdditionalSpeed, double yAdditionalSpeed) {
    	            
    	enemy.mSplineIndex=0;
    	enemy.mDestination = getPoint(0);
    	enemy.mDistanceToNext = getDistanceToFirstPoint(enemy.mX, enemy.mY);

        //enemy.fooimage = mShooterEngineContext.mContext.getResources().getDrawable(R.drawable.spot);
    }
 
	    
	 
	public boolean updatePhysics(Enemy enemy, long now, double elapsed, int canvasWidth, int canvasHeight) {
	    	
		if(enemy.mDestination!=null) {

			final double additionalSpeed = ((enemy.mType.getAcceleration() * elapsed)/1000.0);

			enemy.mSpeed +=((additionalSpeed * elapsed)/1000.0);

			final double distanceMoved = ((enemy.mSpeed * elapsed)/1000.0);

			if(distanceMoved>=enemy.mDistanceToNext) {

				enemy.mX += ((enemy.mDestination.x-enemy.mX) / enemy.mDistanceToNext * distanceMoved);
				enemy.mY += ((enemy.mDestination.y-enemy.mY) / enemy.mDistanceToNext * distanceMoved);

				enemy.mDistanceToNext=enemy.mDestination.z;

				enemy.mSplineIndex++;
				enemy.mDestination = getPoint(enemy.mSplineIndex);

			} else {

				enemy.mX += ((enemy.mDestination.x-enemy.mX) / enemy.mDistanceToNext * distanceMoved);
				enemy.mY += ((enemy.mDestination.y-enemy.mY) / enemy.mDistanceToNext * distanceMoved);

				enemy.mDistanceToNext-=distanceMoved;

			}
		}

		return true;
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
	
}
