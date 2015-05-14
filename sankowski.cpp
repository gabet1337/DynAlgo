#include <iostream>
#include <random>
#include "matrix_inverse.cpp"
#include "input_parser.cpp"
using namespace std;

vvll G,R,A;
random_device rd;
mt19937 gen(rd());
uniform_int_distribution<ll> dis(1,MODULO_P-1);

ll get_random() {
  return dis(gen);
}

void init(int n) {
  G.assign(n, vll(n,0));
  A.assign(n, vll(n,0));
  R.assign(n, vll(n,0));
  for (int i = 0; i < n; i++) G[i][i] = A[i][i] = 1;
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      R[i][j] = get_random();
}

void insert(int i, int j) {
  if (A[i][j]) return;
  A[i][j] = 1;
  sherman_morrison(G, i, j, R[i][j], true);
}

void remove(int i, int j) {
  if (!A[i][j]) return;
  A[i][j] = 0;
  sherman_morrison(G, i, j, R[i][j], false);
}

int transitive_closure() {
  int res = 0;
  size_t size = G.size();
  for (int i = 0; i < size; i++)
    for (int j = 0; j < size; j++)
      if (i == j) continue;
      else if (G[i][j] != 0) res++;
  return res;
}

int main() {
  viii cmds = parse_input();

  for (size_t i = 0; i < cmds.size(); i++) {
    if (cmds[i].first == 1)
      init(cmds[i].second.first);
    else if (cmds[i].first == 2)
      insert(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 3)
      remove(cmds[i].second.first, cmds[i].second.second);
    else if (cmds[i].first == 4)
      cout << transitive_closure() << endl;
  }
  
  return 0;
}
