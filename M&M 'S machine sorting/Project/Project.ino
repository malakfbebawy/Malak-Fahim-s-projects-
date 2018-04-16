/*
Color Sensor      Arduino
-----------      --------
 VCC               5V
 GND               GND
 s0                VCC
 s1                VCC
 s2                12
 s3                11
 OUT               10
 OE                GND

 Servo 180        Arduino
 ---------        -------
 VCC                5v
 GND                GND
 Sig1                6
 Sig2               46

Keypad          Arduino
------          -------
r1                A0
r2                A1
r3                A2
r4                A3
c1                A7
c2                A6
c3                A5
c4                A4

LCD            Arduino    
---           ---------
D[0]~D[3]        53~50
D[4]~D[7]         10~13
Enable            20
R/W               38
Bipolar(RS)       19    
Anod              VCC
Cathod            Ground
VSS               Ground
V0                Ground
VDD               VCC

*/

//LCD and Keypad Defines

#define MrLCDsCrib PORTB
#define DataDir_MrLCDsCrib DDRB
#define MrLCDsControl PORTD
#define DataDir_MrLCDsControl DDRD
#define LightSwitch 1
#define ReadWrite 7
#define BiPolarMood 2

int r1=A0;
int r2=A1;
int r3=A2;
int r4=A3;

int c1=A7;
int c2=A6;
int c3=A5;
int c4=A4;
int cnt=0;
char arr[4];
char pass[4]={'1','2','3','4'};


//Constants
const int s2 = 1;  
const int s3 = 2;  
const int out = 3;   


// Variables  
int red = 0;  
int green = 0;  
int blue = 0;  
long first;
long second;
int init_mod;
int r0,g0,b0;
int RedC=0;
int GreenC=0;
int YellowC=0;

void setup() {

//Lcd AND Keypad initialization
  pinMode(r1,OUTPUT);
  pinMode(r2,OUTPUT);
  pinMode(r3,OUTPUT);
  pinMode(r4,OUTPUT);

  pinMode(c1,INPUT);
  pinMode(c2,INPUT);
  pinMode(c3,INPUT);
  pinMode(c4,INPUT);
  initial_LCD();
  
//Motor 1 Initialization

DDRH = 0b00001000;
TCCR4A=TCCR4B=TCCR4C=0;
TCCR4A |= ( 1 << COM4A1) |(1 <<COM4A0) |(1<<WGM41);
TCCR4B |= (1<<WGM42) |(1<<WGM43) |(1<<CS41);
ICR4 = 39999;
OCR4A= 40000-3000;  //first pos

//Motor 2 Initialization

DDRL = 0b00001000;
TCCR5A=TCCR5B=TCCR5C=0;
TCCR5A |= ( 1 << COM5A1) |(1 <<COM5A0) |(1<<WGM51);
TCCR5B |= (1<<WGM52) |(1<<WGM53) |(1<<CS51);
ICR5 = 39999;
OCR5A= 40000-3000;  //first pos



//Sensor initiaization

  Serial.begin(9600); 
  pinMode(s2, OUTPUT);  
  pinMode(s3, OUTPUT);  
  pinMode(out, INPUT);  
}


void loop()
{
  //lcd.setCursor(cnt,2);
  Send_A_Command(0xc0 | cnt);   
  keypad4x4();
  
  if(cnt==4)
  { 
   if(verifyPassword()==true)
   {
      Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
      delay(2);
      
      Send_A_String("Correct Password ");
      delay(2000);

      Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
      delay(2);
      
      Send_A_String("Red Green Blue ");
      Send_A_Command(0xc0 | 0);  
      Send_A_String("  0    0    0 ");
      while(1)
      {
        OCR4A = 40000-2800;
        delay(1000);
        OCR4A = 40000-3400;
        delay(750);
        sensor_start();
        OCR4A = 40000-3900;
        delay(1000);
          
/*       
        String R=String(RedC);
        String G=String(GreenC);
        String C=String(YellowC);
        String X="  ";
        X.concat(R);
        X.concat("    ");
        X.concat(G);
        X.concat("    ");
        X.concat(C);
        X.concat(" ");
        char *Y;
        X.toCharArray(Y,X.length());
        Send_A_Command(0xc0 | 0);  
        Send_A_String(Y);
  */
      }
         
   }
   else
    {
      Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
      delay(2);    
      Send_A_String("Wrong Password ");
      delay(2000);
      Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
      delay(2);
      cnt=0;
      initial_LCD(); 
    }
  }  
}
  
void sensor_start() 
{
  for (int i=0;i<36;i++)
  {
    delay(100);
    color();
    red+=r0;
    green+=g0;
    blue+=b0; 
  } 
  red/=36;
  green/=36;
  blue/=36;
  
  Serial.print("R Intensity:");  
  Serial.print(red, DEC);  
  Serial.print(" G Intensity: ");
  Serial.print(green, DEC);
  Serial.print(" B Intensity : ");
  Serial.print(blue, DEC);  
 
  if (blue>=29&&green>=29)
    {
      RedC++;
      Serial.println(" - (Red Color)");  
    }
  else if (blue<29&&blue>23&&green<=29) 
     {
      GreenC++;
      Serial.println(" - (Green Color)");
     }
  else if (blue<=23)
  {
    YellowC++;  
    Serial.println(" - (Yellow Color)");  
  }
  else
  {
    Serial.println();
    sensor_start();  
  }
}  
    
void color()  
{    
  digitalWrite(s2, LOW);  
  digitalWrite(s3, LOW);  
  //count frequency of the red color  
  r0 = Pulse_In(out);  
  digitalWrite(s3, HIGH);  
  //count frequency of the blue color
  b0 = Pulse_In(out);  
  digitalWrite(s2, HIGH);  
  //count frequency of the green color
  g0 = Pulse_In(out);  
}

int Pulse_In(int out)
{
 init_mod= digitalRead(out);
 while(digitalRead(out)==init_mod)
 {}
 first=micros();
 while(digitalRead(out)!=init_mod)
 {}
second=micros();
 return (second-first);
}




//LUCA AND AMR


bool verifyPassword()
 {
  if(arr[0]==pass[0] && arr[1]==pass[1] && arr[2]==pass[2] && arr[3]==pass[3])
  {return true;}
  else
 { return false;}
}
void keypad4x4()
{
 
int val;
  //setting the columns as high initially
  digitalWrite(c1,HIGH);
  digitalWrite(c2,HIGH);
  digitalWrite(c3,HIGH);
  digitalWrite(c4,HIGH);
  
  //checking everything one by one
  //case 1: col1 =0 while other col as 1
  digitalWrite(r1,LOW);
  digitalWrite(r2,HIGH);
  digitalWrite(r3,HIGH);
  digitalWrite(r4,HIGH);
  //checking each column for row1 one by one
  if(digitalRead(c1)==0)
  {
    
   //lcd.print("1");
   Send_A_String("1");
   arr[cnt]='1';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(1000);
     cnt++;
 
  }
  else if(digitalRead(c2)==0)
  {
    
   //lcd.print("2");
   Send_A_String("2");
   arr[cnt]='2';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c3)==0)
  {
    
  //lcd.print("3");
   Send_A_String("3");
   arr[cnt]='3';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c4)==0)
  {
    
    //lcd.print("A");
   Send_A_String("A");
   arr[cnt]='A';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  
  //case 2: col2 =0 while other col as 1
  digitalWrite(r1,HIGH);
  digitalWrite(r2,LOW);
  digitalWrite(r3,HIGH);
  digitalWrite(r4,HIGH);
  //checking each column for row1 one by one
  if(digitalRead(c1)==0)
  {
    
 //lcd.print("4");
   Send_A_String("4");
   arr[cnt]='4';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c2)==0)
  {
    
  //lcd.print("5");
   Send_A_String("5");
   arr[cnt]='5';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c3)==0)
  {
    
   //lcd.print("6");
   Send_A_String("6");
   arr[cnt]='6';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c4)==0)
  {
    //lcd.print("B");
   Send_A_String("B");
   arr[cnt]='B';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  
  //case 3: col3 =0 while other col as 1
  digitalWrite(r1,HIGH);
  digitalWrite(r2,HIGH);
  digitalWrite(r3,LOW);
  digitalWrite(r4,HIGH);
  //checking each column for row1 one by one
  if(digitalRead(c1)==0)
  {
   //lcd.print("7");
   Send_A_String("7");
   arr[cnt]='7';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c2)==0)
  {
   //lcd.print("8");
   Send_A_String("8");
   arr[cnt]='8';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c3)==0)
  {
   //lcd.print("9");
   Send_A_String("9");
   arr[cnt]='9';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c4)==0)
  {
  //lcd.print("C");
   Send_A_String("C");
   arr[cnt]='C';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  
  //case 1: col1 =0 while other col as 1
  digitalWrite(r1,HIGH);
  digitalWrite(r2,HIGH);
  digitalWrite(r3,HIGH);
  digitalWrite(r4,LOW);
  //checking each column for row1 one by one
  if(digitalRead(c1)==0)
  {
   //lcd.print("*");
   Send_A_String("*");
   arr[cnt]='*';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c2)==0)
  {
    //lcd.print("0");
   Send_A_String("0");
   arr[cnt]='0';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c3)==0)
  {
    //lcd.print("#");
   Send_A_String("#");
   arr[cnt]='#';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  else if(digitalRead(c4)==0)
  {
//lcd.print("D");
   Send_A_String("D");
   arr[cnt]='D';
    delay(200);
    //lcd.setCursor(cnt,2);
    Send_A_Command(0xc0 | cnt);   
    //lcd.print("*");
    Send_A_String("*");
     delay(200);
     cnt++;
  }
  delay(200);
}


void initial_LCD()
{
   DataDir_MrLCDsControl |= 1<<LightSwitch | 1<<ReadWrite | 1<<BiPolarMood;
 delay(15);

  Send_A_Command(0x01); //Clear Screen 0x01 = 00000001
  delay(200);      //2
  Send_A_Command(0x38);
  delay(20);     //50
  Send_A_Command(0b00001110);
  delay(20);     //50
   Send_A_String("Enter Password");
  }

void Check_IF_MrLCD_isBusy()
{
  DataDir_MrLCDsCrib = 0;
  MrLCDsControl |= 1<<ReadWrite;
  MrLCDsControl &= ~1<<BiPolarMood;

  while (MrLCDsCrib >= 0x80)
  {
    Peek_A_Boo();
  }

  DataDir_MrLCDsCrib = 0xFF; //0xFF means 0b11111111
}


void Peek_A_Boo()
{
  MrLCDsControl |= 1<<LightSwitch;
  asm volatile ("nop");
  asm volatile ("nop");
  MrLCDsControl &= ~1<<LightSwitch;
}

void Send_A_Command(unsigned char command)
{
  Check_IF_MrLCD_isBusy();
  MrLCDsCrib = command;
  MrLCDsControl &= ~ ((1<<ReadWrite)|(1<<BiPolarMood));
  Peek_A_Boo();
  MrLCDsCrib = 0;
}


void Send_A_Character(unsigned char character)
{
  Check_IF_MrLCD_isBusy();
  MrLCDsCrib = character;
  MrLCDsControl &= ~ (1<<ReadWrite);
  MrLCDsControl |= 1<<BiPolarMood;
  Peek_A_Boo();
  MrLCDsCrib = 0;
}


void Send_A_String(char *StringOfCharacters)
{
  while(*StringOfCharacters > 0)
  {
    Send_A_Character(*StringOfCharacters++);
    delay(100);         //
  }
}

