package com.sleepware.ShooterEngine;

public class PlayerBullets extends Enemies {

	/*
    private ArrayList<Enemy> mBulletList;

    
	public PlayerBullets() {

		mBulletList = new ArrayList<Enemy>();
        mBulletList.clear();
	}
	
	
	public Enemy deleteBullet(int i) {
		return mBulletList.remove(i);
	}
	
	public void addBullet(Enemy i) {
		mBulletList.add(i);
	}
	
    public void doStart() {

    	mBulletList.clear();
    }
*/
    /*
    public void doDraw(Canvas canvas) {

	    int numBullets = mEnemyList.size();
	    for (int index = 0; index < numBullets; index ++) {
	    	
	    	mEnemyList.get(index).doDraw(canvas);
	    }
    }
    */
	/*
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
	
    	Enemy c;
	    
	    for (int index = 0; index < mEnemyList.size(); index ++) {
	    	
	    	c = mEnemyList.get(index);
	    	
	    	c.updatePhysics(now, elapsed, canvasWidth, canvasHeight);
	
	    	if(c.mState == Entity.STATE_OFFSCREEN)  {
	    		c.clear();
	    	}
	    }   
    }
    */
    
    public void doCollisions(ShooterEngineContext shooterEngineContext, long now) {
    	
    	//Check every enemy item against everything in playbulletlist

    	for (int playerBulletIndex=0; playerBulletIndex<mEnemyList.size(); playerBulletIndex++) {
	    	    		
    		if(shooterEngineContext.mEnemies.doCollision(now, mEnemyList.get(playerBulletIndex))) {
    			
    			; //nothing to do here? because it'll be cleared as part of updatePhysics
    		}
    	}
    		
    	/*	
        	int iterator = Global.mEnemies.iterateStart();
        	Enemy enemy = Global.mEnemies.getCurrent(iterator);
    		
        	while(enemy!=null && !playerBulletDestroyed) {

        		switch(enemy.getType().mType) {
        		
        		//case EntityType.TYPE_EXPLOSION - can't collide with these
        		//case EntityType.TYPE_POWERUP - only the player can collide with these
        		//case EntityType.TYPE_PLAYER_BULLET - shouldn't be in this list
        		//case EntityType.TYPE_PLAYER - shouldn't be in this list
        		//case EntityType.TYPE_BLANK - is completely ignored
        		
    			case EntityType.TYPE_BULLET:
    				//Bullets cannot collide with each other
    				if(playerBullet.getType().mType==EntityType.TYPE_PLAYER_BULLET) break;
    				//else fall through...
    				
    			case EntityType.TYPE_ENEMY:
    				
    		    	if(enemy.doCollision(playerBullet)) {
    					
    					if(enemy.doHit(now, playerBullet.mType.getAttackDamage())) {
    						Global.mEnemies.removeCurrent(iterator);
    					}
    					    					
    					if(playerBullet.doHit(now, enemy.mType.getAttackDamage())) {
    					
    					}
	        		}
        		}//switch
        		
        		iterator = Global.mEnemies.iterateNext(iterator);
            	enemy = Global.mEnemies.getCurrent(iterator);

    	    }//while
	    }//for    */
    	
    }
    
}
