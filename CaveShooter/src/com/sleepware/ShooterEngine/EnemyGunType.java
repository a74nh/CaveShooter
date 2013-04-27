package com.sleepware.ShooterEngine;



public class EnemyGunType {

	private EntityType mEnemyType;

	private int mRate;  //how often it repeats
	
	private int mGunLevel; //for player guns, only one gun of each level will fire
	
	MovementType mMovementType;
	
	public EnemyGunType(EntityType enemyType, int rate, int gunLevel, MovementType movementType) {
				
		mEnemyType =enemyType;
		mRate =rate;
		mGunLevel = gunLevel;
		mMovementType = movementType;
	}
	
	public int getRate() {
		return mRate;
	}
	
	public int getGunLevel() {
		return mGunLevel;
	}
	
    public Enemy fire(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed) {
    	
		return mMovementType.spawn(shooterEngineContext, now, x, y, xAdditionalSpeed, yAdditionalSpeed, mEnemyType);
	}
    
}
