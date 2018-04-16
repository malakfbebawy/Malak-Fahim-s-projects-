#include<iostream>
#include<fstream>
#include<string>
#include"Burrows_Wheeler.h"
#include"IOstream.h"
#include"huffman.h"
#include"MTF.h"
#include"RLE.h"
#include<map>
#include<sstream>
using namespace std;

bool cmp(char **chars_codes, char* codes, int max_i)
{
	int k=0;
	for (int i = 0; i < max_i; i++)
	{
		for (int j = 0; j < 258; j++)
		{
			if (chars_codes[i][j] != codes[k++])
				return false;
		}
	}
	return true;
}

int min(int x, int y)
{
	if (x < y)
		return x;
	return y;
}

bool str_cmp(string x,string y)
{
	if (x.size() != y.size())
		return false;
	for (int i = 0; i < x.size(); i++)
	{
		if (x[i] != y[i])
			return false;
	}
	return true;
}

int main()
{
	string file_name;
	cout << "Enter the file name: ../out/";
	cin >> file_name;
	//reading file
	IOstream ios;
	string input=ios.read(file_name);
	
	
	//burrows_wheeler_transform
	Burrows_Wheeler BW;
	string tab = BW.Compress(input);

	//MoveToForward transform
	MTF M;
	string out= M.Compress(tab);
	
	//Run Length Encoding 
	RLE R;
	string t=R.Compress(out);
	
	
	//Multiple Huffman Table
	string result="";
	int commas=0;
	map<int, int> pres_index;
	
	int index = -1;
	for (int i = 0; i < t.size(); i++)
	{
		if (t[i] == ',')
		{
			if (i - index == 1)
				i++;
			index = i;
			commas++;
			pres_index[commas] = i;
		}
	}
	
	int number_of_charcters = commas;

	int size = commas/3;
	int max_i = ceil(((float)commas / size));
	commas /=3;
	int first = 0;
	
	string *q=new string[max_i];
	__int8 ** chars_codes = new __int8*[max_i];

	//Huffman encoding
	for (int i = 0; i < max_i; i++)
	{
		q[i] = t.substr(first, pres_index[commas] - first + 1);
		first = pres_index[commas] + 1;
		commas += size;

		huffman h(q[i]);
		h.read_message();
		h.calc_probs();
		h.build_tree();
		h.set_code();
		h.change_codes();
		__int8 * arr = h.canonical_transform();
		chars_codes[i] = arr;
		result += h.getsize();
	}
	cout << result.size()<<endl;


	// writing to binary file
	ios.writetobinfile(file_name, number_of_charcters, chars_codes, result);
	
	//reading from binary file
	int sizer = 0;
	__int8 *codes = NULL;
	string message = "";
	ios.readfrombinfile(file_name, &sizer, &codes, &message);
	
	
	/*
	if (str_cmp(message, result) && sizer == number_of_charcters)
		cout << "semi pass";
	
	if (cmp(chars_codes,codes,max_i))
		cout <<endl<<"pass";
		*/
	//here starts the reverse operation
	//Multiple Huffman codes Reverse 

	
	huffman h_reverse(" ");

	string message_2 = h_reverse.Decode(codes, sizer, message);
	//RLE REVERSE
	RLE R_Reverse;
	string message_3 =R_Reverse.decompress(message_2);
	
	MTF MTF_REVERSE;
	string message_4 = MTF_REVERSE.Decompress(message_3);
	
	Burrows_Wheeler Bw_Reverse;
	string message_5 = Bw_Reverse.decompress(message_4);

	if (str_cmp(message_5, input))cout << "yes";

	ios.write(file_name, message_5);
}