/*
 * GPIO.h
 *
 * Created: 9/6/2016 1:03:23 PM
 *  Author: Malak Fahim
 */ 


#ifndef GPIO_H_
#define GPIO_H_


typedef enum {PA=0,PB,PC,PD}GPIO_PortType;
typedef struct
{
	GPIO_PortType PortName;
	unsigned char Mask;
	unsigned char Direction;
	unsigned char IsPullupUsed;
}GPIO_InitType;

typedef struct
{
	GPIO_PortType PortName;
	unsigned char Mask;
	unsigned char Data;
}GPIO_WriteType;

typedef struct
{
	GPIO_PortType PortName;
	unsigned char Mask;
}GPIO_ReadType;

void GPIO_Init(const GPIO_InitType* InitParamPtr);//bab3at pointer 3la el struct 3ashan as3'ar size mn eny ab3at object mn el struct
void GPIO_Write(const GPIO_WriteType* WriteParamPtr);
unsigned char GPIO_Read(const GPIO_ReadType* ReadParamPtr);





#endif /* GPIO_H_ */