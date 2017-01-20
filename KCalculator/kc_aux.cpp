#include <ctype.h>

#include "kc_aux.hpp"
#include "string_manip.hpp"
#include "CLIK.hpp"

using namespace std;

vector<string> fill_keywords(){

	vector<string> keywords;

	keywords.push_back("exit"); //0
	keywords.push_back("set"); //1
	keywords.push_back("clear"); //2	
	keywords.push_back("cls"); //3
	keywords.push_back("help"); //4
	keywords.push_back("clvar"); //5 
	keywords.push_back("lsvar"); //6	
	keywords.push_back("prcd");	//7
	keywords.push_back("svprg"); //8
	keywords.push_back("exec"); //9
	keywords.push_back("matrix"); //10
	keywords.push_back("bool"); //11
	keywords.push_back("string"); //12
	keywords.push_back("double"); //13
		
	return keywords;

}

bool add_matrix(vector<com_matrix>* vec, vector<string> words , vlist vars){

	if (words.size() <= 0 || words[0] != "matrix") return false;

	if (words.size() <= 1 || !valid_varname(words[1], vars)) return false;

	return false;

}

bool add_bool(vector<com_bool>* vec, vector<string> words, vlist vars ){
return false;
}

bool add_string(vector<com_string>* vec, vector<string> words, vlist vars ){
return false;
}

bool add_double(vector<com_double>* vec, vector<string> words, vlist vars){

	//Declare local variables
	string name = "NO_NAME";
	double value = -1;
	string comment = "";

	//Check double keyword
	if (words.size() < 4 || words[0] != "double") return false;

	//Check name is valid
	string error;
	if (!valid_varname(words[1], vars, &error)){
		cout << '\t' << error << endl;
		return false;
	}
	name = words[1];

	//Check equal sign present
	if (words[2] != "=") return false;

	//Check double value is valid
	if (!next_double(words[3], &value)) return false;

	//Check for semicolons, or descriptor comments
	bool semi_used = false;
	for (int i = 4 ; i < words.size() ; i++){
		if (words[i] == "//"){
			cat_tokens(words, i+1, " ");
			break;
		}else if(words[i] == ";"){
			if (semi_used) return false;
			semi_used = true;
		}else{
			cout << "\t\t\t>" << words[i] << "<" << endl;
			return false;
		}
	}

	com_double new_double = {name, value, comment};
	vars.double_list->push_back(new_double);

	return true;
}

void print_variables(vlist vars){
	
}

bool valid_varname(string name, vlist vars, string* error){
	
	*error = "";

	if (!isalpha(name[0])) return false;

	for (int i = 0 ; i < name.length() ; i++){
		if (!(isalpha(name[i]) || name[i] == '_' || isnum(string(1, name[i])))) return false;
	}

	for (int i = 0 ; i < vars.double_list->size() ; i++){
		*error = "ERROR: Variable name is already claimed by double"; 
		if ((*(vars.double_list))[i].name == name) return false;
	}

	for (int i = 0 ; i < vars.matrix_list->size() ; i++){
		*error = "ERROR: Variable name is already claimed by matrix";
		if ((*(vars.matrix_list))[i].name == name) return false;
	}

	for (int i = 0 ; i < vars.string_list->size() ; i++){
		*error = "ERROR: Variable name is already claimed by string";
		if ((*(vars.string_list))[i].name == name) return false;
	}

	for (int i = 0 ; i < vars.bool_list->size() ; i++){
		*error = "ERROR: Variable name is already claimed by bool";
		if ((*(vars.bool_list))[i].name == name) return false;
	}	

	return true;
}