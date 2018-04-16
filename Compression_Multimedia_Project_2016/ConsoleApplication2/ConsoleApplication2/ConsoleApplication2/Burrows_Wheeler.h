#include<iostream>
#include<string>
#include<algorithm>
#include<vector>
#include<map>
using namespace std;

#pragma once
class Burrows_Wheeler
{
	string file;
	int size;

	string table;

	//used in decompress
	map <pair<int,char>,int> presence_in_table;//index charcter presence(based on 1)
	map <pair<int,char>,int> presence_in_sorted_table;
	map <pair<int, char>, int> index_in_table;
	
public:
	Burrows_Wheeler();
	string Compress(string x);
	string decompress(string table);
	void intializemaps(string,string);
};

