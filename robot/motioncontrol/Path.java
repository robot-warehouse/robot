package motioncontrol;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Integer> pathList = new ArrayList<Integer>();

	public Path() {
		//Dummy instruction set for testing purposes
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(0);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(1);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(1);
		addAction(0);
		addAction(3);
		addAction(0);
		addAction(1);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
		addAction(3);
	}
	
	//Adds a value to the pathList.
	public void addAction(int value) {
		pathList.add(value);
	}
	
	//Returns the pathList
	public List<Integer> getPathList() {
  		return pathList;
  	}
}