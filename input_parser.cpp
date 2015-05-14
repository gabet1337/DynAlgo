#include <vector>
#include <string>
#include <sstream>

using namespace std;

typedef pair<int,int> ii;
typedef pair<int, ii> iii;
typedef vector<iii> viii;

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
