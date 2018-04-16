#include "huffman.h"
#include<algorithm>

#include<bitset>


bool leng_comp(Char A, Char B)
{
	return A.value < B.value;
}

bool alph_comp(Char  A, Char  B)
{
	return A.name < B.name;
}

bool leng_desc_comp(Char A, Char B)
{
	return A.value > B.value;
}

huffman::huffman(string message)
{
	this->in_message = message;
	this->message_buffer = message;  //used in reading 
	tree = new node*[300];
	m_size = 0;         // size of the incomming message
}


void huffman::read_message()
{
	unsigned int pos;
	while (1)
	{
		pos = message_buffer.find(',');

		if (pos == string::npos)
			break;

		if (pos == 0)
			pos = 1;

		string s = message_buffer.substr(0, pos);
		message_buffer.erase(0, pos + 1);

		freq[s]++;
		m_size++;
	}
}


map<string, string> huffman::get_codes()
{
	return codes;
}

void huffman::calc_probs()
{
	map<string, int>::iterator it;
	int k = 0;
	for (k, it = freq.begin(); it != freq.end(), (unsigned int)k<freq.size(); it++, k++)
	{
		node * new_node = new node();
		new_node->character = it->first;
		new_node->probability = (double)(it->second) / m_size;
		new_node->right = NULL;
		new_node->left = NULL;
		tree[k] = new_node;
	}
}

void huffman::set_code()
{
	DFS(tree[0], -1, "");

	if (tree[0]->right == NULL)
	{
		codes[tree[0]->character] = '0';
	}
}

void huffman::DFS(node * current_node, int RL, string prev_code)   //cuurent node is sent from the previous step
{												             // RL tells the current node whether it's the right or the left of the previous one 
	if (RL == 0)
		current_node->code = prev_code + "0";               //if left add zero to the code string
	else if (RL == 1)
		current_node->code = prev_code + "1";               //if right add one to the code string

	if (current_node->right != NULL)
	{
		DFS(current_node->left, 0, current_node->code);
		DFS(current_node->right, 1, current_node->code);
	}
	else
		codes[current_node->character] = current_node->code;
}

bool comp(node* A, node* B)
{
	return A->probability > B->probability;
}

void huffman::build_tree()
{
	unsigned int current_size = freq.size();   //initially the number of all characters

	for (int i = current_size - 2; i >= 0; i--)
	{
		sort(tree, tree + current_size, comp);
		node * new_node = new node();
		new_node->probability = tree[i]->probability + tree[i + 1]->probability;
		new_node->left = tree[i];
		new_node->right = tree[i + 1];
		tree[i] = new_node;
		current_size--;
	}
}


void huffman::change_codes()    // change from old to new with the same lengths
{
	Char * codes = new Char[260];

	map<string, string>::iterator it;
	int i = 0;
	for (it = this->codes.begin(), i = 0; it != this->codes.end(); it++, i++)
	{
		codes[i].name = it->first;
		codes[i].value = it->second.size();
	}

	sort(codes, codes + i, leng_comp);

	int first = 0, last = 0;

	for (; last != i;)
	{
		while (last != i && codes[last].value == codes[first].value)
		{
			last++;
		}
		sort(codes + first, codes + last, alph_comp);
		first = last;
	}


	map<string, string> retrieved_codes;
	int prev_length = codes[0].value;
	int last_code = 0;

	for (int i = 0; i < prev_length; i++)
	{
		retrieved_codes[codes[0].name] += "0";
	}

	for (int i = 1; i < last; i++)
	{
		last_code++;
		if (codes[i].value != prev_length)
		{
			last_code <<= codes[i].value - prev_length;
		}
		bitset<30> char_code = last_code;
		string s = char_code.to_string();
		s.erase(0, 30 - codes[i].value);
		retrieved_codes[codes[i].name] = s;

		prev_length = codes[i].value;
	}

	//255 chars + RUNA +RUNB
	retrieved_codes["RUNA"] += "";
	retrieved_codes["RUNB"] += "";

	for (int i = 1; i < 257; i++)
	{
		retrieved_codes[to_string(i)] += "";
	}
	this->codes = retrieved_codes;
}

__int8* huffman::canonical_transform()     // convert code words to lengths
{

	map<string, string>::iterator it;

	__int8* codes_lenghts = new __int8[260];
	unsigned int i;

	for (i = 0, it = codes.begin(); i < codes.size(); i++, it++)
		codes_lenghts[i] = it->second.size();

	return codes_lenghts;
}

string huffman::getsize()
{
	unsigned int pos;
	string size = "";
	message_buffer = this->in_message;
	while (1)
	{
		pos = message_buffer.find(',');

		if (pos == string::npos)
			break;

		if (pos == 0)
			pos = 1;

		string s = message_buffer.substr(0, pos);
		message_buffer.erase(0, pos + 1);
		size += codes[s];
	}
	return size;
}



string huffman::Decode(__int8 * tables, int size, string binary_message)
{
	map<string, string> table[4];
	string * messages = new string[4];

	messages[0] = messages[1] = messages[2] = messages[3] = "";
	
	string char_code = "";
	int count;
	map<string, string>::iterator it;


	if (size % 3 == 0)
		count = 3;
	else
		count = 4;

	table[0] = inverse_canonical(tables);
	table[1] = inverse_canonical(tables + 258);
	table[2] = inverse_canonical(tables + 258 * 2);

	if (count == 4)
		table[3] = inverse_canonical(tables + 258 * 3);

	int i = 0, char_count = 0;

	for (int j = 0; (unsigned int)j<binary_message.size(); j++)
	{
		char_code += binary_message[j];
		it = table[i].find(char_code);
		if (it != table[i].end())
		{
			messages[i] += it->second+",";
			char_code = "";
			char_count++;
		}

		if (char_count == size / 3)
		{
			i++; char_count = 0;
		}
	}
	string actual="";
	for (int i = 0; i < count; i++)
		actual += messages[i];
	return actual;
}


// this function retrieves a huffman table from a given code word lengths
map<string, string> huffman::inverse_canonical(__int8* code_lengths)
{
	Char * codes = new Char[260];

	codes[0].name = "RUNA";
	//codes[0].value = code_lengths[256];     //Run A & Run B are the last two elements
	codes[1].name = "RUNB";
	//codes[1].value = code_lengths[257];


	for (int i = 2; i < 258; i++)
	{
		codes[i].name = to_string(i - 1);   //codes from 1 -->>256 
	}

	sort(codes, codes + 258, alph_comp);


	int non_zero_count = 0;           //to count non zero lengths in the array
	for (int i = 0; i < 258; i++)
	{
		codes[i].value = code_lengths[i];

		if (code_lengths[i] != 0)
			non_zero_count++;
	}

	sort(codes, codes + 258, leng_desc_comp);     // sort descendingly to get rid of code words of zero length and place the at the bottom of the array
	sort(codes, codes + non_zero_count, leng_comp);   // sort to the first symbol that has zero length



	int first = 0, last = 0;

	for (; last != non_zero_count;)
	{
		while (last != non_zero_count && codes[last].value == codes[first].value)
		{
			last++;
		}
		sort(codes + first, codes + last, alph_comp);
		first = last;
	}


	map<string, string> retrieved_codes;
	int prev_length = codes[0].value;    // code word length of the previous word in the following procedure
	int last_code = 0;                   // the actual code word of the previous of the previous symbol in the following proc.

	for (int i = 0; i < prev_length; i++)
	{
		retrieved_codes[codes[0].name] += "0";
	}

	for (int i = 1; i < non_zero_count; i++)
	{
		last_code++;
		if (codes[i].value != prev_length)
		{
			last_code <<= codes[i].value - prev_length;
		}

		// converting the code to string
		bitset<30> char_code = last_code;
		string s = char_code.to_string();
		s.erase(0, 30 - codes[i].value);
		retrieved_codes[codes[i].name] = s;
		prev_length = codes[i].value;
	}
	map<string, string>result;
	map<string, string>::iterator it;
	for (it = retrieved_codes.begin(); it != retrieved_codes.end(); it++)
		result[it->second] = it->first;
	

	return result;
}


huffman::~huffman()
{

}