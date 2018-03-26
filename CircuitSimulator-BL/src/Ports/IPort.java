package Ports;

import Blocks.IBlock;
import Data.DataType;

public interface IPort {
    DataType DataType = null;
    IBlock AssociatedBlock = null;
    IPort ConnectedTo = null;
}
