import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class KnightsTourSolver {

    public static final int NUMBER_OF_KNIGHT_MOVES = 8;

    private int size;               // size of the board - note, sizes greater than 6 result in MANY solutions
    private int[][] visited;        // the array that stores each solution found, for subsequent printing
    private Stack<Integer> position;   // a stack, for use in accomplishing the bactracking
    ArrayList <Integer> convertedPosition;  
    public  int xCord = 0; 
    public  int yCord = 0;
    public int lastXCord = 0; 
    public int lastYCord = 0; 
    public static int numSolutions = 1; 
    public int finalXCord = -1; 
    public int finalYCord = -1; 

    public KnightsTourSolver(int size) {
        this.size = size; 
        position = new Stack<Integer>();
        convertedPosition = new ArrayList<Integer>();
        convertedPosition.add(1); 
    }

    public int converter (int x, int y) { //Converts coordinates into a number that is unique to each square. 
        int yFinal=y*size;
        int xFinal=x+1;

        int converted = yFinal + xFinal; 

        return converted; 
    }

    public boolean isValid(int x, int y) { //method has caveat that technically 0,0 is not valid

        int fillerPosition = converter(x, y); 

        if (convertedPosition.contains(fillerPosition)){
            return false; 
        }

        if (converter(x, y) > size*size || converter(x, y)<2){
            return false; 
        }

        if (x>=size || y>=size) {
            return false; 
        }

        if (Math.abs(lastXCord - xCord)>2 || Math.abs(lastYCord - yCord)>2) { //This line and the next one took me a solid 4 hours of debugging........
            return false;
        }
        
        if (Math.abs(lastXCord - xCord) + Math.abs(lastYCord - yCord) != 3) {
            return false;
        }
        
        if (x==finalXCord && y==finalYCord){
            return false; 
        }

        if(x<0 || y<0) {
            return false; 
        }

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
        //Problem division line ------------- everything below this might be fried Edit: Not anymore! 
        //int debuggingCount = 0; 
        int count = 2; 
        int kPosition = 0; //array position
        boolean done = false; 
        int[] xMove = { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] yMove = { 1, 2, 2, 1, -1, -2, -2, -1 };
        position.push(8); 

        while (!done) {
            while ((! isValid(xCord, yCord)) && (kPosition < 8)) {
                xCord = lastXCord + xMove[kPosition]; //moves x y cords. 
                yCord = lastYCord + yMove[kPosition]; 
                kPosition++; //add K try again
            }	
            if (isValid(xCord, yCord)) {
                kPosition--; //because k will always ++ so if it's correct k-- here. 
                position.push(kPosition); //push count to stack
                kPosition = 0; 
                solver[yCord][xCord] = count; //fill in square
                count ++;
                lastXCord = xCord; 
                lastYCord = yCord; 
                convertedPosition.add(converter(xCord, yCord)); //adds to an array that gets checked in isValid for repeats. 
                //debuggingCount ++;  
            }
            else {
                if (position.peek()==8) {
                    done = true; //Exit condition. Only top left corner has k=8
                    break; 
                }
                else {
                    kPosition = position.pop();
                    lastXCord = lastXCord - xMove[kPosition]; //moves last position to the last last one. 
                    lastYCord = lastYCord - yMove[kPosition]; 
                    kPosition = kPosition + 1;
                    /*if(count==5){
                        System.out.println(); 
                    }*/
                    count --; 
                    convertedPosition.remove(convertedPosition.size()-1);
                    finalXCord = -1;
                    finalYCord = -1;
                }
            }
            if (count==(size*size)+1) {
                //printStack(); 
                //System.out.println(convertedPosition.toString()); 
                //System.out.println(debuggingCount);
                visited = solver; 
                printBoard();
                kPosition = position.pop();
                lastXCord = lastXCord - xMove[kPosition]; 
                lastYCord = lastYCord - yMove[kPosition];
                finalXCord = xCord; 
                finalYCord = yCord; 
                kPosition = kPosition + 1; 
                count--; 
                convertedPosition.remove(convertedPosition.size()-1); 
                numSolutions ++;  
                /*if (numSolutions == 111){
                    System.out.println(); 
                }*/
                /*if (numSolutions == 112){ //Ignore. An attempt at hard coding an edge case. 
                	while(!position.isEmpty()){
                    position.pop(); 
                	}
                convertedPosition.clear(); 
                lastXCord = 0; 
                lastYCord = 0; 
                position.push(8); 
                position.push(1); 
                kPosition = position.peek(); 
                count = 2; 
                xCord = 1; 
                yCord = 2; 
            }*/
            }
            
        }
        //System.out.println (debuggingCount);
    }

    public void printBoard() {  // call this method each time a solution is found //This method is given
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



