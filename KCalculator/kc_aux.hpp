#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>
#include "KMatrix.hpp"


#ifndef KC_AUXILLIARY_HPP
#define KC_AUXILLIARY_HPP

typedef struct{
	std::string name;
	double var;
	std::string comment;
}com_double;

typedef struct{
	std::string name;
	std::string var;
	std::string comment;
}com_string;

typedef struct{
	std::string name;
	bool var;
	std::string comment;
}com_bool;

typedef struct{
	std::string name;
	KMatrix var;
	std::string comment;
}com_matrix;

typedef struct{
	std::vector<com_double>* double_list;
	std::vector<com_string>* string_list;
	std::vector<com_bool>* bool_list;
	std::vector<com_matrix>* matrix_list;
}vlist;

std::vector<std::string> fill_keywords();

bool add_matrix(std::vector<com_matrix>* vec, std::vector<std::string> words, vlist vars);

bool add_bool(std::vector<com_bool>* vec, std::vector<std::string> words, vlist vars );

bool add_string(std::vector<com_string>* vec, std::vector<std::string> words, vlist vars );

bool add_double(std::vector<com_double>* vec, std::vector<std::string> words, vlist vars );

void print_variables(vlist vars);

bool valid_varname(std::string name, vlist vars, std::string* error=NULL);

#endif
