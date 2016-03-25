package gui.runner;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

import io.FileItem;
import io.FileSystemManager;
import test.TestExecutor;
import xml.SystemConfig;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class TestDirectoryTree extends JPanel{
	
	private final JTree tree;
	private final JPopupMenu popupMenu;
	private final JMenuItem updateMenu;
	private final JMenuItem runMenu;
	private final JMenuItem runWithReportMenu;

	private final DefaultTreeModel model;
	private final FileSystemManager fileSystemManager;
	private final Comparator<DefaultMutableTreeNode> treeNodeComporator;

	public TestDirectoryTree(final FileSystemManager fileSystemManager){
		Objects.requireNonNull(fileSystemManager);
		
		this.fileSystemManager = fileSystemManager;
		this.treeNodeComporator = (firstLeaf,secondLeaf) ->{
			if (firstLeaf.isLeaf() && !secondLeaf.isLeaf()) {
				return 1;
			} 
			else if (!firstLeaf.isLeaf() && secondLeaf.isLeaf()) {
		      return -1;
		    } else {
		      String sa = firstLeaf.getUserObject().toString();
		      String sb = firstLeaf.getUserObject().toString();
		      return sa.compareToIgnoreCase(sb);
		    }
		};
		
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		this.model = new DefaultTreeModel(this.updateTree(SystemConfig.getInstance().getTestRootDirectory(), fileSystemManager), false);
		this.tree = new JTree(this.model);
		this.popupMenu = new JPopupMenu();
		this.updateMenu = new JMenuItem("Update tree");
		this.runMenu = new JMenuItem("Run");
		this.runWithReportMenu = new JMenuItem("Run with report");
		this.popupMenu.add(this.updateMenu);
		this.popupMenu.add(this.runMenu);
		this.popupMenu.add(this.runWithReportMenu);		
		this.addListeners();
		this.add(this.tree);
	}	

	public void updateTreeStructure(){
		this.model.setRoot(this.updateTree(SystemConfig.getInstance().getTestRootDirectory(), this.fileSystemManager));
		this.model.reload();
	}
	
	private DefaultMutableTreeNode updateTree(final String rootDir, final FileSystemManager fileSystemManager){
		Objects.requireNonNull(rootDir);
		Objects.requireNonNull(fileSystemManager);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root directory");
		List<FileItem> fileItems = fileSystemManager.getStructure(rootDir).getSubFolders();
		for(FileItem fileItem : fileItems){
			this.addNode(root,fileItem);
		}
		this.sortTree(root);
		return root;
	}
	
	private void addNode(final DefaultMutableTreeNode current, final FileItem fileItem){
		Objects.requireNonNull(current);
		Objects.requireNonNull(fileItem);
		
		if(!fileItem.isFolder()){
			current.add(new DefaultMutableTreeNode(fileItem.getPath()));
		}
		else{
			DefaultMutableTreeNode newSubFolder = new DefaultMutableTreeNode(fileItem.getPath());
			fileItem.getSubFolders().forEach(subFolder -> this.addNode(newSubFolder, subFolder));
			current.add(newSubFolder);
		}
		
	}
	
	private void addListeners() {
		this.tree.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent event) {
				if(event.getButton() == MouseEvent.BUTTON3){
					TreePath selPath = tree.getPathForLocation(event.getX(), event.getY());
					if(selPath != null){
						tree.setSelectionPath(selPath);
						popupMenu.show(tree, event.getX(), event.getY());
					}
				}
			}
		});
		this.updateMenu.addActionListener((event) -> updateTreeStructure());
		this.runMenu.addActionListener((event) -> {
			Thread thread = new Thread(new TestExecutor(tree.getSelectionPaths(), false));
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		});
		this.runWithReportMenu.addActionListener((event) -> {
			Thread thread = new Thread(new TestExecutor(tree.getSelectionPaths(), true));
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
		});
	}

	private void sortTree(final DefaultMutableTreeNode root) {
		Objects.requireNonNull(root);
  
	    @SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
	    while (e.hasMoreElements()) {
	        DefaultMutableTreeNode node = e.nextElement();
	        if (!node.isLeaf()) {
	            sort(node);
	        }
	    }
	}
	
	private void sort(final DefaultMutableTreeNode parent) {
		Objects.requireNonNull(parent);
		
		int n = parent.getChildCount();
		  for (int i = 0; i < n - 1; i++) {
		    int min = i;
		    for (int j = i + 1; j < n; j++) {
		      if (this.treeNodeComporator.compare(
		    		  (DefaultMutableTreeNode) parent.getChildAt(min),
		                      (DefaultMutableTreeNode) parent.getChildAt(j)) > 0) {
		        min = j;
		      }
		    }
		    if (i != min) {
		      MutableTreeNode a = (MutableTreeNode) parent.getChildAt(i);
		      MutableTreeNode b = (MutableTreeNode) parent.getChildAt(min);
		      parent.insert(b, i);
		      parent.insert(a, min);
		    }
		  }
	}

}
