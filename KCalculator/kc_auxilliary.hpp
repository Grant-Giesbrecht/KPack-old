#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>
// #include "KMatrix.hpp"


#ifndef KC_AUXILLIARY_HPP
#define KC_AUXILLIARY_HPP

typedef struct{
	std::string var;
	std::string comment;
}com_double;

typedef struct{
	std::string var;
	std::string comment;
}com_string;

typedef struct{
	bool var;
	std::string comment;
}com_bool;

typedef struct{
// 	KMatrix var;
	std::string comment;
}com_matrix;

std::vector<std::string> fill_keywords();

bool add_matrix(std::vector<com_matrix>* vec, std::vector<std::string> words );

bool add_bool(std::vector<com_bool>* vec, std::vector<std::string> words );

bool add_string(std::vector<com_string>* vec, std::vector<std::string> words );

bool add_double(std::vector<com_double>* vec, std::vector<std::string> words );

void print_variables(std::vector<com_double> double_list, std::vector<com_matrix> matrix_list, std::vector<com_bool> bool_list, std::vector<com_string> string_list);

#endif
