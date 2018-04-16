/*
 * sound.c
 *
 * Created: 4/27/2017 10:49:57 PM
 *  Author: Malak Fahim
 */ 

#include "sound.h"
#include "GPIO.h"
#include "sound_cfg.h"
#include <stdlib.h>
#include <avr/io.h>
#define BAUDRATE 9600
#define BAUD_PRESCALLER (((F_CPU / (BAUDRATE * 16UL))) - 1)
#define F_CPU 16000000UL

extern const sound_ConfigType sound_ConfigParam[sound_MAX_NUM];

void sound_Init(void)
{
	unsigned long int LoopIndex;
	GPIO_InitType soundParam;
	//GPIO_WriteType LedWrtParam;
	soundParam.Direction = 0x00;   //sound metwasal 3la pin input
	soundParam.IsPullupUsed = 0x00;
	
	for(LoopIndex = 0; LoopIndex < sound_MAX_NUM; LoopIndex ++)
	{
		/*Init LEDs Direcion*/
		soundParam.Mask = 1 << sound_ConfigParam[LoopIndex].soundPin;
		soundParam.PortName = sound_ConfigParam[LoopIndex].soundPort;
		GPIO_Init(&soundParam);
		
	}
	/*ADC init (Generic)*/
	ADCSRA |= ((1<<ADPS2)|(1<<ADPS1)|(1<<ADPS0));   // 16Mhz/128 = 125Khz the ADC reference clock
	ADMUX |= (1<<REFS0); // Voltage reference from Avcc (5v)
	ADCSRA |= (1<<ADEN); // Turn on ADC
	ADCSRA |= (1<<ADSC); // Do an initial conversion because this one is the slowest and to ensure that everything is up and running
}


uint16_t sound_READData(unsigned long int soundID)
{
	
	if(soundID < sound_MAX_NUM)
	{
		ADMUX &= 0xF0; // Clear the older channel that was read
		ADMUX |= sound_ConfigParam[soundID].soundPin; // Defines the new ADC channel to be read
		ADCSRA |= (1<<ADSC); // Starts a new conversion
		while(ADCSRA & (1<<ADSC)); // Wait until the conversion is done
		return ADCW; // Returns the ADC value of the chosen channel
	}
	else
	{
		return -1;
	}
}

/*unsigned char LDR_CmpData(unsigned long int ldrID,uint16_t th)
{
	uint16_t read_value=LDR_READData(ldrID);
	return read_value>th;
}*/

