import java.util.*;
public class TTT{
    static char board[][] = new char[3][3];
    static char currentPlayer = 'X';
    static int moves = 0;

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

    void playGame() {
        Scanner sc = new Scanner(System.in);

        while(true) {
            if(moves == 9) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }
            printBoard();
            System.out.println("Player"+currentPlayer+" turn");
            int row, col;

            if(currentPlayer == 'X') {

                System.out.println("Player X turn");
                row = sc.nextInt();
                col = sc.nextInt();

            }
            else {

                Scanner sc = new Scanner(System.in);
                Random rand = new Random();

                do {
                    row = rand.nextInt(3);
                    col = rand.nextInt(3);
                } while(board[row][col] != '-');

                System.out.println("Computer chose: " + row + " " + col);
            }

            if(row < 0 || row > 2 || col < 0 || col > 2) {
                System.out.println("Invalid input! Enter values between 0 and 2.");
                continue;
            }

            if(board[row][col] == '-'){
                board[row][col] = currentPlayer;
                moves++;
                if(checkWin()){
                    System.out.println("Player"+ currentPlayer+" wins");
                    break;
                }
                currentPlayer = (currentPlayer=='X')? 'O':'X';
            }
            else{
                System.out.println("Invalid move.");
            }
        }
    }
    boolean checkWin(){
        for(int i  =0; i<3; i++){
            if((board[i][0] == currentPlayer &&
                    board[i][1] == currentPlayer &&
                    board[i][2] == currentPlayer) ||
                    (board[0][i] == currentPlayer &&
                            board[1][i] == currentPlayer &&
                            board[2][i] == currentPlayer ) ) {
                return true;
            }
        }
        if((board[0][0] ==currentPlayer &&
                board[1][1] ==currentPlayer &&
                board[2][2] ==currentPlayer ) ||
                (board[0][2] == currentPlayer &&
                        board[1][1] == currentPlayer &&
                        board[2][0] == currentPlayer )) {
            return true;
        }
        return false;
    }
    public static void main(String args[]) {
        TTT ob = new TTT();
        ob.initializeBoard();
        ob.playGame();
    }

}