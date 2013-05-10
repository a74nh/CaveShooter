package com.sleepware.ShooterEngine;

import com.badlogic.gdx.math.Vector2;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;


public class Player extends Entity {
	
     private double mStateTimeStart;
     
     private final int mInvincibleTime = 3000;
          
     private double secretXSpeed;
     private double secretYSpeed;
     
     protected boolean[] mGunActiveList;

     
     
	 public Player(ShooterEngineContext shooterEngineContext, long now, double x, double y, PlayerType playerType) {

		 super();
		 init(shooterEngineContext, 1, now, x, y, playerType, null, 0);
		    
	     mState = STATE_INVINCIBLE;
	     mStateTimeStart =now;
	     
	     //Bullets wil be launched using this! but only cos of hardcode in here!!!!!!!
	     mXSpeed = 0;
	     mYSpeed = 0;
	     
	     secretXSpeed = mType.getInitialSpeed();
	     secretYSpeed = mType.getInitialSpeed();
	 
	     mGunActiveList = new boolean[mNumGuns];
		 mGunActiveList[0]=true;
	 }
	 
	 public double getX() {
		 return mX;
	 }
	 
	 public double getY() {
		 return mY;
	 }
	 
	 public void doDraw(Canvas canvas) {
		 
		 Drawable image;
         
         switch(mState) {
         case STATE_INVINCIBLE:
        	 image=((PlayerType)mType).getImageInvincible();
        	 break;
         default:
        	 image=mType.getImage();
         }
         
     	 internalDoDraw(canvas, image);
	 }
	 
	 public void updatePhysics(long now, double elapsed) {
         
		 Vector2 destination = mShooterEngineContext.mInputController.getFingerDestination(mX,mY);
		 
		 if(destination!=null) {

			 secretXSpeed += ((mType.getAcceleration() * elapsed)/1000.0);
			 secretYSpeed += ((mType.getAcceleration() * elapsed)/1000.0);

	     	 if ((int)destination.x>(int)mX) {
	     		 
	     		 mX += ((secretXSpeed * elapsed)/1000.0);
	     		 if (mX>destination.x) mX=destination.x;
	     	 }
	         else if ((int)destination.x<(int)mX) {
	     	     mX -= ((secretXSpeed * elapsed)/1000.0);
	 		     if (mX<destination.x) mX=destination.x;
	         }
	         
	         if ((int)destination.y>(int)mY) {
	     		 mY += ((secretYSpeed * elapsed)/1000.0);
			     if (mY>destination.y) mY=destination.y;
	         }
	         else if ((int)destination.y<(int)mY) {
	             mY -= ((secretYSpeed * elapsed)/1000.0);
			     if (mY<destination.y) mY=destination.y;
	         }
		 }
		 else {
		     secretXSpeed = mType.getInitialSpeed();
		     secretYSpeed = mType.getInitialSpeed();
		 }
			 
         
         switch(mState) {
      	
     	 case STATE_NEW:
 		    if (mType.getDelay() + mSpawnedTime <= now ) {
 		    	mState=STATE_ACTIVE;
 		    }
 		    break;
 		
     	 case STATE_ACTIVE:
     		 shoot(now);
     		 break;
 	    	
     	 case STATE_INVINCIBLE:
			 if (now>=mStateTimeStart+mInvincibleTime) {
				 mStateTimeStart=now;
				 mState=STATE_ACTIVE;
			 }
	         shoot(now);
             break;
    	 }
         
         
     }
	 
	 private void shoot(long now) {
    	for(int i=0; i<mType.getGunTypeList().size(); i++) {
    		
    		if(mGunActiveList[i]) {
	    		EnemyGunType playerGun = mType.getGunTypeList().get(i);
	    		
			    if (playerGun.getRate() + mLastGunFiredList[i] <= now ) {
			    	
		            playerGun.fire(mShooterEngineContext, mShooterEngineContext.mBullets, now, mX, mY, mXSpeed, mYSpeed, this);
		            mLastGunFiredList[i]=now;
			    }
    		}
    	}
	 }
	 
	 //Returns true if the player is dead
	 public boolean doCollisions(long now) {
    	
    	//Check every enemy item against player
    	
		return mShooterEngineContext.mEnemies.doCollision(now, this);
 
		 /*
		 
		for ( Iterator<Enemy> iter = Global.mEnemies.iterator(); iter.hasNext(); ) {
		 
			Enemy enemy = iter.next();
    	
    		switch(enemy.getType().mType) {
    		
    		//case EntityType.TYPE_EXPLOSION - can't collide with these
    		//case EntityType.TYPE_PLAYER_BULLET - shouldn't be in this list
    		//case EntityType.TYPE_PLAYER - shouldn't be in this list
    		//case EntityType.TYPE_BLANK - is completely ignored
    		
			case EntityType.TYPE_BULLET:
			case EntityType.TYPE_POWERUP:
			case EntityType.TYPE_ENEMY:
    		
	    		if(enemy.doCollision(this)) {
	    			
	    			if(enemy.doHit(now, mType.getAttackDamage())) {
	    				iter.remove();
	    			}
	    			
	    			EnemyType etype = (EnemyType)enemy.getType();
	    				    			
	    			if(etype.hasBonusGun()) {
	    				doCollectPowerUp(now, etype.getBonusGunId());
	    			}
	    			
	    			if(doHit(now, etype.getAttackDamage())) {
	    					return true;
	    			}
	    		}
    		}
    		
    	}//for
	    
	    //Player is not dead! hurrah!
	    return false;*/
	 }
	 
	 
	 public boolean doBackgroundCollisions(long now) {
		 
		 if(mShooterEngineContext.mLevelSections.doBackgroundCollisions(mX, mY, mType.getImageWidth(), mType.getImageHeight())) {
			 if(doHit(now, 1)) {
				 return true;
			 }
		 }
		 return false;
	 }


 	public void doCollectPowerUp(long now, int gunId) {
 		 		
 		if(gunId>=0 && gunId<mNumGuns) {
 			
 			if(mGunActiveList[gunId]) {
 				
 				//Gun is active ... power it more
 				
 			} else {
 			
 				//Gun is not active ... switch to it

	 			EnemyGunType playerGun = mType.getGunTypeList().get(gunId);
	 			int level = playerGun.getGunLevel();
	 			
	 			//Turn off all guns with the same level
	 			for(int i=0; i<mNumGuns; i++) {
	 				if(mType.getGunTypeList().get(i).getGunLevel()==level) mGunActiveList[i]=false;
	 			}
	
	 			//Turn on our gun
	 	 		mGunActiveList[gunId]=true;
 			}
 		}
 		
 		//inc gun rate... or something
 		
 	}

}
