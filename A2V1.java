import java.util.Random;

public class A2V1 {
	
	static Random rand = new Random();
	static int boardSize = rand.nextInt(21 - 5) + 5;
	static int goalValueX = rand.nextInt(boardSize);
	static int goalValueY = rand.nextInt(boardSize);
	static int cornerDecider = rand.nextInt(5-1)+1;
	static int [][]finalArray = GenerateGrid(boardSize, cornerDecider, goalValueX, goalValueY);
	static int [][]visitedArray = new int[boardSize][boardSize];
	
	public static void main(String[] args) {
		int startPosX = getStartingPositionX(cornerDecider, boardSize);
		int startPosY = getStartingPositionY(cornerDecider, boardSize);
		
		System.out.println("This is our starting grid with the start at corner: " + cornerDecider);
		System.out.println("With a starting value of: " + finalArray[startPosX][startPosY]);
		System.out.println("This is a " + boardSize + " by " + boardSize + " grid");
		PrintGrid(finalArray);
		
		long startTime = System.currentTimeMillis();
		
		System.out.println(MovePlayer(startPosX, startPosY));
		
		long endTime = System.currentTimeMillis();
		float duration = ((endTime - startTime)/1000F); //Execution time of the method
		
		System.out.println(duration + " seconds");

		
	}
	
	/*
	 * This recursive method takes in a position on our grid, and recursively moves in cardinal directions. If
	 * The path takes us to a "wall" it will not continue along that path, instead it will change direction, it 
	 * will also not go along a path that we have already visited, that is being accompanied by an empty array and marking
	 * the cells we've visited. This method returns true if the path can reach the goal, and false if there is no possible 
	 * path to the goal.
	 */
	public static boolean MovePlayer(int startPosX, int startPosY) {
		
		int squareValue = finalArray[startPosX][startPosY];

		//PrintGrid(finalArray);
		
		//Base Case
		if(finalArray[startPosX][startPosY] == 0) {
			System.out.println("we did it!");
			return true;
		}
		//Recursive Case
		else {
			
			//checks to see if the square we are currently on is already visited.
			if(visitedArray[startPosX][startPosY] == -1) 
				return false;
			
			//marks the square we are standing on as visited.
			visitedArray[startPosX][startPosY] = -1;
			
			boolean find = false;
		    
		    if(((startPosY + squareValue) <= boardSize-1))
		    	if(MovePlayer(startPosX, startPosY + squareValue)) //Move right, if goal can be reached, end recursion.
		    		find = true; 
		    if((!find && (startPosY - squareValue) >= 0))
		        if(MovePlayer(startPosX, startPosY - squareValue)) //Move left
		        	find = true; 
		    if((!find && (startPosX + squareValue) <= boardSize-1))
		        if(MovePlayer(startPosX + squareValue, startPosY))  //Move down
		        	find = true; 
		    if((!find && (startPosX - squareValue) >= 0))
		        if(MovePlayer(startPosX - squareValue, startPosY)) //Move up
		        	find = true; 

		    return find; //Will return true if there is a path available to be taken.
		}
	}
	
	//Randomly generates the board by giving it parameters.
	public static int[][] GenerateGrid(int boardSize, int cornerDecider, int goalValueX, int goalValueY) {
		Random rand = new Random();
		
		int squareValue = rand.nextInt(boardSize-1)+1;

		int[][] myArr = new int [boardSize][boardSize];
		
		for(int i = 0; i < myArr.length; i++) {
			for(int j = 0; j <= boardSize-1; j++) {
				squareValue = rand.nextInt(boardSize-1)+1;
				myArr[j][i] = squareValue;
			}
		}
		myArr[goalValueX][goalValueY] = 0;
		
		int x = getStartingPositionX(cornerDecider, boardSize);
		int y = getStartingPositionY(cornerDecider, boardSize);
		
		//If by some chance the goal lands on the corner that we are starting on
		if(myArr[x][y] == 0) {
			myArr[x][y] = rand.nextInt(boardSize-1)+1;
			myArr[(boardSize-1)/2][(boardSize-1)/2] = 0;
		}
		return myArr;
	}
	
	//Gets the X starting position by the corner that it started on.
	private static int getStartingPositionX(int cornerDecider, int boardSize){
		if(cornerDecider == 1) {
			return 0;
		}else if(cornerDecider == 2) {
			return 0;
		}else if(cornerDecider == 3) {
			return  boardSize-1;
		}else if(cornerDecider == 4) {
			return  boardSize-1;
		}
		return 0;
	}
	
	//Gets the Y starting position by the corner that it started on.
	private static int getStartingPositionY(int cornerDecider, int boardSize){
		if(cornerDecider == 1) {
			return 0;
		}else if(cornerDecider == 2) {
			return boardSize-1;
		}else if(cornerDecider == 3) {
			return  0;
		}else if(cornerDecider == 4) {
			return  boardSize-1;
		}
		return 0;
	}
	
	//Prints the board
	private static void PrintGrid(int[][] finalArray) {
		for(int i = 0; i < finalArray.length; i++) {
			for(int j = 0; j < finalArray[i].length; j++) {
				System.out.printf("|%2d ", finalArray[i][j]);
			}
			System.out.println();
		}	System.out.println();
	}
}