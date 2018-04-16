Library ieee;
Use ieee.std_logic_1164.all;
--0 e 7aga zayy ma hya

Entity Expander is
	port( input : in std_logic_vector(15 downto 0);
		output :out std_logic_vector(15 downto 0);
		s : in std_logic
		);
end Expander;
Architecture logic of Expander is
begin
	Process (s,input)
	begin
		if s = '0' then
			output <= input;			
		else
			output <= "000000"& input(9 downto 0);
		end if;		
	end process;
end logic;