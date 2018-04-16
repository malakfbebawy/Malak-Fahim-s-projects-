library ieee;
use ieee.std_logic_1164.all;

-- 2X1 MUX (1 selection line), each input and the output are of size n bits --
entity mux2X1 is generic (n : positive := 16);
	port(
		selectionLine: in std_logic;
		input1, input2 : in std_logic_vector(n-1 downto 0);
		output : out std_logic_vector(n-1 downto 0)
	);
end mux2X1;

architecture my_mux2X1 of mux2X1 is
	begin
		process(selectionLine, input1, input2)
			begin
				if(selectionLine = '1') then
					output <= input2;
				else
					output <= input1;
				end if;
		end process;
end architecture;


-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
library ieee;
use ieee.std_logic_1164.all;

-- 4X1 MUX (2 selection line), each input and the output are of size n bits --
entity mux4X1 is generic (n : positive := 16);
	port(
		selectionLine: in std_logic_vector(1 downto 0);
		input1, input2, input3, input4 : in std_logic_vector(n-1 downto 0);
		output : out std_logic_vector(n-1 downto 0)
	);
end mux4X1;

architecture my_mux4X1 of mux4X1 is
	begin
		process(selectionLine, input1, input2, input3, input4)
			begin
				if(selectionLine = "11") then
					output <= input4;
				elsif(selectionLine = "01") then
					output <= input2;
				elsif(selectionLine = "10") then
					output <= input3;
				else
					output <= input1;
				end if;
		end process;
end architecture;

-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
-- 8X1 MUX (3 selection line), each input and the output are of size n bits --
library ieee;
Use ieee.std_logic_1164.all;

ENTITY mux_8_1 IS
	generic (n : integer := 16);
	PORT (a,b,c,d,e,f,g,h :IN std_logic_vector(n-1 downto 0);
    	s0,s1,s2 :IN std_logic;
    	s : out std_logic_vector(n-1 downto 0));
END ENTITY mux_8_1;

ARCHITECTURE a_mux OF mux_8_1 IS
  BEGIN
    s <= h WHEN s0 = '1' AND s1 ='1' AND s2='1'
     ELSE b WHEN s0 = '1' AND s1 ='0'  AND s2='0'
     ELSE c WHEN s0 = '0' AND s1 ='1' AND s2='0'
     ELSE d WHEN s0 = '1' AND s1 ='1' AND s2='0'
     ELSE e WHEN s0 = '0' AND s1 ='0' AND s2='1'
     ELSE f WHEN s0 = '1' AND s1 ='0' AND s2='1'
     ELSE g WHEN s0 = '0' AND s1 ='1' AND s2='1'
     ELSE a;
END a_mux;

-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
-- 16X1 MUX (4 selection line), each input and the output are of size n bits --
library ieee;
use ieee.std_logic_1164.all;

entity mux16X1 is generic (n : positive := 16);
	port(
		selectionLine: in std_logic_vector(3 downto 0);
		a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16: in std_logic_vector(n-1 downto 0);
		output : out std_logic_vector(n-1 downto 0)
	);
end mux16X1;

architecture my_mux16X1 of mux16X1 is
	begin
		process(selectionLine, a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16)
			begin
				if(selectionLine = "1111") then
					output <= a16;
				elsif(selectionLine = "0001") then
					output <= a2;
				elsif(selectionLine = "0010") then
					output <= a3;
				elsif(selectionLine = "0011") then
					output <= a4;
				elsif(selectionLine = "0100") then
					output <= a5;
				elsif(selectionLine = "0101") then
					output <= a6;
				elsif(selectionLine = "0110") then
					output <= a7;
				elsif(selectionLine = "0111") then
					output <= a8;
				elsif(selectionLine = "1000") then
					output <= a9;
				elsif(selectionLine = "1001") then
					output <= a10;
				elsif(selectionLine = "1010") then
					output <= a11;
				elsif(selectionLine = "1011") then
					output <= a12;
				elsif(selectionLine = "1100") then
					output <= a13;
				elsif(selectionLine = "1101") then
					output <= a14;
				elsif(selectionLine = "1110") then
					output <= a15;
				else
					output <= a1;
				end if;
		end process;
end architecture;
-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
-- 1_bit 4X1 MUX (2 selection line), each input and the output are of size 1 bit --
library ieee;
use ieee.std_logic_1164.all;

entity mux4X1_1_bit is
	port(
		selectionLine: in std_logic_vector(1 downto 0);
		input1, input2, input3, input4 : in std_logic;
		output : out std_logic
	);
end mux4X1_1_bit;

architecture my_mux4X1_1_bit of mux4X1_1_bit is
	begin
		process(selectionLine, input1, input2, input3, input4)
			begin
				if(selectionLine = "11") then
					output <= input4;
				elsif(selectionLine = "01") then
					output <= input2;
				elsif(selectionLine = "10") then
					output <= input3;
				else
					output <= input1;
				end if;
		end process;
end architecture;

-------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------
-- 1_bit 4X1 MUX (2 selection line), each input and the output are of size 1 bit --
library ieee;
use ieee.std_logic_1164.all;

entity mux2X1_1_bit is
	port(
		selectionLine, input1, input2: in std_logic;
		output : out std_logic
	);
end mux2X1_1_bit;

architecture my_mux2X1_1_bit of mux2X1_1_bit is
	begin
		process(selectionLine, input1, input2)
			begin
				if(selectionLine = '1') then
					output <= input2;
				else
					output <= input1;
				end if;
		end process;
end architecture;