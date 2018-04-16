#pragma once
#include<iostream>
#include<string>
#include<map>
#include<vector>
using namespace std;

struct node
{
	string character, code;
	double probability;
	node * left;
	node * right;
	
};

struct Char
{
	__int8 value;
	string name;
};


class huffman
{
	map<string, string> codes;
	map<string, int > freq;
	string in_message;
	string message_buffer;
	string out_message;
	node ** tree;
	int m_size;


public:
	huffman(string);
	string huffman::Decode(__int8 * tables, int size, string binary_message);
	void calc_probs();
	void build_tree();
	void read_message();
	void set_code();
	map<string, string> get_codes();
	void DFS(node *, int, string);
	__int8* canonical_transform();     // it converts huffman table to a sorted array(W.R.T. the arrangement of the chars in the table) of code word lengths 
	void change_codes();
	map<string, string> huffman::inverse_canonical(__int8* code_lengths);

	string getsize();
	~huffman();
};
