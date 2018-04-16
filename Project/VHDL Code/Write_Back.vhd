
LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY WriteBack IS 
	PORT ( 
		Clock: IN std_logic;
		Reset: IN std_logic;
		IN_Port_Data: IN  std_logic_vector(15 downto 0);
		MW_Register : IN std_logic_vector(39 downto 0);
		Dst_No: OUT std_logic_vector(3 downto 0);
		Write_Data: OUT std_logic_vector(15 downto 0)
		);
END ENTITY WriteBack;

ARCHITECTURE  WriteBack_Arch OF WriteBack IS

	component mux4X1 is generic (n : positive := 16);
		port(
			SelectionLine: in std_logic_vector(1 downto 0);
			input1, input2, input3, input4 : in std_logic_vector(n-1 downto 0);
			output : out std_logic_vector(n-1 downto 0)
		);
	end component;


	SIGNAL SelectionLine : std_logic_vector(1 downto 0);
	SIGNAL ALU_Output : std_logic_vector(15 downto 0);
	SIGNAL Memory_Output: std_logic_vector(15 downto 0);

	
	
	BEGIN
		SelectionLine <= (MW_Register(38) and MW_Register(36)) & (not(MW_Register(39)) and MW_Register(37));
		ALU_Output <= MW_Register(31 downto 16);
		Memory_Output <= MW_Register(15 downto 0);
		Multiplexer: mux4X1 generic map(16) PORT MAP (SelectionLine,ALU_Output,Memory_Output,IN_Port_Data,IN_Port_Data,Write_Data);
		Dst_No <= MW_Register(35 downto 32);
		
END WriteBack_Arch;