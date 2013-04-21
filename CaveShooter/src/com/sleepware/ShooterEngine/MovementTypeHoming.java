package com.sleepware.ShooterEngine;


public class MovementTypeHoming extends MovementType {

    
    public MovementTypeHoming() {
        
    	super(MOVEMENT_HOMING);    
    }
    
    
    public Enemy spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType entityType) {
    	
    	return new EnemyHoming(shooterEngineContext, now, x, y, xAdditionalSpeed, yAdditionalSpeed, entityType, this);
	}

}
