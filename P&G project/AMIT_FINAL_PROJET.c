/*
 * AMIT_FINAL_PROJET.c
 *
 * Created: 11/5/2016 11:04:23 PM
 *  Author: Malak Fahim
 */ 


#include <avr/io.h>
#include <util/delay.h>
#include "hc05.h"
#include "LED.h"
//#include "ldr.h"
#include "pir.h"
#include "sound.h"



////////lcd////////////
#define MrLCDsCrib PORTB
#define DataDir_MrLCDsCrib DDRB
#define MrLCDsControl PORTD
#define DataDir_MrLCDsControl DDRD
#define LightSwitch 5
#define ReadWrite 7
#define BiPolarMood 2
#define BAUDRATE 9600
#define BAUD_PRESCALLER (((F_CPU / (BAUDRATE * 16UL))) - 1)
#define F_CPU 16000000UL
void Check_IF_MrLCD_isBusy(void);
void Peek_A_Boo(void);
void Send_A_Command(unsigned char command);
void Send_A_Character(unsigned char character);
void Send_A_String(char *StringOfCharacters);

	unsigned long ToUInt(char* str)
	{
		unsigned long mult = 1;
		unsigned long re = 0;
		int len = strlen(str);
		for(int i = len -1 ; i >= 0 ; i--)
		{
			re = re + ((int)str[i] -48)*mult;
			mult = mult*10;
		}
		return re;
	}

//////////////////////

/*#define BAUDRATE 9600
#define BAUD_PRESCALLER (((F_CPU / (BAUDRATE * 16UL))) - 1)
#define F_CPU 16000000UL*/


int main(void)
{
	
	//DDRC=0xff;
	//DDRD|=(1<<4);   //buzzer 3la output bin
	
	LED_init();
	PIR_Init();
	LDR_Init();
	usart_init();  //Blutooth /*USART initialization*/
	
	unsigned int counter=0;
	unsigned int counter_sensor_zero_loud=0;
	unsigned int counter_sensor_zero_low=0;
	
	unsigned int counter_sensor_one_loud=0;
	unsigned int counter_sensor_one_low=0;
	unsigned int index=0;
	
	unsigned int first_iteration =1;
	unsigned char terminating="s";
	
	char *receive_string;   //buffer fady el user yemlah data bb3to ll function elly bt recieve
	char *data_received;
	char *ch; //data from user after token as a string
	int soundLevelinput=1000;
	//////////////////lcd/////////////////
	DataDir_MrLCDsCrib = 0xFF;
	_delay_ms(1000);
	DataDir_MrLCDsControl |= 1<<LightSwitch | 1<<ReadWrite | 1<<BiPolarMood;  //ba7ot 1 3la ligitswitch ya3ny ba2olo el pin(5)in portD 5aliha output ya3ny keda el bins(2,5,7)in port D output
	_delay_ms(500);        //b3mel delay l2en mrlcd slow 3'aleban
	
	
	
	
	     
	     
	
	
	//DDRC=0xff;  //for buzzer
    while(1)
    {
	     
		 
		    /* data_received=hc_05_bluetooth_receive_string(receive_string,'s');
		     //hc_05_bluetooth_receive_byte();
		     ch = strtok(*data_received,'s');
		     //soundLevelinput=ToUInt(data_received
		     soundLevelinput= atoi(*ch);*/
		 
		
		 //first_iteration++;
		
		
		///////////////Blutooth/////////////
		//_delay_ms(500);
		//_delay_ms(500);
		/*Delay of 1s*/
		
		
		
		
		

		///////////////////////////////////
		
		
        
		  
		  /*if(LDR_CmpData(0,10)!=0)
		  {
		     set_led(1,ON);
		     //_delay_ms(500);
		     
		  }
		  else
		  {
			  set_led(1,OFF);
			  //_delay_ms(500);
		  }*/
		  
		  //////////////////set sound level parameter by blutooth////////////////
		  
		  
		  //////////////////////////////////////////////////////////////////////
		  counter_sensor_zero_loud=0;
		  counter_sensor_zero_low=0;
		  counter_sensor_one_loud=0;
		  counter_sensor_one_low=0;
		  
		    for(index=0; index<100; index++)
			{ 
				if( sound_READData(0) >1020 )
				{
				  counter_sensor_zero_loud++;
				}
				else
				{
					counter_sensor_zero_low++;
				}
				if( sound_READData(1) > 1020 )
				{
					counter_sensor_one_loud++;
				}
				else
				{
					counter_sensor_one_low++;
				}
			}
			
			
			 
			  
		  
		       //safe side 
			
			
			if( (counter_sensor_zero_low>counter_sensor_zero_loud) && (counter_sensor_one_low>counter_sensor_one_loud) )
		    {
			
			 
			 
		    //PORTD&=(~(1<<4));   //turn off buzzer el buzzer b2a 3la el led
			 set_led(1,ON);   //green led
			 set_led(0,OFF);    //red led
			 //set_led(2,OFF);     //motion led off   
			 ////////////////////////lcd////////////////
			 if(counter==0)
			 {
				  Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
				  _delay_ms(200);  //2000
				  Send_A_Command(0x38);  // Letting micro controller to know there are 8 bits of data
				  _delay_ms(200);            //  1000The previous command need delay b 50
				  Send_A_Command(0b00001110);  // (1110) mn el shemal ll yemin First 1 for starting the LED,second for the display on,third for cursor on and , fourth for Blinking cursor
				  
				  // _delay_ms(1000);     //The previous command need 50 ms
				  
				  Send_A_Command(0x80 + 0);
				  _delay_ms(50);   //50
				  
				  Send_A_String("Safe region ");
				  _delay_ms(1000);
				  counter++;
				 
			 }
			 
			 //////////////////////////////////////////
			 //set_led(0,ON);
			 
		     
			}//end if of safe side
		  
		  else 
		  {
			  	  if(PIR_READData(0)!=0)
			  	  {
				  	  
				  	  
				  	  set_led(2,ON);
				  	  _delay_ms(2000);
				  	  
				  	  
				  	  
				  	  
			  	  }
			  	  else
			  	  {
				  	  set_led(2,OFF);
				  	  //_delay_ms(500);
				  	  
			  	  }
			  
			  //PORTD|=(1<<4);
			  hc_05_bluetooth_transmit_string("Warning loud voice");
			  /*Transmits a string to Bluetooth Module*/

			  hc_05_bluetooth_transmit_byte(0x0d);
			  /*Transmits Carriage return to Bluetooth Module*/
			  
			  hc_05_bluetooth_transmit_byte(0x0a);
			  /*Transmits New Line to Bluetooth Module for new line*/
			  
			  counter=0;
			  
		
			set_led(1,OFF);  //green off
			set_led(0,ON);    //red on
			  
			Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
			 _delay_ms(200);  //2000
			 Send_A_Command(0x38);  // Letting micro controller to know there are 8 bits of data
			_delay_ms(200);            //  1000The previous command need delay b 50
			 Send_A_Command(0b00001110);  // (1110) mn el shemal ll yemin First 1 for starting the LED,second for the display on,third for cursor on and , fourth for Blinking cursor
			 Send_A_Command(0x80 + 0);
			 _delay_ms(50);   //50
			   
			 Send_A_String("Dangerous region ");
			 _delay_ms(1000);
			  
			  
			  
		  }
		  
		 
		  
    }//end while 
}// end main

void Check_IF_MrLCD_isBusy()
{
	DataDir_MrLCDsCrib = 0; //bt5aly el port B input la2nak 3ayez te2ra mno el bin el a5ira btala3 eh l2ena law btala3 1 yeb2a el lcd busy law betala3 0 teb2a el lcd mesh busy
	MrLCDsControl |= 1<<ReadWrite;  //la2anak 3ayez te2ra mn el lcd 7aleT (D7 elly metwasala b a5r bin f port B )
	MrLCDsControl &= ~1<<BiPolarMood; //command

	while (MrLCDsCrib >= 0x80)  //1000 0000 aw 1000 1010 el 7alten a5r bit 1 ya3ny busy lzalk b3mel >=
	{
		Peek_A_Boo();   //tol ma el LCD busy e3mel delay . el loop di mesh ha3raf a5rog mnha 3'er lama el lcd teb2a mesh busy
	}
	DataDir_MrLCDsCrib = 0xFF; //0xFF means 0b11111111 raga3 el port B output tany 3lashan a2dar a7ot (atala3 ) 3la el port B el command aw el data elly hatgily
}

void Peek_A_Boo()
{
	MrLCDsControl |= 1<<LightSwitch;     //Btala3 3la el bin 5 fl port D 1 elly hia metwasal bl enable bta3 el lcd
	asm volatile ("nop");                //The _delay_1() function is accurate to three CPU cycles mn assmbly language 3ashan mr lcd yshof ur commands
	asm volatile ("nop");
	MrLCDsControl &= ~1<<LightSwitch;     //b3d ma a5ly mrlcd enable wa2t el tanfiz araga3o disable l2eno by7eb yo3od fl dalma
}

void Send_A_Command(unsigned char command)
{
	//Check_IF_MrLCD_isBusy();
	MrLCDsCrib = command;   // port B fi haza el barnameg hasta5demo ema output or input w hona fi haza el satr hwa output l2eny ana 3amel keda f a5r satr gwa check busy so el command mawgod 3la el bins bta3et port B elly hwma metwasalalin b (D0,D7)3la el lcd
	MrLCDsControl &= ~ ((1<<ReadWrite)|(1<<BiPolarMood)); //keda portD elly hwa output da2eman ha5aly el bin 7 ttala3 0 ya3ny h write 3la el lcd w el bin 2 hatala3 3leha 0 ya3ny command w ba2y el bins hatala3 1 la7ez (7,2)dol el connected bl lcd b (RW,rs)
	
	//MrLCDsControl &= ~ ((1<<ReadWrite)|(1<<BiPolarMood)); //bt7ot 1 3nd bin 7(r/w)w ba2y el bin asfar w b3d keda btkawe2 8bits tanieen 3ashwa2ieen  bt7ot 1 3nd el bin 2(bipolar)w bt3mel or ben 2 (BYTES) el 3ashwa2ieen dol w el BYTE el nateg a3melo not then a3mel el BYTE el nateg and m3 el port D
	
	Peek_A_Boo();        //T5aly mr lcd enable l fatra mn el zaman w b3diha traga3o disable
	MrLCDsCrib = 0;      // traga3 el bins elly 3la el port B b zero .
}

void Send_A_Character(unsigned char character)
{
	//Check_IF_MrLCD_isBusy();
	MrLCDsCrib = character;
	MrLCDsControl &= ~ (1<<ReadWrite);
	MrLCDsControl |= 1<<BiPolarMood;
	Peek_A_Boo();
	MrLCDsCrib = 0;
}

void GotoMrLCDsLocation(uint8_t x, uint8_t y)
{
	Send_A_Command(0x80 + y + (x-1)); //la2eny 3ashan at7akem fl cursor lazem ykon 0 b1000 0000 + rakam el 5ana bta3et el 3amod(bagibo mn el array) + hamshy kam 5atwa fl row tab3an -1 l2en el base zero
}

void Send_A_String(char *StringOfCharacters)
{
	while(*StringOfCharacters > 0)
	{
		Send_A_Character(*StringOfCharacters++);
		_delay_ms(1000);         //
	}
}
