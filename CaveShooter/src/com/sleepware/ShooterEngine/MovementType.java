package com.sleepware.ShooterEngine;


public class MovementType {

    public static final int MOVEMENT_STRAIGHT = 0;
    public static final int MOVEMENT_HOMING = 1;
    public static final int MOVEMENT_HOMING_MISSILE = 2;
    public static final int MOVEMENT_SPLINE = 3;
   // public static final int MOVEMENT_ATTACHED = 3;

    int mMovementType;

    
    public MovementType () {
	}
	
    public MovementType(int movementType) {
        
    	mMovementType = movementType;
    }
    
    
	/** Spawn Into Enemy **/
	
    public Enemy spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType entityType) {
    	return null;
    }
    
}
