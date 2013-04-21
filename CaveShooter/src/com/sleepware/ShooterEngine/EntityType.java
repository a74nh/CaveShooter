package com.sleepware.ShooterEngine;

import java.util.ArrayList;
import android.graphics.drawable.Drawable;

public class EntityType {

    public static final int TYPE_ENEMY =  0;
    public static final int TYPE_BULLET = 1;
    public static final int TYPE_EXPLOSION = 2;
    public static final int TYPE_POWERUP = 3;
    public static final int TYPE_PLAYER_BULLET = 4;
    public static final int TYPE_PLAYER = 5;
    public static final int TYPE_BLANK = 5;
    
    public static final int FOREVER =  1000 * 60 * 60; //one hour!
    public static final int INVINCIBLE =  2000000000; //almost max int...
    public static final int BONUSGUN_NONE =  -1;

    
    private final String mName;
    
    protected final int mType;
    
	private Drawable mImage;
	
	private final int mAcceleration;
	private final int mInitialSpeed;
	
	private final int mDelay;  //delay before spawning anything
	
	private final int mHitPoints; //(for player, hits until life lost)

	private final int mLifetime;
	private final int mBonusScore;
	private final int mBonusGunId;
	private final boolean mBoss;

	
	private int mImageWidth;
	private int mImageHeight;
    
	private ArrayList<EnemyGunType> mGunTypeList;

	private ArrayList<EnemyGunType> mExplosionGunTypeList;
	
	private ArrayList<EnemyParasiteType> mParasiteTypeList;

    
	public EntityType(String name, int type, Drawable image, int acceleration, int speed, int delay, int hitPoints, int lifetime,
	         	      int bonusScore, int bonusGunId, boolean boss) {
	
		mName = name;
		
		mType = type;

        mImage = image; //ShooterEngine.mContext.getResources().getDrawable(imageId);

        mAcceleration =acceleration;
		mInitialSpeed =speed;
		
		mDelay =delay;
		mHitPoints =hitPoints;
		
		mLifetime =lifetime;
		mBonusScore = bonusScore;
		mBonusGunId = bonusGunId;
		mBoss = boss;
		
		if(mImage!=null) {
			mImageWidth = mImage.getIntrinsicWidth();
			mImageHeight = mImage.getIntrinsicHeight();
		}
		
        mGunTypeList = new ArrayList<EnemyGunType>();
        mGunTypeList.clear();
        
        mExplosionGunTypeList = new ArrayList<EnemyGunType>();
        mExplosionGunTypeList.clear();
        
        mParasiteTypeList = new ArrayList<EnemyParasiteType>();
        mParasiteTypeList.clear();
	}
	
	
	/** Getters **/
	
	public String getName() {
		return mName;
	}
	
	public int getType() {
		return mType;
	}
	
	public Drawable getImage() {
		return mImage;
	}
	
	public int getAcceleration() {
		return mAcceleration;
	}
	
	public int getInitialSpeed() {
		return mInitialSpeed;
	}
	
	public int getDelay() {
		return mDelay;
	}
	
	public int getHitPoints() {
		return mHitPoints;
	}
	
	public int getImageWidth() {
		return mImageWidth;
	}
	
	public int getImageHeight() {
		return mImageHeight;
	}

	public int getLifetime() {
		return mLifetime;
	}
	
	public int getBonusScore() {
		return mBonusScore;
	}
	
	public int getBonusGunId() {
		return mBonusGunId;
	}
	
	public boolean hasBonusGun() {
		return mBonusGunId!=BONUSGUN_NONE;
	}
	
	public boolean isBoss() {
		return mBoss;
	}

	//TODO: These suck really!
	
	public double getCollisionRadius() {
		return mImageWidth/2;
	}
	
    //TODO: this should be from the type.
    public int getAttackDamage() {
    	return (mType == TYPE_POWERUP) ? 0 : 1;
    }
	
	/** Spawn  **/
	
 //   public Enemy spawn(long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, int accelDirection) {
 //   	return null;
//	}
	
	/** Bullets **/
	 
    public int addGunType(EnemyGunType EntityType) {
    	    	
    	mGunTypeList.add(EntityType);
    	return mGunTypeList.size()-1;
    }
    
    
	public ArrayList<EnemyGunType> getGunTypeList() {
		return mGunTypeList;
	}
    
	
	/** Explosions **/
	
    public void addExplosionGunType(EnemyGunType EntityType) {
    	
    	mExplosionGunTypeList.add(EntityType);
    }
    
	public ArrayList<EnemyGunType> getExplosionGunTypeList() {
		return mExplosionGunTypeList;
	}
	
	
	/** Parasites **/

	public void addParasiteType(EnemyParasiteType parasiteType) {
		
    	mParasiteTypeList.add(parasiteType);
	}

	public ArrayList<EnemyParasiteType> getParasiteTypeList() {
		return mParasiteTypeList;
	}
	
	
	/** Spawn Into Enemy **/
	/*
    public Enemy spawn(long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, int accelDirection,
    		           int movementType, int gravity, int gravityDirection, Spline spline) {
    	
    	//TODO: Should this die if player or blank is spawned here ??
    	
    	switch (movementType) {
    	
    	case EntityType.MOVEMENT_STRAIGHT:
    		return new EnemyStraight(now, x, y, this, xAdditionalSpeed, yAdditionalSpeed, accelDirection, gravity, gravityDirection);

    	case EntityType.MOVEMENT_HOMING:
    		return new EnemyHoming(now, x, y, this);

    	case EntityType.MOVEMENT_SPLINE:
    		return new EnemySpline(now, x, y, this, spline);

    	//case EntityType.MOVEMENT_ATTACHED:
    	//	return new EnemyAttached(now, x, y, this, xAdditionalSpeed, yAdditionalSpeed, accelDirection, parent);

    	default:
    		return null;
    	}
	}
	*/
}
