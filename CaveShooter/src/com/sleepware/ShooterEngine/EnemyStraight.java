package com.sleepware.ShooterEngine;


public class EnemyStraight extends Enemy {
	
	MovementTypeStraight mMovementType;
	
    
    public EnemyStraight(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType type, MovementTypeStraight movementType) {
        
    	super(shooterEngineContext, now, x, y, type);
    	
    	mMovementType = movementType;
    	
        //sort out the initial speed
        mXSpeed = ((double)type.getInitialSpeed() * mMovementType.mCosAccelDirectionRadians) + xAdditionalSpeed;
        mYSpeed = ((double)type.getInitialSpeed() * mMovementType.mSinAccelDirectionRadians) + yAdditionalSpeed;
    }
    
 
    public boolean updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
    	final double additionalSpeed = ((mType.getAcceleration() * elapsed)/1000.0);
    	final double additionalGravitySpeed = ((mMovementType.mGravity * elapsed)/1000.0);

    	mXSpeed += (additionalSpeed * mMovementType.mCosAccelDirectionRadians) + (additionalGravitySpeed * mMovementType.mGravityCosAccelDirectionRadians);
    	mYSpeed += (additionalSpeed * mMovementType.mSinAccelDirectionRadians) + (additionalGravitySpeed * mMovementType.mGravitySinAccelDirectionRadians);
    	
    	mX += ((mXSpeed * elapsed)/1000.0);
    	mY += ((mYSpeed * elapsed)/1000.0);
	    	
    	return super.updatePhysics(now, elapsed, canvasWidth, canvasHeight);    	
    }

}
