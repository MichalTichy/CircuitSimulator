/**
 * Ports interface
 *
 * @author Prášek Matěj - xprase07
 * @author Tichý Michal - xtichy26
 */
package Ports;

import Common.IResetable;
import Common.IValidatable;

public interface IPort extends IResetable, IValidatable {
    /**
     * Method that disconnect port
     */
    void Disconnect();

    /**
     * Method that determines if is port connected
     *
     * @return true if is connected, false otherwise
     */
    boolean GetWhetherConnected();

    /**
     * Method that find connected port
     * @return connected port
     */
    IPort getConnectedPort();
}
