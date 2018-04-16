
LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY Memory IS 
	PORT ( 
		Clock: IN std_logic;
		Reset: IN std_logic;
		EM : IN  std_logic_vector(41 downto 0);
		MW_Register : OUT std_logic_vector(39 downto 0);
		Out_Port_output: OUT std_logic_vector(15 downto 0);
		Memory_output: OUT std_logic_vector(15 downto 0)
		);
END ENTITY Memory;

ARCHITECTURE  Memory_Arch OF Memory IS

	component ram is generic( addressBusWidth: integer := 16; memoryWordLength: integer := 16 );
		PORT(	clk : IN std_logic;
			writeEnable  : IN std_logic;
			address : IN  std_logic_vector(addressBusWidth - 1 DOWNTO 0);
			dataIn  : IN  std_logic_vector(memoryWordLength - 1 DOWNTO 0);
			dataOut : OUT std_logic_vector(memoryWordLength - 1 DOWNTO 0)
		);
	END component;


	component Reg is generic (n: integer := 16);
		PORT ( clk,reset,regEnable: IN std_logic;
			d: IN std_logic_vector(n-1 downto 0);
			q: OUT std_logic_vector(n-1 downto 0)
			);
	END component;


	SIGNAL MemoryWriteEnable : std_logic;
	SIGNAL Register_Input: std_logic_vector(39 downto 0);
	SIGNAL OutPortRegisterEnable: std_logic;
	SIGNAL MemoryDataOut: std_logic_vector(15 downto 0);

	
	
	BEGIN
		MemoryWriteEnable <= ( not(EM(3)) and ( EM(5) or (EM(1) and (not (EM(0)) ))));
		OutPortRegisterEnable <= not (EM(0)) and EM(4);
		R: ram generic map(10,16) PORT MAP (Clock,MemoryWriteEnable,EM(39 downto 30),EM(29 downto 14),MemoryDataOut);
		Register_Input <= EM(2) & EM(4) & EM(1) & EM(0) & EM(9 downto 6) & EM(29 downto 14) & MemoryDataOut;
		M_W_Reg: Reg generic map(40) PORT MAP (Clock,Reset,'1',Register_Input,MW_Register);
		Out_Port_Register: Reg generic map(16) PORT MAP (Clock,Reset,OutPortRegisterEnable,EM(29 downto 14),Out_Port_output);
		
		Memory_output <= MemoryDataOut;
		
END Memory_Arch;