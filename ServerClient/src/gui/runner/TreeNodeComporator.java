package gui.runner;

import java.util.Comparator;
import java.util.Objects;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodeComporator implements Comparator<DefaultMutableTreeNode>{

		@Override 
	  public int compare(final DefaultMutableTreeNode a, final DefaultMutableTreeNode b) {
		Objects.requireNonNull(a);
		Objects.requireNonNull(b);
		
	    //Sort the parent and child nodes separately:
	    if (a.isLeaf() && !b.isLeaf()) {
	      return 1;
	    } else if (!a.isLeaf() && b.isLeaf()) {
	      return -1;
	    } else {
	      String sa = a.getUserObject().toString();
	      String sb = b.getUserObject().toString();
	      return sa.compareToIgnoreCase(sb);
	    }
	  }
}
