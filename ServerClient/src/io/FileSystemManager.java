package io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import javax.swing.JOptionPane;
import types.LogLevels;
import utils.Logger;
import utils.Utils;

public class FileSystemManager {
	
	private String[] getSubFolders(final String folder){
		Utils.requireNonNull(folder);
		
		final String [] subfolders = new File(folder).list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return new File(dir+"\\"+name).isDirectory();
			}
		});
		return subfolders;
	}
	
	private String[] getSubFiles(final String folder){
		Utils.requireNonNull(folder);
		
		final String [] subfiles = new File(folder).list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(new File(dir+"\\"+name).isFile() && name.endsWith(".test")){
					return true;
				}
				else{
					return false;
				}
			}
		});
		Arrays.sort(subfiles);
		return subfiles;
	}
	
	public FileItem getStructure(final String folder){
		Utils.requireNonNull(folder);
		
		File directory = new File(folder);
		FileItem result = null;
		if(!directory.exists()){
			Logger.log(LogLevels.ERROR, "FileSystemManager", "Root test directory does not exist!");
			JOptionPane.showMessageDialog(null, "Root test directory does not exist!");
		}
		else{
			result = new FileItem(folder, true);
			String[] subfiles = this.getSubFiles(folder);
			String[] subfolders = this.getSubFolders(folder);
			if(subfiles != null){
				for(String currentFile : subfiles){
					result.addToSubFolders(new FileItem(currentFile, false));
				}
			}
			if(subfolders != null){
				for(String currentFolder : subfolders){
					FileItem temp = this.getStructure(folder + "\\"+currentFolder);
					if(temp != null){
						result.addToSubFolders(temp);
					}
				}	
			}
			if(result.getSubFolders().isEmpty()){
				return null;
			}
		}
		return result;
	}
}
