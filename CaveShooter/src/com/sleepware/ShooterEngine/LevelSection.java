package com.sleepware.ShooterEngine;

import java.util.ArrayList;


public class LevelSection {
	
	private ShooterEngineContext mShooterEngineContext;
	
	public final static int SECTION_STILL =0;
	public final static int SECTION_TIMED_STILL =1;
	public final static int SECTION_SCROLL_X =2;
	public final static int SECTION_SCROLL_X_UP =3;
	public final static int SECTION_SCROLL_X_DOWN =4;
	public final static int SECTION_BOSS_STILL=5;
	
	public final static int NO_IMAGE =-1;

	public int mId;
	public int mStartPosition;
	public int mEndPosition;
	public int mType;   //scrolling or still type (eg SECTION_STILL)
	public int mTiming; //speed in pixels/sec OR time in ms to stop for 
	public int mNumBossesRemaining; //for SECTION_BOSS_STILL, number of bosses needed to be killed before starting next section
	
    private ArrayList<EnemyResource> mEnemyList;
    	
	public LevelSection(ShooterEngineContext shooterEngineContext, int id, int startPosition, int endPosition, int type, int timing, int numBosses) {
		
		mShooterEngineContext = shooterEngineContext;
		
		mId = id;
		mStartPosition =startPosition;
		mEndPosition =endPosition;
		mType =type;
		mTiming =timing;
		mNumBossesRemaining = numBosses;
		
		mEnemyList = new ArrayList<EnemyResource>();
		mEnemyList.clear();
	}
	
	public void addEnemyResourcetoEnd (EnemyResource enemyResource) {
		
		mEnemyList.add(enemyResource);
	}
	
    public void nextEnemyResource() {
    	if(mEnemyList.size()>0) {
    		mEnemyList.remove(0);
    	}
    }
    
    private EnemyResource getEnemyResource(int index) {
    	
    	if(index>=mEnemyList.size()) return null;
    	
    	return mEnemyList.get(index);
    }
	
    
    public void spawnEnemies(long now, int current) {
    	
    	boolean repeating=true;
    	int index=0;
		EnemyResource possibleEnemy = getEnemyResource(index);
    	
    	while(possibleEnemy!=null && repeating) {
    		
        	if(possibleEnemy.mBegin<=current) {
        		
        		//Found an enemy to spawn
        		
        		possibleEnemy.spawn(mShooterEngineContext, now);
        		
        		if(possibleEnemy.mRepeat>0) {
        			index++;
        		} else {
        			mEnemyList.remove(index);
        		}
        		
        	} else if(possibleEnemy.mRepeat>0) {
        		//else it's a repeater, so continue searching
    			index++;
        		
        	} else {
        		repeating=false;
        	}
        	
        	possibleEnemy = getEnemyResource(index);
    	}
    	
    }
    
    /*
    boolean found=false;

	do
	{
		found=false;
		EnemyResource possibleEnemy = section.getCurrentEnemyResource();

		if(possibleEnemy != null) {
    		switch(section.mType) {
    	
	    	case LevelSection.SECTION_STILL:    		
	    	case LevelSection.SECTION_TIMED_STILL:
	    	case LevelSection.SECTION_BOSS_STILL:
		    	if(possibleEnemy.mBegin<=now-mLastTimeJumpedSection) found=true;
	    		break;
	    	
	    	case LevelSection.SECTION_SCROLL_X:
	    	case LevelSection.SECTION_SCROLL_X_UP:
	    	case LevelSection.SECTION_SCROLL_X_DOWN:
	    		if(possibleEnemy.mBegin<=mXRightWallPos) found=true;
	            break;
    		}
	        
    		if(found) {
    		    Enemy e = possibleEnemy.spawn(now);
    			Global.mEnemies.addEnemy(e);
    			section.nextEnemyResource();
    		}
    	}
	} while(found);
    */
}