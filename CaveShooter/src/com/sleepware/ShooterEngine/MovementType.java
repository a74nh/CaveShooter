package com.sleepware.ShooterEngine;



public class MovementType {

    public static final int MOVEMENT_STRAIGHT = 0;
    public static final int MOVEMENT_HOMING = 1;
    public static final int MOVEMENT_HOMING_MISSILE = 2;
    public static final int MOVEMENT_SPLINE = 3;
    public static final int MOVEMENT_ATTACHED = 4;

    public int mMovementType;
    

    public MovementType(int movementType) {
        
    	mMovementType = movementType;
    }
    
    public void OnEnemyConstruction(Enemy enemy, double xAdditionalSpeed, double yAdditionalSpeed) {
    	assert(false);
    }
    
    public boolean updatePhysics(Enemy enemy, long now, double elapsed, int canvasWidth, int canvasHeight) {
    	return false;
    }
    

}
