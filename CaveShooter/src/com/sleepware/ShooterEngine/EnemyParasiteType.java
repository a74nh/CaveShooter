package com.sleepware.ShooterEngine;

//TODO: make a ParasiteType as subclass of EnemyGunType

public class EnemyParasiteType {

	ShooterEngineContext mShooterEngineContext;
	
	private EntityType mEnemyType;  //how often it repeats
	private MovementType mMovementType;
	
	public int mParentDeathAttack; //Damage to send to parent when dies.
	
	
	public EnemyParasiteType(ShooterEngineContext shooterEngineContext, EntityType enemyType, MovementType movementType, int parentDeathAttack) {
		
		mShooterEngineContext = shooterEngineContext;
		mEnemyType =enemyType;
		mMovementType = movementType;
		mParentDeathAttack = parentDeathAttack;
	}
	
    public Enemy hatch(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, Entity parent) {
    	
		return mShooterEngineContext.mEnemies.newEnemy(mShooterEngineContext, now, x, y, 0, 0, mEnemyType, mMovementType, parent, mParentDeathAttack);
	}
}
