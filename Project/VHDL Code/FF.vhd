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

Entity neg_FF is
	port( clk,reset, regEnable, d : in std_logic;
	q : out std_logic);
end neg_FF;
Architecture a_negFF of neg_FF is
begin
	Process (Clk,reset)
	begin
		if reset = '1' then
			q <= '0';
		elsif (falling_edge(clk) and regEnable='1') then
			q <= d;
		end if;
	end process;
end a_negFF;

------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------
Library ieee;
Use ieee.std_logic_1164.all;

Entity FF is
	port( clk,reset, regEnable, d : in std_logic;
	q : out std_logic);
end FF;

Architecture aFF of FF is
begin
	Process (Clk,reset)
	begin
		if reset = '1' then
			q <= '0';
		elsif (rising_edge(clk) and regEnable='1') then
			q <= d;
		end if;
	end process;
end aFF;
