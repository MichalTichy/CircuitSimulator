/**
 * Input ports interface
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Data.IData;

public interface IInputPort extends IPort {
    /**
     * Method, that determines if port changed data
     *
     * @return true if port changed data, false otherwise
     */
    boolean GetWhetherDataChanged();

    /**
     * Getter for data
     * @return port data
     */
    IData GetData();

    /**
     * Setter for default port value
     */
    void SetDefaultValue();
}
