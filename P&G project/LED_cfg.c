/*
 * LED_cfg.c
 *
 * Created: 9/6/2016 1:08:04 PM
 *  Author: Malak Fahim
 */ 
#include "LED.h"
#include "LED_cfg.h"

const LED_configtype led_configparam_arr [max_num_led]=
{
	{PC,7,ACTIVE_HIGH},{PD,6,ACTIVE_HIGH},{PC,6,ACTIVE_HIGH},{PD,4,ACTIVE_HIGH}
		
};