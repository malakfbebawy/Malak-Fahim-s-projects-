/*
 * ldr.h
 *
 * Created: 11/2/2016 11:05:08 PM
 *  Author: Malak Fahim
 */ 


#ifndef LDR_H_
#define LDR_H_

#include "GPIO.h"
#include <stdlib.h>
#include <avr/io.h>/*mo2akatan*/

typedef struct
{
	GPIO_PortType ldrPort;
	unsigned char ldrPin;
	
}LDR_ConfigType;



void LDR_Init(void);
uint16_t LDR_READData(unsigned long int ldrID);
unsigned char LDR_CmpData(unsigned long int ldrID,uint16_t th);






#endif /* LDR_H_ */