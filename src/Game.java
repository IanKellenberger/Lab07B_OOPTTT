import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private TTTBoard board;
    private TTTPlayer playerX;
    private TTTPlayer playerO;
    private TTTPlayer currentPlayer;
    private JButton[][] buttons;

    public Game() {
        board = new TTTBoard();
        playerX = new TTTPlayer("Player X", 'X');
        playerO = new TTTPlayer("Player O", 'O');
        currentPlayer = playerX;
        buttons = new JButton[3][3];
    }

    public void startGame() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(3, 3));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                int finalRow = row;
                int finalCol = col;
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        makeMove(finalRow, finalCol);
                    }
                });
                frame.add(buttons[row][col]);
            }
        }

        frame.setVisible(true);
    }

    private void makeMove(int row, int col) {
        if (board.placeMove(row, col, currentPlayer.getSymbol())) {
            buttons[row][col].setText(String.valueOf(currentPlayer.getSymbol()));
            if (board.checkWin(currentPlayer.getSymbol())) {
                int response = JOptionPane.showConfirmDialog(null,
                        currentPlayer.getName() + " wins! Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else if (board.isFull()) {
                int response = JOptionPane.showConfirmDialog(null,
                        "It's a tie! Would you like to play again?",
                        "Game Over", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    resetGame();
                } else {
                    System.exit(0);
                }
            } else {
                switchPlayer();
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }

    private void resetGame() {
        board.reset();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(" ");
            }
        }
        currentPlayer = playerX;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
