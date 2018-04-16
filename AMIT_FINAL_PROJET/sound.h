/*
 * sound.h
 *
 * Created: 4/27/2017 10:49:14 PM
 *  Author: Malak Fahim
 */ 


#ifndef SOUND_H_
#define SOUND_H_


#include "GPIO.h"
#include <stdlib.h>
#include <avr/io.h>/*mo2akatan*/

typedef struct
{
	GPIO_PortType soundPort;
	unsigned char soundPin;
	
}sound_ConfigType;



void sound_Init(void);
uint16_t sound_READData(unsigned long int soundID);
unsigned char sound_CmpData(unsigned long int soundID,uint16_t th);





#endif /* SOUND_H_ */