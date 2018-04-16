#include "MTF.h"

MTF::MTF()
{
	for (int i = 0; i<256; i++)
	{
		char temp = i;
		all_chars += temp;
	}
	this->all_chars += -1;
}

string MTF::Compress(string test)
{
	int k = 0;            //counter ye2llel el location
	int location;       //location of character when i find it in the all_char array  
	char goal;          //The character which i look for  

	stringstream out;
	for (int i = 0; i<test.size(); i++)
	{
		for (int m = 0; m<all_chars.size(); m++)
		{
			if (test[i] == all_chars[m])
			{
				location = m;
				out << location << ",";
				goal = all_chars[m];
				break;
			}
		}
		if (location != 0)
		{
			for (int j = location - 1; j >= 0; j--)
			{
				all_chars[location - k] = all_chars[j];
				k++;
			}
			all_chars[0] = goal;
			k = 0;

		}
	}
	return out.str();
}
string MTF::Decompress(string output)
{
	int k = 0;            //counter ye2llel el location
	int location;       //el index bta3 el character f all_chars w da elly ba2rah mn el file   
	char goal;          //The character which i look for  
	int x;

	for (int i = 0; i < output.size(); i++)
	{
		if (output[i] == ',')
			output[i] = ' ';
	}


	stringstream out(output);
	string s;
	while (!out.eof())
	{
		if (out >> x)
		{
			location = x;

			goal = all_chars[location];

			s += goal;
			if (location != 0)
			{
				for (int j = location - 1; j >= 0; j--)
				{
					all_chars[location - k] = all_chars[j];
					k++;
				}
				all_chars[0] = goal;
				k = 0;
			}
		}
	}
		return s;
}
