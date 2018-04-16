LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE IEEE.numeric_std.all;
use ieee.math_real.all;

ENTITY ram IS
	generic(
		addressBusWidth: integer := 16;
		memoryWordLength: integer := 16
	);
	PORT(
		clk : IN std_logic;
		writeEnable  : IN std_logic;
		address : IN  std_logic_vector(addressBusWidth - 1 DOWNTO 0);
		dataIn  : IN  std_logic_vector(memoryWordLength - 1 DOWNTO 0);
		dataOut : OUT std_logic_vector(memoryWordLength - 1 DOWNTO 0));
END ENTITY ram;

ARCHITECTURE sync_ram_a OF ram IS

	TYPE ram_type IS ARRAY(0 TO 2**addressBusWidth - 1) OF std_logic_vector(memoryWordLength - 1 DOWNTO 0);
	SIGNAL ram : ram_type ;	
	BEGIN
		PROCESS(clk) IS
			BEGIN
				IF falling_edge(clk) and writeEnable = '1'   THEN
					ram(to_integer(unsigned(address))) <= dataIn;
				END IF;
		END PROCESS;
		dataOut <= ram(to_integer(unsigned(address)));
END sync_ram_a;
