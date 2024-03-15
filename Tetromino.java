import java.awt.*;
import java.util.Scanner;

/**
 * Tetrominos
 * @author Kubilay Yazman
 *
 */
public class Tetromino {
    /**
     * Letter type of tetromino
     */
    private Color _color;
    private char letter;
    private int targetY;
    private int targetX;

    private static int liveTetromino = 0;
    public int id;

    /**
     * Counter to check if the letter has returned to its origin state
     */
    private int returnOriginCounter;
    private int tetroPosition = 0;
    private int rowSize, colSize;
    /**
     * An area of letter shape in matrix without spaces. All tetrominos using 4 block for drawing. That's why it define constant variable and initiliaze with 4
     */
    private final int letterCount = 4;
	private final int _colSize = 4, _rowSize = 4;

    /**
     * Tetromino area
     */
    private char[][] tetro = new char[4][4];

    /**
     * Coordinates of letters on tetro matrix, this is using for draw tetro to board
     */
    private int[][] letterCoordinates = new int[4][2];

    /**
     * Letter coordinates of tetromino on board, this is using for move tetro or delete tetro from board
     */
    public int[][] letterCoordinatesOnBoard = new int[4][2];

    /**
     * If tetro added to board first time it rotates on top of board with this variable we checks that for other fit operations
     */
    public boolean isRotated = false;
    public utils.route targetRoute;

    public utils.route oppositeOfTargetRoute;

    /* Getters */

    public int getReturnOriginCounter() {return returnOriginCounter;}
    public Color getColor() {return _color;}

    public char getLetter() {return letter;}
    public char[][] getShape() {return tetro;}
    public int getLetterCount() {return letterCount;}
    public int[][] getLetterCoordinates() {return letterCoordinates;}
    public int getTetroPosition() {return tetroPosition;}
    public int getTargetY() {return targetY;}
    public int getTargetX() {return targetX;}

    public boolean isXFitted() { return (this.getTargetX() == this.letterCoordinatesOnBoard[0][1]); }
    public boolean isYFitted() { return (this.getTargetY() == this.letterCoordinatesOnBoard[0][0]); }


    /**
     * the shape of the tetrominos is determined according to the letter entered.
     * @param type shape type
     */
    public void setShape(utils.shapes type)
    {
        _color = type.getShapeType();
        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                tetro[i][j] = ' ';

        switch(type)
        {
            case I:
                tetro[0][0] = tetro[1][0] = tetro[2][0] = tetro[3][0] = 'I';
                returnOriginCounter = 1;
                letter = 'I';
                break;

            case O:
                tetro[0][0] = tetro[0][1] = tetro[1][0] = tetro[1][1] = 'O';
                returnOriginCounter = 0;
                letter = 'O';
                break;

            case T:
                tetro[0][0] = tetro[0][1] = tetro[0][2] = tetro[1][1] = 'T';
                returnOriginCounter = 3;
                letter = 'T';
                break;

            case J:
                tetro[0][1] = tetro[1][1] = tetro[2][1] = tetro[2][0] = 'J';
                returnOriginCounter = 3;
                letter = 'J';
                break;

            case L:
                tetro[0][0] = tetro[1][0] = tetro[2][0] = tetro[2][1] = 'L';
                returnOriginCounter = 3;
                letter = 'L';
                break;

            case S:
                tetro[0][1] = tetro[0][2] = tetro[1][0] = tetro[1][1] = 'S';
                returnOriginCounter = 1;
                letter = 'S';
                break;

            case Z:
                tetro[0][0] = 'Z';
                tetro[0][1] = 'Z';
                tetro[1][1] = 'Z';
                tetro[1][2] = 'Z';
                returnOriginCounter = 1;
                letter = 'Z';
                break;
        }

        setLetterCoordinates();
    }


    /**
     * function that print tetro
     *
     */
    public final void printTetro() {
        for(char[] row : tetro) {
            for(char el : row) {
                System.out.print(el);
            }
            System.out.println();
        }
    }



    /**
     * it determining letter coordinates in tetro matrix
     */
    public void setLetterCoordinates()
    {
        int _rowSize = rowSize, _colSize = colSize;
        int idx = 0;

        for(int row = _rowSize-1; row>=0; row--)
        {
            for(int col = 0; col<_colSize; col++)
            {
                if(tetro[row][col] != ' ')
                {
                    letterCoordinates[idx][0] = row;
                    letterCoordinates[idx][1] = col;
                    idx++;
                }
            }
        }
    }



    /**
     * it determining letter coordinates in tetro matrix
     * @param direction direction for rotate the tetromino
     */
    public void rotate(utils.route direction)
    {

        liveTetromino++;

        int len = 4;
        this.tetroPosition += 1;
        this.tetroPosition %= this.getReturnOriginCounter()+1;

        if(direction == utils.route.toRight)
        {
            for (int x = 0; x < (len + 1) / 2; x ++)
            {
                for (int y = 0; y < len / 2; y++)
                {
                    char value = tetro[len - 1 - y][x];
                    tetro[len - 1 - y][x] = tetro[len - 1 - x][len - y - 1];
                    tetro[len - 1 - x][len - y - 1] = tetro[y][len - 1 -x];
                    tetro[y][len - 1 - x] = tetro[x][y];
                    tetro[x][y] = value;
                }
            }
        }
        else if(direction == utils.route.toLeft)
        {
            for (int x = 0; x < (len + 1) / 2; x ++)
            {
                for (int y = 0; y < len / 2; y++)
                {
                    char value = tetro[x][len - 1 - y];
                    tetro[x][len - 1 - y] = tetro[len - y - 1][len - 1 - x];
                    tetro[len - y - 1][len - 1 - x] = tetro[len - 1 -x][y];
                    tetro[len - 1 - x][y] = tetro[y][x];
                    tetro[y][x] = value;
                }
            }
        }

        setLetterCoordinates(); //setting coordinates of letters in matrix
    }

    //constructor

    /**
     * @param type tetromino shape type
     */
    Tetromino(utils.shapes type)
    {
        id = liveTetromino++;
        rowSize = _rowSize;
        colSize = _colSize;

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                tetro[i][j] = ' ';

        setShape(type);
    }

    /**
     * no parameter constructor
     */
    Tetromino()
    {
        id = liveTetromino++;
        rowSize = _rowSize;
        colSize = _colSize;

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                tetro[i][j] = ' ';

    }

    Tetromino(Tetromino givenTetro)
    {
        rowSize = _rowSize;
        colSize = _colSize;

        for(int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                tetro[i][j] = givenTetro.tetro[i][j];

        this.setLetterCoordinates();
    }

    /**
     * Sets the coordinates where the tetromino will go
     * @param targetCol target col
     * @param targetRow target row
     */
    public void setTargetCoordinates(int targetCol, int targetRow) {
        targetY = targetRow;
        targetX = targetCol;
    }
};