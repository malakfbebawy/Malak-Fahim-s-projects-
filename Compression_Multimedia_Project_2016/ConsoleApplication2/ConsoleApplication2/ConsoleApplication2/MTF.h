#pragma once
#include<iostream>
#include<string>
#include<sstream>
using namespace std;


class MTF
{
private :
	string all_chars;        //all 256 characters

public:
	MTF();
	string Compress(string);
	string Decompress(string);
};

