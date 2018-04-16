#pragma once
#include<iostream>
#include<string>
#include<algorithm>
using namespace std;

//Compress:
//search zeros in string and put them in a string
//convert zeros to number
//convert number to bijective base_2
//replace 1,2 with RUNA,RUNB respectively.
//replace substring zeros in string Move to front to substring RUNs.


//Decompress:
//search Runs in test and put them in string remeber start and end of RUNs
//convert runs to bijective base2 
//After finishing search, (int)arr[size-1]*pow(2,counter starts with zero) loop till zero
//now there is a number, make numbertozeros function
//place this string of zeroes in test between start and end.

class RLE
{
public:
	RLE();
	int zerostonumber(string str);
	string ReplaceString(string& str, const string& search, const string& replace);
	string numbertoRUNA_RUNBs(int decimal);
	string Compress(string test);
	void convertRUNstoBijectiveBase2(string &toconvert);
	int convertBijectiveBase2tonumber(string &toconvert, int last = 0);
	string numbertozeros(int number);
	string decompress(string& test);

	~RLE();
};

