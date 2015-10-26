package template;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public State node;
	public List<State> rest;
	
	public Node(){}
	
	public Node(State state) {
		node = state;
		rest = new ArrayList<State>();
		
		//Calcul des enfants en fonction des actions = transitions
	}

}
