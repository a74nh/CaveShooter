package com.sleepware.ShooterEngine;



public class MovementTypeStraight extends MovementType {

	public double mAccelDirectionRadians; //angle (recalculated into radians)
	public double mCosAccelDirectionRadians;
	public double mSinAccelDirectionRadians;
    
    //The force of gravity
    public int mGravity;
    public double mGravityAccelDirectionRadians; //angle (recalculated into radians)
    public double mGravityCosAccelDirectionRadians;
    public double mGravitySinAccelDirectionRadians;
    
    public MovementTypeStraight(int accelDirection, int gravity, int gravityDirection) {
        
    	super(MOVEMENT_STRAIGHT);
    	
        mAccelDirectionRadians = Math.toRadians(accelDirection);
        mCosAccelDirectionRadians = Math.cos(mAccelDirectionRadians);
        mSinAccelDirectionRadians = Math.sin(mAccelDirectionRadians);
        
        mGravity = gravity;
        mGravityAccelDirectionRadians = Math.toRadians(gravityDirection);
        mGravityCosAccelDirectionRadians = Math.cos(mGravityAccelDirectionRadians);
        mGravitySinAccelDirectionRadians = Math.sin(mGravityAccelDirectionRadians);
    }
    
    public void OnEnemyConstruction(Enemy enemy, double xAdditionalSpeed, double yAdditionalSpeed) {
            	    	
        //sort out the initial speed
    	enemy.mXSpeed = ((double)enemy.mType.getInitialSpeed() * mCosAccelDirectionRadians) + xAdditionalSpeed;
    	enemy.mYSpeed = ((double)enemy.mType.getInitialSpeed() * mSinAccelDirectionRadians) + yAdditionalSpeed;
    }
    
 
    public boolean updatePhysics(Enemy enemy, long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
    	final double additionalSpeed = ((enemy.mType.getAcceleration() * elapsed)/1000.0);
    	final double additionalGravitySpeed = ((mGravity * elapsed)/1000.0);

    	enemy.mXSpeed += (additionalSpeed * mCosAccelDirectionRadians) + (additionalGravitySpeed * mGravityCosAccelDirectionRadians);
    	enemy.mYSpeed += (additionalSpeed * mSinAccelDirectionRadians) + (additionalGravitySpeed * mGravitySinAccelDirectionRadians);
    	
    	enemy.mX += ((enemy.mXSpeed * elapsed)/1000.0);
    	enemy.mY += ((enemy.mYSpeed * elapsed)/1000.0);
	    	
    	return true;  	
    }

}
