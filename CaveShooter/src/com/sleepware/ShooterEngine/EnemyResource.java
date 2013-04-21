package com.sleepware.ShooterEngine;

public class EnemyResource {
	
	public EntityType mEnemyType; //which EnemyType

	public int mBegin; //What point in level should start (either X position or ms)
	private int mXStart;
	private int mYStart;
	MovementType mMovementType;

	public boolean mRepeater;
	public int mRepeat;
	public int mRepeatdelay;
	
	public EnemyResource(EntityType enemyType, int begin, int xStart, int yStart, MovementType movementType, int repeat, int repeatdelay) {
		mEnemyType =enemyType;
		mBegin = begin;
		mXStart =xStart;
		mYStart =yStart;
		mMovementType = movementType;
		
		mRepeat = (repeat>0) ? repeat : 1;
		mRepeatdelay = repeatdelay;
	}
	
	
	public Enemy spawn(ShooterEngineContext shooterEngineContext, long now) {
		Enemy e = mMovementType.spawn(shooterEngineContext, now, mXStart, mYStart, 0, 0, mEnemyType);
	
		if(mRepeat>0) {
			mRepeat--;
			mBegin+=mRepeatdelay;
		}
		
		return e;
	}

}