package com.sleepware.ShooterEngine;

public class PowerUpType {

	private int mPoints;
	private int mGunTypeID;
	
	public final int GUN_TYPE_NONE = -1;

	public PowerUpType(int points, int gunTypeID) {
		
		mPoints = points;
		mGunTypeID = gunTypeID;
	}


	public int getPoints() {
		return mPoints;
	}

	public int getGunTypeID() {
		return mGunTypeID;
	}

}
