/*
 * GPIO.c
 *
 * Created: 9/6/2016 1:03:41 PM
 *  Author: Malak Fahim
 */ 

#include "GPIO.h"
#include <avr/io.h>

/*
#define SET_REG_DATA(REG,DATA,MASK)  REG&= ~MASK;\ //read and write in register at the same time
                                     REG|= (DATA & MASK)       //bad practice because of delay between two instruction
*/




#define SET_REG_DATA(REG,DATA,MASK) REG = (REG & ~MASK) | (DATA & MASK) //read then write  


void GPIO_Init(const GPIO_InitType* InitParamPtr)
{
	switch(InitParamPtr->PortName)
	{
		case PA:
		{
			SET_REG_DATA(DDRA,InitParamPtr->Direction,InitParamPtr->Mask);
			SET_REG_DATA(PORTA,(InitParamPtr->IsPullupUsed & ~InitParamPtr->Direction),InitParamPtr->Mask);
		}
		break;
		
		case PB:
		{
			SET_REG_DATA(DDRB,InitParamPtr->Direction,InitParamPtr->Mask);
			SET_REG_DATA(PORTB,(InitParamPtr->IsPullupUsed& ~InitParamPtr->Direction),InitParamPtr->Mask);			
		}
		break;
		case PC:
		{
			SET_REG_DATA(DDRC,InitParamPtr->Direction,InitParamPtr->Mask);
			SET_REG_DATA(PORTC,(InitParamPtr->IsPullupUsed& ~InitParamPtr->Direction),InitParamPtr->Mask);			
		}
		break;
		case PD:
		{
			SET_REG_DATA(DDRD,InitParamPtr->Direction,InitParamPtr->Mask);
			SET_REG_DATA(PORTD,(InitParamPtr->IsPullupUsed & ~InitParamPtr->Direction),InitParamPtr->Mask);			
		}
		break;
		default:
		{
			/*Do no thing , invalid port name*/
		}
		break;
	}
}
void GPIO_Write(const GPIO_WriteType* WriteParamPtr)
{
	switch(WriteParamPtr->PortName)
	{
		case PA:
		{
			SET_REG_DATA(PORTA,WriteParamPtr->Data,WriteParamPtr->Mask);
		}
		break;
		
		case PB:
		{
			SET_REG_DATA(PORTB,WriteParamPtr->Data,WriteParamPtr->Mask);
		}
		break;
		case PC:
		{
			SET_REG_DATA(PORTC,WriteParamPtr->Data,WriteParamPtr->Mask);
		}
		break;
		case PD:
		{
			SET_REG_DATA(PORTD,WriteParamPtr->Data,WriteParamPtr->Mask);
		}
		break;
		default:
		{
			/*Do no thing , invalid port name*/
		}
		break;
	}	
}

unsigned char GPIO_Read(const GPIO_ReadType* ReadParamPtr)
{
	unsigned char Data;
	switch(ReadParamPtr->PortName)
	{
		case PA:
		{
			Data = PINA & ReadParamPtr->Mask;
		}
		break;
		case PB:
		{
			Data = PINB & ReadParamPtr->Mask;
		}
		break;
		case PC:
		{
			Data = PINC & ReadParamPtr->Mask;
		}
		break;
		case PD:
		{
			Data = PIND & ReadParamPtr->Mask;
		}
		break;
		default:
		{
			Data = 0;
		}
		break;
	}
	return Data;
}


