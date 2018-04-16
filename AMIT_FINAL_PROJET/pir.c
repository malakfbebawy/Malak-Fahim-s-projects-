/*
 * pir.c
 *
 * Created: 11/5/2016 11:17:19 PM
 *  Author: Malak Fahim
 */ 

#include"pir.h"
#include"GPIO.h"
#include "pir_cfg.h"
extern const PIR_ConfigType PIR_ConfigParam[PIR_MAX_NUM];
void PIR_Init(void)
{
	GPIO_InitType pirparam;      //safir mn el layer elly t7t yetmely 3ashan yro7 ll layer elly t7t
	unsigned long int loopindex;
	
	
	pirparam.Direction=0x00;   //pir 3la fat7a input 3ashan te2ra mno
	pirparam.IsPullupUsed=0x00; //talam el led output so akid mafish pyll up l2eb pull up m3 el input
	
	for(loopindex=0; loopindex<PIR_MAX_NUM; loopindex++)
	{
		pirparam.PortName=PIR_ConfigParam[loopindex].pirPort;
		pirparam.Mask=1<<PIR_ConfigParam[loopindex].pirPin;
		GPIO_Init(&pirparam);
		
		
	}
	
	
}

uint16_t PIR_READData(unsigned long int PIRID)
{
	GPIO_ReadType pir_readparam;
	uint16_t readen_value;
	if(PIRID<PIR_MAX_NUM)
	{
		
		pir_readparam.PortName=PIR_ConfigParam[PIRID].pirPort;
		pir_readparam.Mask=1<<PIR_ConfigParam[PIRID].pirPin;
		
		
		readen_value=GPIO_Read(&pir_readparam);
		
		if(readen_value==0)
		return 0;
		else
		return 1;
	}
	
}

