/**
 * Created by Toshiba on 3/22/2016.
 */
public class MazeData {

    public final static int EMPTY = 0;
    public final static int BLOCK = 1;
    public final static int NORMAL_DOT = 2;
    public final static int MAGIC_DOT = 3;
    public static int DOT_TOTAL = 0;
    public static final int GRID_SIZE_X = 29;
    public static final int GRID_SIZE_Y = 31;
    public static int mazeData[][] = new int[GRID_SIZE_X + 1][GRID_SIZE_Y + 1];
    public final static int GRID_GAP = 16;
    public final static int GRID_STROKE = 2;
    final static int xoffset = GRID_GAP * 2;
    final static int yoffset = GRID_GAP * 2;

    private static int makeInRange(int a, char coordinate) {

        if (a < 0) {
            return 0;
        }
        else if ((coordinate == 'X') && (a > GRID_SIZE_X)) {
            return GRID_SIZE_X;
        }
        else if ((coordinate == 'Y') && (a > GRID_SIZE_Y)) {
            return GRID_SIZE_Y;
        }

        return a;
    }

    // set the grid of maze data to be BLOCK
    public static void setBlockMazeData(int x1, int y1, int x2, int y2) {
        x1 = makeInRange(x1,'X');
        y1 = makeInRange(y1,'Y');
        x2 = makeInRange(x2,'X');
        y2 = makeInRange(y2,'Y');

        for (int i = x1; i <= x2; i++) {
            mazeData[i][y1] = BLOCK;
            mazeData[i][y2] = BLOCK;

        }
        for (int i = y1; i <= y2; i++) {
            mazeData[x1][i] = BLOCK;
            mazeData[x2][i] = BLOCK;
        }
    }

    public static double calcGridX(double x) {
            return GRID_GAP * x + xoffset;
    }

    public static double calcGridY(double y) {
            return GRID_GAP * y + yoffset;
    }

    public static int getData(int x, int y) {
            return mazeData[x][y];
    }

    public static void setData(int x, int y, int value) {
            mazeData[x][y] = value;

        if ((value == MAGIC_DOT) || (value == NORMAL_DOT)) {
                DOT_TOTAL++;
            }
        } // end setData
    }
