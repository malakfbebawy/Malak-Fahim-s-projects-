/*
 * lcd.c
 *
 * Created: 4/28/2017 7:19:35 PM
 *  Author: Malak Fahim
 */

#include "lcd.h"

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
 
