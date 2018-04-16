#include "Burrows_Wheeler.h"


Burrows_Wheeler::Burrows_Wheeler()
{
	file = "";
	table = "";
}



int mystrsort(const void *a, const void *b)
{
	return strcmp(*(const char **)a, *(const char **)b);
}



int mycharsort(const void *a, const void *b)
{
	return 0;
}


string Burrows_Wheeler::Compress(string x)
{
	//initialization
	this->file = x;
	file += -1;
	this->size = file.size();
	file += file;


	//Rotation
	char ** arr = new char *[size];
	
	for (int i = 0; i < size; i++)
	{
		 arr[i]= &(file[i]);
	}

	//sorting
	qsort(arr, size, sizeof(char*), mystrsort);
	
	//construction the table to send to the next level
	for (int i = 0; i < size; i++)
		table += arr[i][size-1];


	delete[]arr;
	return table;
}

string Burrows_Wheeler::decompress(string table)
{
	//initialization
	this->table = table;
	this->size = table.size();

	//prepare the lexographically sorted string and maps to make search easier
	string sorted_table = table;
	sort(sorted_table.begin(), sorted_table.end());
	sorted_table.erase(sorted_table.begin());
	sorted_table += -1;

	intializemaps(table, sorted_table);


	//start the reversr transformation
	char charcter_in_turn = -1;
	int x = table.find(charcter_in_turn);
	do{
		file += sorted_table[x];

		int presnce = presence_in_sorted_table[make_pair(x, sorted_table[x])];
		
		//x = find_in_map(presnce, sorted_table[x]);
		x = index_in_table[make_pair(presnce,sorted_table[x])];
		cout << size - file.size() << "\n";
	} while (sorted_table[x] != -1);
	return file;
	
}

void Burrows_Wheeler::intializemaps(string table,string sorted_table)
{
	map <char, int> pres;
	for (int i = 0; i < table.size(); i++)
	{
		char ch1 = table[i];
		int Q=pres[ch1]++;
		presence_in_table[make_pair(i, ch1)] = Q;

		index_in_table[make_pair(Q, ch1)]=i;
	}

	map <char, int> pres2;
	for (int i = 0; i < sorted_table.size(); i++)
	{
		char ch2 = sorted_table[i];
		int Q=pres2[ch2]++;
		presence_in_sorted_table[make_pair(i, ch2)] = Q;
	}
/*
	pair<char, int> x = make_pair(12, '\n');
	if (presence_in_table[x]>0)
	{
		cout << "yes";
		cout << presence_in_table[x];
	}
	system("pause");*/
}

