/**
 * Created by Toshiba on 3/22/2016.
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WallBlackLine extends Line {
    public WallBlackLine(float x1, float y1, float x2, float y2) {
        init(x1, y1, x2, y2);
        setStrokeWidth(MazeData.GRID_STROKE + 1);
        setStrokeWidth(MazeData.GRID_STROKE + 1);
    }

    public WallBlackLine(Color lineColor, float x1, float y1, float x2, float y2) {
        init(x1, y1, x2, y2);

        setStrokeWidth(MazeData.GRID_STROKE);
        setStroke(lineColor);
    }

    private void init(float x1, float y1, float x2, float y2) {
        setCache(true);
        if (x1 == x2) { // vertical line
            setStartX(MazeData.calcGridX(x1));
            setStartY(MazeData.calcGridY(y1) + MazeData.GRID_STROKE);
            setEndX(MazeData.calcGridX(x2));
            setEndY(MazeData.calcGridY(y2) - MazeData.GRID_STROKE);
        }
        else { // horizontal line
            setStartX(MazeData.calcGridX(x1) + MazeData.GRID_STROKE);
            setStartY(MazeData.calcGridY(y1));
            setEndX(MazeData.calcGridX(x2) - MazeData.GRID_STROKE);
            setEndY(MazeData.calcGridY(y2));
        }
    }
}
