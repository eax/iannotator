/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arsivci;

import java.util.EventObject;

/**
 *
 * @author eax
 */
public class PolygonEvent extends EventObject {
    private String translation = "";
    private String description = "";
    public PolygonEvent(Object source){
        super(source);
    }

    /**
     * @return the translation
     */
    public String getTranslation() {
        return translation;
    }

    /**
     * @param translation the translation to set
     */
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
