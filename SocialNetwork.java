import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * BiBFS : faster than BFS Suppose every person has k friends, and Source S and
 * Destination D have a friend C in common. 1. Traditional breadth-first search
 * from S to D: We go through roughly k+k*k nodes: each of S¡¯s k friends, and
 * then each of their k friends. 2. Bidirectional breadth-first search: We go
 * through 2k nodes: each of S¡¯s k friends and each of D¡¯s k friends. Of course,
 * 2k is much less than k+k*k. 3. Generalizing this to a path of length q, we
 * have this: 3.1 BFS: O(kq) 3.2 Bidirectional BFS: 0( kq/2 + kq/2), which is
 * just 0( kq/2)
 * 
 * @author shuoshuo
 * 
 */
public class SocialNetwork {

	public Deque<Person> findPathBiBFS(Map<Integer, Person> people, int source,
			int destination) {
		// define two BFSData
		BFSData sourceData = new BFSData(people.get(source));
		BFSData destinateData = new BFSData(people.get(destination));

		while (!sourceData.isFinished() && !destinateData.isFinished()) {
			// search from source
			Person meet = searchNextLevel(sourceData, destinateData);
			// find
			if (meet != null) {
				return merge(sourceData, destinateData, meet.getId());
			}

			// search from destination
			meet = searchNextLevel(destinateData, sourceData);
			// find
			if (meet != null) {
				return merge(destinateData, sourceData, meet.getId());
			}
		}

		return null;
	}

	private Deque<Person> merge(BFSData sourceData, BFSData destinateData,
			int id) {
		// endl -> source, end2 -> dest
		PathNode end1 = sourceData.visited.get(id);
		PathNode end2 = destinateData.visited.get(id);

		Deque<Person> pathOne = end1.collapse(false);
		Deque<Person> pathTwo = end2.collapse(true);

		pathTwo.removeFirst(); // remove connection
		pathOne.addAll(pathTwo); // add second path

		return pathOne;
	}

	// search one level each time and return the meet person if got
	private Person searchNextLevel(BFSData sourceData, BFSData destinateData) {
		// count the current node in cur level
		int size = sourceData.toVisited.size();

		for (int i = 0; i < size; i++) {
			// iterator person in cur level
			PathNode curNode = sourceData.toVisited.poll();

			// check whether visited
			if (destinateData.visited.containsKey(curNode.getPerson().getId())) {
				return curNode.getPerson();
			}

			// get friends
			for (Person friend : curNode.getPerson().getFriends()) {
				// add friend to visited and toVisited
				if (!sourceData.visited.containsKey(friend.getId())) {
					PathNode next = new PathNode(friend, curNode);
					// flag visited
					sourceData.visited.put(friend.getId(), next);
					// add to queue to next level visit
					sourceData.toVisited.add(next);
				}
			}
		}

		return null;
	}
}

class BFSData {
	Person person;
	public Map<Integer, PathNode> visited = new HashMap<>();
	public Queue<PathNode> toVisited = new LinkedList<>();

	BFSData(Person person) {
		this.person = person;
		PathNode sourcePath = new PathNode(person, null);
		visited.put(person.getId(), sourcePath);
		toVisited.add(sourcePath);
	}

	public boolean isFinished() {
		return toVisited.isEmpty();
	}
}

// store visited path
class PathNode {
	Person person;
	PathNode pre;

	PathNode(Person person, PathNode pre) {
		this.person = person;
		pre = null;
	}

	Person getPerson() {
		return person;
	}

	public Deque<Person> collapse(boolean startsWithRoot) {
		Deque<Person> path = new LinkedList<>();

		PathNode node = this;
		while (node != null) {
			if (startsWithRoot) {
				path.addLast(node.getPerson());
			} else {
				path.addFirst(node.getPerson());
			}

			node = node.pre;
		}

		return path;
	}
}

class Person {
	int id;
	String name;
	// friends list
	List<Person> friends;

	Person(int id, String name) {
		this.id = id;
		this.name = name;
	}

	// add friend to list
	public void addFriends(Person p) {
		friends.add(p);
	}

	// return friends
	public List<Person> getFriends() {
		return friends;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

class Machine {
	int id;
    Map<Integer, Person> people = new HashMap<>();
    
	public Person getPersonWithID(int personID) {
		return people.get(personID);
	}
}


// A server that holds list of all machines
class Server {
	HashMap<Integer, Machine> machines = new HashMap<Integer, Machine>();
	HashMap<Integer, Integer> personToMachineMap = new HashMap<Integer, Integer>();

	public Machine getMachineWithid(int machineID) {
		return machines.get(machineID);
	}

	public int getMachineIDForUser(int personID) {
		Integer machineID = personToMachineMap.get(personID);
		return machineID == null ? -1 : machineID;
	}

	public Person getPersonWithID(int personID) {
		Integer machineID = personToMachineMap.get(personID);
		if (machineID == null)
			return null;

		Machine machine = getMachineWithid(machineID);
		if (machine == null)
			return null;

		return machine.getPersonWithID(personID);
	}
}