package cs3500.pa03.view;


import java.io.PrintStream;

/**
 * The view of the BattleSalvo
 */
public class BattleSalvoView {
  private final PrintStream out;

  public BattleSalvoView(PrintStream out) {
    this.out = out;
  }


  /**
   * Shows the given prompt to the user
   */
  public void showPrompt(String prompt) {
    System.out.println(prompt);
  }

  /**
   * prints the board for the user to see
   *
   * @param board the board that should be displayed to the user
   */
  public void printBoard(char[][] board) {
    for (char[] chars : board) {
      for (int j = 0; j < chars.length; j++) {
        System.out.print(chars[j] + " ");
      }
      System.out.println();
    }
  }
}
