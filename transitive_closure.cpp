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
using namespace std;

typedef vector<bool> vb;
typedef vector<vb> vvb;

typedef pair<int,int> ii;
typedef pair<int, ii> iii;
typedef vector<iii> viii;

vvb graph;
vvb trans;

vvb floyd_trans(vvb G) {
  size_t n = G.size();
  for (int k = 0; k < n; k++)
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        G[i][j] = G[i][j] || (G[i][k] && G[k][j]);
  return G;
}

void trans_eager() {
  for (size_t i = 0; i < trans.size(); i++) {
    if (i) cout << endl;
    for (size_t j = 0; j < trans.size(); j++)
      cout << trans[i][j] << " ";
  }
  cout << endl << endl;
}

void init_lazy(size_t n) {
  graph.assign(n, vb(n, false));
  for (int i = 0; i < n; i++)
    graph[i][i] = true;
}

void insert_lazy(int i, int j) {
  if (i == j) return;
  graph[i][j] = true;
}

void delete_lazy(int i, int j) {
  if (i == j) return;
  graph[i][j] = false;
}

void init_eager(size_t n) {
  graph.assign(n, vb(n, false));
  for (int i = 0; i < n; i++)
    graph[i][i] = true;
  trans.assign(n, vb(n, false));
  for (int i = 0; i < n; i++)
    trans[i][i] = true;
}

void insert_eager(int i, int j) {
  if (i == j) return;
  graph[i][j] = true;
  trans = floyd_trans(graph);
}

void delete_eager(int i, int j) {
  if (i == j) return;
  graph[i][j] = false;
  trans = floyd_trans(graph);
}

vector<string> &split(const string &s, char delim, vector<string> &elems) {
  stringstream ss(s);
  string item;
  while (getline(ss, item, delim)) {
      elems.push_back(item);
  }
  return elems;
}


vector<string> split(const string &s, char delim) {
  vector<string> elems;
  split(s, delim, elems);
  return elems;
}

//1 for init
//2 for insert
//3 for delete
//4 for transitive closure?
viii parse_input() {
  viii result;
  string line,temp;
  int arg1, arg2;
  while (getline(cin, line)) {
    if (line.substr(0, 4) == "init") {
      arg1 = atoi(line.substr(5, line.size()-1).c_str());
      result.push_back(iii(1, ii(arg1,arg1)));
    } else if (line.substr(0, 4) == "inse") {
      temp = line.substr(7, line.size()-1);
      vector<string> s = split(temp, ',');
      arg1 = atoi(s[0].c_str());
      arg2 = atoi(s[1].c_str());
      result.push_back(iii(2, ii(arg1,arg2)));
    } else if (line.substr(0, 4) == "dele") {
      temp = line.substr(7, line.size()-1);
      vector<string> s = split(temp, ',');
      arg1 = atoi(s[0].c_str());
      arg2 = atoi(s[1].c_str());
      result.push_back(iii(3, ii(arg1,arg2)));
    } else if (line.substr(0, 4) == "tran") {
      result.push_back(iii(4,ii(0,0)));
    }
  }
  return result;
}


int main() {
  viii cmds = parse_input();

  //eager:
  for (size_t i = 0; i < cmds.size(); i++) {
    if (cmds[i].first == 1)
      init_eager(cmds[i].second.first);
    else if (cmds[i].first == 2)
      insert_eager(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 3)
      delete_eager(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 4)
      trans_eager();
  }

  return 0;
}

