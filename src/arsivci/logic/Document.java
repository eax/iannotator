/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arsivci.logic;

import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author eax
 */
public class Document {
    ArrayList<Translation> translations = new ArrayList<Translation>();
    private String filename;

    public void addTranslation(Translation t){
        this.translations.add(t);
    }

    public void removeTranslation(Translation t){
        this.translations.remove(t);
    }

    public void removeTranslation(int idx){
        this.translations.remove(idx);
    }


    public Document(String filename){
        this.filename = filename;
    }

}
