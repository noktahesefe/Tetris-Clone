import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;


public class TetrisBoard extends JFrame{

    /**
     * inner class for arrow key controls
     */
    private class arrowListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_UP:
                    int initY = currTetro.letterCoordinatesOnBoard[0][1], initX = currTetro.letterCoordinatesOnBoard[0][0];
                    currTetro.rotate(utils.route.toRight);
                    add(currTetro, initY, initX);
                    break;
                case KeyEvent.VK_DOWN:
                    move(currTetro, utils.route.toDown);
                    break;
                case KeyEvent.VK_LEFT:
                    move(currTetro, utils.route.toLeft);
                    break;
                case KeyEvent.VK_RIGHT:
                    move(currTetro, utils.route.toRight);
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }

    private int row;
    private int col;

    public boolean isGameStart;

    /**
     * tetromino which moving on board
     */
    public Tetromino currTetro;

    /**
     * tetromino which will send to board
     */
    public Tetromino nextTetro = new Tetromino();


    /**
     * Area where the objects are created. In addition, the game stops when the board fills up to this limit.
     */
    private final int creationAreaHeight = 4;


    /**
     * Tetris Board
     */
    public JButton[][] gameArea;

    /**
     * matrix of window of next tetro
     */
    public JButton[][] nextTetroArea;
    /* GETTERS */


    /**
     * it returns row
     * @return row
     */
    public int getRowSize()
    {
        return row;
    }

    /**
     * it returns col
     * @return col
     */
    public int getColSize()
    {
        return col;
    }

    /**
     * it returns creationAreaHeight
     * @return creationAreaHeight
     * @see TetrisBoard#creationAreaHeight creationAreaHeight
     */
    public int getCreationAreaHeight() { return creationAreaHeight; }

    /* END OF GETTERS*/

    /**
     * it adds given tetromino to Tetris Board
     * @param tetro
     * a Tetromino for add to board
     */
    public void add(Tetromino tetro)
    {
        currTetro = tetro;
        int[][] tetroLetterIndexes = tetro.getLetterCoordinates(); //takes coordinates of letters from matrix of tetro

        //indexes of places to start drawing
        int startXIndex = this.col/2;
        int startYIndex = getCreationAreaHeight()-1;

        //reference points
        int referenceY = tetroLetterIndexes[0][0];
        int referenceX = tetroLetterIndexes[0][1];

        //drawing tetro to board by tetroLetterIndexes
        for(int i=0; i<4; i++)
        {
            this.gameArea[startYIndex+(tetroLetterIndexes[i][0]-referenceY)][startXIndex+(tetroLetterIndexes[i][1]-referenceX)].setBackground(tetro.getColor()); //tetro.getLetter();
            tetro.letterCoordinatesOnBoard[i][0] = startYIndex+(tetroLetterIndexes[i][0]-referenceY);
            tetro.letterCoordinatesOnBoard[i][1] = startXIndex+(tetroLetterIndexes[i][1]-referenceX);
        }
    }

    /**
     * it add tetro to given indexes
     * @param tetro tetromino for add to board
     * @param initX given X coordinate
     * @param initY given Y coordinate
     */
    public void add(Tetromino tetro, int initX, int initY)
    {
        currTetro = tetro;

        int[][] tetroLetterIndexes = tetro.getLetterCoordinates(); //takes coordinates of letters from matrix of tetro

        //indexes of places to start drawing
        int startXIndex = initX;
        int startYIndex = initY;

        //reference points
        int referenceY = tetroLetterIndexes[0][0];
        int referenceX = tetroLetterIndexes[0][1];

        //drawing tetro to board by tetroLetterIndexes
        if(canDrawable(tetro, initX, initY))
        {
            this.clearIndexes(currTetro.letterCoordinatesOnBoard);
            for(int i=0; i<4; i++)
            {
                this.gameArea[startYIndex+(tetroLetterIndexes[i][0]-referenceY)][startXIndex+(tetroLetterIndexes[i][1]-referenceX)].setBackground(tetro.getColor()); //tetro.getLetter();
                tetro.letterCoordinatesOnBoard[i][0] = startYIndex+(tetroLetterIndexes[i][0]-referenceY);
                tetro.letterCoordinatesOnBoard[i][1] = startXIndex+(tetroLetterIndexes[i][1]-referenceX);
            }
        }

    }

    /**
     * it checks canDrawable tetromino after rotate
     * @param tetro tetromino for checks
     * @param initX X coordinate for check
     * @param initY Y coordinate for check
     * @return if tetromino can drawable for given coordinates it returns true otherwise false
     */
    public boolean canDrawable(Tetromino tetro, int initX, int initY)
    {
        int[][] tetroLetterIndexes = tetro.getLetterCoordinates(); //takes coordinates of letters from matrix of tetro

        //indexes of places to start drawing
        int startXIndex = initX;
        int startYIndex = initY;

        //reference points
        int referenceY = tetroLetterIndexes[0][0];
        int referenceX = tetroLetterIndexes[0][1];

        //drawing tetro to board by tetroLetterIndexes
        for(int i=0; i<4; i++)
            if(startYIndex+(tetroLetterIndexes[i][0]-referenceY) < 0 || startYIndex+(tetroLetterIndexes[i][0]-referenceY) >= this.getRowSize() || startXIndex+(tetroLetterIndexes[i][1]-referenceX) < 0 || startXIndex+(tetroLetterIndexes[i][1]-referenceX) >= this.getColSize() || (this.gameArea[startYIndex+(tetroLetterIndexes[i][0]-referenceY)][startXIndex+(tetroLetterIndexes[i][1]-referenceX)].getBackground() != areaBg && this.gameArea[startYIndex+(tetroLetterIndexes[i][0]-referenceY)][startXIndex+(tetroLetterIndexes[i][1]-referenceX)].getBackground() != currTetro.getColor()))
                return false;

        return true;
    }

    /**
     * Moves the given tetromino on the board in the given direction.
     * @param tetro
     * A tetromino which move on board
     * @param direction
     * direction of move of tetromino
     * @return return true if move succesful
     */
    public boolean move(Tetromino tetro, utils.route direction)
    {
        int xStep = 0, yStep = 0;

        switch(direction)
        {
            case toRight:
                xStep = 1;
                break;
            case toLeft:
                xStep = -1;
                break;
            case toDown:
                yStep = 1;
                break;
            case toUp:
                yStep = -1;
                break;
        }

        int[][] coordinates = new int[4][2];
        for(int i=0; i<4; i++)
        {
            coordinates[i][0] =  tetro.letterCoordinatesOnBoard[i][0];
            coordinates[i][1] =  tetro.letterCoordinatesOnBoard[i][1];
        }

        JButton[][] tempBoard = new JButton[row][col];
        for(int i=0; i<row; i++)
        {
            for(int j=0; j<col; j++)
            {
                tempBoard[i][j] = new JButton();
                tempBoard[i][j].setBackground(gameArea[i][j].getBackground());
            }
        }

        clearIndexes(coordinates);

        for(int i=0; i<4; i++)
        {
            int yOfelement = coordinates[i][0], xOfelement = coordinates[i][1];
            //checking collision
            if(xOfelement+xStep < 0 || xOfelement+xStep >= this.getColSize() || yOfelement+yStep < 0 || yOfelement+yStep >= this.getRowSize() || this.gameArea[yOfelement+yStep][xOfelement+xStep].getBackground() != areaBg)
            {
                for(int j=0; j<row; j++)
                    for(int k=0; k<col; k++)
                        gameArea[j][k].setBackground(tempBoard[j][k].getBackground());

                return false;
            }

        }

        for(int i=0; i<4; i++)
        {
            int yOfelement = tetro.letterCoordinatesOnBoard[i][0], xOfelement = tetro.letterCoordinatesOnBoard[i][1];

            this.gameArea[yOfelement+yStep][xOfelement+xStep].setBackground(tetro.getColor());
            tetro.letterCoordinatesOnBoard[i][0] = yOfelement+yStep;
            tetro.letterCoordinatesOnBoard[i][1] = xOfelement+xStep;

        }

        return true;
    }


    /**
     * it clear given indexes on board, it using when collision while drawing letter to map
     * @param indexes
     * Indexes which will clear on board
     */
    public void clearIndexes(int[][] indexes)
    {
        for(int i=0; i<4; i++)
            gameArea[indexes[i][0]][indexes[i][1]].setBackground(areaBg);
    }

    Color areaBorder = new Color(31, 31, 31);
    Color areaBg = new Color(62, 62, 62);

    /**
     * no parameter constructor
     */
    TetrisBoard()
    {
        row = getCreationAreaHeight();
        col = 4;
        gameArea = new JButton[row][col];
        for(int i=0; i<row; i++)
        {
            for(int j=0; j<col; j++)
            {
                gameArea[i][j] = new JButton();
                gameArea[i][j].setBackground(areaBg);
            }
        }

    }



    /**
     * program check tetromino is stopped with this variable
     */
    public boolean isTetroPlaced = true;
    Timer timer;
    JLabel score;

    /**
     * It creates an Tetris Game which given board areas
     * @param areaRow
     * area of row of Tetris Board
     * @param areaCol
     * area of col of Tetris Board
     */
    TetrisBoard(int areaRow, int areaCol)
    {

        isGameStart = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1000, 1000));
        setLayout(new GridLayout(1,2,0,0));
        setFocusable(true);

        addKeyListener(new arrowListener());
        pack();

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(24,12,0,0));
        row = areaRow;
        col = areaCol;

        gameArea = new JButton[row][col];

        for(int i=0; i<row; i++)
        {
            for(int j=0; j<col; j++)
            {
                gameArea[i][j] = new JButton();
                gameArea[i][j].setEnabled(false);
                gameArea[i][j].setBackground(areaBg);
                gameArea[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gamePanel.add(gameArea[i][j]);
            }
        }
        this.add(gamePanel, BorderLayout.WEST);
        gamePanel.setBorder(new EtchedBorder(EtchedBorder.RAISED));
        gamePanel.setBackground(areaBg);

        JPanel nextTetroPanel = new JPanel();

        nextTetroPanel.setLayout(new GridLayout(6,6,0,0));
        nextTetroArea = new JButton[4][3];

        int tetroRow = -1, tetroCol = 0;

        for(int i=0; i<6; i++)
        {
            tetroCol = 0;
            for(int j=0; j<6; j++)
            {

                if(i==0 || j==0 || i>4 || j>3)
                    nextTetroPanel.add(Box.createHorizontalStrut(10));
                else
                {
                    nextTetroArea[tetroRow][tetroCol] = new JButton();
                    nextTetroArea[tetroRow][tetroCol].setEnabled(false);
                    nextTetroArea[tetroRow][tetroCol].setBackground(areaBg);
                    nextTetroArea[tetroRow][tetroCol].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    nextTetroPanel.add(nextTetroArea[tetroRow][tetroCol]);
                    tetroCol++;
                }
            }

            tetroRow++;

        }

        setNextTetro();


        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(isTetroPlaced)
                {
                    Tetromino tetro = new Tetromino();
                    char letter = nextTetro.getLetter();
                    utils.shapes Letter = utils.shapes.valueOf(String.valueOf(letter));
                    tetro.setShape(Letter);

                    add(tetro,5,4);
                    setNextTetro();
                    isTetroPlaced = false;
                }
                else
                {
                    if(!move(currTetro, utils.route.toDown))
                    {
                        if(currTetro.letterCoordinatesOnBoard[3][0] <= creationAreaHeight-1)
                        {
                            JOptionPane.showMessageDialog(TetrisBoard.super.

                                    rootPane, "Game Over");
                                    timer.stop();
                        }

                        isTetroPlaced = true;
                        checkLine();
                    }
                }

            }
        });



        JPanel optionsPanel = new JPanel();

        timer.start();

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        quitButton.setSize(30,30);
        JLabel scoreLabel = new JLabel("Score : ");
        scoreLabel.setForeground(Color.WHITE);
        score = new JLabel("0");
        score.setForeground(Color.WHITE);


        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1,2,10,10));
        scorePanel.add(scoreLabel);
        scorePanel.add(score);
        scorePanel.setBackground(areaBg);

        optionsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        optionsPanel.add(scorePanel);
        optionsPanel.add(quitButton);


        JPanel cont = new JPanel();
        Component empty = Box.createHorizontalStrut(5);


        cont.setLayout(new GridLayout(5,1,0,0));
        cont.add(Box.createHorizontalStrut(5));
        cont.add(Box.createHorizontalStrut(5));
        cont.add(Box.createHorizontalStrut(5));
        cont.add(Box.createHorizontalStrut(5));
        cont.add(nextTetroPanel, BorderLayout.CENTER);
        cont.add(optionsPanel, BorderLayout.CENTER);

        cont.add(Box.createHorizontalStrut(10));
        cont.setBackground(areaBg);
        nextTetroPanel.setBackground(areaBg);
        optionsPanel.setBackground(areaBg);

        add(cont, BorderLayout.EAST);


        setVisible(true);


    }

    /**
     * it prepares next tetromino panel
     */
    private void setNextTetro()
    {
        char letter = utils.getRandomTetroType();
        utils.shapes Letter = utils.shapes.valueOf(String.valueOf(letter));
        nextTetro.setShape(Letter);

        for(int i=0; i<4; i++)
            for(int j=0; j<3; j++)
                nextTetroArea[i][j].setBackground(areaBg);

        for(int[] idx : nextTetro.getLetterCoordinates())
                nextTetroArea[idx[0]][idx[1]].setBackground(nextTetro.getColor());

    }

    /**
     * it checks line if it can deletable it delete and slide other rows
     */
    public void checkLine()
    {
        boolean isFill;
        int row = this.getRowSize()-1;
        while (row >= 1)
        {
            isFill = true;
            for(int col = 0; col < this.getColSize(); col++)
            {
                if(this.gameArea[row][col].getBackground() == areaBg)
                {
                    isFill = false;
                    break;
                }
            }

            if(isFill)
            {
                for(int i=row; i>=1; i--)
                    for(int j=0; j<this.getColSize(); j++)
                        gameArea[i][j].setBackground(gameArea[i-1][j].getBackground());

                Integer _score = Integer.parseInt(score.getText()) + 100;
                score.setText(_score.toString());

            }
            else
                row--;

        }

    }

}
