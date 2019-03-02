import java.io.*;
import java.util.*;

public class Maze {
  private char[][]maze;
  private boolean animate = false;//false by default

  /*Constructor loads a maze text file, and sets animate to false by default.
    1. The file contains a rectangular ascii maze, made with the following 4 characters:
    '#' - Walls - locations that cannot be moved onto
    ' ' - Empty Space - locations that can be moved onto
    'E' - the location of the goal (exactly 1 per file)
    'S' - the location of the start(exactly 1 per file)

    2. The maze has a border of '#' around the edges. So you don't have to check for out of bounds!

    3. When the file is not found OR the file is invalid (not exactly 1 E and 1 S) then:
      throw a FileNotFoundException or IllegalStateException
  */
  public Maze(String filename) throws FileNotFoundException{
    try {
      setAnimate(true);
      File text = new File(filename);
      Scanner in = new Scanner(text);
      String lines = "";
      int count = 1;
      int length = 0;
      while (in.hasNextLine()) {
        String current = in.nextLine();
        length = current.length()+1;
        lines += current + "\n";
        count++;
      }
    //  System.out.println(count+","+length);
      maze = new char[count][length];
      int index = 0;
      int charIndex = 0;
      while (index < count && charIndex < lines.length()) {
        maze[index][charIndex % length] = lines.charAt(charIndex);
        charIndex++;
        if (lines.charAt(charIndex) == '\n') {
          index++;
          charIndex++;
        }
      }
      int e = 0;
      int s = 0;
      for (int i = 0; i < maze.length; i++) {
        for (int x = 0; x < maze[i].length; x++) {
          if (maze[i][x] == 'E') {
            e++;
          }
          if (maze[i][x] == 'S') {
            s++;
          }
        }
      }
      if (e != 1 || s != 1) {
        throw new IllegalStateException("Not the right number of starts or ends");
      }
      // Printing out 2D array of maze
    //  for (char[] c : maze) {
      //  System.out.println(Arrays.toString(c));
      //}
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
  }


  private void wait(int millis){
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
    }
  }


  public void setAnimate(boolean b){
    animate = b;
  }

  public void clearTerminal(){
    //erase terminal, go to top left of screen.
    System.out.println("\033[2J\033[1;1H");
  }

  /*Return the string that represents the maze.
     It should look like the text file with some characters replaced.
  */
  public String toString(){
    String result = "";
  //  System.out.println(maze.length);
    for (int i = 0; i < maze.length; i++) {
      for (int x = 0; x < maze[i].length; x++) {
        result += maze[i][x];
      }
      result += "\n";
    }
    return result;
  }

  /*Wrapper Solve Function returns the helper function
    Note the helper function has the same name, but different parameters.
    Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
  */
  public int solve(){
    // find location of s
    int x = 0;
    int y = 0;
    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[i].length; j++) {
        if (maze[i][j] == 'S') {
          x = i;
          y = j;
        }
      }
    }
    maze[x][y] = ' ';
  //  System.out.println(toString());
    return solve(x,y,0);
  }

  /*
    Recursive Solve function:
    A solved maze has a path marked with '@' from S to E.
    Returns the number of @ symbols from S to E when the maze is solved,
    Returns -1 when the maze has no solution.
    Postcondition:
      The S is replaced with '@' but the 'E' is not.
      All visited spots that were not part of the solution are changed to '.'
      All visited spots that are part of the solution are changed to '@'
  */
  private int solve(int row, int col, int steps){ // steps is number of @ symbols
    //automatic animation! You are welcome.
    if(animate){
      clearTerminal();
      System.out.println(this);
      wait(20);
    }
    // end of automatic code
    if (maze[row][col] == 'E') { // finished maze
    //  System.out.println(steps);
      return steps;
    }
    if (maze[row][col] != ' ') { // not a viable move
      return -1; // failed
    }
    // possible moves up, right, down, left
    int[] xMoves = new int[] { 0, 1, 0, -1 };
    int[] yMoves = new int[] { -1, 0, 1, 0 };
    for (int i = 0; i < 4; i++) {
      maze[row][col] = '@'; // set current place as part of solution
      int next = solve(row+xMoves[i],col+yMoves[i], steps+1); // next move, branching out
      if (next != -1) { // this is a solution !!!
        return next; // returns steps yielded from the successful run
      }
      // all else has failed, this move yields no solution
      maze[row][col] = '.';
    }
    return -1;
  }

  public static void main(String[] args) {
    try {
      Maze m = new Maze(args[0]);
      System.out.println(m.toString());
      System.out.println(m.solve());
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }
}
