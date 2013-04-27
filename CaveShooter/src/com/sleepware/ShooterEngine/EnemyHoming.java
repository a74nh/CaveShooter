package com.sleepware.ShooterEngine;



public class EnemyHoming extends Enemy{

	MovementTypeHoming mMovementType;

	double mSpeed;

	
    //TODO: Add in additional speeds?  (for when being fired from a gun)  (If we want this...)
    //TODO: Add in an additional direction  (for mapping the spline along) (If we want this...)
	
    public EnemyHoming(ShooterEngineContext shooterEngineContext, long now, double x, double y, double xAdditionalSpeed, double yAdditionalSpeed, EntityType type, MovementTypeHoming movementType) {
		
    	super(shooterEngineContext, now, x, y, type);
    	mMovementType = movementType;

        mSpeed = (double)type.getInitialSpeed();
	}
 
	
	public boolean updatePhysics(long now, double elapsed, int canvasWidth, int canvasHeight) {
    	
		double playerX = 0;
		double playerY = 0;
		
		Player player = mShooterEngineContext.getPlayer();
		if(player!=null) {
			playerX = player.mX;
			playerY = player.mY;
		}
		
		final double xdist = playerX - mX;
		final double ydist = playerY - mY;
		final double dist = (float) Math.sqrt((xdist*xdist)+(ydist*ydist));		
				
    	final double additionalSpeed = ((mType.getAcceleration() * elapsed)/1000.0);

		mSpeed +=((additionalSpeed * elapsed)/1000.0);
		
		final double distanceMoved = ((mSpeed * elapsed)/1000.0);

		if(distanceMoved>=dist) {
			
			mX = playerX;
			mY = playerY;
		}
		else {
			
			mX += ((playerX-mX) / dist * distanceMoved);
			mY += ((playerY-mY) / dist * distanceMoved);
		}
    	
    	return super.updatePhysics(now, elapsed, canvasWidth, canvasHeight);    	
    }
}
