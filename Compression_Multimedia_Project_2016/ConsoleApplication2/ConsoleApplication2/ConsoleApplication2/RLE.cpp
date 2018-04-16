#include "RLE.h"


RLE::RLE()
{
}
int RLE::zerostonumber(string str)
{
	int counter = 0;
	for (int i = 0; i < str.size(); i++)
	{
		if (str[i] == '0') counter++;
	}
	return counter;
}

string RLE::ReplaceString(string& str, const string& search,
	const string& replace) {
	int pos = 0;
	while ((pos = str.find(search, pos)) != string::npos) {
		str.replace(pos, search.length(), replace);
		pos += replace.length();
	}
	return str;
}

string RLE::numbertoRUNA_RUNBs(int decimal)
{
	if (decimal == 0) return "0";
	string alphabet[2] = { "1", "2" };
	int base = 2;
	string str = "";
	while (decimal > 0)
	{
		decimal--;
		int reminder = decimal % 2;
		decimal = decimal - reminder;
		reminder++;
		str += alphabet[reminder - 1];
		str += ",";
		decimal = decimal / 2;
	}
	reverse(str.begin(), str.end());
	str.erase(0, 1);
	ReplaceString(str, "1", "RUNA");
	ReplaceString(str, "2", "RUNB");
	return str;
}

string RLE::Compress(string test)
{
	int header = 0, tail = 0;
	string temp = "";
	while (header <= test.size())
	{
		if (header == 0)
		{
			bool f = true;
			while (f)
			{

				if (test[header] == '0' && (test[header + 1] == ',' || (header == test.size() - 1)))
				{

					if ((header == test.size() - 1))
					{
						temp += test[header];
						string toreplace = numbertoRUNA_RUNBs(zerostonumber(temp));
						test.replace(0, temp.size(), toreplace);
						header -= temp.size() - toreplace.size();
						f = false;
					}
					else
					{
						temp += test[header];
						temp += test[header + 1];
						header += 2;
					}
				}
				else
				{
					f = false;
					if (temp != "")
					{
						temp.pop_back();
						string toreplace = numbertoRUNA_RUNBs(zerostonumber(temp));
						test.replace(0, temp.size(), toreplace);
						header -= temp.size() - toreplace.size();
					}
				}
			}
		}
		else
		{
			int saveheader = header;
			bool f = true;
			while (f)
			{

				if (test[header] == '0' && test[header - 1] == ',' && (test[header + 1] == ',' || (header == test.size() - 1)))
				{
					if ((header == test.size() - 1))
					{
						temp += test[header];
						string toreplace = numbertoRUNA_RUNBs(zerostonumber(temp));
						test.replace(saveheader, temp.size(), toreplace);
						header -= temp.size() - toreplace.size();
						f = false;
					}
					else
					{
						temp += test[header];
						temp += test[header + 1];
						header += 2;
					}
				}
				else
				{
					f = false;
					if (temp != "")
					{
						temp.pop_back();
						string toreplace = numbertoRUNA_RUNBs(zerostonumber(temp));
						test.replace(saveheader, temp.size(), toreplace);
						header -= temp.size() - toreplace.size();
					}
				}
			}
		}
		header++; temp = "";
	}
	return test;//some thing have to be happen here
}

void RLE::convertRUNstoBijectiveBase2(string &toconvert)
{
	ReplaceString(toconvert, "RUNA", "1");
	ReplaceString(toconvert, "RUNB", "2");
	ReplaceString(toconvert, ",", "");
}



string RLE::numbertozeros(int number)
{
	string zeros = "";
	for (int i = 0; i < number; i++)
		zeros += "0,";
	return zeros;
}


string RLE::decompress(string& test)
{
	test.pop_back();
	int header = 0; string temp = "", temp2;
	while (header < test.size())
	{
		if (header == 0)
		{
			bool f = true;
			while (f)
			{
				if (header >= test.size())
				{
					f = false;
					if (temp != "")
					{
						temp2 = temp;
						convertRUNstoBijectiveBase2(temp);
						string temp3 = numbertozeros(convertBijectiveBase2tonumber(temp, 1));
						test.replace(0, temp2.size(), temp3);
						test.pop_back();
						header -= temp.size() - temp3.size();
					}
				}
				else if (test[header] == 'R')
				{
					for (int i = 0; i < 5; i++)
						temp += test[header++];
				}
				else
				{
					if (temp != "")
					{
						temp2 = temp;
						convertRUNstoBijectiveBase2(temp);
						string temp3 = numbertozeros(convertBijectiveBase2tonumber(temp,0));
						test.replace(0, temp2.size(), temp3);
						header -= temp2.size() - temp3.size();
					}
					f = false;
				}
			}
		}
		else
		{
			int saveheader = header;
			bool f = true;
			while (f)
			{
				if (header >= test.size())
				{
					if (temp != "")
					{
						temp2 = temp;
						convertRUNstoBijectiveBase2(temp);
						string temp3 = numbertozeros(convertBijectiveBase2tonumber(temp, 1));
						test.replace(saveheader, temp2.size(), temp3);
						test.pop_back();
						header -= temp2.size() - temp3.size();
					}
					f = false;
				}
				else if (test[header] == 'R')
				{
					for (int i = 0; i < 5; i++)
						temp += test[header++];
				}
				else
				{
					if (temp != "")
					{
						temp2 = temp;
						convertRUNstoBijectiveBase2(temp);
						string temp3 = numbertozeros(convertBijectiveBase2tonumber(temp,0));
						test.replace(saveheader, temp2.size(), temp3);
						header -= temp2.size() - temp3.size();
					}
					f = false;
				}
			}
		}
		header++; temp = "";
	}
	return test;
}



int RLE::convertBijectiveBase2tonumber(string &toconvert, int last)
{
	int number = 0; int pos = 0;
	int stringsize = toconvert.size() - last;
	int* intNumber = new int[stringsize];
	for (int i = 0; i < stringsize; i++)
		intNumber[i] = toconvert[i] - '0';
	for (int i = stringsize - 1; i > -1; i--)
		number = number + intNumber[i] * pow(2, pos++);
	return number;

}


/*TODO*/
/* there is a wrong in this function*/

RLE::~RLE()
{
}
