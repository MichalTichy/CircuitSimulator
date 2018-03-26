import java.awt.geom.Point2D;

public interface IBlock {
    Point2D Position = null;
    BlockStatus Status = BlockStatus.Idle;

    void TickDetected();
}

