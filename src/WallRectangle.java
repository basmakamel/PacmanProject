/**
 * Created by Toshiba on 3/22/2016.
 */
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class WallRectangle extends Parent {
    public double x1;
    public double y1;
    public double x2;
    public double y2;

    public WallRectangle(float x1, float y1, float x2, float y2) {
        Rectangle r = new Rectangle();
        r.setX(MazeData.calcGridX(x1));
        r.setY(MazeData.calcGridY(y1));
        r.setWidth(MazeData.calcGridX(x2) - MazeData.calcGridX(x1));
        r.setHeight(MazeData.calcGridY(y2) - MazeData.calcGridY(y1));
        r.setStrokeWidth(MazeData.GRID_STROKE);
        r.setStroke(Color.BLUE);
        r.setArcHeight(12);
        r.setArcWidth(12);
        r.setArcHeight(12);
        r.setCache(true);
        getChildren().add(r);
        MazeData.setBlockMazeData(Math.round(x1), Math.round(y1), Math.round(x2), Math.round(y2));
    }

}
