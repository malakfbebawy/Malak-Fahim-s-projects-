Data_segment_name  segment  para 
MyAtt db 07h
YourAtt db 0F0h
MyRow db 0
MyColumn db 0
YourRow db 13
YourColumn db 0
char db ?

Data_segment_name ends

Stack_segment_name segment para stack
      dw 16 dup(0)               ;define your stack segment
Stack_segment_name ends

Code_segment_name  segment 
    Main_prog  proc far
        assume SS:Stack_segment_name,CS:Code_segment_name,DS:Data_segment_name

	mov AX,Data_segment_name         ; load the starting address of the data
        mov DS,AX                        ; segment into DS reg.
	
	call initVga
	
	call initSer
	
	whi:	
	call send
	call recieve
	jmp whi

        mov ax,4c00h                     ; exit program
        int 21h

    Main_prog      endp




   initVga proc near
	; set cursor at (0,0) position
	mov dh,0
	mov dl,0
	mov bh,0
	mov ah,02h
	int 10h	

	; set MyAtt in the upper place
	mov al,20h
	mov bl,MyAtt
	mov bh,0
	mov cx,80*12
	mov ah,09h
	int 10h
	


	; set cursor at (0,0) position
	mov dh,13
	mov dl,0
	mov bh,0
	mov ah,02h
	int 10h	

	; set YourAtt in the bottom place
	mov al,20h
	mov bl,YourAtt
	mov bh,0
	mov cx,80*13
	mov ah,09h
	int 10h
	ret 
    initVga endp

    initSer proc near
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





    send proc near
	
	mov ah,01h
	int 16h
	jz notAvail
	mov ah,0h
	int 16h
	;now al have the charcter
	mov char,al
	mov dx,03fdh;
	s:
	in al,dx
 	test al,00100000b
	jz s
	mov al,char
	mov dx,03f8h
	out dx,al

	; now set current position to my cursor
	call setMyCursor
	;checking
	cmp char,27
	jnz notescape 
	mov ax,4c00h
	int 21h
	notescape: 
	cmp char,0dh
	jnz notenter
	call enterPressed
	jmp notAvail
	notenter:
	call printchar	
	notAvail :

	ret
    send endp



    setMyCursor proc near
	inc MyColumn
	cmp MyColumn,79
	jbe norm
	mov myColumn,0
	inc myRow
	cmp myRow,12 
	jbe norm
	call Scrollme
	mov myRow,12
	norm :
	;after checking now set the positions
	
	mov dh,MyRow
	mov dl,MyColumn
	mov bh,0
	mov ah,02h
	int 10h
	
	ret
    setMyCursor endp




    Scrollme proc near
	mov ah,6       ; function 6
	mov al,1        ; scroll by 1 line    
	mov bh,MyAtt       ; normal video attribute         
	mov ch,0       ; upper left Y
	mov cl,0        ; upper left X
	mov dh,12     ; lower right Y
	mov dl,79      ; lower right X 
	int 10h           

	ret
    Scrollme endp


ScrollYou proc near
	mov ah,6       ; function 6
	mov al,1        ; scroll by 1 line    
	mov bh,YourAtt       ; normal video attribute         
	mov ch,13       ; upper left Y
	mov cl,0      ; upper left X
	mov dh,24     ; lower right Y
	mov dl,79      ; lower right X 
	int 10h           

	ret
    ScrollYou endp



    printchar proc near
	mov al,char
	mov bh,0
	mov bl,myAtt
	mov cx,1
	mov ah,09h
	int 10h
	ret
    printchar endp

printYourchar proc near
	mov al,char
	mov bh,0
	mov bl,YourAtt
	mov cx,1
	mov ah,09h
	int 10h
	ret
    printYourchar endp




    enterPressed proc near
	inc MyRow
	mov MyColumn,0
	cmp MyRow,12
	jbe SAFE
	call Scrollme
	mov MyRow,12 
	SAFE:
		;after checking now set the positions	
	mov dh,MyRow
	mov dl,MyColumn
	mov bh,0
	mov ah,02h
	int 10h
	
	ret
    enterPressed endp

	YourenterPressed proc near
	inc YourRow
	mov YourColumn,0
	cmp YourRow,24
	jbe SAFE
	call ScrollYou
	mov YourRow,24 
	SAFE:
		;after checking now set the positions	
	mov dh,YourRow
	mov dl,YourColumn
	mov bh,0
	mov ah,02h
	int 10h
	
	ret
    YourenterPressed endp




    recieve proc near
	mov dx,03fdh
	in al,dx
	test al,1 ; data recieved or not
	jz nodata
	mov dx,03f8h
	in char,dx
	call setYourCursor
	
	;checking
	cmp char,27
	jnz notescape 
	mov ax,4c00h
	int 21h
	notescape: 
	cmp char,0dh
	jnz notenter
	call YourenterPressed
	jmp nodata
	notenter:
	call printYourchar	
	
	
	
	nodata:
	ret
    recieve endp
 



    setYourCursor proc near

	inc YourColumn
	cmp YourColumn,79
	jbe norm
	mov YourColumn,0
	inc YourRow
	cmp YourRow,24 
	jbe norm
	call Scrollyou
	mov YourRow,24
	norm :
	;after checking now set the positions
	
	mov dh,YourRow
	mov dl,YourColumn
	mov bh,0
	mov ah,02h
	int 10h
	
	ret
    setYourCursor endp

   

Code_segment_name   ends
                    end Main_prog
