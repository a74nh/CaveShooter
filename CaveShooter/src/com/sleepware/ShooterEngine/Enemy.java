package com.sleepware.ShooterEngine;


public class Enemy extends Entity {
	
	
	
    private long mDeathTime;    //when it should auto die
 

    
    public Enemy(ShooterEngineContext shooterEngineContext, long now, double x, double y, EntityType type) {

    	super(shooterEngineContext, now, x, y, type);

    	switch(type.mType) {
    	
    	case EntityType.TYPE_BULLET:
        case EntityType.TYPE_PLAYER_BULLET:
    		mCollisionType =POINT_COLLISION;
    	
    	//case EntityType.TYPE_ENEMY:
    	//case EntityType.TYPE_EXPLOSION:
    	//case EntityType.TYPE_POWERUP:
    	default:
    		mCollisionType =CIRCLE_COLLISION;
    	}
    	
        mState = STATE_NEW;  
        
        mDeathTime = now + type.getLifetime();

        int enemyTypeBulletListSize =mType.getGunTypeList().size();
        
		mLastGunFiredList = new long[enemyTypeBulletListSize];
    }
    
 
    //Returns false if dead
    public boolean updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
		if(mState==STATE_DEAD) return false;
    	
    	if((mX>canvasWidth || mX<0 || mY>canvasHeight || mY<0) && mState==STATE_ACTIVE) {
    		mState =STATE_OFFSCREEN;
    		return false;
    	}
    	
    	if(now>=mDeathTime) {
    		doExplosions(now);
    		return false;
    	}
    	
    	
    	switch(mState) {
    	
    	case STATE_NEW:
		    if (mType.getDelay() + mSpawnedTime <= now ) {
		    	mState=STATE_ACTIVE;
		    }
		    break;
		
    	case STATE_ACTIVE:
	    	for(int i=0; i<mType.getGunTypeList().size(); i++) {
	    		
	    		EnemyGunType enemyGun = mType.getGunTypeList().get(i);
	    		
			    if (enemyGun.getRate() + mLastGunFiredList[i] <= now ) {
			    	
		            Enemy newBullet = enemyGun.fire(mShooterEngineContext, now, mX, mY, mXSpeed, mYSpeed);
		            mShooterEngineContext.mEnemies.addEnemy(newBullet);
		            mLastGunFiredList[i]=now;
			    }
	    	}
	    	break;
   		}
    	
    	return true;
    }

    
}
