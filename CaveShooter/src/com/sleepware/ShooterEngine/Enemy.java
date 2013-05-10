package com.sleepware.ShooterEngine;

import com.badlogic.gdx.math.Vector3;



public class Enemy extends Entity {
	
	MovementType mMovementType;
	double mSpeed;
	
    private long mDeathTime;    //when it should auto die
	    
    //For MovementTypeHomingMissle
	double vx;
	double vy;
	double mDirectionRadians;
	double mDirectionDegrees;
    
    //For MovementTypeSpline	 
    int mSplineIndex;
    Vector3 mDestination; //Where the next point is
    float mDistanceToNext; //Distance to the next point
    //Drawable fooimage; //for drawing spots of the spline

 
    
    public void init(ShooterEngineContext shooterEngineContext, int id,
    		         long now, double x, double y, 
    		         double xAdditionalSpeed, double yAdditionalSpeed, 
    		         EntityType type, MovementType movementType, Entity parent,
    		         int parentDeathAttack) {

    	super.init(shooterEngineContext, id, now, x, y, type, parent, parentDeathAttack);

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
    	
    	mMovementType = movementType;
        mSpeed = (double)type.getInitialSpeed();

        mState = STATE_NEW;  
        
        mDeathTime = now + type.getLifetime();

        int enemyTypeBulletListSize =mType.getGunTypeList().size();
        
		mLastGunFiredList = new long[enemyTypeBulletListSize];
		
		mMovementType.OnEnemyConstruction(this, xAdditionalSpeed, yAdditionalSpeed);
    	
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
    	
        if(!mMovementType.updatePhysics(this, now, elapsed, canvasWidth, canvasHeight)) {
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
			    	
		            enemyGun.fire(mShooterEngineContext, mShooterEngineContext.mEnemies, now, mX, mY, mXSpeed, mYSpeed, this);
		            mLastGunFiredList[i]=now;
			    }
	    	}
	    	break;
   		}
    	
    	return true;
    }

}
