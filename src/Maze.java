/**
 * Created by Toshiba on 3/22/2016.
 */
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class Maze extends Parent {
    Group group = new Group();
    public BooleanProperty gamePaused;
    public final Ghost[] ghosts;
    private boolean addLifeFlag;
    private final SimpleIntegerProperty livesCount;
    public PacMan pacMan;
    private static final Image PACMAN_IMAGE = new Image(Maze.class.getResourceAsStream("left1.png"));




    public Maze() {

        pacMan = new PacMan(this, 15, 24);

        final Ghost ghostBlinky = new Ghost(
                new Image(getClass().getResourceAsStream("ghostred1.png")),
                new Image(getClass().getResourceAsStream("ghostred2.png")),
                this,
               /* pacMan,*/
                15, // x
                14, // y
                0,  // x Direction
                -1, // y Direction
                1);

        final Ghost ghostPinky = new Ghost(
                new Image(getClass().getResourceAsStream("ghostpink1.png")),
                new Image(getClass().getResourceAsStream("ghostpink2.png")),
                this,
                // pacMan,
                14,
                15,
                1,  // x Direction
                0,  // y Direction
                5);


        final Ghost ghostInky = new Ghost(
                new Image(getClass().getResourceAsStream("ghostcyan1.png")),
                new Image(getClass().getResourceAsStream("ghostcyan2.png")),
                this,
                //pacMan,
                12,
                15,
                1,   // x Direction
                0,   // y Direction
                20);


        final Ghost ghostClyde = new Ghost(
                new Image(getClass().getResourceAsStream("ghostorange1.png")),
                new Image(getClass().getResourceAsStream("ghostorange2.png")),
                this,
                // pacMan,
                16,
                15,
                1,   // x Direction
                0,   // y Direction
                30);

        ghosts = new Ghost[]{ghostBlinky, ghostPinky, ghostInky, ghostClyde};

        livesCount = new SimpleIntegerProperty(2);

        final ImageView livesImage1 = new ImageView(PACMAN_IMAGE);
        livesImage1.setX(MazeData.calcGridX(18));
        livesImage1.setY(MazeData.calcGridY(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage1.visibleProperty().bind(livesCount.greaterThan(0));
        livesImage1.setCache(true);
        final ImageView livesImage2 = new ImageView(PACMAN_IMAGE);
        livesImage2.setX(MazeData.calcGridX(16));
        livesImage2.setY(MazeData.calcGridY(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage2.visibleProperty().bind(livesCount.greaterThan(1));
        livesImage2.setCache(true);
        final ImageView livesImage3 = new ImageView(PACMAN_IMAGE);
        livesImage3.setX(MazeData.calcGridX(14));
        livesImage3.setY(MazeData.calcGridY(MazeData.GRID_SIZE_Y + 0.8f));
        livesImage3.visibleProperty().bind(livesCount.greaterThan(2));
        livesImage3.setCache(true);
        gamePaused = new SimpleBooleanProperty(false);
        Rectangle bg = new Rectangle(0, 0, MazeData.calcGridX(MazeData.GRID_SIZE_X + 2), MazeData.calcGridY(MazeData.GRID_SIZE_Y + 3));
        bg.setFill(Color.BLACK);
        bg.setCache(true);
        group.getChildren().add(bg);




        // Inner border of outside wall top and bottom(blue)
        group.getChildren().add(new WallRectangle(0, 0, MazeData.GRID_SIZE_X, MazeData.GRID_SIZE_Y));

        // Top middle vertical wall (blue)
        group.getChildren().add(new WallRectangle(14, -0.5f, 15, 4));
        group.getChildren().add(new WallBlackRectangle(13.8f, -1, 15.3f, 0));

        group.getChildren().add(new WallRectangle(2, 2, 5, 4)); // upper-left rectangle
        group.getChildren().add(new WallRectangle(7, 2, 12, 4)); // upper 2nd-from-left rectangle
        group.getChildren().add(new WallRectangle(17, 2, 22, 4)); // upper 2nd-from-right rectangle
        group.getChildren().add(new WallRectangle(24, 2, 27, 4)); // upper-right rectangle
        group.getChildren().add(new WallRectangle(2, 6, 5, 7)); // left-side 2nd from top rectangle

        // middle top T
        group.getChildren().add(new WallRectangle(14, 6, 15, 10));
        group.getChildren().add(new WallRectangle(10, 6, 19, 7));
        group.getChildren().add(new WallBlackLine(14.05f, 7, 14.95f, 7));

        // Upper-left sideways T
        group.getChildren().add(new WallRectangle(7.5f, 9, 12, 10));
        group.getChildren().add(new WallRectangle(7, 6, 8, 13));
        group.getChildren().add(new WallBlackLine(8, 9, 8, 10));

        // Upper-right sideways T
        group.getChildren().add(new WallRectangle(17, 9, 21.5f, 10));
        group.getChildren().add(new WallRectangle(21, 6, 22, 13));
        group.getChildren().add(new WallBlackLine(21, 9, 21, 10));

        group.getChildren().add(new WallRectangle(24, 6, 27, 7)); // right-side 2nd from top rectangle

        //cage and the gate
        group.getChildren().add(new WallRectangle(10, 12, 19, 17));
        group.getChildren().add(new WallRectangle(10.5f, 12.5f, 18.5f, 16.5f));
        final Rectangle cageRect = new Rectangle(MazeData.calcGridX(13), MazeData.calcGridY(12), MazeData.GRID_GAP * 3, MazeData.GRID_GAP / 2);
        cageRect.setStroke(Color.GREY);
        cageRect.setFill(Color.GREY);
        cageRect.setCache(true);
        group.getChildren().add(cageRect);

        // Vertical rectangle below left side door
        group.getChildren().add(new WallRectangle(7, 15, 8, 20));

        // Vertical rectangle below right side door
        group.getChildren().add(new WallRectangle(21, 15, 22, 20));

        // middle middle T
        group.getChildren().add(new WallRectangle(14, 19, 15, 23));
        group.getChildren().add(new WallRectangle(10, 19, 19, 20));
        group.getChildren().add(new WallBlackLine(14.05f, 20, 14.95f, 20));

        // left L
        group.getChildren().add(new WallRectangle(4, 22, 5, 26));
        group.getChildren().add(new WallRectangle(2, 22, 5, 23));
        group.getChildren().add(new WallBlackRectangle(4, 22.05f, 5, 23.2f));

        group.getChildren().add(new WallRectangle(7, 22, 12, 23)); // left lower horizontal rectangle

        // right L
        group.getChildren().add(new WallRectangle(24, 22, 25, 26));
        group.getChildren().add(new WallRectangle(24, 22, 27, 23));
        group.getChildren().add(new WallBlackRectangle(24, 22.05f, 25, 23.2f));

        group.getChildren().add(new WallRectangle(17, 22, 22, 23)); // right lower horizontal rectangle

        group.getChildren().add(new WallRectangle(-1, 25, 2, 26)); // left horizontal wall
        group.getChildren().add(new WallRectangle(27, 25, MazeData.GRID_SIZE_X + 1, 26)); // right horizontal wall

        // left bottom upside-down T
        group.getChildren().add(new WallRectangle(7, 25, 8, 29));
        group.getChildren().add(new WallRectangle(2, 28, 12, 29));
        group.getChildren().add(new WallBlackLine(7.05f, 28, 7.95f, 28));

        // lower middle T
        group.getChildren().add(new WallRectangle(14, 25, 15, 29));
        group.getChildren().add(new WallRectangle(10, 25, 19, 26));
        group.getChildren().add(new WallBlackLine(14.05f, 26, 14.95f, 26));

        // right bottom upside-down T
        group.getChildren().add(new WallRectangle(21, 25, 22, 29));
        group.getChildren().add(new WallRectangle(17, 28, 27, 29));
        group.getChildren().add(new WallBlackLine(21.05f, 28, 21.95f, 28));

        // Outer border of outside wall
        final Rectangle outerWall = new Rectangle(MazeData.calcGridX(-0.5f), MazeData.calcGridY(-0.5f), (MazeData.GRID_SIZE_X + 1) * MazeData.GRID_GAP, (MazeData.GRID_SIZE_Y + 1) * MazeData.GRID_GAP);
        outerWall.setStrokeWidth(MazeData.GRID_STROKE);
        outerWall.setStroke(Color.BLUE);
        outerWall.setFill(null);
        outerWall.setArcWidth(12);
        outerWall.setArcHeight(12);
        outerWall.setCache(true);
        group.getChildren().add(outerWall);

        group.getChildren().add(new WallRectangle(-1, 9, 5, 13)); // outer wall above left side door
        group.getChildren().add(new WallRectangle(-1, 9.5f, 4.5f, 12.5f)); // inner wall above left side door
        group.getChildren().add(new WallRectangle(-1, 15, 5, 20)); // outer wall below left side wall
        group.getChildren().add(new WallRectangle(-1, 15.5f, 4.5f, 19.5f)); // inner wall below left side door wall

        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 9, MazeData.GRID_SIZE_X + 1, 13)); // outer wall above right side door
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 9.5f, MazeData.GRID_SIZE_X + 1, 12.5f)); // inner wall above right side door
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 5, 15, MazeData.GRID_SIZE_X + 1, 20)); // outer wall below right side wall
        group.getChildren().add(new WallRectangle(MazeData.GRID_SIZE_X - 4.5f, 15.5f, MazeData.GRID_SIZE_X + 1, 19.5f)); // inner wall below right side door wall


        //WallBlackRectangles to adjust display
        group.getChildren().add(new WallBlackRectangle(-2, 8, -0.5f, MazeData.GRID_SIZE_Y)); // black-out left garbage outside the wall
        group.getChildren().add(new WallBlackRectangle(-0.5f, 8, 0, 9.5f)); // black-out horizontal line inside outer-left wall above side door
        group.getChildren().add(new WallBlackRectangle(-0.5f, 19.5f, 0, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-left wall below side door

        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X + 0.5f, 8, MazeData.GRID_SIZE_X + 2, MazeData.GRID_SIZE_Y)); // black-out garbage on right side of outside wall
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 8, MazeData.GRID_SIZE_X + 0.5f, 9.5f)); // black-out horizontal line inside outer-right wall above side door
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X, 19.5f, MazeData.GRID_SIZE_X + 0.5f, MazeData.GRID_SIZE_Y)); // black-out horizontal lines inside outer-right wall below side door

        // black-out outer walls inside both side doors
        group.getChildren().add(new WallBlackRectangle(-1, 13, 1, 15)); // left
        group.getChildren().add(new WallBlackRectangle(MazeData.GRID_SIZE_X - 1, 13, MazeData.GRID_SIZE_X + 1, 15)); // right

        // Add back 4 blue wall segments that were deleted
        group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 9, -0.5f, 9.5f));
        group.getChildren().add(new WallBlackLine(Color.BLUE, -0.5f, 19.5f, -0.5f, 20));
        group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 9, MazeData.GRID_SIZE_X + 0.5f, 9.5f));
        group.getChildren().add(new WallBlackLine(Color.BLUE, MazeData.GRID_SIZE_X + 0.5f, 19.5f, MazeData.GRID_SIZE_X + 0.5f, 20));


        //add dots to the maze
        //add dots horizontally
        putDotHorizontally(2, 12, 1);
        putDotHorizontally(17, 27, 1);
        putDotHorizontally(2, 27, 5);
        putDotHorizontally(2, 5, 8);
        putDotHorizontally(24, 27, 8);
        putDotHorizontally(10, 13, 8);
        putDotHorizontally(16, 19, 8);
        putDotHorizontally(2, 12, 21);
        putDotHorizontally(17, 27, 21);
        putDotHorizontally(2, 2, 24);
        putDotHorizontally(27, 27, 24);
        putDotHorizontally(7, 12, 24);
        putDotHorizontally(17, 22, 24);
        putDotHorizontally(2, 5, 27);
        putDotHorizontally(24, 27, 27);
        putDotHorizontally(10, 12, 27);
        putDotHorizontally(17, 19, 27);
        putDotHorizontally(2, 27, 30);


        //add dots vertically
        putDotVertically(1, 1, 8);
        putDotVertically(28, 1, 8);
        putDotVertically(1, 21, 24);
        putDotVertically(28, 21, 24);
        putDotVertically(1, 27, 30);
        putDotVertically(28, 27, 30);
        putDotVertically(3, 24, 27);
        putDotVertically(26, 24, 27);
        putDotVertically(6, 1, 27);
        putDotVertically(23, 1, 27);
        putDotVertically(9, 5, 8);
        putDotVertically(20, 5, 8);
        putDotVertically(9, 24, 27);
        putDotVertically(20, 24, 27);
        putDotVertically(13, 1, 4);
        putDotVertically(16, 1, 4);
        putDotVertically(13, 21, 24);
        putDotVertically(16, 21, 24);
        putDotVertically(13, 27, 30);
        putDotVertically(16, 27, 30);

        getChildren().add(group);
        group.getChildren().addAll(ghosts);
        group.getChildren().add(pacMan);


    }

    public final Dot createDot(int x1, int y1, int type) {

        Dot d = new Dot((int) MazeData.calcGridX(x1), (int) MazeData.calcGridY(y1), type);
        if (type == MazeData.MAGIC_DOT) {

            d.playTimeline(); // for the dot to blink

        }
        // set the dot type in data model
        MazeData.setData(x1, y1, type);

        // set dot reference
        MazeData.setDot(x1, y1, d);

        return d;
    }

    public final void putDotHorizontally(int x1, int x2, int y) {

        Dot dot;
        for (int x = x1; x <= x2; x++) {
            if (MazeData.getData(x, y) == MazeData.EMPTY) {
                int dotType;

                if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
                    dotType = MazeData.MAGIC_DOT; // magic dot in corners
                } else {
                    dotType = MazeData.NORMAL_DOT; // normal dot everywhere else
                }
                dot = createDot(x, y, dotType); // create dot

                // insert dots into group
                group.getChildren().add(dot);
            } else {
                //    if (DEBUG) {
                System.out.println("!! WARNING: Trying to place horizontal dots at occupied position (" + x + ", " + y + ")");
            }
        }
    }


    public final void putDotVertically(int x, int y1, int y2) {
        Dot dot;
        for (int y = y1; y <= y2; y++) {
            if (MazeData.getData(x, y) == MazeData.EMPTY) {
                int dotType;

                if ((x == 28 || x == 1) && (y == 3 || y == 24)) {
                    dotType = MazeData.MAGIC_DOT; // magic dot in corners
                } else {
                    dotType = MazeData.NORMAL_DOT; // magic dot everywhere else
                }

                dot = createDot(x, y, dotType);
                group.getChildren().add(dot);
            } else {
                //   if (DEBUG) {
                System.out.println("!! WARNING: Trying to place vertical   dots at occupied position (" + x + ", " + y + ")");
            }
        }
    }

    public void addLife() {

        if (addLifeFlag) {
            livesCount.set(livesCount.get() + 1);
//      livesCount ++;
            addLifeFlag = false;
        }
    }
}




