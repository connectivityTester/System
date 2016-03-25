package io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FileItem implements Comparable<FileItem>{
	
	private final String path;
	private final boolean isFolder;
	private final List<FileItem> subFolders= new ArrayList<FileItem>();
	
	public FileItem(final String path, final boolean isFolder){
		Objects.requireNonNull(path);
		
		this.path = path.substring(path.lastIndexOf("\\")+1,  path.length());
		this.isFolder = isFolder;
	}

	@Override
	public int compareTo(final FileItem fileItem) 	{ 	return fileItem.isFolder ? 1 : -1;	}
	public List<FileItem> getSubFolders() 			{	return this.subFolders;				}
	public String getPath() 						{	return this.path;					}
	public boolean isFolder() 						{	return this.isFolder;				}
	
	public void addToSubFolders(final FileItem folder){
		Objects.requireNonNull(folder);
		
		this.subFolders.add(folder);
		Collections.sort(this.subFolders);
	}	
}
