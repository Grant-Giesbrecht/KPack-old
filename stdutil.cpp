#include <tgmath.h>
#include <fstream>
#include <iomanip>

#include "stdutil.hpp"

using namespace std;

void choose_scientific(double in, double threshold, double precision){
    
    if (in / threshold >= 1 || in < pow(10, -1*precision) ){
        cout.precision(precision);
        cout << std::scientific;
    }else{
        cout.precision(precision);
        cout << std::fixed;
    }
}

bool print_file(std::string filename, int tabs){
    
    ifstream file(filename);
    if (!file.is_open()){
        return false;
    }
    
    string s;
    while (file.good()){
        getline(file, s);
        
        cout << s << endl;
    }
    
    return true;
}

void print_vector(vector<vector<double> > v, int indent, bool spaces){

	//Select indentation type
	char indent_char = '\t';
	if (spaces){
		indent_char = ' ';
	}

	for (int r = 0 ; r < v.size() ; r++){
		
		//indent
		for (int i = 0; i < indent ; i++){
			cout << indent_char;
		}

		cout << "| ";
		for (int c = 0 ; c < v[r].size()-1 ; c++){
			cout << v[r][c] << ", ";
		}
		cout << v[r][v[r].size()-1] << " |" << endl;
	}

}

string indent_line(int indentation, bool use_spaces, bool indent_in_function){

	char ind = '\t';
	if (use_spaces) ind = ' ';

	string out = ""; 
	for (int i = 0 ; i < indentation ; i++){
		out = out + ind;
	}

	if (indent_in_function) cout << out;
	
	return out;
}