Data_segment_name  segment  para

;;;;;;;;Functions Parameters;;;;;;;;;;;;;;;;
curposX db ?
curposY db ?
Otherplayerscore dw 0h             ;value of score
YourScore dw 0h
;hayt3;ayaro

Time Dw 0 		;SS:mm Time he is playing at orr stopped at
StartingTime Dw 0 		;SS:mm Time he is playing at or stopped at

YouReady db 0
PlayerReady db 0
UserOp db ?
UserResult dw 0
Chances db 3
RES  DB 10 DUP ('$')          ;array bagama3 fih arkam el decimal elly gaya mn ta7wil el hexa ll decimal (ta7wil word hexa to decimal) 
BinaryRes DB 16 dup(0)
HexRes DB 4 dup(0)
delete db 0
NoAnswerV db 0              ;el 7agat di 3ashan mesh bye3raf ye3mel jump mn el proc ll main lazm intermediate jump
While db 0
win db 0





;;;;;Dimensions Of The Graphics;;;;;;;;;;;;;
OtherolayerPosX db 2                       ;position of value of score
OtherolayerPosY db 64
YourScorePosX db 2                       ;value at row 2
YourScorePosY db 6                       ;value at column 6
TimeStartX db 3                          ;position of value of Time
TimeStartY db 37
FirstOperandPosX db 10
FirstOperandPosY db 6
SecondOperandPosX db 12
SecondOperandPosY db 6
ResultPosX db 14
ResultPosY db 6
OperationPosX db 11
OperationPosY db 25


;;;;;;;;;;;;;;;;;;;;;;;;;;Game Parameters;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

FirstNum db 088h
SecondNum db 0c3h
Result dw ?
Operation db 5   ;0-> add, 1->sub , 2-> Mul , 3->Div , 4->AND , 5->OR , 6->XOR , 7-> NAND , 8->NOR
Mode db 2         ; 0-> Binary 1-> Decimal , 2-> hexadecimal




;;;;;;;;;;;;;;;;;;;;;;;;Strings in video ram;;;;;;;;;;;;;;;;;;;;;;;;;;;;
msg1 db "your score $ "
msg2 db "Who is the smarter ? $ "
msg3 db "Other player score $ "
msg4 db "Time $ "
msg5 db "|(0)Add| $"
msg6 db "|(1)Sub| $"
msg7 db "|(2)Mul| $"
msg8 db "|(3)Div| $"
msg9 db "|(4)And| $"
msg10 db "|(5)Or| $"
msg11 db "|(6)Xor| $"
msg12 db "|(7)Nand| $"
msg13 db "|(8)Nor| $"
msg14 db "----------------------- $"
msg15 db " Mode is binary $"
msg16 db " Mode is decimal $"
msg17 db " Mode is hexadecimal $"
msg18 db " You have $"
msg19 db " CHANCES $"
msg20 db "Good Job. Check Your Score :) $"
msg21 db "Sorry for that, Good luck next time $"
msg22 db "Press any key to The next question or escape to exit $"



Data_segment_name ends

Stack_segment_name segment para stack
      dw 150 dup(0)               ;define your stack segment
Stack_segment_name ends

Code_segment_name  segment 
    Main_prog  proc far
        assume SS:Stack_segment_name,CS:Code_segment_name,DS:Data_segment_name

	mov AX,Data_segment_name         ; load the starting address of the data
        mov DS,AX                        ; segment into DS reg.
	
	call initSer
	
	PlayAgain:

	call RecievePlayerScore
	call sendMyscore



	call initgame
	
	call initVga

	call GenerateNums
	
	call sendGenerated

	call start20


	call Cleararray

	call printmodeoperation

	call PrintNums

	;to be deleted
	call printop

;Start Of the Game lama kol 7aga tezhar w yebtedy yetfa3el m3 el le3ba
		
		call PrintNumOfChances
	
		notAvail:
		whi:
		
		call isitFinished ; is the other user got the answer
		cmp noAnswerV,0
		jnz Drawn             ;law ma7adesh gaweb yedba t3ado drawn
		call updatetime
		cmp noAnswerV,0
		jnz Drawn

		cmp Chances,0
		jz whi

		
		call printoperationnum
		cmp while,1
		jz whi

		call CheckOpFromUser
		cmp win,1
		jz WinSituation
		call PrintNumOfChances
		
		jmp whi



		WinSituation:
		call UpdateYourScore
		call SendFF ; A flag to inform the other computer to stop counting
		call initVga
		call Cheers
		jmp Ready
		

		Drawn:
		call initVga
		call goodluck
		jmp Ready
		
		Ready:
		call PrintReady
		
;		call SendMyScore
;		call RecievePlayerScore
		

		Waiting:

		cmp YouReady,1
		jz CheckPlayer
		call CheckUserInput      ;mstany character mn elly pyel3ab el send 
	
		

		CheckPlayer:
		cmp PlayerReady,1
		jz Ready2
		call CheckPlayerSerial        ;tshof el serial etba3at 3leh 7aga wla la2 wlaw etba3t elly etba3at da dd wla escape


		Ready2:
		cmp YouReady,1
		jnz Waiting
		cmp PlayerReady,1
		jnz CheckPlayer
		jmp PlayAgain
		


		
		Exit:
        mov ax,4c00h                     ; exit program
        int 21h

    Main_prog      endp


CheckUserInput proc near

		mov ah,01h ;check keyboard buffer
		int 16h
		jz notAvail2
		mov ah,0h
		int 16h
		;now al have the charcter

		cmp al,27
		jz Exit
		mov YouReady,1
		call SendDD              ;yeb3at DD law hwa ready  
		notAvail2:
		ret
CheckUserInput endp




	SendMyScore proc
		SEND20:
   		mov dx,03FDh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND20

   		MOV AL,byte ptr YourScore[0]
   		mov dx,03f8h
   		OUT dx,AL		

		mov di,6
   		call waitt
		
		SEND21:
   		mov dx,03FDh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND21

   		MOV AL,byte ptr YourScore[1]
   		mov dx,03f8h
   		OUT dx,AL		
		
		ret
	SendMyScore endp


waitt proc    

        mov ah, 0
        int 1Ah ; actual time
        mov bx,dx
        delay:
            mov ah, 0
            int 1Ah
            sub dx,bx
            cmp di,dx
        ja delay
        ret

    waitt endp

	RecievePlayerScore proc
		
		REC124:

		mov dx,03FDH
		IN aL,dx
		TEST aL,1
		JZ REC124

		mov dx,03F8H
		IN AL,dx
		mov byte ptr Otherplayerscore[0],al



		REC125:

		mov dx,03FDH
		IN aL,dx
		TEST aL,1
		JZ REC125

		mov dx,03F8H
		IN AL,dx
		mov byte ptr Otherplayerscore[1],al


		ret
	RecievePlayerScore endp



initgame proc

	mov YouReady,0
	mov PlayerReady,0
	mov UserResult,0
	mov chances,3

	mov delete,0
	mov NoAnswerV, 0
	mov While,0
	mov win,0

	ret
initgame endp


printop proc
		mov curposX,24                     
		mov curposY,0                    
		call  setcurspos

mov dl,Operation
add dl,30h
mov ah,02
int 21h
ret
printop endp


CheckPlayerSerial proc
		
		mov dx,03fdh
		IN AL,dx
		TEST AL,1
		JZ noplayerinput

		mov dx,03F8H
		IN AL,dx
		cmp al,0ddh
		jnz noplayerinput
		mov PlayerReady,1		

		noplayerinput:
		ret		
CheckPlayerSerial endp



PrintReady proc 
		mov curposX,20                     
		mov curposY,15                    
		call  setcurspos
		lea dx,msg22

 		mov ah,09h                     ;interupt to print string
		int 21h
	
	ret
PrintReady endp

goodluck proc 
		
		mov curposX,18                     
		mov curposY,15                    
		call  setcurspos

		lea dx,msg21

 		mov ah,09h                     ;interupt to print string
		int 21h
	
		ret
goodluck endp



Cheers proc 
		
;to print Cheering message
	
		mov curposX,18                     
		mov curposY,15                    
		call  setcurspos

		lea dx,msg20

 		mov ah,09h                     ;interupt to print string
		int 21h
	
		ret
Cheers endp


isitFinished proc 
	
		mov dx,03FDH
		IN aL,dx
		TEST aL,1
		JZ NA

		mov dx,03F8H
		IN AL,dx
		cmp al,0FFh        ;elly byel3ab hayeb3at ff law 7alel s7
		jz Draw
		
		NA: mov NoAnswerV,0
		ret
		
		Draw:	mov NoAnswerV,1

		ret


isitFinished endp


SendFF proc 
		
   		SEND7:
   		mov dx,03FDh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND7

   		MOV AL,0FFh
   		mov dx,03f8h
   		OUT dx,AL		

		ret
SendFF endp

SendDD proc 
		
   		SEND8:
   		mov dx,03FDh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND8

   		MOV AL,0DDh
   		mov dx,03f8h
   		OUT dx,AL		

		ret
SendDD endp



PrintNumOfChances proc 
		mov curposX,16                     
		mov curposY,6                    
		call  setcurspos

		lea dx,msg18		
 		mov ah,09h                     ;interupt to print string
		int 21h
	
		mov curposX,16                     
		mov curposY,16                    
		call  setcurspos
		
		mov dl,Chances
		add dl,30h
		mov ah,02
		int 21h

		mov curposX,16                     
		mov curposY,17                    
		call  setcurspos
		lea dx,msg19
		mov ah,09h                     ;interupt to print string
		int 21h

		ret
PrintNumOfChances endp


PrintNums proc
		cmp Mode,0
		jnz notBinary
		call ConvertToBinary
		jmp EndPrint

		notBinary:
		cmp Mode,1
		jnz notDecimal
		call printfirstnumindec
		call printsecondnumindec
		call printresultindec
		
		jmp EndPrint

		notDecimal:
		call ConvertToHex

		EndPrint:

		ret
PrintNums endp


ConvertToHex proc	
	call PrintHexFirstNumber	
	call PrintHexSecondNumber	
	
	call PrintHexResult	
	ret
ConvertToHex endp



PrintHexFirstNumber proc
	
	mov curposX,10
   	mov curposY,6
	call setcurspos


		mov ch,FirstNum
		mov bl,FirstNum
	

		and bl,0f0h
		mov cl,04
		shr bl,cl       ;agibo ymin 3ashan gib el value bta3to 3ashan lama akarno b 9
		cmp bl,09
		jbe nomod2
		add bl,37h
		jmp p
		nomod2:
		add bl,30h
		p:
	   

		mov dl,bl
		mov ah,02
		int 21h


		and ch,0fh
		cmp ch,09
		jbe nomodification2
		add ch,37h
		jmp q
		nomodification2:
		add ch,30h
		q:
		mov dl,ch
		mov ah,02
		int 21h


		ret
PrintHexFirstNumber endp





PrintHexSecondNumber proc
		
		mov curposX,12
		mov curposY,6
		call setcurspos

		mov ch,SecondNum
		mov bl,SecondNum
	

		and bl,0f0h
		mov cl,04
		shr bl,cl
		cmp bl,09
		jbe nomod3
		add bl,37h
		jmp p2
		nomod3:
		add bl,30h
		p2:
		mov dl,bl
		mov ah,02
		int 21h




		and ch,0fh
		cmp ch,09
		jbe nomodification
		add ch,37h
		jmp q2
		nomodification:
		add ch,30h
		q2:
		mov dl,ch
		mov ah,02
		int 21h


		ret
PrintHexSecondNumber endp





PrintHexResult proc

	mov curposX,14
   	mov curposY,6
	call setcurspos
  

	call ClearHexArray
 		
		mov dx,0
		mov ax,Result
		mov cx,10h
		div cx
		cmp dl,09
		jbe RR
		add dl,37h
		jmp s1
		RR:
		add dl,30h
		s1:
		mov HexRes[3],dl


		mov dx,0
		mov cx,10h
		div cx
		cmp dl,09
		jbe TT
		add dl,37h
		jmp s2
		TT:
		add dl,30h
		s2:
		mov HexRes[2],dl
		

		mov dx,0
		mov cx,10h
		div cx
		cmp dl,09
		jbe YY
		add dl,37h
		jmp s3
		YY:
		add dl,30h
		s3:
		mov HexRes[1],dl
		
		mov dx,0
		mov cx,10h
		div cx
		cmp dl,09
		jbe uu
		add dl,37h
		jmp s4
		uu:
		add dl,30h
		s4:
		mov HexRes[0],dl

		mov cx,4
		mov bx,0

		Looping:
		
		mov dl,HexRes[bx]
		mov ah,02h
		int 21h
		inc bx
		Loop Looping

		ret
PrintHexResult endp




ConvertToBinary proc
	call PrintBinaryFirstNumber	
	call PrintBinarySecondNumber	
	call PrintBinaryResult	
	ret
ConvertToBinary endp

PrintBinaryFirstNumber proc 
	mov al,FirstNum
	mov ah,0
	call BinaryArray

	mov curposX,10
   	mov curposY,6
	call setcurspos
    

	mov cx,08h                ;aksa rakm ykon 8 bit

	StillPrinting3:
	mov bx,cx
	dec bx
	mov dl,BinaryRes[bx]
	add dl,30h
	mov ah,02
	int 21h
	Loop StillPrinting3
	ret

PrintBinaryFirstNumber endp

PrintBinarySecondNumber proc 
	mov al,SecondNum
	mov ah,0
	call BinaryArray

	mov curposX,12
   	mov curposY,6
	call setcurspos
    

	mov cx,08h             ;aksa rakm ykon 8 bit

	StillPrinting2:
	mov bx,cx
	dec bx
	mov dl,BinaryRes[bx]
	add dl,30h
	mov ah,02
	int 21h
	Loop StillPrinting2
	ret

PrintBinarySecondNumber endp



PrintBinaryResult proc 
	mov ax,Result
	call BinaryArray

	mov curposX,14
   	mov curposY,6
	call setcurspos
    

	mov cx,10h                ;l2en el result momken tkon b3d el ta7wil ela binary tkon 16 bit 4 digits

	StillPrinting:
	mov bx,cx
	dec bx
	mov dl,BinaryRes[bx]
	add dl,30h
	mov ah,02
	int 21h
	Loop StillPrinting
	ret

PrintBinaryResult endp



BinaryArray proc
		;now we have number ax and we want to store its binary in resbinary array in DS
		;first clear this array 
		call clearBinaryArray

		mov bx,0
		DivAgain:

		mov cl,02
		div  cl
		mov BinaryRes[bx],ah
		inc bx
		mov ah,0
		cmp al,0
		jnz DivAgain
		ret
BinaryArray endp

ClearBinaryArray proc
		
	mov cx,10h

	mov bx,cx

	Clearing:
	dec bx
	mov BinaryRes[bx],0
	Loop Clearing
	ret

ClearBinaryArray endp



ClearHexArray proc
		
	mov cx,04h
	mov bx,cx
	Clearing1:
	dec bx
	mov HexRes[bx],0
	Loop Clearing1
	ret

ClearHexArray endp


   sendGenerated proc 
   		;function to send FN and SN And Op And Mode and Result
   		SEND:
   		mov dx,03fdh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND

   		MOV AL,FirstNum
   		mov dx,03f8h
   		OUT dx,AL


   		SEND2:
   		mov dx,03fdh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND2

   		MOV AL,SecondNum
   		mov dx,03f8h
   		OUT dx,AL

   		SEND3:
   		mov dx,03FDh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND3

   		MOV AL,Operation
   		mov dx,03f8h
   		OUT dx,AL


		SEND4:
		mov dx,03fdh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND4

   		MOV AL,Mode
   		mov dx,03f8h
   		OUT dx,AL
   		
   		SEND5:
   		mov dx,03fdh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND5

   		MOV AL,byte ptr Result
   		mov dx,03f8h
   		OUT dx,AL

   		SEND6:
   		mov dx,03fdh
   		IN AL,dx
   		TEST AL,00100000B
   		JZ SEND6

   		MOV AL,byte ptr Result[1]
   		mov dx,03f8h
   		OUT dx,AL

   	
   		ret
   sendGenerated endp 



RecieveGenerated proc
		Rec:
		mov dx,03fdh
		IN AL,dx
		TEST AL,1
		JZ Rec

		mov dx,03f8h
		IN AL,dx
		MOV FirstNum,al


		Rec2:
		mov dx,03fdh
		IN AL,dx
		TEST AL,1
		JZ Rec2

		mov dx,03f8h
		IN AL,dx
		MOV SecondNum,al
		
		Rec3:
		mov dx,03fdh
		IN AL,dx
		TEST AL,1
		JZ Rec3

		mov dx,03F8H
		IN AL,dx
		MOV Operation,al

		
		Rec4:
		mov dx,03fdh
		IN AL,dx
		TEST AL,1
		JZ Rec4

		mov dx,03F8H
		IN AL,dx
		MOV Mode,al

		Rec5:
		mov dx,03FDH
		IN AL,dx
		TEST AL,1
		JZ Rec5

		mov dx,03F8H
		IN AL,dx
		MOV byte ptr Result,al
		

		Rec6:
		mov dx,03FDH
		IN AL,dx
		TEST AL,1
		JZ Rec6

		mov dx,03F8H
		IN AL,dx
		MOV byte ptr Result[1],al
		
		ret
RecieveGenerated endp






   initVga proc 
	call clrscr                       ;to  clear the screen
	call printwhois
	 
	call printurscore
	call printurscoreval                ;print value of scote in decimal 

	call printotherplayer
	call printotherscore               
	call Timeword
	call Addword
	call Subword
	call Mulword
	call Divword
	call Andword
	call Orword
	call Xorword
	call Nandword
	call Norword
	call Linerword1
	call Linerword2
	call Linerword3
	call Linerword4
	call Linerwordresult

	ret 
    initVga endp



    initSer proc 
	mov al,80h
	mov dx,03fbh
	out dx,al
	mov al,0ch
	mov dx,03f8h


	out dx,al
	mov al,00
	mov dx,03f9h
	out dx,al
	mov al,0fh
	mov dx,03fbh
	out dx,al

	ret
    initSer endp



    UpdateYourScore proc
	cmp byte ptr Time[1],10
	jnb GOTO20
	add YourScore,15
	jmp ScoreUpdated	

	GOTO20 :
	cmp byte ptr Time[1],20
	jnb GoTo30
	add YourScore,10
	jmp ScoreUpdated	
	
	GoTo30 :
	add YourScore,5	
	
	ScoreUpdated:	

	RET
    UpdateYourScore endp



    GenerateNums proc 
	
	

	mov ah,02ch	
	int 21h
	;1/100 sec fe el dl	
	
	mov FirstNum,dl
	
	int 21h
	mov SecondNum,dl
	
	;;mo2akatan
	mov cl,2
	mov al,SecondNum
	mul cl
	mov SecondNum,al
	;;;;;;;;;;;;;;;;;;;;;;;

	mov ah,02ch
	int 21h
	add FirstNum,dl
		
	int 21h
	add SecondNum,dl
	
	;Generating mode
	int 21h
	mov al,dl
	mov ah,0
	mov cl,03
	div cl	
	mov Mode,ah
	
	CantBeThis:	

	;Generating Operation
	mov ah,02ch
	int 21h
	mov al,dl
	mov ah,0
	mov cl,09
	div cl
	mov Operation,ah

  
	mov ah,0
	mov Result,0
	;Generate Result

	cmp Operation,0
	jnz notAdd
	mov al,FirstNum
	add al,SecondNum
	adc ah,0
	mov Result,ax
	jmp complete

	notAdd:
	cmp Operation,1
	jnz notSubtract
	mov dl,Firstnum
	cmp dl,SecondNum
	jb CantBeThis
	mov al,FirstNum
	sub al,SecondNum
	mov byte ptr Result,al
	jmp complete

	notSubtract :
	cmp Operation,2
	jnz notMul
	mov al,FirstNum
	mov bl,SecondNum
	mul bl
	mov Result,ax
	jmp complete



;;;;Revise this
	notMul:
	cmp Operation,3
	jnz notDiv
	cmp SecondNum,0
	jz CantBeThis
	mov al,FirstNum
	mov ah,0
	mov bl,SecondNum
	div bl
	mov byte ptr Result,al
	jmp complete


	notDiv:
	cmp Operation,4
	jnz notAnd
	mov al,FirstNum
	And al,SecondNum
	mov byte ptr Result,al
	jmp complete


	notAnd:
	cmp Operation,5
	jnz notOr
	mov al,FirstNum
	OR al,SecondNum
	mov byte ptr Result,al
	jmp complete


	notOr:
	cmp Operation,6
	jnz notXor
	mov al,FirstNum
	XOR al,SecondNum
	mov byte ptr Result,al
	jmp complete


	notXor:
	cmp Operation,7
	jnz notNand
	mov al,FirstNum
	And al,SecondNum
	Xor AL,0FFh           ;3ashan te3mel not b3d ma 3mlt and
	mov byte ptr Result,al
	jmp complete


	notNand:
	cmp Operation,8
	mov al,FirstNum
	OR al,SecondNum
	XOR AL,0FFh
	mov byte ptr Result,al
	
	complete:
	RET
    GenerateNums endp


   start20 proc 
	mov ah,02ch
	int 21h

	;second in dh w el la7azat fe el dl
	
	mov byte ptr StartingTime[0],dl
	
	
	mov byte ptr StartingTime[1],dh
	ret
   start20 endp


   updatetime proc
	
	mov ah,02ch
	int 21h

	;second in dh w el la7azat fe el dl
	
	cmp byte ptr StartingTime[1],dh
	ja modifymins

	sub dh,byte ptr StartingTime[1]
	mov byte ptr Time[1],dh
	jmp cont

	modifymins:
	mov al ,byte ptr StartingTime[1]
	sub al,dh
	mov ah,60
	sub ah,al
	mov byte ptr Time[1],ah
	
	cont :	

	
	cmp byte ptr StartingTime[0],dl	
	ja modify
	sub dl,byte ptr StartingTime[0]
	mov byte ptr Time[0],dl
	jmp print

	modify:		
	mov al,byte ptr StartingTime[0]
	sub al,dl
	mov ah,100
	sub ah,al
	mov byte ptr Time[0],ah
	dec byte ptr Time[1]
	
	print :
	

	cmp byte ptr Time[1],30
	jnz normal
	mov byte ptr Time[0],0
	normal:


	call printtime


	cmp byte ptr Time[1],30
	jb normal2
	mov NoAnswerV,1
	normal2:
	
	

	ret 
   updatetime endp


   printtime proc
	
;;Set el cursor
	mov curposX,4                     
	mov curposY,36                     
	call  setcurspos

	;printing minutes
	mov al,byte ptr Time[1]
	mov ah,0
	mov cl,10
	div cl

	add ax,3030h
	mov bx,ax
	mov dl,bl
	mov ah,02
	int 21h
	
	mov dl,bh
	mov ah,02
	int 21h
	
	
	mov dl,':'
	int 21h	


	mov al,byte ptr Time[0]
	mov ah,0
	mov cl,10
	div cl
	
	add ax,3030h
	mov bx,ax
	mov dl,bl
	mov ah,02h
	int 21h
	mov dl,bh
	int 21h	
	
	
	ret
   printtime endp

 GetOpFromUser proc 
	againplease:

 	mov curposX,11                     
	mov curposY,25
	call  setcurspos


	mov ah,01h
	int 16h
	jz notAvailable



 	mov ah,0h
	int 16h



;;mo2akatan
	cmp al,30h
	jb againplease            ;3ashan yekteb mn 0 to 8

	cmp al,38h
	ja againplease

	;now al have the charcter
	mov while,0
	sub al,30h
	mov UserOp,al
	ret
	notAvailable:
	mov While,1
	ret

GetOpFromUser endp


CheckOpFromUser proc 

	mov UserResult,0
	mov ah,0

	cmp UserOp,0
	jnz notAdd2
	mov al,FirstNum
	add al,SecondNum
	adc ah,00
	mov UserResult,ax
	jmp complete2

	notAdd2:
	cmp UserOp,1
	jnz notSubtract2
	mov al,FirstNum
	sub al,SecondNum
	mov ah,0
	mov UserResult,ax
	jmp complete2

	notSubtract2 :
	cmp UserOp,2
	jnz notMul2
	mov al,FirstNum
	mov bl,SecondNum
	mul bl
	mov UserResult,ax
	jmp complete2



;;;;Revise this
	notMul2:
	cmp UserOp,3
	jnz notDiv2
	mov al,FirstNum
	mov ah,0
	mov bl,SecondNum
	div bl
	mov ah,0
	mov UserResult,ax
	jmp complete2


	notDiv2:
	cmp UserOp,4
	jnz notAnd2
	mov al,FirstNum
	And al,SecondNum
	mov ah,0
	mov UserResult,ax
	jmp complete2


	notAnd2:
	cmp UserOp,5
	jnz notOr2
	mov al,FirstNum
	OR al,SecondNum
	mov ah,0
	mov UserResult,ax
	jmp complete2


	notOr2:
	cmp UserOp,6
	jnz notXor2
	mov al,FirstNum
	XOR al,SecondNum
	mov ah,0
	mov UserResult,ax
	jmp complete2


	notXor2:
	cmp UserOp,7
	jnz notNand2
	mov al,FirstNum
	And al,SecondNum
	Xor AL,0FFh
	mov ah,0
	mov UserResult,ax
	jmp complete2


	notNand2:
	cmp UserOp,8
	mov al,FirstNum
	OR al,SecondNum
	XOR AL,0FFh
	mov ah,0
	mov UserResult,ax
	
	

	complete2:
	
	mov ax,UserResult
	cmp Result,ax
	jz Winq
	Dec Chances
	;;;;Go To Loop Again
	

	ret

	Winq :
	mov Win,1
	ret
   CheckOpFromUser endp





     ;;;;;;;;;;;;;;;;;;;;;;;;;;;;set cursor position ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

	 setcurspos proc 
	 mov dh,curposX           ;set cursor at row 
     mov dl,curposY           ;set cursor at column                               
     mov bh,0
     mov ah,02
     int 10h
	 ret
	 
	 setcurspos endp
	  
	  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;clear the screen;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

	clrscr proc 
	
   	mov ax,0b800h
   	mov es,ax
   	mov di,0
   	mov al,' '
   	mov ah,07d
   	loop_clear:
        mov word ptr es:[di],ax
        inc di
        inc di
        cmp di,4000
        jle loop_clear
ret
clrscr endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print who is the smarter;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

printwhois proc 
mov curposX,0                     ;set cursor at row 0
mov curposY,26                     ;set cursor at column 0
call  setcurspos
 
 lea dx,msg2
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
printwhois endp
;;;;;;;;;;;;;;;;;;;;;;;;;print word of ' your score ' ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

printurscore proc 
mov curposX,1                     ;set cursor at row 0
mov curposY,3                      ;set cursor at column 0
call  setcurspos
 
 lea dx,msg1
 mov ah,09h                     ;interupt to print string
		int 21h

 
		ret
printurscore endp


;;;;;;;;;;;;;;;;;;;;;;;;;print word of ' other player ' ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

printotherplayer proc 
mov curposX,1                     ;set cursor at row 0
mov curposY,58                     ;set cursor at column 0
call  setcurspos
 
 lea dx,msg3
 mov ah,09h                     ;interupt to print string
		int 21h

 
		ret
printotherplayer endp

Cleararray proc 
	MOV BX,0
	CLEAR:
	
	mov RES[BX],'$'
	INC BX
	CMP BX,9
	JNZ CLEAR
	RET
Cleararray endp
 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print urscorevalue in decimal;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 printurscoreval proc  

	call Cleararray
     MOV AX,YourScore
      
    LEA SI,RES
    CALL convrttodecimal

	mov curposX,2
	mov curposY,6
	call setcurspos
    LEA DX,RES             ;haya5od mn el array w ye3red el array haya7tawy 3la arkam decimal gaya mn taftit el rakm el hexa
    MOV AH,9               ;hayo2af l7d ma yla2y doolar sign
    INT 21H 
            
   ret        
 printurscoreval ENDP

convrttodecimal PROC 
   	MOV CX,0                ;counter ye3ed number of digit in stack
    MOV BX,10
   
LOOP1: MOV DX,0             ;asafar dx la2eny ha2sem 
       DIV BX
       ADD DL,30H           ;dl b3d el 2esma hay7tawy 3la remainder (awel rakam decimal) l2eno haykon mn 0 to 9 azawed 30 3lashan lama agy print in ascii 
       PUSH DX              ;stack ya7tawy 3la el arkam el decimal el btantog mn el 2esma el natga mn taftit el hexa
       INC CX                ;ya3ny y2oly enek zawet 7aga fl stack
       CMP AX,9              ;el rakam el asasy elly galy mn el division akarno b 9 3ashan law akbar ya3ny lessa hexa aro7 afateto
       JA LOOP1
     
       ADD AL,30H            ;hna ana damant en el rakm el asay etfatet 5ales w al b3d el 2esma 2sba7at mn 0 to 9 ya3ny decimal
       MOV [SI],AL           ;an2elha ll array of res elly bygama3 el 7agat el decimal
     
LOOP2: POP AX                ;pop mn el stack mn el a5er ll awel el arkam el decimal elly gama3taha w a7otohom fl array of result
       INC SI
       MOV [SI],AL
       LOOP LOOP2
       RET
 convrttodecimal ENDP

  ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print scorevalue of the other player in decimal;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
 printotherscore proc  
    call Cleararray
	 MOV AX,Otherplayerscore
      
    LEA SI,RES
    CALL convrttodecimal

   mov curposX,2
   mov curposY,64
call setcurspos
    LEA DX,RES             ;haya5od mn el array w ye3red el array haya7tawy 3la arkam decimal gaya mn taftit el rakm el hexa
    MOV AH,9
    INT 21H 
            
   ret        
 printotherscore ENDP
 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;Print 'Time ';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

 Timeword proc 
mov curposX,3                     
mov curposY,37                    
call  setcurspos
 
 lea dx,msg4
 mov ah,09h                     ;interupt to print string
		int 21h

 
		ret
Timeword endp

 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;Print 'Add';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Addword proc 
mov curposX,8                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg5
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Addword endp
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Sub';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Subword proc 
mov curposX,8                     
mov curposY,57                    
call  setcurspos
 
 lea dx,msg6
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Subword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Mul';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Mulword proc 
mov curposX,8                     
mov curposY,64                    
call  setcurspos
 
 lea dx,msg7
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Mulword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Div';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Divword proc 
mov curposX,10                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg8
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Divword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'And';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Andword proc 
mov curposX,10                     
mov curposY,57                    
call  setcurspos
 
 lea dx,msg9
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Andword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Or';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Orword proc 
mov curposX,10                     
mov curposY,64                    
call  setcurspos
 
 lea dx,msg10
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Orword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Xor';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Xorword proc 
mov curposX,12                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg11
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Xorword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Nand';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Nandword proc 
mov curposX,12                     
mov curposY,57                    
call  setcurspos
 
 lea dx,msg12
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Nandword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print 'Nor';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Norword proc 
mov curposX,12                     
mov curposY,65                    
call  setcurspos
 
 lea dx,msg13
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Norword endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print '----';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Linerword1 proc 
mov curposX,7                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg14
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Linerword1 endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print '----';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Linerword2 proc 
mov curposX,9                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg14
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Linerword2 endp


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print '----';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Linerword3 proc 
mov curposX,11                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg14
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Linerword3 endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print '----';;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Linerword4 proc 
mov curposX,13                     
mov curposY,50                    
call  setcurspos
 
 lea dx,msg14
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Linerword4 endp

 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print Firstnumber in decimal;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
printfirstnumindec proc  
      call Cleararray
	  ;pop cx
	  ;mov RES,0
	  MOV SI,0
     MOV AH,0
     MOV AL,FirstNum
      
    LEA SI,RES
    CALL convrttodecimal

   	mov curposX,10
   	mov curposY,6
	call setcurspos
     ;mov RES[2],0     ;fih moshkela law el first number b3d el ta7wil tele3 2 digit byzawed 3leh digit w hia res[2] a5er digit mn otherplayer score 
	 ;MOV RES[3],0
    LEA DX,RES            ;haya5od mn el array w ye3red el array haya7tawy 3la arkam decimal gaya mn taftit el rakm el hexa
    MOV AH,9            ;output string hayo2af l7d ma yla2y dollar sign fl array of res ay l7d ma el arkam elly fih te5las 
    INT 21H 
            
   ret        
printfirstnumindec ENDP

 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print secondnumber in decimal;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
printsecondnumindec proc  
      call Cleararray
	  ;pop cx
	  ;mov RES,0
	  MOV SI,0
     MOV AH,0
     MOV AL,SecondNum
      
    LEA SI,RES
    CALL convrttodecimal

   mov curposX,12
   mov curposY,6
call setcurspos
     ;mov RES[2],0     ;fih moshkela law el first number b3d el ta7wil tele3 2 digit byzawed 3leh digit w hia res[2] a5er digit mn otherplayer score 
	 ;MOV RES[3],0
    LEA DX,RES            ;haya5od mn el array w ye3red el array haya7tawy 3la arkam decimal gaya mn taftit el rakm el hexa
    MOV AH,9
    INT 21H 
            
   ret        
printsecondnumindec ENDP

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print '----'of Result;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
Linerwordresult proc 
mov curposX,13                     
mov curposY,6                    
call  setcurspos
 
 lea dx,msg14
 mov ah,09h                     ;interupt to print string
		int 21h
		ret
Linerwordresult endp

 ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print Result in decimal;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
printresultindec proc  
      call Cleararray
	  ;pop cx
	  ;mov RES,0
	  MOV SI,0
     MOV AH,0
     MOV Ax,Result
      
    LEA SI,RES
    CALL convrttodecimal

   mov curposX,14
   mov curposY,6
call setcurspos
     ;mov RES[2],0     ;ana 3amlaha subtraction el 4 el ziada di hia a5er digit fi 144 lessa mawgoda f RES[2] f 3ashan keda btt3ered 
	 ;MOV RES[3],0
    LEA DX,RES            ;haya5od mn el array w ye3red el array haya7tawy 3la arkam decimal gaya mn taftit el rakm el hexa
    MOV AH,9
    INT 21H 
            
   ret        
printresultindec ENDP

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print mode of operation;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
printmodeoperation proc 
mov curposX,8                     
mov curposY,5
call  setcurspos
cmp Mode,0
jz B 
cmp Mode,1
jz D
   lea dx,msg17
 mov ah,09h                     ;interupt to print string
		int 21h
		jmp continue

B:
     lea dx,msg15
 mov ah,09h                     ;interupt to print string
		int 21h
		jmp continue
D:
    lea dx,msg16
 mov ah,09h                     ;interupt to print string
		int 21h
		jmp continue
continue:
ret
printmodeoperation endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;print operation number;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
printoperationnum proc 

call GetOpFromUser
cmp while,1
jz EXIT2


mov curposX,11
mov curposY,25
call  setcurspos

mov dl,UserOp

add dl,30h
mov ah,02
int 21h

EXIT2:

ret
printoperationnum endp





Code_segment_name   ends
                    end Main_prog
