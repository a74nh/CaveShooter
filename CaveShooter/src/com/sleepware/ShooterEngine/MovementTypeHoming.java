package com.sleepware.ShooterEngine;



public class MovementTypeHoming extends MovementType {

    public MovementTypeHoming() {
        
    	super(MOVEMENT_HOMING);    
    }
    

    //TODO: Add in additional speeds?  (for when being fired from a gun)  (If we want this...)
    //TODO: Add in an additional direction  (for mapping the spline along) (If we want this...)
    
    public void OnEnemyConstruction(Enemy enemy, double xAdditionalSpeed, double yAdditionalSpeed) {

    }


    public boolean updatePhysics(Enemy enemy, long now, double elapsed, int canvasWidth, int canvasHeight) {

    	double playerX = 0;
    	double playerY = 0;

    	Player player = enemy.mShooterEngineContext.getPlayer();
    	if(player!=null) {
    		playerX = player.mX;
    		playerY = player.mY;
    	}

    	final double xdist = playerX - enemy.mX;
    	final double ydist = playerY - enemy.mY;
    	final double dist = (float) Math.sqrt((xdist*xdist)+(ydist*ydist));		

    	final double additionalSpeed = ((enemy.mType.getAcceleration() * elapsed)/1000.0);

    	enemy.mSpeed +=((additionalSpeed * elapsed)/1000.0);

    	final double distanceMoved = ((enemy.mSpeed * elapsed)/1000.0);

    	if(distanceMoved>=dist) {

    		enemy.mX = playerX;
    		enemy.mY = playerY;
    	}
    	else {

    		enemy.mX += ((playerX-enemy.mX) / dist * distanceMoved);
    		enemy.mY += ((playerY-enemy.mY) / dist * distanceMoved);
    	}

    	return true;
    } 
    
}
