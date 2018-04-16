LIBRARY IEEE;
USE IEEE.std_logic_1164.all;

ENTITY ALU IS generic (n: integer := 16);
	PORT( a,b : IN Std_logic_vector(n-1 downto 0);
		op : IN std_logic_vector (5 DOWNTO 0);
        carryIn : IN std_logic;  
		carryOut, overflowFlag : OUT std_logic;
		output : OUT Std_logic_vector(n-1 downto 0)
	);
END ENTITY ALU;

ARCHITECTURE struct OF ALU IS
	COMPONENT my_nadder IS
		GENERIC (n : integer := 8);
		PORT(a,b : IN std_logic_vector(n-1  DOWNTO 0);
             		cin : IN std_logic;  
            		s : OUT std_logic_vector(n-1 DOWNTO 0);    
              		cout, overflow : OUT std_logic);
	END COMPONENT;
signal tempout, tempin1, tempin2 : std_logic_vector(n-1 downto 0);
signal cintemp,couttemp, aluOverflowFlag : std_logic;

BEGIN
tempin1 <= not a when (op(5 downto 1) = "10100") -- for NEG, NOT
		else a; -- for ADD, SUB, INC, DEC, LDM
tempin2 <= (others => '1') when (op = "101011") -- for DEC
	  else b when (op = "100000") -- for ADD
	  else not b when (op = "100001") -- for SUB
	  else (others => '0'); -- for NEG, NOT, INC

cintemp <= '1' when (op = "100001" or op = "101001" or op = "101010") -- for SUB, NEG, INC
	    else '0'; --for ADD, DEC
u0: my_nadder GENERIC MAP (16) PORT MAP (tempin1,tempin2,cintemp,tempout,couttemp, aluOverflowFlag);

output <= tempout when (op(5 downto 1) = "10000" or op(5 downto 2) = "1010") -- ADD, SUB, NOT, NEG, INC, DEC
	else a and b when (op = "100010") --AND
	else a or b when (op = "100011") --OR
	else a(14 downto 0) & carryIn when op = "101100" --RLC
	else carryIn & a(15 downto 1) when op = "101101" --RRC
	else a(14 downto 0) & '0' when op = "110001" --SHL
	else '0' & a(15 downto 1) when op = "110010" --SHR
	else a; --default

carryOut <= couttemp when (op(5 downto 1) = "10000" or op(5 downto 2) = "1010") -- ADD, SUB, NOT, NEG, INC, DEC
	else a(0) when (op = "101101") -- RRC
	else a(n-1) when (op = "101100") -- RLC
	else '1' when (op = "111101") -- SETC
	else '0'; -- deafult
overflowFlag <= aluOverflowFlag when (op(5 downto 1) = "10000" or op(5 downto 2) = "1010") -- ADD, SUB, NOT, NEG, INC, DEC
	else '0';
END struct;