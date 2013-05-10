package com.sleepware.ShooterEngine;

public class MovementTypeAttached extends MovementType {

	private int mOffsetx;
	private int mOffsety;

	
	public MovementTypeAttached(int offsetx, int offsety) {
        
    	super(MOVEMENT_ATTACHED);
    	
    	mOffsetx = offsetx;
    	mOffsety = offsety;
    }
    
    public void OnEnemyConstruction(Enemy enemy, double xAdditionalSpeed, double yAdditionalSpeed) {

    }
    
 
    public boolean updatePhysics(Enemy enemy, long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
    	enemy.mX = enemy.mParent.getX()+mOffsetx;
    	enemy.mY = enemy.mParent.getY()+mOffsety;
	    	
    	return true;    	
    }

}
