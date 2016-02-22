package io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileItem implements Comparable<FileItem>{
	
	private final String path;
	private final boolean isFolder;
	private final List<FileItem> subFolders= new ArrayList<FileItem>();
	
	public FileItem(String path, boolean isFolder){
		this.path = path.substring(path.lastIndexOf("\\")+1,  path.length());
		this.isFolder = isFolder;
	}

	@Override
	public int compareTo(FileItem fileItem) { 	return fileItem.isFolder ? 1 : -1;	}
	public List<FileItem> getSubFolders() 	{	return this.subFolders;				}
	public String getPath() 				{	return this.path;					}
	public boolean isFolder() 				{	return this.isFolder;				}
	
	public void addToSubFolders(FileItem folder){
		this.subFolders.add(folder);
		Collections.sort(this.subFolders);
	}

	
	
}
