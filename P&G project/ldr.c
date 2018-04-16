/*
 * ldr.c
 *
 * Created: 11/2/2016 11:05:27 PM
 *  Author: Malak Fahim
 */ 

#include "ldr.h"
#include "GPIO.h"
#include "ldr_cfg.h"
#include <stdlib.h>
#include <avr/io.h>
#define BAUDRATE 9600
#define BAUD_PRESCALLER (((F_CPU / (BAUDRATE * 16UL))) - 1)
#define F_CPU 16000000UL

extern const LDR_ConfigType LDR_ConfigParam[LDR_MAX_NUM];

void LDR_Init(void)
{
	unsigned long int LoopIndex;
	GPIO_InitType ldrParam;
	//GPIO_WriteType LedWrtParam;
	ldrParam.Direction = 0x00;   //ldr metwasal 3la pin input
	ldrParam.IsPullupUsed = 0x00;
	
	for(LoopIndex = 0; LoopIndex < LDR_MAX_NUM; LoopIndex ++)
	{
		/*Init LEDs Direcion*/
		ldrParam.Mask = 1 << LDR_ConfigParam[LoopIndex].ldrPin;
		ldrParam.PortName = LDR_ConfigParam[LoopIndex].ldrPort;
		GPIO_Init(&ldrParam);
		
	}
/*ADC init (Generic)*/
	ADCSRA |= ((1<<ADPS2)|(1<<ADPS1)|(1<<ADPS0));   // 16Mhz/128 = 125Khz the ADC reference clock
	ADMUX |= (1<<REFS0); // Voltage reference from Avcc (5v)
	ADCSRA |= (1<<ADEN); // Turn on ADC
	ADCSRA |= (1<<ADSC); // Do an initial conversion because this one is the slowest and to ensure that everything is up and running
}


uint16_t LDR_READData(unsigned long int ldrID)
{
	if(ldrID < LDR_MAX_NUM)
	{	
		ADMUX &= 0xF0; // Clear the older channel that was read
		ADMUX |= LDR_ConfigParam[ldrID].ldrPin; // Defines the new ADC channel to be read
		ADCSRA |= (1<<ADSC); // Starts a new conversion
		while(ADCSRA & (1<<ADSC)); // Wait until the conversion is done
		return ADCW; // Returns the ADC value of the chosen channel		
	}
	else 
	{
		return -1;
	}
}

unsigned char LDR_CmpData(unsigned long int ldrID,uint16_t th)
{
	uint16_t read_value=LDR_READData(ldrID);
	return read_value>th;
}
