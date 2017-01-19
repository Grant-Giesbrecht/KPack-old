#include "kc_auxilliary.hpp"

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

bool add_matrix(vector<com_matrix>* vec, vector<string> words ){

	return false;

}

bool add_bool(vector<com_bool>* vec, vector<string> words ){
return false;
}

bool add_string(vector<com_string>* vec, vector<string> words ){
return false;
}

bool add_double(vector<com_double>* vec, vector<string> words ){
return false;
}

void print_variables(std::vector<com_double> double_list, std::vector<com_matrix> matrix_list, std::vector<com_bool> bool_list, std::vector<com_string> string_list){
	
}