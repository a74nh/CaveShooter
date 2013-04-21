package com.sleepware.ShooterEngine;

import android.graphics.Canvas;
import com.badlogic.gdx.math.Vector2;


public class HudEntry {
	
	protected ShooterEngineContext mShooterEngineContext;
	protected Vector2 mPosition;
	protected int mScoreSpeed;
    
	public HudEntry(ShooterEngineContext shooterEngineContext, Vector2 position) {
		
		mShooterEngineContext = shooterEngineContext;
		mPosition = position;
	}
	
	public void doDraw(Canvas canvas) {
	}
	
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    }
    
}
