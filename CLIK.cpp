#include "CLIK.hpp"

using namespace std;

/*
Reads a string (defined by being enclosed in single or double quotes) and returns the detected string.

in - input string in which to look for a enclosed string
success - Returns true if string detected
start_idx - index in 'in' at which to begin scan
last_idx - index at which first detected string ended (aka index of closing quotes)
allow_before - if true, function will not fail if non whitespace occurs before detected string 

Returns first detected string, empty string upon fail
*/
string next_string(string in, bool& success, int start_idx, int* last_idx, bool allow_before){

	string out = "";
	bool in_string = false;

	success = false;
	*last_idx = start_idx;

	for (int i = start_idx; i < in.length() ; i++){
		if (in[i] == '\'' || in[i] == '\"'){
			
			if (in_string){ //End
				success = true;
				*last_idx = i;
				return out;
			}else{ //Begin string
				in_string = true;
			}
			
		}else if( in_string ){
			out = out + in[i];
		}else if(in[i] != ' ' && in[i] != '\t' && !allow_before){
			success = false;
			*last_idx = i;
			return "";
		}

	}

	return out;
}

/*
Reads a string (defined by being enclosed in single or double quotes) and returns the detected string.

in - input string in which to look for a enclosed string
success - Returns true if string detected
start_idx - index in 'in' at which to begin scan
last_idx - index at which first detected string ended (aka index of closing quotes)
allow_before - if true, function will not fail if non whitespace occurs before detected string 

Returns first detected string, empty string upon fail
*/
string next_phrase(string in, bool& success, int start_idx, char starter, char ender, int* last_idx, bool allow_before){

	string out = "";
	bool in_string = false;

	success = false;
	*last_idx = start_idx;

	for (int i = start_idx; i < in.length() ; i++){
		if (in[i] == starter){
			
			if (in_string){ //End

			}else{ //Begin string
				in_string = true;
			}
		}else if(in[i] == ender){

			if (in_string){
				success = true;
				*last_idx = i;
				return out;
			}else if(!allow_before){
				success = false;
				*last_idx = i;
				return "";
			}
				
		}else if( in_string ){
			out = out + in[i];
		}else if(in[i] != ' ' && in[i] != '\t' && !allow_before){
			success = false;
			*last_idx = i;
			return "";
		}

	}

	return out;
}

bool next_double(string in, double* value){
	
	try{
		*value = stod(in);
	}catch(...){
		*value = -1;
		return false;
	}

	return true;
}

// /*
// Determines if the next word
// */
// bool require_next_word(std::string in, sd::string required, int start_idx, int* last_idx=NULL){

// }