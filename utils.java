import java.awt.*;
import java.util.Random;

/**
 * helper functions and enums
 */
public class utils {
    /**
     * It is checked whether the input received from the user is valid. (Tetromino letters, R and Q are valid)
     * @param c input for check
     * @return true if is input valid
     */
    public static boolean isValidInput(char c) //it check input is valid
    {
        return (c == 'I' || c == 'O' || c == 'T' || c == 'J' || c == 'L' || c == 'S' || c == 'Z' || c == 'Q' || c == 'R');
    }

    /**
     * it clears terminal
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static char getRandomTetroType()
    {
        char[] tetros = new char[]{'I','O','T','J','L','S','Z'};
        Random rand = new Random();
        return tetros[rand.nextInt(7)];
    }

    /**
     * Directions. It uses in move and rotate functions
     * @see TetrisBoard#move(Tetromino, route)
     * @see Tetromino#rotate(route)
     */
    public enum route {toRight, toLeft, toUp, toDown};

    /**
     * Tetromino shapes
     */
    public enum shapes
    {
        I('I'), O('O'), T('T'), J('J'), L('L'), S('S'), Z('Z');

        private Color shape;

        private shapes(char givenShape)
        {
            switch (givenShape)
            {
                case 'I':
                    shape = new Color(19,218,249);
                    break;
                case 'O':
                    shape = new Color(218,230,42);
                    break;
                case 'T':
                    shape = new Color(120,71,236);;
                    break;
                case 'J':
                    shape = new Color(16, 56, 215);
                    break;
                case 'L':
                    shape = new Color(215, 108, 16);
                    break;
                case 'S':
                    shape = new Color(53, 241, 23);
                    break;
                case 'Z':
                    shape = new Color(238,38,65);
                    break;
            }
        }

        public Color getShapeType() {
            return shape;
        }
    }
}
