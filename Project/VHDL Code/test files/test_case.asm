LDM R0, 0				#Line 0
0
LDM R1, 18
19
LDM R2, 21
22
LDM R4, 12
12
LDM R5, 14
14
STD R0, 3				#Set M[3] by 0
IN R3					#Get Input
AND R3, R3, R3
JN R1					#If negative JMP Line_21
LDD R0, 3				#else
ADD R0, R0, R3			#Add input to M[3]
STD R0, 3
JMP R4					#Jump to get input Line_12
CALL R2					#Call abs Line_25
JMP R5					#jump to Add to M[3] Line_15
STD R4, 3				#Set M[3] by 1
NEG R3					#Negate the Input
RET						#Return
STD R2, 3				#Set M[3] by 1