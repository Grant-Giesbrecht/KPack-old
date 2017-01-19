#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>

#include "string_manip.hpp"
#include "stdutil.hpp"
#include "kc_auxilliary.hpp"

using namespace std;



int main(){

	//Declare keywords
	vector<string> keywords = fill_keywords();

	//Declare variable vectors
	vector<com_double> double_list;
	vector<com_string> string_list;
	vector<com_bool> bool_list;
	vector<com_matrix> matrix_list;

	//Main loop
	bool running = true;
	string input;
	vector<string> words;
	int idx;
	while(running){

		//Prompt and recieve input
		cout << ": ";
		getline(cin, input);
		words = parse(input, " ");

		idx = strvec_contains(keywords, words[0]);
		if (idx == 0){ //Exit CMD
			running = false;
			break;
		}else if (idx == 2 || idx == 3){ //Clear CMD
			try{
				system("clear");
				system("cls");
			}catch(...){
			}
		}else if (idx == 4){ //Help CMD
			print_file("kc_help.txt", 0);
		}else if(idx == 5){ //Clear vars CMD
			double_list.clear();
			matrix_list.clear();
			bool_list.clear();
			string_list.clear();
			cout << "\tAll variables cleared" << endl;
		}else if(idx == 6){ //List vars CMD
			print_variables(double_list, matrix_list, bool_list, string_list);
		}else if(idx == 7){ //Print record

		}else if(idx == 8){ //Save record as program file

		}else if(idx == 9){ //Execute program file

		}else if (idx == 10){ //Matrix CMD
			if (!add_matrix(&matrix_list, words)) cout << "\tERROR: Failed to add matrix" << endl;
		}else if (idx == 11){ //Bool CMD
			if (!add_bool(&bool_list, words)) cout << "\tERROR: Failed to add bool" << endl;
		}else if (idx == 12){ // String CMD
			if (!add_string(&string_list, words)) cout << "\tERROR: Failed to add string" << endl;
		}else if (idx == 13){ //Double CMD
			if (!add_double(&double_list, words)) cout << "\tERROR: Failed to add double" << endl;
		}else{ // DETERMINE IF DOUBLE OR EVALUATION

		}
	}

	return 0;
}

