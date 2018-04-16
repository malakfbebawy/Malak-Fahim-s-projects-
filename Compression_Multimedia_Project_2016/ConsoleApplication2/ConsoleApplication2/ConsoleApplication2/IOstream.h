#include<iostream>
#include<fstream>
#include<string>
#include<bitset>
using namespace std;
#pragma once
class IOstream
{
public:
	string read(string);
	void writetobinfile(string name,int size,__int8 **x,string message);
	void readfrombinfile(string, int*, __int8**, string*);
	void write(string,string);
};

