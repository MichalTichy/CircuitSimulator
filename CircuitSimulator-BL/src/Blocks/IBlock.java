package Blocks;

import java.awt.geom.Point2D;

public interface IBlock {
    Point2D GetPosition();
    BlockStatus GetStatus();

    void TickDetected();
}

