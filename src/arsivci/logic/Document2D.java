
package arsivci.logic;

import arsivci.DocumentEvent;
import arsivci.DocumentEventListener;
import arsivci.PolygonEvent;
import arsivci.PolygonEventListener;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author eax
 */
public class Document2D extends Component implements MouseListener, MouseMotionListener {

    Document doc;
    BufferedImage img;
    Graphics2D g;
    //ArrayList<Polygon> polygons = new ArrayList<Polygon>();
    ArrayList<Point> pts = new ArrayList<Point>(4); //temporary points array
    Translation curtrans;
    BufferedImage prev;
    BufferedImage prevtrans;//state between previous translation and marking...

    public Document2D(BufferedImage img, String filename) {
        super();
        doc = new Document(filename);
        this.img = img;
        prev = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        prev.setData(img.getData());
        prevtrans = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        prevtrans.setData(img.getData());
        g = this.img.createGraphics();
        float alfa = 0.5f;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alfa));
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        int idx = hitOnPolygon(x,y);
        if (idx == -1) {            
            pts.add(new Point(x, y));
            putPoint(x, y);
            if (pts.size() == 4) {
                Polygon tmp = createPolygon(pts);
                BufferedImage _tmpimg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
                _tmpimg.setData(prevtrans.getData());
                curtrans = createTranslation(tmp, _tmpimg);
                fireDocumentEvent(new DocumentEvent(this));
                PolygonShape _tmp = new PolygonShape();
                _tmp.draw(tmp, g);
                prevtrans.setData(img.getData());
            }
            prev.setData(img.getData());
            repaint();
            me.consume();
        }
        else {
            curtrans = doc.translations.get(idx);
            fireDocumentEvent(new DocumentEvent(this));
        }
    }

    private void putPoint(int x, int y) {
        g.setColor(Color.red);
        g.fillOval(x - 5, y - 5, 10, 10);
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
    }

    public void mouseMoved(MouseEvent me) {
        int x = me.getX();
        int y = me.getY();
        int idx = hitOnPolygon(x, y);
        if (idx > -1) {
            Polygon p = doc.translations.get(idx).getPoly();
            PolygonShape _tmp = new PolygonShape();
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            _tmp.draw(p, g);
            PolygonEvent pe = new PolygonEvent(this);
            pe.setTranslation(doc.translations.get(idx).getTranslation());
            pe.setDescription(doc.translations.get(idx).getDescription());
            firePolygonEvent(pe);
            repaint();
            me.consume();
            return;
        }
        else if(doc.translations.size() > 0)
            img.setData(prev.getData());
        repaint();
        me.consume();
        
    }

    private int hitOnPolygon(int x, int y) {
        for (Translation t : doc.translations) {
            if (t.getPoly().contains(x, y)) {
                return doc.translations.indexOf(t);
            }
        }
        return -1;
    }

    private Polygon createPolygon(java.util.List list) {
        Polygon p = new Polygon();
        Point point = null;
        Iterator it = list.iterator();
        //if the list does not contain the required two points, return.
        if (list.size() < 4) {
            return null;
        }
        for (; it.hasNext();) {
            point = (Point) it.next();
            p.addPoint((int) point.getX(), (int) point.getY());
        }
        list.clear();
        return p;
    }

    private Translation createTranslation(Polygon p, BufferedImage img) {
        Translation t = new Translation();
        t.setPoly(p);
        t.setImg(img);
        doc.addTranslation(t);
        return t;
    }

    public void deleteSelectedTranslation(){
        int idx = doc.translations.indexOf(curtrans);
        if(idx<(doc.translations.size()-1)) {
            img.setData(curtrans.getImg().getData());
            repaint();
            
            for(int i = idx+1; i<doc.translations.size();i++) {
                Polygon _dummy = doc.translations.get(i).getPoly();
                for(int j=0;j<4;j++)
                    putPoint(_dummy.xpoints[j], _dummy.ypoints[j]);
                PolygonShape _tmp = new PolygonShape();
                _tmp.draw(_dummy, g);
                repaint();
            }
        }
        prev.setData(img.getData());
        doc.translations.remove(curtrans);
    }

    public void setTranslationText(String trans_txt) {
        curtrans.setTranslation(trans_txt);
    }

    public void setDescriptionText(String trans_desc) {
        curtrans.setDescription(trans_desc);
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        //g = (Graphics2D) g;
        g.drawImage(img, 0, 0, null);
        //TODO: do the drawing
    }

    class PolygonShape {

        public void draw(Polygon p, Graphics g) {
            g.setColor(Color.blue);
            g.fillPolygon(p);
        }//end of draw for Polygon
    }
    // Create the listener list
    protected javax.swing.event.EventListenerList documentlistenerList =
            new javax.swing.event.EventListenerList();
    protected javax.swing.event.EventListenerList polygonlistenerList =
            new javax.swing.event.EventListenerList();

    // This methods allows classes to register for DocumentEvents
    public void addDocumentEventListener(DocumentEventListener listener) {
        documentlistenerList.add(DocumentEventListener.class, listener);
    }

    // This methods allows classes to unregister for DocumentEvents
    public void removeDocumentEventListener(DocumentEventListener listener) {
        documentlistenerList.remove(DocumentEventListener.class, listener);
    }

    // This private class is used to fire DocumentEvents
    void fireDocumentEvent(DocumentEvent evt) {
        Object[] listeners = documentlistenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == DocumentEventListener.class) {
                ((DocumentEventListener) listeners[i + 1]).documentEventOccured(evt);
            }
        }
    }

    // This methods allows classes to register for DocumentEvents
    public void addPolygonEventListener(PolygonEventListener listener) {
        polygonlistenerList.add(PolygonEventListener.class, listener);
    }

    // This methods allows classes to unregister for DocumentEvents
    public void removePolygonEventListener(PolygonEventListener listener) {
        polygonlistenerList.remove(PolygonEventListener.class, listener);
    }

    // This private class is used to fire DocumentEvents
    void firePolygonEvent(PolygonEvent evt) {
        Object[] listeners = polygonlistenerList.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i += 2) {
            if (listeners[i] == PolygonEventListener.class) {
                ((PolygonEventListener) listeners[i + 1]).polygonEventOccured(evt);
            }
        }
    }
}
