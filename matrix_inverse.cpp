#include <iostream>
#include <vector>
#define MODULO_P 2147483647LL
using namespace std;
typedef long long int ll;
typedef vector<ll> vll;
typedef vector<vll> vvll;
typedef vector<int> vi;
typedef vector<vi> vvi;

// return a % b (positive value)
ll mod(ll a, ll b) {
  return ((a%b)+b)%b;
}

// returns d = gcd(a,b); finds x,y such that d = ax + by
ll extended_euclid(ll a, ll b, ll &x, ll &y) {  
  ll xx = y = 0;
  ll yy = x = 1;
  while (b) {
    ll q = a/b;
    ll t = b; b = a%b; a = t;
    t = xx; xx = x-q*xx; x = t;
    t = yy; yy = y-q*yy; y = t;
  }
  return a;
}

// computes b such that ab = 1 (mod n), returns -1 on failure
ll mod_inverse(ll a, ll n) {
  ll x, y;
  ll d = extended_euclid(a, n, x, y);
  if (d > 1) return -1;
  return mod(x,n);
}

vvll AuvA;
ll vAu, mod_inv;
void sherman_morrison(vvll &A, ll row, ll col, ll val, bool is_insert) {
  int n = (int)A.size();
  if (AuvA.size() == 0) AuvA.assign(n,vll(n,0));
  //calc AuvA
  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++) {
      AuvA[i][j] = (A[i][row] * ((A[col][j]*val)%MODULO_P))%MODULO_P;
    }
  
  //calc 1(+-) vAu
  vAu = (A[col][row] * val * val)%MODULO_P;
  mod_inv = mod_inverse(vAu,MODULO_P);
  //ll vAu = vA[row] * val;
  if (is_insert) vAu = (vAu+1)%MODULO_P;
  else vAu = (1 - vAu + MODULO_P)%MODULO_P;

  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++)
      AuvA[i][j] = (AuvA[i][j] * mod_inv)%MODULO_P;

  for (int i = 0; i < n; i++)
    for (int j = 0; j < n; j++) {
      if (is_insert)
        A[i][j] = (A[i][j]-AuvA[i][j]+MODULO_P)%MODULO_P;
      else
        A[i][j] = (A[i][j]+AuvA[i][j])%MODULO_P;
    }
}
