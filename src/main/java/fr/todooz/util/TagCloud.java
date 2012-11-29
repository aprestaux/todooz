package fr.todooz.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagCloud {
	
	private List<String> tags = new ArrayList<String>();

    public void add(String... listTags) {
    	if (listTags != null) {
    	for ( String tag : listTags )
    		if (!(this.contains(tag)) && (tag != null) && (tag.length() != 0)) {
    			tags.add(tag);
    		}
    	}
    }

    public int size() {
    	return tags.size();
    }
    
    public boolean contains(String tagToTest) {
    	for ( String tag : tags )
    		if (tag == tagToTest) {
    			return true;
    		}
    	return false;
    }
    
    public void top(int size) {
    	if (size > 0) {
    		if (tags.size() >= size) {
    			tags = tags.subList(0, size);
    		}
    	}else{
    		tags = Collections.emptyList();
    	}
    }
    
    public void shuffle() {
    	Collections.shuffle(tags);
    }
    
}
	   
