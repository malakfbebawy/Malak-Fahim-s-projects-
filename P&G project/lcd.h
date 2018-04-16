/*
 * lcd.h
 *
 * Created: 4/28/2017 7:16:52 PM
 *  Author: Malak Fahim
 */ 


#ifndef LCD_H_
#define LCD_H_

#define MrLCDsCrib PORTB
#define DataDir_MrLCDsCrib DDRB
#define MrLCDsControl PORTD
#define DataDir_MrLCDsControl DDRD
#define LightSwitch 5
#define ReadWrite 7
#define BiPolarMood 2
void Check_IF_MrLCD_isBusy(void);
void Peek_A_Boo(void);
void Send_A_Command(unsigned char command);
void Send_A_Character(unsigned char character);
void Send_A_String(char *StringOfCharacters);






#endif /* LCD_H_ */