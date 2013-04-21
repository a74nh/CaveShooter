package com.sleepware.ShooterEngine;

public class MovementTypeHomingMissile extends MovementType {

	public int mInitialDirection;
	public int mTurnSpeed;
	
    public MovementTypeHomingMissile(int direction, int turnspeed) {
        
    	super(MOVEMENT_HOMING_MISSILE);
    	mInitialDirection=direction;
    	mTurnSpeed=turnspeed;
    }
    
    
    public Enemy spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType entityType) {
    	
    	return new EnemyHomingMissile(shooterEngineContext, now, x, y, xAdditionalSpeed, yAdditionalSpeed, entityType, this);
	}

}
