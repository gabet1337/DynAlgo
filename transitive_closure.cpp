//lazy implementation of transitive closure using Floyd-Warshall
//init: O(n^2)
//insert & delete: O(1)
//transitive closure: O(n^3)
#include <vector>
#include <string>
#include <iostream>
#include <cstdlib>
#include <sstream>
#include <algorithm>
#include <iterator>
#include "input_parser.cpp"
using namespace std;

typedef vector<bool> vb;
typedef vector<vb> vvb;

vvb graph;
vvb trans;
int num_edges = 0;

vvb floyd_trans(vvb &G) {
  size_t n = G.size();
  for (int k = 0; k < n; k++)
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        G[i][j] = G[i][j] || (G[i][k] && G[k][j]);
  return G;
}

void update_num_edges() {
  int num = 0;
  for (int i = 0; i < trans.size(); i++)
    for (int j = 0; j < trans.size(); j++)
      if (i == j) continue;
      else if (trans[i][j]) num++;
  num_edges = num;
}

int trans_eager() {
  return num_edges;
}

int trans_lazy() {
  trans = floyd_trans(graph);
  update_num_edges();
  return num_edges;
}

void insert_lazy(int i, int j) {
  if (i == j) return;
  graph[i][j] = true;
}

void delete_lazy(int i, int j) {
  if (i == j) return;
  graph[i][j] = false;
}

void init(size_t n) {
  graph.assign(n, vb(n, false));
  trans.assign(n, vb(n, false));
  for (int i = 0; i < n; i++)
    graph[i][i] = trans[i][i] = true;
}

void insert_eager(int i, int j) {
  if (i == j) return;
  graph[i][j] = true;
  trans = floyd_trans(graph);
  update_num_edges();
}

void delete_eager(int i, int j) {
  if (i == j) return;
  graph[i][j] = false;
  trans = floyd_trans(graph);
  update_num_edges();
}



int main() {
  viii cmds = parse_input();

  //eager:
  // for (size_t i = 0; i < cmds.size(); i++) {
  //   if (cmds[i].first == 1)
  //     init(cmds[i].second.first);
  //   else if (cmds[i].first == 2)
  //     insert_eager(cmds[i].second.first, cmds[i].second.second);
  //   else if (cmds[i].first == 3)
  //     delete_eager(cmds[i].second.first, cmds[i].second.second);
  //   else if (cmds[i].first == 4)
  //     cout << trans_eager() << endl;
  // }
  //lazy
  for (size_t i = 0; i < cmds.size(); i++) {
    if (cmds[i].first == 1)
      init(cmds[i].second.first);
    else if (cmds[i].first == 2)
      insert_lazy(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 3)
      delete_lazy(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 4)
      cout << trans_lazy() << endl;
  }

  return 0;
}

