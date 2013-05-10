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
	
    public void fire(ShooterEngineContext shooterEngineContext, Enemies enemies, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, Entity parent) {
    	
    	enemies.newEnemy(shooterEngineContext, now, x, y, 0, 0, mEnemyType, mMovementType, parent, 0);
	}
    
}
