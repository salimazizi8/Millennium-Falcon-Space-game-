package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.IGraph;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.graph.Vertex;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship {
    long startTime = System.nanoTime(); // start time of rescue phase

/**
 * The spaceship is on the location given by parameter state.
 * Move the spaceship to Kamino and then return.
 * This completes the first phase of the mission.<br><br>
 * <p>
 * If the spaceship continues to move after reaching Kamino, rather than
 * returning, it will not count. A return from this procedure while
 * not on Kamino count as a failure.<br><br>
 * <p>
 * There is no limit to how many steps you can take, but the score is
 * directly related to how long it takes you to find Kamino.<br><br>
 * <p>
 * At every step, you know only the current planet's ID, the IDs of
 * neighboring planets, and the strength of the signal from Kamino
 * at each planet.<br><br>
 * <p>
 * In this stage of the game,<br>
 * (1) In order to get information about the current state, use
 * functions currentID(), neighbors(), and signal().<br><br>
 * <p>
 * (2) Use method onKamino() to know if your ship is on Kamino.<br><br>
 * <p>
 * (3) Use method moveTo(int id) to move to a neighboring planet
 * with the given ID. Doing this will change state to reflect the
 * ship's new position.
 * */

// WORKS (Tested)?
// Implemented using Depth-First Search (DFS) Algorithm.
    @Override
    public void hunt(HunterStage state) {
        Stack<Vertex> stack = new Stack<>();

        int initialID = state.currentID();
        List<Vertex> visited1 = new ArrayList<>();

        iterativeDFS(state, state.currentID(), visited1);
//        stack.push(state.currentID());
//        Set<Integer> visited = new HashSet<>();
//        Stack<Integer> previous = new Stack<>();
//        previous.push(initialID);
//        //int limit = 5, j = 0;
//// && j < limit
//        while(!stack.isEmpty()) {
//
//            int current = stack.pop();
////            System.out.println(current);
//
//            if (current != initialID) {
//                List<PlanetStatus> spice = new ArrayList<>();
//                PlanetStatus[] neighbours = state.neighbors();
////
//                spice = Arrays.stream(neighbours).sorted((n1, n2) -> Double.compare(n1.signal(), n2.signal())).collect(Collectors.toList());
//
//
//
//                List<Integer> ids = new ArrayList<>();
//                for (int k = 0; k < neighbours.length; k++) {
//                    ids.add(neighbours[k].id());
//
//                }
//
//
//
//                if (ids.contains(current)) {
//                    previous.push(current);
//                    state.moveTo(current);
//                } else {
//                    boolean found = false;
//                    while (!found) {
//                        int gama = previous.pop();
//                        state.moveTo(gama);
//
////                        PlanetStatus[] temNeighbors = state.neighbors();
////                        for (int j = 0; j < temNeighbors.length; j++) {
////                            if (temNeighbors[j].id() == current) {
////                                state.moveTo(current);
////                                found = true;
////                            }
////                        }
//                    }
//                }
//
//            }
//

//
//            if (!visited.contains(current)) {
//                visited.add(current);
//                PlanetStatus[] aBunch = state.neighbors();
////                System.out.print("Neighbours: ");
//                for (int i = 0; i < aBunch.length; i++) {
////                    System.out.print(aBunch[i].id());
////                    System.out.print(" ");
//          //          stack.push(current);
//                    stack.push(aBunch[i].id());
//                }
//            }
////            System.out.println(stack);
////            j++;
//        }
    }

    private void iterativeDFS (HunterStage state, int EarthID, List<Vertex> visited) {
        Stack<Vertex> stack = new Stack<>();
        Stack<Vertex> travelled = new Stack<>();
        Vertex earth = new Vertex(EarthID, "Earth");

        stack.push(earth);
        travelled.push(earth);

        while (!stack.isEmpty()) {
            Vertex current =  stack.pop();

            if (!visited.contains(current)) {
                visited.add(current);

                if (current.id() != EarthID) {
                    state.moveTo(current.id());
                    travelled.push(current);
                    if (state.onKamino()) {
                        return;
                    }
                    visited.add(current);
                }

                List<PlanetStatus> spice = new ArrayList<>();
                PlanetStatus[] neighbours = state.neighbors();

                spice = Arrays.stream(neighbours).sorted((n1, n2) -> Double.compare(n1.signal(), n2.signal())).collect(Collectors.toList());

                for (PlanetStatus neighbor : spice) {
                    Vertex neighbor1 = new Vertex(neighbor.id(), neighbor.name());

                    stack.push(neighbor1);
                }
            } else {
                state.moveTo(travelled.pop().id());
            }
        }

    }

/**
 * The spaceship is on the location given by state. Get back to Earth
 * without running out of fuel and return while on Earth. Your ship can
 * determine how much fuel it has left via method fuelRemaining(), and how
 * much fuel is needed to travel on a link via link's fuelNeeded().<br><br>
 * <p>
 * Each Planet has some spice. Moving to a Planet automatically
 * collects any spice it carries, which increases your score. your
 * objective is to return to earth with as much spice as possible.<br><br>
 * <p>
 * You now have access to the entire underlying graph, which can be
 * accessed through parameter state. currentNode() and earth() return
 * planets of interest, and planets() returns a collection of
 * all planets in the graph.<br><br>
 * <p>
 * Note: Use moveTo() to move to a destination node adjacent to
 * your ship's current node.
 **/

    @Override
    public void gather(GathererStage state) {
        // TODO: Implement this method
     //   Queue<Planet> queueOfPlanets = new LinkedList<>();
        Planet currentPlan = state.currentPlanet();

        // Source and Destination Planets for our Game:
        Planet source = state.currentPlanet();
        Planet sink = state.earth();

        // initially all planets are Undiscovered:
      //  Set<Planet> UndiscoveredPlanets = state.planets();
        // add the currentPlanet first also to the UnDiscovered Set:
       // UndiscoveredPlanets.add(currentPlan);

        // Label the initial Planet as Discovered:
       // UndiscoveredPlanets.remove(currentPlan);

        // Get the underlying Graph of the Universe:
        ImGraph<Planet, Link> imGraph = state.planetGraph();
        // Add the current Planet into the Queue:
     //   queueOfPlanets.add(currentPlan);

      //  while(!queueOfPlanets.isEmpty()) {
       //     Planet v = queueOfPlanets.remove();  // retrieving and removing the head of this queue
            // if we have reached back to Earth, we are done.
//            if (v.equals(state.earth())) {
//                return;
//            }

           List<Planet> sourceToSink =  imGraph.shortestPath(source, sink);
           for (Planet element: sourceToSink) {
           //    if (UndiscoveredPlanets.contains(element)) {
                   state.moveTo(element);
                   if(element.equals(state.earth()))
                       return;
           }
           // Do we really need Enqueing and Dequing because, we have the List
           // of the Planets to pass through !
        }

    }
