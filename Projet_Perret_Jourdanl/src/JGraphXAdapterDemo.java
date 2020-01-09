
import com.mxgraph.layout.*;
import com.mxgraph.swing.*;
import org.jgrapht.*;
import org.jgrapht.ext.*;
import org.jgrapht.graph.*;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * A demo applet that shows how to use JGraphX to visualize JGraphT graphs. Applet based on
 * JGraphAdapterDemo.
 *
 */
public class JGraphXAdapterDemo extends JApplet{
    //https://jgrapht.org/guide/UserOverview
	private static final long serialVersionUID = 2202072534703043194L;

    private static final Dimension DEFAULT_SIZE = new Dimension(530, 320);

    private JGraphXAdapter<String, DefaultEdge> jgxAdapter;
    private ListenableGraph<String, DefaultEdge> g;
    
    //Code rajouter par nous
    private bdd bdd;

    /**
     * An alternative starting point for this demo, to also allow running this applet as an
     * application.
     *
     * @param args command line arguments
     */

    @Override
    public void init()
    {
        // create a JGraphT graph
        g = new DefaultListenableGraph<>(new DefaultDirectedGraph<>(DefaultEdge.class));

        // create a visualization using JGraph, via an adapter
        jgxAdapter = new JGraphXAdapter<>(g);

        setPreferredSize(DEFAULT_SIZE);
        mxGraphComponent component = new mxGraphComponent(jgxAdapter);
        component.setConnectable(false);
        component.getGraph().setAllowDanglingEdges(false);
        getContentPane().add(component);
        resize(DEFAULT_SIZE);
    }
  //Méthode rajouter par nous
    public void ajouterData(bdd bdd) {
    	
    	TreeSet<String> listeuser = bdd.getListeUser();
    	Iterator<String> iter=listeuser.iterator();
    	while(iter.hasNext()) {
    		String user = iter.next();
    		g.addVertex(user);
    	}
    	Iterator<String> iteruser2 = listeuser.iterator();
    	while(iteruser2.hasNext()) {
    		String user2 = iteruser2.next();
    		TreeSet<String> listRt = bdd.getListRt(user2);
    		Iterator<String> userRt=listRt.iterator();
        	while(userRt.hasNext()) {
        		String adja = userRt.next();
        		g.addEdge(user2, adja);
        	}
    	
    	}
    
        
     // positioning via jgraphx layouts
        mxCircleLayout layout = new mxCircleLayout(jgxAdapter);

        // center the circle
        int radius = 100;
        layout.setX0((DEFAULT_SIZE.width / 2.0) - radius);
        layout.setY0((DEFAULT_SIZE.height / 2.0) - radius);
        layout.setRadius(radius);
        layout.setMoveCircle(true);

        layout.execute(jgxAdapter.getDefaultParent());
        // that's all there is to it!...
    	
    }
}

