# CZ2001_Project2

1. Problem

You are given an undirected unweighted graph G, which represents a city’s road
network with n nodes being intersections/endpoints and m edges being roads. Among
the n nodes, h of them are hospitals and you are interested in finding, for each node,
the distance (i.e., the number of edges in the shortest path) from each node to the
nearest hospital. h could take any value from 1 to n.

(a) Design an algorithm for computing the distance from each node in G to its
nearest hospital. Output the distance and the shortest path for each node to a
file.

(b) Design an algorithm to complete the task (a) but its time complexity should not
depend on the total number of hospitals h. You could skip (b) if your algorithm
in (a) already satisfies this complexity constraint.

(c) In some circumstances (e.g., big disasters, fires, etc.), having the distance from
each node to the nearest hospital is not good enough. Instead, we are
interested in finding the distances to top-2 nearest hospitals from each node.
Revise your algorithm in (b) to accommodate this new requirement. If revision
is not possible, you are free to design a new algorithm.

(d) Propose an algorithm that works generally for computing the distances from
each node to top-k nearest hospitals for an input value of k.

