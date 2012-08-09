package de.rentoudu.cylian.command.bookmark;

import java.util.Comparator;

import de.rentoudu.cylian.entity.Bookmark;

public class BookmarkComparator implements Comparator<Bookmark> {

	public int compare(Bookmark o1, Bookmark o2) {
		int result = o1.getWorld().compareTo(o2.getWorld());
	    
		if (result == 0) {
	    	result = o1.getName().compareTo(o2.getName());
	    }
		
		return result;
	}

}
