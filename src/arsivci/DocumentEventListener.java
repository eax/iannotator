/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arsivci;

import java.util.EventListener;

/**
 *
 * @author eax
 */
public interface DocumentEventListener extends EventListener {
    public void documentEventOccured(DocumentEvent de);
}
