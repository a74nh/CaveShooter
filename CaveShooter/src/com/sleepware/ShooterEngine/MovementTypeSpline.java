package com.sleepware.ShooterEngine;

import java.util.List;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector3;


public class MovementTypeSpline extends MovementType {

	private CatmullRomSpline mSpline;
	
	private List<Vector3> mSplinePoints;
	
	public MovementTypeSpline(String splineString) {
		
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
    
	
	public Vector3 getPoint(int index) {
		
		if(index>=mSplinePoints.size()) return null;

		return mSplinePoints.get(index);
	}
	
	
    public Enemy spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType entityType) {
    	
    	return new EnemySpline(shooterEngineContext, now, x, y, xAdditionalSpeed, yAdditionalSpeed, entityType, this);
	}

    
	public float getDistanceToFirstPoint(double x, double y) {
		
		Vector3 point = mSplinePoints.get(0);
		
		final float xdist = (float) (point.x - x);
		final float ydist = (float) (point.y - y);
		return (float) Math.sqrt((xdist*xdist)+(ydist*ydist));		
	}
}
