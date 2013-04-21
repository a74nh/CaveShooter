package com.sleepware.ShooterEngine;

import android.content.Context;

public class ShooterEngineContext {
	
	
	public Context mContext;
	public LevelSections mLevelSections;
	public EnemyTypes mEnemyTypes;
    public Enemies mEnemies;
	public PlayerBullets mBullets;
	public Hud mHud;
	
	private Player mPlayer;
	public PlayerType mPlayerType;
	
	public InputController mInputController;
	public boolean mStateRunning;

	public int mScore;
	public double mFPS;
	public int mLives;

	
	ShooterEngineContext(Context context) {

		mContext = context;
	    mEnemyTypes = new EnemyTypes();
	    mEnemies = new Enemies();
	    mBullets = new PlayerBullets();
	    mHud = new Hud();
	    
		mLevelSections=null;
		mPlayer=null;
		mPlayerType=null;
		
		mInputController = new InputController(this);
    	mStateRunning=false;

		mScore=0;
		mFPS=0;
		mLives=3;  ///TODO:  NOOO!!!! Set from xml!!!!
	}
	
	public void clear() {
		mContext = null;
	    mEnemyTypes = null;
	    mEnemies = null;
	    mBullets = null;
		mLevelSections=null;
		mHud=null;
		mPlayer=null;
		mPlayerType=null;
		
		mInputController=null;
		mStateRunning=false;
		
		mScore=0;
		mFPS=0;
		mLives=0;
	}
	
	public Player getPlayer() {
		return mPlayer;
	}
	
	public void setPlayer(Player p) {
		mPlayer =p;
	}
}
