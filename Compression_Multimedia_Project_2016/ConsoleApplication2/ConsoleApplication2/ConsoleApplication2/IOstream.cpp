#include "IOstream.h"

void IOstream::write(string name,string message)
{
	ofstream f("out/"+name+"_out"+".txt");
	f << message;
	f.close();
}


string IOstream::read(string name)
{
	string y;
	char x;
	ifstream f("in/"+name+".txt");

	while (!(f).eof())
	{
		if ((f).read(&x, 1))
		{
			y += x;
		}
	}
	return y;
}


void IOstream::writetobinfile(string name, int size, __int8 **arr_of_codes, string message)
{
	name += ".bin";
	ofstream out("binaries/"+name, ios::binary);
	out.write(reinterpret_cast<const char *>(&size), sizeof(size));

	//Writing the table of codes into string

	int block = size / 3;
	int max_i = ceil(((float)size / block));
	
	string finalresult="";

	for (int i = 0; i < max_i; i++)
	{
		for (int j = 0; j < 258; j++)
		{
			bitset<8> b;
			b = arr_of_codes[i][j];
			string s = b.to_string();
			s.erase(0, 3);
			finalresult += s;
		}
	}


	//Writing the table of codes into binary files

	char b1 = 0;
	int s1 = 0;
	char x1;
	for (int i = 0; i < finalresult.size(); i++)
	{
		if (s1 > 7)
		{
			out.write(reinterpret_cast<const char *>(&b1), sizeof(b1));
			b1 = 0;
			s1 = 0;
		}
		b1 = (b1 << 1) | (finalresult[i] - '0');

		s1++;
	}
	
	////Writing the actual message

	char b = b1;
	int s = s1;
	char x;
	for (int i = 0; i < message.size(); i++)
	{	
		if (s > 7)
		{
			out.write(reinterpret_cast<const char *>(&b), sizeof(b));
			b = 0;
			s = 0;
		}
		b=(b << 1)|(message[i]-'0');

		s++;
	}

	b <<= (8 - s);
	out.write(reinterpret_cast<const char *>(&b), sizeof(b));
	
	char final_byte_size = s;
	out.write(reinterpret_cast<const char *>(&final_byte_size), sizeof(final_byte_size));


	out.close();
}



void IOstream::readfrombinfile(string name, int *size, __int8 **arr_of_codes, string *message)
{
	name += ".bin";
	ifstream in("binaries/"+name, ios::binary);
	in.read(reinterpret_cast<char *>(&(*size)), sizeof(size));

	int block = *size / 3;
	int max_i = ceil(((float)*size / block));

	string finalresult = "";

	int byte_to_read = ceil(((float)max_i*258*5)/8); //258
	
	for (int i = 0; i < byte_to_read; i++)
	{
		bitset<8> byte(0);
		in.read(reinterpret_cast<char *>(&byte), 1);
		finalresult += byte.to_string();
	}

	
	(*arr_of_codes) = new __int8[max_i*258];//258
	
	int j = 0;
	for (int i = 0; i < max_i*258; i++)//258
	{
		string code = finalresult.substr(0,5);
		char x = 0;
		for (int k = 0; k < 5; k++)
		{
			x = (x << 1) | code[k]-'0';
		}
		(*arr_of_codes)[j++] = x;
		finalresult.erase(0, 5);
	}

	bitset<8> byte(0);

	while (!in.eof())
	{
		if (in.read(reinterpret_cast<char *>(&byte), 1))
		{
			finalresult += byte.to_string();
		}
	}
	unsigned long bits_in_last_byte = byte.to_ulong();
	finalresult.resize(finalresult.size()-8-(8-bits_in_last_byte));
	*message = finalresult;
	
	return;
}