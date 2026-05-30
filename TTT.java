import javax.swing.*;
import java.awt.*;

class TT{
    char board[][] = new char[3][3];
    char currentPlayer = 'X';
    int moves = 0;
    int winCells[][] = new int[3][2];
    boolean hasWinner = false;

    void initializeBoard(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++) {
                board[i][j] = '-';
            }
        }
    }

    void printBoard(){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
    }

    void playGame(int row, int col) {

        if(board[row][col] == '-'){
            board[row][col] = currentPlayer;
            moves++;
            if(checkWin()){
                System.out.println("Player "+ currentPlayer+" wins");

            }
            else if(moves == 9) {
                System.out.println("It's a draw!");
            }
            else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
        else{
            System.out.println("Invalid move.");
        }
    }
    boolean checkWin(){
        // Rows & Columns
        for(int i = 0; i < 3; i++){
            // Row
            if(board[i][0] == currentPlayer &&
                    board[i][1] == currentPlayer &&
                    board[i][2] == currentPlayer){

                winCells[0] = new int[]{i,0};
                winCells[1] = new int[]{i,1};
                winCells[2] = new int[]{i,2};

                hasWinner = true;
                return true;
            }

            // Column
            if(board[0][i] == currentPlayer &&
                    board[1][i] == currentPlayer &&
                    board[2][i] == currentPlayer){

                winCells[0] = new int[]{0,i};
                winCells[1] = new int[]{1,i};
                winCells[2] = new int[]{2,i};

                hasWinner = true;
                return true;
            }
        }

        // Diagonal
        if(board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer){

            winCells[0] = new int[]{0,0};
            winCells[1] = new int[]{1,1};
            winCells[2] = new int[]{2,2};

            hasWinner = true;
            return true;
        }

        // Anti-diagonal
        if(board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer){

            winCells[0] = new int[]{0,2};
            winCells[1] = new int[]{1,1};
            winCells[2] = new int[]{2,0};

            hasWinner = true;
            return true;
        }

        return false;
    }

}

public class TTT extends JFrame {
    JButton[][] buttons = new JButton[3][3];
    JButton restart;
    JLabel status;
    JPanel grid;
    TT ob = new TT();
    float progress = 0.0f; //for animation
    Timer timer; //for animation

    public TTT() {
        ob.initializeBoard();

        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setLayout(new BorderLayout(3, 3));
        getContentPane().setBackground(new Color(30, 30, 30)); // background
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        status = new JLabel("Player X Turn", SwingConstants.CENTER);
        status.setOpaque(true);
        status.setBackground(new Color(20, 20, 20));
        status.setForeground(Color.WHITE);
        status.setFont(new Font("Segoe UI Emoji", Font.BOLD, 16));

        add(status, BorderLayout.NORTH);

        grid = new JPanel(new GridLayout(3,3)) {
            protected void paintChildren(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                // draw only if win exists
                if(ob.hasWinner) {

                    int cellW = getWidth() / 3;
                    int cellH = getHeight() / 3;

                    int r1 = ob.winCells[0][0];
                    int c1 = ob.winCells[0][1];
                    int r3 = ob.winCells[2][0];
                    int c3 = ob.winCells[2][1];

                    int x1 = c1 * cellW + cellW / 2;
                    int y1 = r1 * cellH + cellH / 2;

                    int x2 = c3 * cellW + cellW / 2;
                    int y2 = r3 * cellH + cellH / 2;

                    // animation point
                    int x = (int)(x1 + (x2 - x1) * progress);
                    int y = (int)(y1 + (y2 - y1) * progress);

                    // ✨ glow layer
                    g2.setColor(new Color(255, 0, 0, 80));
                    g2.setStroke(new BasicStroke(12));
                    g2.drawLine(x1, y1, x, y);

                    // 🔴 main line
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(6));
                    g2.drawLine(x1, y1, x, y);
                }
            }
        };
        grid.setBackground(new Color(50, 50, 50));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 40));
                btn.setBackground(Color.BLACK);
                btn.setForeground(Color.WHITE); // default before click
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

                int row = i;
                int col = j;

                btn.addActionListener(e -> handleMove(row, col));

                buttons[i][j] = btn;
                grid.add(btn);
            }
        }

        add(grid, BorderLayout.CENTER);

        restart = new JButton("Restart 🔁");
        restart.setBackground(new Color(255, 165, 0)); // orange
        restart.setForeground(Color.BLACK);
        restart.setFocusPainted(false);
        restart.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));

        restart.addActionListener(e -> resetGame());
        add(restart, BorderLayout.SOUTH);

        setVisible(true);
    }

    void handleMove(int row, int col) {
        if(ob.board[row][col] == '-') {

            ob.playGame(row, col);

            buttons[row][col].setText(String.valueOf(ob.board[row][col]));

            if(ob.board[row][col] == 'X') {
                buttons[row][col].setForeground(Color.RED); // red
            } else {
                buttons[row][col].setForeground(Color.GREEN); // green
            }

            if(ob.hasWinner) {
                status.setText("Player " + ob.currentPlayer + " Wins! 🎉");
                status.setForeground(new Color(255, 215, 0));
                startAnimation();
                playSound();
                disableBoard();
            }
            else if(ob.moves == 9) {
                status.setText("It's a Draw!");
            }
            else {
                status.setText("Player " + ob.currentPlayer + " Turn");
            }
        }
    }
    void playSound() {
        try {
            java.awt.Toolkit.getDefaultToolkit().beep();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    void startAnimation() {
        progress = 0.0f;

        timer = new Timer(20, e -> {
            progress += 0.05f;

            if(progress >= 1.0f) {
                progress = 1.0f;
                timer.stop();
            }

            grid.repaint();
        });

        timer.start();
    }

    void disableBoard() {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setEnabled(false);
            }
        }
    }

    void resetGame() {
        ob.initializeBoard();
        ob.moves = 0;
        ob.currentPlayer = 'X';
        ob.hasWinner = false;
        progress = 0.0f;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);

                buttons[i][j].setBackground(Color.BLACK);
                buttons[i][j].setForeground(Color.WHITE);
            }
        }

        status.setText("Player X Turn");
        repaint();
    }

    public static void main(String[] args) {
        new TTT();
    }
}
