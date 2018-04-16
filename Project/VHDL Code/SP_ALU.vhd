LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY SP_Alu IS generic (register_size: integer := 16);
	PORT( 
		input : IN Std_logic_vector(register_size-1 downto 0);
		op, enable : IN std_logic;
		output : OUT Std_logic_vector(register_size-1 downto 0)
	);
END ENTITY SP_Alu;

ARCHITECTURE my_SP_Alu OF SP_Alu IS
	COMPONENT my_nadder IS
		GENERIC (n : integer := 8);
		PORT(a,b : IN std_logic_vector(n-1  DOWNTO 0);
             		cin : IN std_logic;  
            		s : OUT std_logic_vector(n-1 DOWNTO 0);    
              		cout, overflow : OUT std_logic);
	END COMPONENT;
signal tempout, tempin2 : std_logic_vector(register_size-1 downto 0);
signal cintemp,couttemp, aluOverflowFlag : std_logic;

BEGIN
cintemp <= not op;
tempin2 <= (others => op);
u0: my_nadder GENERIC MAP (register_size) PORT MAP (input,tempin2,cintemp,tempout,couttemp, aluOverflowFlag);
output <= tempout when enable = '1'
	else input; --default
END my_SP_Alu;