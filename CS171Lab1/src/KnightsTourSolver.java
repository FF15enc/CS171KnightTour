//Collaborator: Michelle Lee
//I changed the stack name to position

import java.awt.Point;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashSet;

public class KnightsTourSolver {
    
    public static final int NUMBER_OF_KNIGHT_MOVES = 8;
    
    private int size;               // size of the board - note, sizes greater than 6 result in MANY solutions
    private int[][] visited;        // the array that stores each solution found, for subsequent printing
    private Stack<Integer> position;   // a stack, for use in accomplishing the backtracking    
    ArrayList <Integer> convertedPosition; 
    ////////////////////////////////
    // INSERT ADDITIONAL INSTANCE //
    // VARIABLES HERE AS DESIRED  //
    ////////////////////////////////
                           
    
    
    public KnightsTourSolver(int size) {
    	this.size = size; 
        position = new Stack<Integer>();
        convertedPosition = new ArrayList<Integer>();
        convertedPosition.add(1); 
    }
    
    public int converter (int x, int y) {
    	int yFinal=y*size;
    	int xFinal=x+1;
    	
    	int converted = yFinal + xFinal; 
    	
    	return converted; 
    }
    
    public boolean isValid(int x, int y) {
    	
    	int fillerPosition = converter(x, y); 
    	
        if (convertedPosition.contains(fillerPosition)){
        	return false; 
        }
    	
    	if (converter(x, y) > size*size || converter(x, y)<1){
    		return false; 
    	}
    	
    	if (x>=size || y>=size) {
    		return false; 
    	}
    	if(x<0 || y<0) {
    		return false; 
    	}
    	
    	/*int fillerY = position.peek(); 
    	position.pop(); 
    	int fillerX = position.peek(); 
    	position.push(fillerY); 
    	
    	if(fillerY==y && fillerX==x) {
    		return false; 
    	}
    	*/
    	else {
    		return true; 
    	}
    }
    
    
    public void printStack() {  // not necessary, but may prove helpful in debugging...
    	for (int i = 0; i < position.size(); i++) {
            System.out.print(position.get(i) + " ");
        }
        System.out.println();
    }
    
    public void findSolutions() {
    	
    	int [][] solver = new int [size][size]; //create matrix
    	
    	for (int i = 0; i < size; i++) // creation of matrix such that all null space is filled w/ 1k 
            for (int j = 0; j < size; j++)
                solver[i][j] = 100;
    	
    	solver[0][0] = 1;  //set first block to 1 since when examples were given, all solutions started with block 00
    	//Problem division line -------------
    	int[] xMove = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] yMove = { 1, 2, 2, 1, -1, -2, -2, -1 };
        
        int lastX = 0;
        int lastY = 0;
        position.push(0); 
        position.push(0); 
        
        boolean done = false; 
        int count = 2; 
    	
        while (!done && !position.isEmpty()) {
        	for (int k = 0; k<8; k++) {
        		int yCord = lastY + yMove[k]; 
        		int xCord = lastX + xMove[k]; 
        		//System.out.println (yCord); 
        		//System.out.println (xCord); 
        		if(isValid(xCord, yCord)) {
            		solver [yCord][xCord] = count; 
            		count++; 
            		lastX = xCord; 
            		lastY = yCord; 
            		position.push(xCord); 
            		position.push(yCord); 
            		convertedPosition.add(converter(xCord, yCord)); 
            		break; 
        		}
        		else if(k == 7) {
        			position.pop(); 
                	position.pop();
                	lastY = position.peek();
                	position.pop(); 
                	lastX = position.peek();
                	position.push(lastY); 
        		}
        	}
        	if (convertedPosition.size()>=size*size) {
        		done = true; 
        	}
        }
        System.out.println(convertedPosition.toString()); 
    	printStack(); 
        visited = solver; 
        printBoard(); 
    }
    
    public void printBoard() {  // call this method each time a solution is found
    	int numSolutions = 1; 
        System.out.println("Solution " + numSolutions);
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++)
                System.out.print(visited[r][c] + "\t");
            System.out.println();
        }
        System.out.println();
    }
    

    public static void main(String[] args) {
        System.out.println("Enter board size to solve: ");
        Scanner scanner = new Scanner(System.in);
        int boardSize = scanner.nextInt();
        scanner.close();
        KnightsTourSolver knightsTourSolver = new KnightsTourSolver(boardSize);
        knightsTourSolver.findSolutions();
    }
}
