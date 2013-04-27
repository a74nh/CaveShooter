package com.sleepware.ShooterEngine;


public class EnemyParasiteType {

	ShooterEngineContext mShooterEngineContext;
	
	private EntityType mEnemyType;  //how often it repeats

	private int mOffsetx;
	private int mOffsety;
	private int mParentDeathAttack;
	
	public EnemyParasiteType(ShooterEngineContext shooterEngineContext, EntityType enemyType, int offsetx, int offsety, int parentDeathAttack) {
		
		mShooterEngineContext = shooterEngineContext;
		mEnemyType =enemyType;
		mOffsetx =offsetx;
		mOffsety =offsety;
		mParentDeathAttack =parentDeathAttack;
	}

	
	public int getOffsetx() {
		return mOffsetx;
	}
	
	public int getOffsety() {
		return mOffsety;
	}
	
	public int getParentDeathAttack() {
		return mParentDeathAttack;
	}
	
    public Enemy hatch(long now, Entity parent) {
    	
		return new EnemyAttached(mShooterEngineContext, now, mOffsetx, mOffsety, mEnemyType, mParentDeathAttack, parent);
	}
}
