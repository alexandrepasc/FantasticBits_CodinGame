import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Grab Snaffles and try to throw them through the opponent's goal!
 * Move towards a Snaffle and use your team id to determine where you need to throw it.
 **/
class Player {
    
    public static void debug (String msg) {
        System.err.println (msg);
    }
    public static void debug (int msg) {
        System.err.println (msg);
    }
    
    enum Status {
        GRABBED (1),
    	OTHERWISE (0);
    	
    	public int value;
    	
    	private Status(int value)  {
    		this.value = value;
    	}
    }
    
    public static class Wizard {
        int wizardId = 100;
        int x;
        int y;
        int vx;
        int vy;
        Status state;
        
        int targetX;
        int targetY;
        int targetId;
                
        public void setWizardId(int id) {
            wizardId = id;
        }
        public int getWizardId() {
            return wizardId;
        }
        
        public void setX(int coord) {
            x = coord;
        }
        public int getX() {
            return x;
        }
        
        public void setY(int coord) {
            y = coord;
        }
        public int getY() {
            return y;
        }
        
        public void setVx(int coord) {
            vx = coord;
        }
        public int getvX() {
            return vx;
        }
        
        public void setVy(int coord) {
            vy = coord;
        }
        public int getVy() {
            return vy;
        }
        
        public void setState(Status status_) {
            state = status_;
        }
        public Status getState() {
            return state;
        }
        
        public void setTargetX(int x) {
        	targetX = x;
        }
        public int getTargetX() {
        	return targetX;
        }
        
        public void setTargetY(int y) {
        	targetY = y;
        }
        public int getTargetY() {
        	return targetY;
        }
        
        public void setTargetId(int id) {
        	targetId = id;
        }
        public int getTargetId() {
        	return targetId;
        }
    }
    
    public static class Snaffle {
        int snaffleId = 100;
        int x;
        int y;
        int vx;
        int vy;
        Status state;
        boolean onField;
        int wizardId;
        
        public void setSnaffleId(int id) {
        	snaffleId = id;
        }
        public int getSnaffleId() {
            return snaffleId;
        }
        
        public void setX(int coord) {
            x = coord;
        }
        public int getX() {
            return x;
        }
        
        public void setY(int coord) {
            y = coord;
        }
        public int getY() {
            return y;
        }
        
        public void setVx(int coord) {
            vx = coord;
        }
        public int getvX() {
            return vx;
        }
        
        public void setVy(int coord) {
            vy = coord;
        }
        public int getVy() {
            return vy;
        }
        
        public void setState(Status status_) {
            state = status_;
        }
        public Status getState() {
            return state;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left

        int goalX;
        int goalY = 3750;
        
        if (myTeamId == 0) {
            goalX = 0;
        }
        else {
            goalX = 16000;
        }
        
        Wizard[] wizards = new Wizard[2];
        for (int aux = 0; aux < wizards.length; aux++) {
            wizards[aux] = new Wizard();
        }
        
        Snaffle[] snaffles = new Snaffle[7];
        for (int aux = 0; aux < snaffles.length; aux++) {
            snaffles[aux] = new Snaffle();
        }
        
        // game loop
        while (true) {
            int myScore = in.nextInt();
            int myMagic = in.nextInt();
            int opponentScore = in.nextInt();
            int opponentMagic = in.nextInt();
            int entities = in.nextInt(); // number of entities still in game
            for (int i = 0; i < entities; i++) {
                int entityId = in.nextInt(); // entity identifier
                String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
                int x = in.nextInt(); // position
                int y = in.nextInt(); // position
                int vx = in.nextInt(); // velocity
                int vy = in.nextInt(); // velocity
                int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise
                
                if (entityType.equals("WIZARD")) {
                    setWizard(wizards, entityId, x, y, vx, vy, state);
                }
                
                if (entityType.equals("SNAFFLE")) {
                	/*debug("x: " + x);
                    debug("y: " + y);*/
                    setSnaffle(snaffles, entityId, x, y, vx, vy, state);
                }
            }
            
            /*for (int i = 0; i < snaffles.length; i++) {
            	debug("x: " + snaffles[i].getX());
            	debug("y: " + snaffles[i].getY());
            }*/
            
            targetSnaffle(wizards, snaffles);
            
            for (int i = 0; i < 2; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // Edit this line to indicate the action for each wizard (0 ≤ thrust ≤ 150, 0 ≤ power ≤ 500)
                // i.e.: "MOVE x y thrust" or "THROW x y power"
            	System.out.println("MOVE " + wizards[i].getTargetX() + " " + wizards[i].getTargetY() + " 100");
            	
                //System.out.println("MOVE 8000 3750 100");
            }
        }
    }
    
    public static void setWizard(Wizard[] wizards, int entityId, int x, int y, int vx, int vy, int state) {
        for (int aux = 0; aux < wizards.length; aux++) {
            if (wizards[aux].getWizardId() == 100) {
                wizards[aux].setWizardId(entityId);
                
                setCoord(wizards[aux], x, y, vx, vy);
                
                wizards[aux].setState(setState(state));
                
                aux = wizards.length;
            }
            else {
                if (wizards[aux].getWizardId() == entityId) {
                    setCoord(wizards[aux], x, y, vx, vy);
                    
                    wizards[aux].setState(setState(state));
                    
                    /*debug(wizards[aux].getX());
                    debug(wizards[aux].getY());*/
                }
            }
            /*debug("x: " + wizards[aux].getX());
            debug("y: " + wizards[aux].getY());*/
        }
    }
    
    public static void setSnaffle(Snaffle[] snaffles, int entityId, int x, int y, int vx, int vy, int state) {
        for (int aux = 0; aux < snaffles.length; aux++) {
            if (snaffles[aux].getSnaffleId() == 100) {
                snaffles[aux].setSnaffleId(entityId);
                
                setCoord(snaffles[aux], x, y, vx, vy);
                
                snaffles[aux].setState(setState(state));
                
                aux = snaffles.length;
            }
            else {
                if (snaffles[aux].getSnaffleId() == entityId) {
                    setCoord(snaffles[aux], x, y, vx, vy);
                    
                    snaffles[aux].setState(setState(state));
                    
                    /*debug("x: " + snaffles[aux].getX());
                    debug("y: " + snaffles[aux].getY());*/
                }
            }
            /*debug("x: " + snaffles[aux].getX());
            debug("y: " + snaffles[aux].getY());*/
        }
    }
    
    public static void setCoord(Wizard wiz, int x, int y, int vx, int vy) {
        wiz.setX(x);
        wiz.setY(y);
        wiz.setVx(vx);
        wiz.setVy(vy);
    }
    public static void setCoord(Snaffle snaf, int x, int y, int vx, int vy) {
        snaf.setX(x);
        snaf.setY(y);
        snaf.setVx(vx);
        snaf.setVy(vy);
    }
    
    public static Status setState (int state) {
        switch (state) {
            case 0:
                return Status.OTHERWISE;
                //break;
            case 1:
                return Status.GRABBED;
                //break;
            default:
                return null;
        }
    }
    
    public static void targetSnaffle(Wizard[] wizards, Snaffle[] snaffles) {
    	double[][][] distWizSnaf = new double[wizards.length][snaffles.length][snaffles.length];
    	
    	for (int i = 0; i < wizards.length; i++) {
    		for (int o = 0; o < snaffles.length; o++) {
    			distWizSnaf[i][o][0] = snaffles[o].getSnaffleId();
    			distWizSnaf[i][o][1] = getDistance(wizards[i].getX(), wizards[i].getY(), snaffles[o].getX(), snaffles[o].getY());
            }
    	}
    	
    	double compare = 17671;
    	int arrayPos[] = new int[wizards.length];
    	
    	for (int i = 0; i < wizards.length; i++) {
    		for (int o = 0; o < snaffles.length; o++) {
    			double aux = compareDist(distWizSnaf[i][o][1], compare);
    			if (aux != compare) {
    				compare = aux;
    				arrayPos[i] = o;
    			}
    		}
    	}
    	
    	for (int i = 0; i < wizards.length; i++) {
    		int targId = (int) distWizSnaf[i][arrayPos[i]][0];
    		
    		for (int o = 0; o < snaffles.length; o++) {
    			if (targId == snaffles[o].getSnaffleId()) {
    				wizards[i].setTargetX(snaffles[o].getX());
    				wizards[i].setTargetY(snaffles[o].getY());
    				wizards[i].setTargetId(snaffles[o].getSnaffleId());
    			}
    		}
    	}
    	
    	if (arrayPos[0] == arrayPos[1]) {
    		if (distWizSnaf[0][arrayPos[0]][1] < distWizSnaf[1][arrayPos[0]][1]) {
    			int targId = (int) getNewWizardTarget(arrayPos[1], distWizSnaf[1], snaffles.length);
    			
    			for (int o = 0; o < snaffles.length; o++) {
        			if (targId == snaffles[o].getSnaffleId()) {
        				wizards[1].setTargetX(snaffles[o].getX());
        				wizards[1].setTargetY(snaffles[o].getY());
        				wizards[1].setTargetId(snaffles[o].getSnaffleId());
        			}
        		}
    		}
    		else if (distWizSnaf[0][arrayPos[0]][1] > distWizSnaf[1][arrayPos[0]][1]) {
    			int targId = (int) getNewWizardTarget(arrayPos[0], distWizSnaf[0], snaffles.length);
    			
    			for (int o = 0; o < snaffles.length; o++) {
        			if (targId == snaffles[o].getSnaffleId()) {
        				wizards[0].setTargetX(snaffles[o].getX());
        				wizards[0].setTargetY(snaffles[o].getY());
        				wizards[0].setTargetId(snaffles[o].getSnaffleId());
        			}
        		}
    		}
    	}
    }
    
    public static double getNewWizardTarget(int arrayPos, double[][] distWizSnaf, int length) {
    	double compare = 17671;
    	
    	for (int i = 0; i < length; i++) {
    		double aux = compareDist(distWizSnaf[i][1], distWizSnaf[arrayPos][1], compare);
    		if (aux != compare) {
				compare = aux;
				arrayPos = i;
			}
    	}
    	
    	return distWizSnaf[arrayPos][0];
    }

    public static double compareDist(double value, double compare) {
    	if (value < compare) {
    		return value;
    	}
    	else {
    		return compare;
    	}
    }
    public static double compareDist(double value, double compareMin, double compare) {
    	if ((value > compareMin) && (value < compare)) {
    		return value;
    	}
    	else {
    		return compare;
    	}
    }
    /*public static int getCloser(Wizard wizard, Snaffle[] snaffles) {
        
    }*/
    public static double getDistance(int wizX, int wizY, int snafX, int snafY) {
    	int a = Math.abs(wizX - snafX);
    	int b = Math.abs(wizY - snafY);
    	
    	return Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }
}
