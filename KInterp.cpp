#include <vector>

#include "KInterpAux.hpp"
#include "KInterp.hpp"
#include "string_manip.hpp"
#include "stdutil.hpp"
#include "kc_aux.hpp"

using namespace std;

const string this_filename = "KInterp.cpp";

/*
Interprets a string as a mathematical expression. Accepts variables (specified in the KVar object), and will return the result of the expression.

input - input string to analyze
vars - KVar object containing variables to use in analysis (and to which to add if new variables are created)
out - Result of evaluation

Returns true if evaluation completed successfully and without error. Else returns false and reports the error code in 'out'.
*/
bool interpret(std::string input, KVar& vars, all_ktype& out){

	//Process input string (Ensure whitespace and parse)
	ensure_whitespace(input, "[](){};+-*=^%!");
	ensure_whitespace_full(input, "//");
	ensure_whitespace_full(input, "||");
	ensure_whitespace_full(input, "&&");
	vector<string> words = parse(input, " ");

	// for (int i = 0 ; i < words.size() ; i++){
	// 	std::cout << ">" << words[i] << "<" << endl;
	// }

	/*--------------------------------------------------------------------------------
	------ Now program checks each operator and makes new calls to 'interpret()' -----
	--------------------------------------------------------------------------------*/

	//Check for equals signs
	for (int i = 0 ; i < words.size() ; i++){
		if (words[i].length() == 1 && words[i][0] == '='){ //Variable assignment detected, evaluate expression and assign to left side

			//Evaluate expression
			if (!interpret(cat_tokens(words, i+1, " "), vars, out)){
				return false;
			}

			//Detect the variable and it's type
			string varname;
			string error;
			char type;
			if (!detect_variable(words, 0, i-1, vars, varname, type, &error)){
				out.type = 'e';
				out.s = error;
				return false;
			}

			//Assign value to new variable
			if (type != out.type){
				out.s = "ERROR: Can not assign " + char_to_typestr(out.type) + " value to " + char_to_typestr(type) + " variable.";
				out.type = 'e';
				return false;
			}

			// cout << "Variable Name: " << varname << endl;
			// cout << "Variable Type: " << type << endl;
		}
	}

	//Convert variables to literals
	for (int i = 0 ; i < words.size() ; i++){

		if (is_varname(words[i], vars, NULL)){
			char type = vars.get_var_type(words[i]);
			if (type == 'd'){
				double double_out;
				cout << i << "\t " << words[i] << endl;
				if (!vars.get_double(words[i], double_out)){
					out.type ='e';
					out.s = "ERROR: Failed to locate double variable '" + words[i] + "'";
					return false;
				}
				words[i] = to_string(double_out);
			}else if(type == 'b'){
				bool bool_out;
				if (!vars.get_bool(words[i], bool_out)){
					out.type = 'e';
					out.s = "ERROR: Failed to locate bool variable '" + words[i] + "'";
					return false;
				}
				words[i] = bool_to_str(bool_out, true);
			}else if(type == 's'){
				string str_out;
				if (!vars.get_string(words[i], str_out)){
					out.type = 'e';
					out.s = "ERROR: Failed to locate string variable '" + words[i] + "'";
					return false;
				}
				words[i] = str_out;
			}else if(type == 'm'){
				KMatrix mat_out;
				if (!vars.get_matrix(words[i], mat_out)){
					out.type = 'e';
					out.s = "ERROR: Failed to locate matrix variable '" + words[i] + "'";
					return false;
				}
				words[i] = mat_out.get_string();
			}else{
				out.type = 'e';
				out.s = "SOFTWARE ERROR: Failed to determine variable type. Problem in file: " + this_filename;
				return false;
			}
		}
	}

	for (int i = 0 ; i < words.size() ; i++){
		std::cout << ">" << words[i] << "<" << endl;
	}

	return true;
}
