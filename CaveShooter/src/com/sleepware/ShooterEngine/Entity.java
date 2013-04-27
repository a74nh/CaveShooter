package com.sleepware.ShooterEngine;

import java.util.ArrayList;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Entity {
	
	ShooterEngineContext mShooterEngineContext;

    protected double mX;
    protected double mY;

    protected double mXSpeed;
    protected double mYSpeed;
    
    protected int mState;
    
    public static final int STATE_NEW = 1;
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_OFFSCREEN = 3;  //am i needed?
    public static final int STATE_DEAD = 4; 
    public static final int STATE_INVINCIBLE = 5;  //(for player)
    
    protected EntityType mType;
    
    protected long mSpawnedTime;  //when it was created
 
    protected int mRemainingHitpoints;
    
    
    /**  Guns  **/
    
    protected long[] mLastGunFiredList;
    
    protected int mNumGuns;
    
    
    /**  Parasites  **/
    
	private ArrayList<Entity> mParasiteList;
    
    
    /**  Collisions  **/
    
    protected int mCollisionType;
    public static final int NO_COLLISION = 0;
    public static final int CIRCLE_COLLISION = 1;
    public static final int POINT_COLLISION = 2;
    
    
    public Entity(ShooterEngineContext shooterEngineContext, long now, double x, double y, EntityType type) {
    	
    	mShooterEngineContext = shooterEngineContext;
    	mX = x;
        mY = y;
        mType = type;

        mState = STATE_NEW;  
 
        mSpawnedTime = now;
        
        mRemainingHitpoints =mType.getHitPoints();
        		
        mNumGuns =mType.getGunTypeList().size();
        
		mLastGunFiredList = new long[mNumGuns];
		
		mParasiteList = new ArrayList<Entity>();
		mParasiteList.clear();
		
		mCollisionType =CIRCLE_COLLISION;
		
		hatchParasites(now);
    }

    public double getX() {
    	return mX;
    }
    
    public double getY() {
    	return mY;
    }
    
    public EntityType getType() {
    	return mType;
    }
    
    public int getState() {
    	return mState;
    }
    
    public int getCollisionType() {
    	return mCollisionType;
    }
        
 
    //TODO: assuming centre of image!
	
	public double getXCollisionPoint() {
		return mX;
	}
	
	public double getYCollisionPoint() {
		return mY;
	}
    
    
    @Override
    public String toString() {
        return "Coordinate: [" + mX + "," + mY + "]";
    }
    
    public void doDraw(Canvas canvas) {

    	internalDoDraw(canvas, mType.getImage());
    }
    
    //grumble grumble hate hate
    protected void internalDoDraw(Canvas canvas, Drawable image) {

    	int imageWidth = mType.getImageWidth();
    	int imageHeight = mType.getImageHeight();
    	
        int xLeft = (int) mX - (imageWidth / 2);
    	int yTop = (int) mY - (imageHeight / 2);

        image.setBounds(xLeft, yTop, xLeft + imageWidth, yTop + imageHeight);
        image.draw(canvas);
    }
    
    
    protected void doExplosions(long now) {
    	
    	mState=STATE_DEAD;
    	
    	for(int i=0; i<mType.getExplosionGunTypeList().size(); i++) {
    		
    		EnemyGunType enemyExplosionGun = mType.getExplosionGunTypeList().get(i);
    				    	
	            Enemy newBullet = enemyExplosionGun.fire(mShooterEngineContext, now, mX, mY, mXSpeed, mYSpeed);
	            mShooterEngineContext.mEnemies.addEnemy(newBullet);
    	}
    	
    	//Kill all its attached parasites too..
		for(int i=0; i<mParasiteList.size(); i++) {
			
			Entity e = mParasiteList.get(i);
			
			if(e.mState!=STATE_DEAD) {
				e.doExplosions(now);
				mShooterEngineContext.mEnemies.removeEnemy((Enemy)e);
			}
		}
		
		if(mType.isBoss()) {
			mShooterEngineContext.mLevelSections.bossKilled();
		}
    }
        
    
    public boolean doCollision(Entity opposingEnemy) {

		switch(mCollisionType) { //good guy
		
		case CIRCLE_COLLISION:
			
			switch(opposingEnemy.getCollisionType()) {  //bad guy
			
			case CIRCLE_COLLISION: {
		    	final double a = opposingEnemy.mType.getCollisionRadius() + mType.getCollisionRadius();
		        final double dx = opposingEnemy.mX - mX;
		        final double dy = opposingEnemy.mY - mY;
		        return a * a > (dx * dx + dy * dy);
			}
			case POINT_COLLISION: {
				final double a = mType.getCollisionRadius();
				final double dx = opposingEnemy.getXCollisionPoint() - mX;
				final double dy = opposingEnemy.getYCollisionPoint() - mY;
	            return a * a >= (dx * dx + dy * dy);
			}
				
			//case NO_COLLISION:
			}
			break;
		
		case POINT_COLLISION:
		
			switch(opposingEnemy.getCollisionType()) {
			
			case CIRCLE_COLLISION: {
				final double a = opposingEnemy.mType.getCollisionRadius();
				final double dx = opposingEnemy.mX - getXCollisionPoint();
				final double dy = opposingEnemy.mY - getYCollisionPoint();
	            return a * a >= (dx * dx + dy * dy);
			}
				
			case POINT_COLLISION: {
				final double dx = opposingEnemy.mX - getXCollisionPoint();
				final double dy = opposingEnemy.mY - getYCollisionPoint();
	            return 1 >= (dx * dx + dy * dy);
			}
				
			//case NO_COLLISION:
			}
			break;
		}
		
		return false;
	}
    
    private void hatchParasite(long now, int id) {
    	
        Enemy e = mType.getParasiteTypeList().get(id).hatch(now, this);
        mParasiteList.add(e);
        mShooterEngineContext.mEnemies.addEnemy(e);
    }
    
    private void hatchParasites(long now) {
    	
    	//Gotta hatch them all
    	int size = mType.getParasiteTypeList().size();    	
    	for(int i=0; i<size; i++) {
    		hatchParasite(now, i);
    	}
    }

    
	public boolean doHit(long now, int damage) {
		 
		 //Cannot hit these types:
		 //STATE_NEW
		 //STATE_OFFSCREEN
		 //STATE_DEAD
		 //STATE_INVINCIBLE
		 
		 if(mState==STATE_ACTIVE) {
			 
			mRemainingHitpoints-=damage;
	    	
			if(mRemainingHitpoints<=0) {
	    		
				//Inc score here because this was killed by a collision
				mShooterEngineContext.mScore += mType.getBonusScore();
				
	    		doExplosions(now);
	    		return true;
	    	}
		 }
		 return false;
	}
    
	
	public void doCollectPowerUp(long now, int gunId) {
		return;
	}
}
