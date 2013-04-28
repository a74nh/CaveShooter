package com.sleepware.ShooterEngine;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Paint.Style;

public class LevelLayerShapeMap extends LevelLayerImage {
	
    private ArrayList<Vector2> mTopCoords;
    private ArrayList<Vector2> mBottomCoords;
    
	private int mTopStartCoord;
	private int mTopEndCoord;
	private int mBottomStartCoord;
	private int mBottomEndCoord;
	
	private Path mPath;
	private Paint mPaint;

	public LevelLayerShapeMap(ShooterEngineContext shooterEngineContext, double relativeSpeed, int start, int finish, int imageid, String topCoords, String bottomCoords)
	{
		super(shooterEngineContext, relativeSpeed, start, finish, imageid);
		
		mTopCoords = splitString(topCoords);
		mBottomCoords = splitString(bottomCoords);
		
    	mPath = new Path();
    	
    	mPaint = new Paint();
    	mPaint.setStyle(Style.STROKE);
    	mPaint.setColor(Color.GRAY);
    	mPaint.setStrokeWidth(5);
    	mPaint.setAntiAlias(true);
	}	
		
	private ArrayList<Vector2> splitString(String coordString) {
	
		String temp[] = coordString.split(",");
		ArrayList<Vector2> retArray  = new ArrayList<Vector2>((temp.length+1)/2);

		for(int i=0; i<temp.length; i=i+2 ) {
			Vector2 vector = new Vector2(Integer.parseInt(temp[i]), Integer.parseInt(temp[i+1]));
			retArray.add(vector);
		}
		return retArray;
	}

	
	public void doStart(long now, int canvasWidth, int canvasHeight) {
		super.doStart(now, canvasWidth, canvasHeight);
		
		mTopStartCoord=0;
		mBottomStartCoord=0;
		
		mTopEndCoord=-1;
		for(int i=0; i<mTopCoords.size(); i++) {
			if(mTopCoords.get(i).x<mLeftPos) mTopStartCoord=i;
			if(mTopCoords.get(i).x>mXPosition && mTopEndCoord==-1) mTopEndCoord=i;
		}
		
		mBottomEndCoord=-1;
		for(int i=0; i<mBottomCoords.size(); i++) {
			if(mBottomCoords.get(i).x<mLeftPos) mBottomStartCoord=i;
			if(mBottomCoords.get(i).x>mXPosition && mBottomEndCoord==-1) mBottomEndCoord=i;
		}
		
	}

	
	//returns true if not active
    public boolean updatePhysics(double xMovement, double yMovement, LevelSection levelSection, int canvasWidth, int canvasHeight ) {
    	
    	if (super.updatePhysics(xMovement, yMovement, levelSection, canvasWidth, canvasHeight)) return true;
    	
    	while(mTopStartCoord+1<mTopCoords.size() && mTopCoords.get(mTopStartCoord+1).x<mLeftPos) mTopStartCoord++;

    	while(mTopEndCoord+1<mTopCoords.size() && mTopCoords.get(mTopEndCoord).x<mXPosition) mTopEndCoord++;

    	while(mBottomStartCoord+1<mBottomCoords.size() && mBottomCoords.get(mBottomStartCoord+1).x<mLeftPos) mBottomStartCoord++;

    	while(mBottomEndCoord+1<mBottomCoords.size() && mBottomCoords.get(mBottomEndCoord).x<mXPosition) mBottomEndCoord++;

    	return false;
    }
    	
    
    public void doDraw(Canvas canvas) {
    	
    	if (isNotActive()) return;

    	mPath.reset();
    	    	
    	//draw the top line

    	mPath.moveTo((float)(mTopCoords.get(mTopStartCoord).x-mLeftPos), -10);
    	
    	for(int i=mTopStartCoord; i<=mTopEndCoord; i++) {
        	mPath.lineTo((float)(mTopCoords.get(i).x-mLeftPos), (float)((mTopCoords.get(i).y)-mTopPos));
    	}
    	
    	mPath.lineTo((float)(mTopCoords.get(mTopEndCoord).x-mLeftPos), -10);	
    	mPath.close();

    	
    	//draw the bottom line

    	mPath.moveTo((float)(mBottomCoords.get(mBottomStartCoord).x-mLeftPos), canvas.getHeight()+10);
    	
    	for(int i=mBottomStartCoord; i<=mBottomEndCoord; i++) {
        	mPath.lineTo((float)(mBottomCoords.get(i).x-mLeftPos), (float)((mBottomCoords.get(i).y)-mTopPos));
    	}
    	
    	mPath.lineTo((float)(mBottomCoords.get(mBottomEndCoord).x-mLeftPos), canvas.getHeight()+10);	
    	mPath.close();
    	
    	
    	canvas.clipPath(mPath, Region.Op.REPLACE);

    	super.doDraw(canvas);
    	
    	canvas.clipRect(0, 0, canvas.getWidth(), canvas.getHeight(), Region.Op.REPLACE);
    	
    	canvas.drawPath(mPath, mPaint);
    }

}
