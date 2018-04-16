library ieee;
use ieee.std_logic_1164.all;
USE IEEE.numeric_std.all;
use ieee.math_real.all;

entity decoder is
	generic (numOfSelectionLines: integer := 3);
	port (
		enable: in std_logic;
		input: in std_logic_vector(numOfSelectionLines-1 downto 0);
		output: out std_logic_vector(2**numOfSelectionLines-1 downto 0)
	);
end decoder;

architecture my_decoder of decoder is
	begin
	process (enable, input)
		begin
		output <= (others => '0');
		if(enable = '1') then
			output(to_integer(unsigned(input))) <= '1';
		end if;
	end process;
end architecture;