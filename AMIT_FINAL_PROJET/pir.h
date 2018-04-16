/*
 * pir.h
 *
 * Created: 11/5/2016 11:16:36 PM
 *  Author: Malak Fahim
 */ 


#ifndef PIR_H_
#define PIR_H_


#include "GPIO.h"
#include <stdlib.h>
#include <avr/io.h>/*mo2akatan*/

typedef struct
{
	GPIO_PortType pirPort;
	unsigned char pirPin;
	
}PIR_ConfigType;



void PIR_Init(void);
uint16_t PIR_READData(unsigned long int ldrID);






#endif /* PIR_H_ */