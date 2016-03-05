#include <stdio.h>
#include <stdlib.h>

int charToInt (char c)
{
	int out;
	switch (c)
	{	
		case '0' : out = 0;
			break;
		case '1' : out = 1;
			break;	
		case '2' : out = 2;
			break;
		case '3' : out = 3;
			break;
		case '4' : out = 4;
			break;
		case '5' : out = 5;
			break;
		case '6' : out = 6;
			break;
		case '7' : out = 7;
			break;
		case '8' : out = 8;
			break;
		case '9' : out = 9;
			break;
		case 'a' : out = 10;
			break;
		case 'b' : out = 11;
			break;
		case 'c' : out = 12;
			break;
		case 'd' : out = 13;
			break;
		case 'e' : out = 14;
			break;
		case 'f' : out = 15;
			break;
	}
	return out;
}

int hexToInt (char h1, char h0)
{
	int i1;
	int i0;

	int output;

	i1 = charToInt(h1);
	i0 = charToInt(h0);

	output = i1*16 + i0;
	return output;
	
}

int main (void) 
{

	char *input;
	int output = 0;

	//Get input in HEX
	input = (char *)malloc(sizeof(char)*10);
	scanf ("%s",input);

	//Alpha
	output+=255;
	output = output<<8;

	//Red
	output+=hexToInt( *(input+0), *(input+1) );
	output = output<<8;

	//Green
	output+=hexToInt( *(input+2), *(input+3) );
	output = output<<8;
	
	//Blue
	output+=hexToInt( *(input+4), *(input+5) );

	//Print output
	printf("%d\n",output);
}

