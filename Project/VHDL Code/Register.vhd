Library ieee;
Use ieee.std_logic_1164.all;

-- 4 inputs:
-- 	1/ The clk
-- 	2/ the reset signal
-- 	3/ The input itself (d)
-- 	4/ The register enable
-- 1 output:
--	q
-- The logic is to :
-- if reset signal is sent then set the register value (output --> q) to zero
-- else if it's a rising edge of the clock and the regEnable is activated then take the input(d) and store it in the register (q)
-- else preserve the register value (q) as it is 

Entity Reg is Generic ( n : integer := 16);
	port( clk,reset, regEnable : in std_logic;
	d : in std_logic_vector(n-1 downto 0);
	q : out std_logic_vector(n-1 downto 0));
end Reg;
Architecture aRegister of Reg is
begin
	Process (Clk,reset)
	begin
		if reset = '1' then
			q <= (others=>'0');
		elsif (rising_edge(clk) and regEnable='1') then
			q <= d;
		end if;
	end process;
end aRegister;