import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private JLabel statusLabel = new JLabel("Player X's Turn");

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        // Create buttons and add to panel
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(font);
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(this);
                boardPanel.add(buttons[i][j]);
            }
        }

        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(statusLabel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();

        if (!clicked.getText().equals("")) {
            return; // Cell already filled
        }

        clicked.setText(String.valueOf(currentPlayer));

        if (checkWinner()) {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableButtons();
            showRestartDialog();
        } else if (isDraw()) {
            statusLabel.setText("It's a draw!");
            showRestartDialog();
        } else {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s Turn");
        }
    }

    private boolean checkWinner() {
        String symbol = String.valueOf(currentPlayer);

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (symbol.equals(buttons[i][0].getText()) &&
                    symbol.equals(buttons[i][1].getText()) &&
                    symbol.equals(buttons[i][2].getText()))
                return true;
            if (symbol.equals(buttons[0][i].getText()) &&
                    symbol.equals(buttons[1][i].getText()) &&
                    symbol.equals(buttons[2][i].getText()))
                return true;
        }

        // Check diagonals
        if (symbol.equals(buttons[0][0].getText()) &&
                symbol.equals(buttons[1][1].getText()) &&
                symbol.equals(buttons[2][2].getText()))
            return true;
        if (symbol.equals(buttons[0][2].getText()) &&
                symbol.equals(buttons[1][1].getText()) &&
                symbol.equals(buttons[2][0].getText()))
            return true;

        return false;
    }

    private boolean isDraw() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().equals(""))
                    return false;
        return true;
    }

    private void disableButtons() {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                btn.setEnabled(false);
    }

    private void resetBoard() {
        for (JButton[] row : buttons)
            for (JButton btn : row) {
                btn.setText("");
                btn.setEnabled(true);
            }
        currentPlayer = 'X';
        statusLabel.setText("Player X's Turn");
    }

    private void showRestartDialog() {
        int option = JOptionPane.showConfirmDialog(this,
                "Do you want to play again?", "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}

