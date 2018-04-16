/*
 * LED.c
 *
 * Created: 9/6/2016 1:08:22 PM
 *  Author: Malak Fahim
 */ 
#include"LED.h"
#include"GPIO.h"
#include "LED_cfg.h"
extern const LED_configtype led_configparam_arr [max_num_led];
void LED_init()
{
	GPIO_InitType ledparam;      //safir mn el layer elly t7t yetmely 3ashan yro7 ll layer elly t7t
	unsigned long int loopindex;
	GPIO_WriteType ledwrparam;
	
	ledparam.Direction=0xff;   //kol el led output leza a7san a5aliha parra el loop
	ledparam.IsPullupUsed=0x00; //talam el led output so akid mafish pyll up l2eb pull up m3 el input
	
	for(loopindex=0; loopindex<max_num_led; loopindex++)
	{
		ledparam.PortName=led_configparam_arr[loopindex].ledport;
		ledparam.Mask=1<<led_configparam_arr[loopindex].ledpin;
		GPIO_Init(&ledparam);
		
		/*ana 3ayez kol led fl initialization tkon matfia */
		/*so ana 3ayez a write*/
		
		ledwrparam.PortName=led_configparam_arr[loopindex].ledport;
		ledwrparam.Mask=1<<led_configparam_arr[loopindex].ledpin;
		ledwrparam.Data=led_configparam_arr[loopindex].ledconnection;
		
		GPIO_Write(&ledwrparam);
		
	}
	
	
}


unsigned char set_led(unsigned long int id,led_datatype ledData)
{
	GPIO_WriteType ledwrparam;
	unsigned char funcstate;
	if(id<max_num_led)
	{
		funcstate=1;
		ledwrparam.PortName=led_configparam_arr[id].ledport;
		ledwrparam.Mask=1<<led_configparam_arr[id].ledpin;
		ledwrparam.Data=led_configparam_arr[id].ledconnection ^ ledData;
		GPIO_Write(&ledwrparam);
	}
	else
	{
		funcstate=-1;
	}
	
	return funcstate;
}
