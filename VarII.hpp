#include <iostream>
#include <stdio.h>
#include <fstream>
#include <vector>
#include <string>

#include "../Libraries/Eigen/Eigen"

#include "../KMatrix/KMatrix.hpp"
#include "../UFiles/string_manip.hpp"
#include "../UFiles/stdutil.hpp"

#ifndef VAR_II_HPP
#define VAR_II_HPP

//User Macros
#define VRII_COMMENT_STYLE 1
	// VRII_COMMENT_STYLE options
	#define VRII_COMMENT_ABOVE 1.0
	#define VRII_COMMENT_SAME_LINE 2.0
#define VRII_DOUBLE_SPACE 2
#define VRII_SAVE_COMMENTS 3
#define VRII_READ_COMMENTS 4

#define VRII_HEADER_COMMENT "VarII_Header_Comment_NOT_A_VARIABLE"

typedef struct{
	std::string ID;
	char type;
}identifier;

class VarII{
public:
	VarII();
	VarII(std::string filename);
	~VarII();

	void set(int prop, double val);
	void set(int prop, bool val=true);
	void set(int prop, std::string val);

	//Access info
	bool contains(std::string var_id);
	void clear_contents();
	std::vector<std::string> get_variable_IDs();
	int num_variables();
	bool get_var_index(std::string var_id, int* index, char* type);
	bool get_var_lindex(std::string var_id, int* index, char* type);
	
	//Add/remove variables
	bool add_double(std::string var_id, double val, std::string comment="");
	bool get_double(std::string var_id, double& out);
	bool add_matrix(std::string var_id, KMatrix& val, std::string comment=""); //Saved as Eigen Matrix
	bool get_matrix(std::string var_id, KMatrix& out); //Can also retrieve Eigen Matricies
	bool add_matrix(std::string var_id, Eigen::MatrixXd& val, std::string comment=""); //Can be retrieved as KMatrix
	bool get_matrix(std::string var_id, Eigen::MatrixXd& out); //Can also receive KMatricies (which are all stored as Eigen Matrix);
	bool add_string(std::string var_id, std::string val, std::string comment="");
	bool get_string(std::string var_id, std::string& out);
	bool add_bool(std::string var_id, bool val, std::string comment="");
	bool get_bool(std::string var_id, bool& out);
	std::string get_comment(std::string var_id, bool& success);
	std::string get_comment(std::string var_id);

	//Read write functions
	bool load_file(std::string filename, bool print_out=false); // DO THIS - Not yet done - headers not implemented, string reading must still incorperate special comments, I must compile and test the function, and I must write the function to do a full-match comment check (for // comments)
	bool write_file(std::string filename); //DO THIS

	//Standard functions
	void print(int indent=0, bool use_spaces=false);
	void println(int indent=0, bool use_spaces=false);

private:

	//Settings Variables
	bool save_comments;
	bool read_comments;
	int comment_style;
	bool double_space_comment;
	bool print_allowed;

	//Vectors
	std::vector<identifier> IDs;
	std::vector<std::string> comments; //Alligns with IDs in vector
	std::vector<double> double_vars;
	std::vector<KMatrix> kmatrix_vars;
	std::vector<Eigen::MatrixXd> matrix_vars;
	std::vector<std::string> string_vars;
	std::vector<bool> bool_vars;
	
	//Private Functions
	int count_type(int index, char type=-1);

};


#endif
