#include "string_manip.hpp"

using namespace std;

/*
 Ensures that space characters sandwich all the target characters in the string 'in'
 
 in - string in which to ensure targets surrounded by whitespace
 targets - characters to surround with whitespace
 
 Void return
 */
void ensure_whitespace(string& in, string targets){
    
    //Add whitespace buffer around all target characters
    for (int i = 0 ; i < in.length() ; i++){
        if (targets.find(in[i]) != string::npos){ //Found in string
            in = in.substr(0, i) + " " + in[i] + " " + in.substr(i+1);
            i++;
        }
    }
    
    //Remove excess white space - no consecutive spaces
    for (int i = 0; i+1 < in.length() ; i++){
        if (in[i] == ' ' && in[i+1] == ' '){
            in = in.substr(0, i) + in.substr(i+1);
            i--;
        }
    }
    
}

/*
 Parses a string into a vector of strings. String broken at each instance of 'delin'. 'delin' is excluded from the output.
 
 input - string to parse
 delin - deliniating characters
 
 Returns a vector of parsed strings
 */
vector<string> parse(string input, string delin){
    
    vector<string> output;
    
    int len_counter = 0; //Number of elements in the deliated seciton
    for (int i = 0; i < input.length() ; i++){
        
        if ((delin.find(input[i]) != string::npos)){ //Deliniator found
            
            //Add block to output if section exists (not two deliniators in a row)
            if (len_counter > 0){
                output.push_back(input.substr(i-len_counter , len_counter));
            }
            
            //Reset length counter
            len_counter = 0;
            
        }else if(i+1 == input.length()){ //Handle end conditions
            len_counter++;
            
            if (input.substr(i-len_counter+1 , len_counter).length() > 0){
                output.push_back(input.substr(i-len_counter+1 , len_counter));
            }
            
        }else{
            
            //Increment length counter
            len_counter++;
            
        }
    }
	
	return output;
}

/*
 Determines if the string represents a number (decimal or integer)
 
 s - string to analyze
 
 Returns true if 's' is a number
 */
bool isnum(string s){
    string::const_iterator it = s.begin();
    while (it != s.end() && (std::isdigit(*it) || *it == '.')) ++it;
    return !s.empty() && it == s.end();
}


/*
 Removes any characters following an instance of the 'commentor' string. Must be complete 'commentor' to trigger. Modifies input string 's'.
 
 s - string whose comments are to be removed
 commentor - string indicating comment follows
 
 Void return
 */
void remove_comments(string& s, string commentor){
    
	for (int i = 0 ; i < s.length() ; i++){
		if (s.substr(i, commentor.length()) == commentor){
			s = s.substr(0, i);
		}
	}

}

/*
 Removes any character included in 'targets' from the end of 's'. The target char must be the last character in order to be removed. Modifies 's'
 
 s - string to modify
 targets - characters to target
 
 Void return
 */
void remove_from_end(string& s, string targets){

	for (int i = (int)s.length() - 1 ; i >= 0 ; i--){
		if ( targets.find(s.substr(i, 1)) != string::npos){
			s.pop_back();
		}else{
			i = -1;
		}
	}

}

/*
 Concatonates a vector of strings into single string
 
 t - vector of strings to concatonate
 idx - index at which to start concatonating (inclusive & through end)
 joint - string to add between each vector element concatonated
 
 Returns concatonated strings
 */
string cat_tokens(vector<string> t, int idx, string joint){

	if (t.size() <= idx){
		return "";
	}
    
    if (idx < 0) idx = 0;

	string out = t[idx]; 
	for (int i = idx+1 ; i < t.size() ; i++){
		out = out + joint + t[i]; 
	} 

	return out;
}

/*
 Converts the input string's lowercase letters to uppercase letters.
 
 s - string to convert to upper case
 
 Returns the capitolized string
 */
string to_uppercase(std::string s){
	for (int i = 0; i < s.length() ; i++){
		s[i] = toupper(s[i]);
	}
	return s;
}

/*
 Converts the input string's uppercase letters to lowercase letters.
 
 s - string to convert
 
 Rerturns the lowercase string
 */
string to_lowercase(std::string s){
	for (int i = 0; i < s.length() ; i++){
		s[i] = tolower(s[i]);
	}
	return s;
}

/*
 Returns a string representing the input boolean value.
 
 x - bool representing value to output
 uppercase - if true, will print TRUE/FALSE instead of True/False
 */
string bool_to_str(bool x, bool uppercase){
	if (x){
		if (uppercase){
			return "TRUE";
		}else{
			return "True";
		}
	}else{
		if (uppercase){
			return "FALSE";
		}else{
			return "False";
		}
	}
}

string format_newline(string input, string prefix){
    
    string out = "";
    
    vector<string> parts = parse(input, "\n");
    for (unsigned long i = 0 ; i < parts.size() ; i++){
        if (out != "") out = out + '\n';
        out = out + prefix + parts[i];
    }
    
    return out;
}




