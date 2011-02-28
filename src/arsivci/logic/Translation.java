/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arsivci.logic;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author eax
 */
public class Translation {
    private Polygon poly;
    private BufferedImage img;
    private String translation;
    private String description;
    private ArrayList imgs = new ArrayList();

    public Translation() {
        poly = new Polygon();
        translation = "";
        description = "";
    }

    /**
     * @return the poly
     */
    public Polygon getPoly() {
        return poly;
    }

    /**
     * @param poly the poly to set
     */
    public void setPoly(Polygon poly) {
        this.poly = poly;
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

    /**
     * @return the images
     */
    public ArrayList getImgs() {
        return imgs;
    }

    public void addImage(Image img) {
        this.imgs.add(img);
    }

    public void removeImage(Image img){
        this.imgs.remove(img);
    }

    public void removeImage(int idx) {
        this.imgs.remove(idx);
    }

    /**
     * @return the image
     */
    public BufferedImage getImg() {
        return img;
    }

    /**
     * @param img the image to set
     */
    public void setImg(BufferedImage img) {
        this.img = img;
    }
}
