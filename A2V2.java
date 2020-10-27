import java.util.Random;
import java.util.Stack;


public class A2V2 {
	
	static Random rand = new Random();
	static int boardSize = rand.nextInt(21 - 5) + 5;
	static int goalValueX = rand.nextInt(boardSize);
	static int goalValueY = rand.nextInt(boardSize);
	static int cornerDecider = rand.nextInt(5-1)+1;
	static int [][]finalArray = GenerateGrid(boardSize, cornerDecider, goalValueX, goalValueY);
	static boolean [][]visitedArray = new boolean[boardSize][boardSize];
	
	public static void main(String[] args) {
		
		setVisited(true);
		int startPosX = getStartingPositionX(cornerDecider, boardSize);
		int startPosY = getStartingPositionY(cornerDecider, boardSize);
		
		System.out.println("corner: " + cornerDecider);
		PrintGrid(finalArray);
		
		System.out.println(MoveCharacter(startPosX, startPosY));
		

	}
	
	/*
	 * Move character takes the starting X coordinate and starting Y coordinate of the game and returns true if there
	 * is a possible path to reach the goal, and false if there is NO possible path from the start to the goal.
	 */
	public static boolean MoveCharacter(int startPosX, int startPosY) {
		int x = startPosX;
		int y = startPosY;
		
		Stack<Node> myStack = new Stack<Node>(); //Initializing an empty stack
		
		Node temp = new Node(x,y);
		
		myStack.push(temp);	//Pushing our starting Node into the stack.
		
		
		while(!myStack.isEmpty()) {
			
			temp = myStack.peek();
			int direction = temp.getDir();
			x = temp.getX();
			y = temp.getY();
			int squareValue = finalArray[temp.getX()][temp.getY()];

			temp.setDir(temp.getDir() + 1);
			myStack.pop();
			myStack.push(temp);
			temp = myStack.peek();
			
	
			if(finalArray[temp.getX()][temp.getY()] == 0) {
				System.out.println("we did it!");
				return true;
			}
			
			if(direction == 0) { //go right
				if(((temp.getY() + squareValue) <= boardSize -1) && visitedArray[x][temp.getY() + squareValue]){
					Node path = new Node(x, (temp.getY() + squareValue));
					visitedArray[x][(temp.getY() + squareValue)] = false;
					myStack.push(path);
				}
			}
			else if(direction == 1) { //go down
				if(((temp.getX() + squareValue) <= boardSize - 1) && visitedArray[temp.getX() + squareValue][y]){
					Node path = new Node((temp.getX() + squareValue), y);
					visitedArray[temp.getX() + squareValue][y] = false;
					myStack.push(path);
				}
			}
			else if(direction == 2) { //go left
				if(((temp.getY() - squareValue) >= 0) && visitedArray[x][temp.getY() - squareValue]) {
					Node path = new Node(x, (temp.getY() - squareValue));
					visitedArray[x][(temp.getY() - squareValue)] = false;
					myStack.push(path);
				}
			}
			else if(direction == 3) { //go up
				if(((temp.getX() - squareValue) >= 0) && visitedArray[temp.getX() - squareValue][y]){
					Node path = new Node((temp.getX() - squareValue), y);
					visitedArray[temp.getX() - squareValue][y] = false;
					myStack.push(path);
				}
			}
			
			//If the path has no possible direction, set the path to true meaning that the path has already been checked
			//and it lead to a dead end.
			else {
				visitedArray[temp.getX()][temp.getY()] = true;
				myStack.pop();
			}
			
		}
		return false;
	}
	
	//Array that keeps track of the paths we used to find the goal
	private static void setVisited(boolean b) {
		for(int i = 0; i < visitedArray.length; i++) {
			for (int j = 0; j < visitedArray[i].length; j++) {
				visitedArray[i][j] = b;
			}
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

/*
 * Node class that holds an x & y value of a square on the grid, along with a direction.
 * Every value of direction has a cardinal direction that the path will take given the opportunity.
 */
class Node{
	private int x,y;
	private int dir;
	
	public Node(int i, int j) {
		this.x = i;
		this.y = j;
		
		this.dir = 0;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
}