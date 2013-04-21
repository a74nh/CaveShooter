package com.sleepware.ShooterEngine;

public class EnemyAttached extends Enemy {
	
	private int mOffsetx;
	private int mOffsety;
	private int mParentDeathAttack;

	private Entity mParent;
    
    
    public EnemyAttached(ShooterEngineContext shooterEngineContext, long now, int offsetx, int offsety, EntityType type, int parentDeathAttack, Entity parent) {
		
    	super(shooterEngineContext, now, parent.getX()+offsetx, parent.getY()+offsety, type);

    	mOffsetx = offsetx;
    	mOffsety = offsety;
    	mParentDeathAttack = parentDeathAttack;
    	mParent = parent;
	}
 
    public boolean updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
    	mX = mParent.getX()+mOffsetx;
    	mY = mParent.getY()+mOffsety;
	    	
    	return super.updatePhysics(now, elapsed, canvasWidth, canvasHeight);    	
    }

    
    //Returns true if dead
    public boolean doHit(long now, int damage) {
    	
    	if(super.doHit(now, damage)) {
    		
    		//Send damage back up to the parent
        	if(mParent.doHit(now, mParentDeathAttack)) {
        		mShooterEngineContext.mEnemies.removeEnemy((Enemy)mParent);
        	}
        	
        	return true;
    	}
    	
    	return false;
    }
    
}
