Library ieee;
Use ieee.std_logic_1164.all;

Entity Fetch is
	port( clk,reset,DE_flush,stall,INT : in std_logic;
		PC : in std_logic_vector(15 downto 0);
		FD : out std_logic_vector(15 downto 0)
		);
end Fetch;
Architecture logic of Fetch is
signal RAM_DataOut: std_logic_vector(15 downto 0);
signal mux_out : std_logic_vector(15 downto 0);
signal reg_en,reg_rst, neg_last_was_flush: std_logic;
begin
	neg_last_was_flush_FF: Entity work.neg_FF port map(clk, reset, '1', DE_flush, neg_last_was_flush);
	Ram : Entity work.ram generic map(16,16) port map(clk,'0',PC,"0000000000000000",RAM_DataOut);
	MUX: Entity work.mux2X1 generic map (16) port map(INT, RAM_DataOut,"1000001011000111",mux_out); --1000001011000111=82C7
	reg_en <= '0' when stall = '1' else '1';
	reg_rst <= '1' when (DE_flush = '1' and neg_last_was_flush = '0') or reset = '1' else '0';
	FD_REG : Entity work.Reg generic map(16) port map(clk,reg_rst,reg_en, mux_out,FD);
end logic;