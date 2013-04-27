package com.sleepware.ShooterEngine;

import java.util.ArrayList;


import android.graphics.Canvas;
import android.os.Bundle;

public class Enemies {
	
    private ArrayList<Enemy> mEnemyList;
    
    private static final String KEY_ENEMIES = "mEnemies";      

    private static EnemyBlank mEnemyBlank;
    private int mNumBlanks;
	
	public Enemies() {

		mEnemyList = new ArrayList<Enemy>();
        mEnemyList.clear();
        
        mEnemyBlank =new EnemyBlank();
        mNumBlanks=0;
	}
	
	
    public void doStart() {

    	mEnemyList.clear();        
    }

    
    public void addEnemy(Enemy enemy) {
    	
        mEnemyList.add(enemy);
    }
    
    
    private void removeEnemy(int index) {
    	//Just set it to a blank enemy for now to stop screwing with the list
    	if(mEnemyList.get(index)!=mEnemyBlank) {
    		mEnemyList.set(index, mEnemyBlank);
    		mNumBlanks++;
    	}
    }
    
    public void removeEnemy(Enemy enemy) {
    	
    	int index = mEnemyList.indexOf(enemy);
    	
    	if(index!=-1) {
            mEnemyList.set(index, mEnemyBlank);
            mNumBlanks++;
    	}
    }
    
    
    public void doDraw(Canvas canvas) {

	    int num = mEnemyList.size();
	    for (int index =num-1; index >=0; index --) {
	    	
	    	mEnemyList.get(index).doDraw(canvas);
	    }
    }
    
    public void cleanup() {
    	
	    for (int index = 0; index < mEnemyList.size() && mNumBlanks>0; index ++) {
	    	if(mEnemyList.get(index).getState()==Entity.STATE_DEAD) {
	    		//Enemy is dead
	    		mEnemyList.remove(index);
	    		index--;
	    		mNumBlanks--;
	    	}
	    }
	    //Force this just to be sure
	    mNumBlanks=0;
    }
    
    public void updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
	
	    Enemy c;
	    
	    for (int index = 0; index < mEnemyList.size(); index ++) {
	    	
	    	c = mEnemyList.get(index);
	    	
	    	if(!c.updatePhysics(now, elapsed, canvasWidth, canvasHeight)) {
	    		//Enemy died on us
	    		mEnemyList.remove(index);
	    		index--;
	    	}
	    }	    
    }
    
    //Returns true if the good guy dies
    public boolean doCollision(long now, Entity goodGuy) {
    	
		EntityType gtype = goodGuy.getType();
		
		int listsize = mEnemyList.size();
		
		//The list shouldn't be decreasing in size.
		//It might increase (eg explosions), but we don't care about them (For now)
		for (int index = 0; index < listsize; index ++) {
	
			Enemy enemy = mEnemyList.get(index);
			EntityType etype = enemy.getType();

			boolean checkCollision = false;
			
			switch(etype.mType) {
			
			//case EntityType.TYPE_EXPLOSION - can't collide with these
			//case EntityType.TYPE_PLAYER_BULLET - shouldn't be in this list
			//case EntityType.TYPE_PLAYER - shouldn't be in this list
			//case EntityType.TYPE_BLANK - is completely ignored
			
			case EntityType.TYPE_BULLET:
				//Bullets cannot collide with each other
				if(gtype.mType!=EntityType.TYPE_PLAYER_BULLET) checkCollision=true;
				break;
				
			case EntityType.TYPE_POWERUP:
				//Only the player can collide with these
				if(gtype.mType==EntityType.TYPE_PLAYER) checkCollision=true;
				break;
				
			case EntityType.TYPE_ENEMY:
				checkCollision=true;
			
			}
			
			if(checkCollision) {
				
		    	if(enemy.doCollision(goodGuy)) {

					if(enemy.doHit(now, gtype.getAttackDamage())) {
						removeEnemy(index);
					
						//Only the player can collect any goodies
		    			if(gtype.mType==EntityType.TYPE_PLAYER && etype.hasBonusGun()) {
		    				goodGuy.doCollectPowerUp(now, etype.getBonusGunId());
		    			}
					}
					
					if(goodGuy.doHit(now, etype.getAttackDamage())) {
						//Good guy is dead, best return back...
						return true;
					}
	    		}
			}//if	
	    }//for
		
		return false;
    }
    
    
    public void saveState(Bundle map) {
    	map.putDoubleArray(KEY_ENEMIES, coordArrayListToArray(mEnemyList));
    }
   
    public void restoreState(Bundle savedState) {
    	mEnemyList = coordArrayToArrayList(savedState.getDoubleArray(KEY_ENEMIES));
    }
    
    
    
    /** TODO: BORKEN
     * Given a ArrayList of Enemy, we need to flatten them into an array of
     * ints before we can stuff them into a map for flattening and storage.
     * 
     * @param cvec : a ArrayList of Enemy objects
     * @return : a simple array containing the x/y values of the Enemy
     * as [x1,y1,x2,y2,x3,y3...]
     */
    private double[] coordArrayListToArray(ArrayList<Enemy> cvec) {
        int count = cvec.size();
        double[] rawArray = new double[count * 2];
        for (int index = 0; index < count; index++) {
      //  	Enemy c = cvec.get(index);
      //      rawArray[2 * index] = c.mX;
       //     rawArray[2 * index + 1] = c.mY;
        }
        return rawArray;
    }

    /** TODO: BORKEN
     * Given a flattened array of ordinate pairs, we reconstitute them into a
     * ArrayList of Enemy objects
     * 
     * @param rawArray : [x1,y1,x2,y2,...]
     * @return a ArrayList of Enemy
     */
    private ArrayList<Enemy> coordArrayToArrayList(double[] rawArray) {
        ArrayList<Enemy> coordArrayList = new ArrayList<Enemy>();

        int coordCount = rawArray.length;
        for (int index = 0; index < coordCount; index += 2) {
        	//Enemy c = new Enemy(mContext, rawArray[index], rawArray[index + 1], -300, 0);
            //coordArrayList.add(c);
        }
        return coordArrayList;
    }

}
