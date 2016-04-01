import javafx.animation.Animation;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PacMan extends MovingObject {
    public int dotEatenCount;
    public SimpleIntegerProperty score;
    private static final int[] ROTATION_DEGREE = new int[]{0, 90, 180, 270};
    private int keyboardBuffer;
    private final SimpleIntegerProperty currentDirection;

    public PacMan(Maze maze, int x, int y) {

        this.maze = maze;
        this.x = x;
        this.y = y;

        Image defaultImage = new Image(getClass().getResourceAsStream("left1.png"));
        images = new Image[]{defaultImage,
                new Image(getClass().getResourceAsStream("left2.png")),
                defaultImage,
                new Image(getClass().getResourceAsStream("round.png"))
        };

        dotEatenCount = 0;
        score = new SimpleIntegerProperty(0);
        currentDirection = new SimpleIntegerProperty(MOVE_LEFT);

        imageX = new SimpleIntegerProperty((int) MazeData.calcGridX(x));
        imageY = new SimpleIntegerProperty((int) MazeData.calcGridX(y));

        xDirection = -1;
        yDirection = 0;

        ImageView pacmanImage = new ImageView(defaultImage);
        pacmanImage.xProperty().bind(imageX.add(-13));
        pacmanImage.yProperty().bind(imageY.add(-13));
        pacmanImage.imageProperty().bind(imageBinding);
        IntegerBinding rotationBinding = new IntegerBinding() {

            {
                super.bind(currentDirection);
            }

            @Override
            protected int computeValue() {
                return ROTATION_DEGREE[currentDirection.get()];
            }
        };
        pacmanImage.rotateProperty().bind(rotationBinding);

        keyboardBuffer = -1;

        getChildren().add(pacmanImage); // patweb
    }

    private void moveHorizontally() {

        moveCounter++;

        if (moveCounter < ANIMATION_STEP) {
            imageX.set(imageX.get() + (xDirection * MOVE_SPEED));
//      imageX += xDirection * MOVE_SPEED;
        } else {
            moveCounter = 0;
            x += xDirection;

            imageX.set((int) MazeData.calcGridX(x));
//      imageX = MazeData.calcGridX(x);

            // the X coordinate of the next point in the grid
            int nextX = xDirection + x;

            if ((y == 14) && (nextX <= 1 || nextX >= 28)) {
                if ((nextX < -1) && (xDirection < 0)) {
                    x = MazeData.GRID_SIZE_X;
                    imageX.set((int) MazeData.calcGridX(x));
//          imageX = MazeData.calcGridX(x);
                } else {
                    if ((nextX > 30) && (xDirection > 0)) {
                        x = 0;
                        imageX.set((int) MazeData.calcGridX(x));
//            imageX = MazeData.calcGridX(x);
                    }
                }
            } else // check if the character hits a wall
                if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
                    state = STOPPED;
                }
        }
    }

    private void moveVertically() {

        moveCounter++;

        if (moveCounter < ANIMATION_STEP) {
            imageY.set(imageY.get() + (yDirection * MOVE_SPEED));
//      imageY += yDirection * MOVE_SPEED;
        } else {
            moveCounter = 0;
            y += yDirection;
            imageY.set((int) MazeData.calcGridX(y));
//      imageY = MazeData.calcGridX(y);

            // the Y coordinate of the next point in the grid
            int nextY = yDirection + y;

            // check if the character hits a wall
            if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
                state = STOPPED;
            }
        }
    }

    private void moveRight() {

        if (currentDirection.get() == MOVE_RIGHT) {
//    if ( currentDirection == MOVE_RIGHT )  return;
            return;
        }

        int nextX = x + 1;

        if (nextX >= MazeData.GRID_SIZE_X) {
            return;
        }

        if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
            return;
        }

        xDirection = 1;
        yDirection = 0;

        keyboardBuffer = -1;
        currentDirection.set(MOVE_RIGHT);
//    currentDirection = MOVE_RIGHT;

        state = MOVING;
    }

    private void moveLeft() {

        if (currentDirection.get() == MOVE_LEFT) {
//    if ( currentDirection == MOVE_LEFT )   return;
            return;
        }

        int nextX = x - 1;

        if (nextX <= 1) {
            return;
        }

        if (MazeData.getData(nextX, y) == MazeData.BLOCK) {
            return;
        }

        xDirection = -1;
        yDirection = 0;

        keyboardBuffer = -1;
        currentDirection.set(MOVE_LEFT);
//    currentDirection = MOVE_LEFT;

        state = MOVING;
    }

    private void moveUp() {

        if (currentDirection.get() == MOVE_UP) {
//    if ( currentDirection == MOVE_UP )  return;
            return;
        }

        int nextY = y - 1;

        if (nextY <= 1) {
            return;
        }

        if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
            return;
        }

        xDirection = 0;
        yDirection = -1;

        keyboardBuffer = -1;
        currentDirection.set(MOVE_UP);
        state = MOVING;
    }

    private void moveDown() {

        if (currentDirection.get() == MOVE_DOWN) {
//    if ( currentDirection == MOVE_DOWN ) return;
            return;
        }

        int nextY = y + 1;

        if (nextY >= MazeData.GRID_SIZE_Y) {
            return;
        }

        if (MazeData.getData(x, nextY) == MazeData.BLOCK) {
            return;
        }

        xDirection = 0;
        yDirection = 1;

        keyboardBuffer = -1;
        currentDirection.set(MOVE_DOWN);

        state = MOVING;
    }

    private void handleKeyboardInput() {

        if (keyboardBuffer < 0) {
            return;
        }

        if (keyboardBuffer == MOVE_LEFT) {
            moveLeft();
        } else if (keyboardBuffer == MOVE_RIGHT) {
            moveRight();
        } else if (keyboardBuffer == MOVE_UP) {
            moveUp();
        } else if (keyboardBuffer == MOVE_DOWN) {
            moveDown();
        }

    }

    public void setKeyboardBuffer(int k) {
        keyboardBuffer = k;
    }

    private void updateScore() {
        if (y != 14 || (x > 0 && x < MazeData.GRID_SIZE_X)) {
            Dot dot = (Dot) MazeData.getDot(x, y);

            if (dot != null && dot.isVisible()) {
                score.set(score.get() + 10);
                dot.setVisible(false);
                dotEatenCount++;

                if (score.get() >= 10000) {
                    maze.addLife();
                }

            }
        }
    }

    public void hide() {
        setVisible(false);
        timeline.stop();
    }

    @Override
    public void moveOneStep() {
        if (maze.gamePaused.get()) {
            if (timeline.getStatus() != Animation.Status.PAUSED) {
                timeline.pause();
            }

            return;
        }

        // handle keyboard input only when Pac-Man is at a point on the grid
        if (currentImage.get() == 0) {
            handleKeyboardInput();
        }

        if (state == MOVING) {

            if (xDirection != 0) {
                moveHorizontally();
            }

            if (yDirection != 0) {
                moveVertically();
            }

            if (currentImage.get() < ANIMATION_STEP - 1) {
                currentImage.set(currentImage.get() + 1);
//        currentImage++;
            } else {
                currentImage.set(0);
//        currentImage=0;
                updateScore();
            }

        }

    }

    public void resetStatus() {
        state = MOVING;
        currentDirection.set(MOVE_LEFT);
        xDirection = -1;
        yDirection = 0;

        keyboardBuffer = -1;
        currentImage.set(0);
        moveCounter = 0;

        x = 15;
        y = 24;

        imageX.set((int) MazeData.calcGridX(x));
        imageY.set((int) MazeData.calcGridY(y));
        setVisible(true);
        start();
    }

}
