package com.sleepware.ShooterEngine;

import android.graphics.drawable.Drawable;

public class PlayerType extends EntityType {

	private Drawable mImageInvincible;

	
	public PlayerType(String name, Drawable imageId, Drawable imageInvincibleId,  int acceleration, int speed, int delay, int lives) {
	
		super(name, EntityType.TYPE_PLAYER, imageId, acceleration, speed, delay, lives, FOREVER, 0, BONUSGUN_NONE, false);

        mImageInvincible = imageInvincibleId; //ShooterEngine.mContext.getResources().getDrawable(imageInvincibleId);
	}
		
	/** Getters **/

	public Drawable getImageInvincible() {
		return mImageInvincible;
	}
	
	
	/** Spawn Into Enemy **/
	
    public Player spawn(ShooterEngineContext shooterEngineContext, long now, double x, double y) {
    	
    	return new Player(shooterEngineContext, now, x, y, this);
	}
	
}
