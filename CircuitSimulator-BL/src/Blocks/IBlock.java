package Blocks;

import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.IValidatable;

import java.awt.geom.Point2D;

public interface IBlock extends IValidatable,IResetable,ITimeTickConsumer {
    Point2D GetPosition();
    BlockStatus GetStatus();
    void Execute();
}

