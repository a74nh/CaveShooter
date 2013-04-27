package com.sleepware.ShooterEngine;


import android.graphics.Canvas;


public class EnemyBlank extends Enemy {
	    	
	    public EnemyBlank() {
	    	
	    	super(null, 0, 0, 0, new EntityType("blank", EntityType.TYPE_BLANK, null, 0, 0, 0, EntityType.INVINCIBLE, EntityType.FOREVER, 0, 0, false));
	        mState = STATE_DEAD;  
			mCollisionType =NO_COLLISION;
	    }

	    public void doDraw(Canvas canvas) {
	    	return;
	    }
	    
	    protected void doExplosions(long now) {
	    	return;
	    }
	    
	    public boolean doCollision(Entity opposingEnemy) {
			return false;
		}
	    
	    public boolean doHit(long now, int mParentDeathAttack) {
			return false;
		}
}
