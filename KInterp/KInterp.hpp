#ifndef KINTERP_HPP
#define KINTERP_HPP

#include <string>
#include <stdio.h>

#include "KMatrix.hpp"
#include "KVar.hpp"

typedef struct{
	char type;
	std::string s;
	KMatrix km;
	bool b;
	double d;
}all_ktype;

bool interpret(std::string input, KVar& vars, all_ktype& output);

#endif
