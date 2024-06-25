import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGame extends JFrame {
    private static final int BOARD_SIZE = 3;
    private JButton[][] buttons;
    private char[][] board;
    private char currentPlayer;

    public TicTacToeGame() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));

        initializeBoard();
        currentPlayer = 'X';

        setVisible(true);
    }

    private void initializeBoard() {
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        board = new char[BOARD_SIZE][BOARD_SIZE];

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 40));
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                add(buttons[row][col]);

                board[row][col] = '-';
            }
        }
    }

    private void checkForWinner(int row, int col) {
        // Check row
        if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != '-') {
            announceWinner(board[row][0]);
        }
        // Check column
        if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != '-') {
            announceWinner(board[0][col]);
        }
        // Check diagonal
        if ((board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-')
                || (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-')) {
            announceWinner(board[1][1]);
        }
        // Check for tie
        boolean tie = true;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    tie = false;
                    break;
                }
            }
            if (!tie) {
                break;
            }
        }
        if (tie) {
            JOptionPane.showMessageDialog(this, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
    }

    private void announceWinner(char winner) {
        JOptionPane.showMessageDialog(this, "Player " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        resetGame();
    }

    private void resetGame() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                buttons[row][col].setEnabled(true);
                buttons[row][col].setText("");
                board[row][col] = '-';
            }
        }
        currentPlayer = 'X';
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (board[row][col] == '-') {
                board[row][col] = currentPlayer;
                buttons[row][col].setText(String.valueOf(currentPlayer));
                buttons[row][col].setEnabled(false);

                checkForWinner(row, col);

                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToeGame();
            }
        });
    }
}
