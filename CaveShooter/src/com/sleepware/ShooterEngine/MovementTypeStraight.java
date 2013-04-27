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
    
    
    public Enemy spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType entityType) {
    	
    	return new EnemyStraight(shooterEngineContext, now, x, y, xAdditionalSpeed, yAdditionalSpeed, entityType, this);
	}

}
