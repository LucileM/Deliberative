package template;

/* import table */
import logist.simulation.Vehicle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import logist.agent.Agent;
import logist.behavior.DeliberativeBehavior;
import logist.plan.Plan;
import logist.task.Task;
import logist.task.TaskDistribution;
import logist.task.TaskSet;
import logist.topology.Topology;
import logist.topology.Topology.City;

/**
 * An optimal planner for one vehicle.
 */
@SuppressWarnings("unused")
public class DeliberativeTemplate implements DeliberativeBehavior {

	enum Algorithm { BFS, ASTAR }
	
	/* Environment */
	Topology topology;
	TaskDistribution td;
		
	/* the properties of the agent */
	Agent agent;
	int capacity;

	/* the planning class */
	Algorithm algorithm;
	
	@Override
	public void setup(Topology topology, TaskDistribution td, Agent agent) {
		this.topology = topology;
		this.td = td;
		this.agent = agent;
		
		// initialize the planner
		int capacity = agent.vehicles().get(0).capacity();
		String algorithmName = agent.readProperty("algorithm", String.class, "ASTAR");
		
		// Throws IllegalArgumentException if algorithm is unknown
		algorithm = Algorithm.valueOf(algorithmName.toUpperCase());
		
	}
	
	@Override
	public Plan plan(Vehicle vehicle, TaskSet tasks) {
		Plan plan;

		// Compute the plan with the selected algorithm.
		switch (algorithm) {
		case ASTAR:
			// ...
			plan = naivePlan(vehicle, tasks);
			break;
		case BFS:
			// ...
			plan = naivePlan(vehicle, tasks);
			break;
		default:
			throw new AssertionError("Should not happen.");
		}		
		return plan;
	}
	
	private Plan naivePlan(Vehicle vehicle, TaskSet tasks) {
		City current = vehicle.getCurrentCity();
		Plan plan = new Plan(current);
		System.out.println("Compute naivePlan for vehicle :" + vehicle.id());
		System.out.println(tasks.toString());
		for (Task task : tasks) {
			// move: current city => pickup location
			for (City city : current.pathTo(task.pickupCity))
				plan.appendMove(city);

			plan.appendPickup(task);

			// move: pickup location => delivery location
			for (City city : task.path())
				plan.appendMove(city);

			plan.appendDelivery(task);

			// set current city
			current = task.deliveryCity;
		}
		return plan;
	}
	
	private Plan BFSPlan(Vehicle vehicle, TaskSet tasks) {
		City current = vehicle.getCurrentCity();
		Plan plan = new Plan(current);
		System.out.println("Compute BFS for vehicle :" + vehicle.id());
		System.out.println(tasks.toString());
		
		// Initialize states
		State initialState = computeInitialState(vehicle,tasks);
		List<State> finalStates = computeFinalStates(vehicle,tasks);
		
		// Build the tree		
		return plan;
	}

	@Override
	public void planCancelled(TaskSet carriedTasks) {
		
		if (!carriedTasks.isEmpty()) {
			// This cannot happen for this simple agent, but typically
			// you will need to consider the carriedTasks when the next
			// plan is computed.
		}
	}
	
	private State computeInitialState(Vehicle vehicle, TaskSet tasks) {
		List<City> listTasksCities = new ArrayList<Topology.City>();
		List<Boolean> isDeliver = new ArrayList<Boolean>();
		for (Task task : tasks) {
			listTasksCities.add(task.pickupCity);
			isDeliver.add(false);
		}
			
		return new State(vehicle.getCurrentCity(),tasks, isDeliver);
	}
	
	private List<State> computeFinalStates(Vehicle vehicle,TaskSet tasks) {
		List<City> listTasksCities = new ArrayList<Topology.City>(); 
		for (Task task : tasks)
			listTasksCities.add(task.deliveryCity);
		List<State> finalStates = new ArrayList<State>();
		for (City city : topology.cities()) 
			finalStates.add(new State(city,listTasksCities));
		return finalStates;
	}

	private boolean isFinalStates(State s, List<State> finalStates) {
		for (State state : finalStates) {
			if (state.equals(s)) return true;
		}
		return false;
	}
	//A FAIRE CORRECTEMENT
	private List<State> succ(State n) {
		List<State> successeurs = new ArrayList<State>();
		City currentCity = n.positionVehicle;
		List<City> neighbors = currentCity.neighbors();
		
		// Task to pickup here
		for (int i = 0 ; i< n.positionTaks.size();i++) {
				
		}
		
		successeurs.add(n);
		return successeurs;
	}
	
	private State BFS(State S0,List<State> finalStates) {
		State n = new State();
		Queue<State> Q = new LinkedList<State>();
		List<State> C = new ArrayList<State>();
		Q.add(S0); //add to end of queue
		
		while (true) {
			n = Q.remove();//return the head of the Q and remove it
			if (isFinalStates(n, finalStates)) return n; 
			if (!C.contains(n)) {
				C.add(n);
				List<State> S = succ(n);
				Q.addAll(S);
			}
		}
	}
}
