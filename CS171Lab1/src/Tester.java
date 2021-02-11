import java.util.Scanner;
import java.util.Stack;


public class Tester {
    
    private int boardSize;
    private Stack<Integer> colPositions;
    private int solutionsFound = 0;
    
    public Tester(int boardSize) {
        this.boardSize = boardSize;
        colPositions = new Stack<Integer>();
    }
    
    private boolean isValid(int col) {
        
        for (int i = 0; i < colPositions.size(); i++) {
            if (col >= boardSize) return false;
            int ci = colPositions.get(i);
            int r = colPositions.size();
            int c = col;
            
            if (ci == c)          return false; //column conflict with previously placed queen
            if (c - ci == r - i)  return false; //major diagonal conflict with previously placed queen
            if (ci - c == r - i)  return false; //minor diagonal conflict with previously placed queen
        }
        return true; //if you got here, there were no conflicts!
    }
    
    private void printSolution() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                System.out.print(colPositions.get(r) == c ? " Q " : " * ");
            }
            System.out.println();
        }
        System.out.println(); //put a blank line after the solution to separate solutions from one another visually
    }
    
    private void printStack() {
        for (int i = 0; i < colPositions.size(); i++) {
            System.out.print(colPositions.get(i) + " ");
        }
        System.out.println();
    }
    
    public void findSolutions() {
        int col = 0;
        
        //printStack();  // UNCOMMENT FOR DEBUGGING
        
        boolean done = false;
        while (! done) {
            
            while ((! isValid(col)) && (col < boardSize)) {
                col++;
            }
            
            if (col < boardSize) {  //if valid queen placement found on the current row, then...
                
                colPositions.push(col);  // put this placement on the stack, and try to place the next queen (in the next row)
                col = 0;                  // again testing placements from col=0 to col=7...
            }
            else { // no valid queen placement was found on the current row, so...
                
                if (colPositions.isEmpty()) {
                    done = true;
                    break;
                }
                else {
                    col = colPositions.pop() + 1;
                }
                
            }
            
            if (colPositions.size() == boardSize) {
                printSolution();
                col = colPositions.pop() + 1;
                solutionsFound++;
            }
            
            //printStack(); // UNCOMMENT FOR DEBUGGING
        }
        System.out.println("Solutions Found: " + solutionsFound);
        
        colPositions = new Stack<Integer>();  // reset just in case this method 
        solutionsFound = 0;                   // gets called more than once
    }
    
    public static void main(String[] args) {
        System.out.println("Enter board size to solve: ");
        Scanner scanner = new Scanner(System.in);
        int boardSize = scanner.nextInt();
        scanner.close();
        Tester nQueensSolver = new Tester(boardSize);
        nQueensSolver.findSolutions();
    }

}
