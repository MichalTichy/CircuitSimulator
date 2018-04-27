package Blocks;

import Common.IResetable;
import Common.ITimeTickConsumer;
import Common.IValidatable;

import java.awt.geom.Point2D;

public interface IBlock extends IValidatable,IResetable,ITimeTickConsumer {
    BlockStatus GetStatus();
    void Execute();
}

