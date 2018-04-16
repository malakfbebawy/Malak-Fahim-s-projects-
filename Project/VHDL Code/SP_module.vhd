library ieee;
use ieee.std_logic_1164.all;

entity SP_module is 
	generic (register_size: integer := 16);
	port(
			--inputs
			Clk, Reset, DEC_SP_en, DEC_SP_fun, F_A0, F_A1, MW_WB: in std_logic;
			MW_Dst, EM_Dst, WB_write_data: in std_logic_vector(register_size-1 downto 0);
			--outputs
			SP_reg: out std_logic_vector(register_size-1 downto 0)
		);
end entity SP_module;
architecture my_SP_module of SP_module is
	component SP_Alu IS generic (register_size: integer := 16);
		PORT( 
			input : IN Std_logic_vector(register_size-1 downto 0);
			op, enable : IN std_logic;
			output : OUT Std_logic_vector(register_size-1 downto 0)
		);
	END component;
	component Reg is Generic ( n : integer := 16);
		port( clk,reset, regEnable : in std_logic;
		d : in std_logic_vector(n-1 downto 0);
		q : out std_logic_vector(n-1 downto 0));
	end component;
	component mux4X1 is generic (n : positive := 16);
		port(
			selectionLine: in std_logic_vector(1 downto 0);
			input1, input2, input3, input4 : in std_logic_vector(n-1 downto 0);
			output : out std_logic_vector(n-1 downto 0)
		);
	end component;
	signal SP_mux_output, SP_input, SP_re_output: Std_logic_vector(register_size-1 downto 0);
	signal SP_mux_selection_lines: std_logic_vector(1 downto 0);
	signal SP_enable: std_logic;
	begin
	SP_mux_selection_lines(0) <= F_A0 and DEC_SP_en;
	SP_mux_selection_lines(1) <= F_A1 and DEC_SP_en;
	SP_enable <= DEC_SP_en or MW_WB;
	SP_mux: mux4X1 generic map(register_size) port map (SP_mux_selection_lines, SP_re_output, MW_Dst, EM_Dst, EM_Dst, SP_mux_output);
	my_SP_Alu: SP_Alu generic map (register_size) port map(SP_mux_output, DEC_SP_fun, DEC_SP_en, SP_input);
	SP_Register: Reg generic map(register_size) port map(Clk, Reset, SP_enable, SP_input, SP_re_output);
	SP_reg <= SP_re_output;
end my_SP_module;