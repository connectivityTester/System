package gui;

import java.util.Comparator;

import javax.swing.tree.DefaultMutableTreeNode;

public class TreeNodeComporator implements Comparator<DefaultMutableTreeNode>{

	@Override 
	  public int compare(DefaultMutableTreeNode a, DefaultMutableTreeNode b) {
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
