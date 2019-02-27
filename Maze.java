import java.io.*;
import java.util.*;

public class Maze {
  private char[][]maze;
  private boolean animate;//false by default

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
      File text = new File(filename);
      Scanner in = new Scanner(text);
      String lines = "";
      int count = 1;
      int length = 0;
      while (in.hasNextLine()) {
        String current = in.nextLine();
        length = current.length();
        lines += current + "\n";
        count++;
      }
      char[][] maze = new char[count][length];
      int index = 0;
      int charIndex = 0;
      while (index < count && charIndex > lines.length()) {
    //    System.out.println(index+","+charIndex+","+lines.charAt(charIndex));
        maze[index][charIndex % length] = lines.charAt(charIndex);
        charIndex++;
        if (lines.charAt(charIndex) == '\n') {
          System.out.println(Arrays.toString(maze[index]));
          index++;
          charIndex++;
        }
      }
      // Printing out 2D array of maze
      for (char[] c : maze) {
        System.out.println(Arrays.toString(c));
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    }
      //COMPLETE CONSTRUCTOR
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

  /*Wrapper Solve Function returns the helper function
    Note the helper function has the same name, but different parameters.
    Since the constructor exits when the file is not found or is missing an E or S, we can assume it exists.
  */
  public int solve(){

            //find the location of the S.


            //erase the S


            //and start solving at the location of the s.

            //return solve(???,???);
    return -1;
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
  private int solve(int row, int col){ //you can add more parameters since this is private
        //automatic animation! You are welcome.
    if(animate){
      clearTerminal();
      System.out.println(this);
      wait(20);
    }

        //COMPLETE SOLVE

    return -1; //so it compiles
  }

  public static void main(String[] args) {
    try {
      Maze m = new Maze("Maze1.txt");
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }
}
