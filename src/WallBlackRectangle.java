/**
 * Created by Toshiba on 3/22/2016.
 */

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class WallBlackRectangle extends Parent {

    public WallBlackRectangle(float x1, float y1, float x2, float y2) {

        Rectangle rectangle = new Rectangle();
        rectangle.setX(MazeData.calcGridX(x1) + MazeData.GRID_STROKE);
        rectangle.setY(MazeData.calcGridY(y1) + MazeData.GRID_STROKE);
        rectangle.setWidth(MazeData.GRID_GAP * (x2 - x1) - MazeData.GRID_STROKE * 2);
        rectangle.setHeight(MazeData.GRID_GAP * (y2 - y1) - MazeData.GRID_STROKE * 2);
        rectangle.setStrokeWidth(MazeData.GRID_STROKE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setArcWidth(3);
        rectangle.setArcHeight(3);
        rectangle.setCache(true);

        getChildren().add(rectangle); // patweb
    }

}