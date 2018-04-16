/*
 * LED.h
 *
 * Created: 9/6/2016 1:08:45 PM
 *  Author: Malak Fahim
 */ 


#ifndef LED_H_
#define LED_H_
#include"GPIO.h"
typedef enum {ACTIVE_HIGH=0x00,ACTIVE_LOW=0xff}led_connectiontype;

typedef struct  
{
	GPIO_PortType ledport;
	unsigned char ledpin;
	led_connectiontype ledconnection;
	
}LED_configtype;

typedef enum{ON=0xff,OFF=0x00}led_datatype;      //el led_datatype di mesh gwa el LED_configtype l2en ON w OFF btt3'ayar mesh sabta wa2t el runtime
	void LED_init();
	unsigned char set_led(unsigned long int id,led_datatype ledData);   //b7es lama t call el func di teb3atlaha el id bta3 el led w 3ayez t5aliha on wla off
                                                                        //unsigned long int da akbar size b7es law 3andy 3add kbir awi mn led


#endif /* LED_H_ */