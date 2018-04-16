library ieee;
use ieee.std_logic_1164.all;

entity reg_file is
	generic (register_size: integer := 16);
	port(
			Clk, Reset: in std_logic;
			--inputs for ordinary registers
			dst_address, src1_address, src2_address, write_address: in std_logic_vector(3 downto 0);
			write_enable, INT_signal: in std_logic;
			write_data: in std_logic_vector(register_size-1 downto 0);
			-- inputs for CCR
			memory_CCR, ALU_CCR: in std_logic_vector(3 downto 0);
			EM_RETI, DE_RETI: in std_logic;
			EM_up_flags, DE_up_flags: in std_logic_vector(3 downto 0);
			--inputs for SP
			DEC_SP_en, DEC_SP_fun, F_A0, F_A1: in std_logic;
			MW_Dst, EM_Dst, PC: in std_logic_vector(register_size-1 downto 0);
			--outputs
			dst, src1, src2, R8, R9: out std_logic_vector(register_size-1 downto 0);
			CCR: out std_logic_vector(3 downto 0)
		);
end entity reg_file;

architecture my_reg_file of reg_file is
	component SP_module is generic (register_size: integer := 16);
		port(
				--inputs
				Clk, Reset, DEC_SP_en, DEC_SP_fun, F_A0, F_A1, MW_WB: in std_logic;
				MW_Dst, EM_Dst, WB_write_data: in std_logic_vector(register_size-1 downto 0);
				--outputs
				SP_reg: out std_logic_vector(register_size-1 downto 0)
			);
	end component SP_module;
	component mux16X1 is generic (n : positive := 16);
		port(
			selectionLine: in std_logic_vector(3 downto 0);
			a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16: in std_logic_vector(n-1 downto 0);
			output : out std_logic_vector(n-1 downto 0)
		);
	end component;
	component Reg is Generic ( n : integer := 16);
		port( clk,reset, regEnable : in std_logic;
		d : in std_logic_vector(n-1 downto 0);
		q : out std_logic_vector(n-1 downto 0));
	end component;
	component decoder is generic (numOfSelectionLines: integer := 3);
		port (
			enable: in std_logic;
			input: in std_logic_vector(numOfSelectionLines-1 downto 0);
			output: out std_logic_vector(2**numOfSelectionLines-1 downto 0)
		);
	end component;
	component mux4X1_1_bit is
		port(
			selectionLine: in std_logic_vector(1 downto 0);
			input1, input2, input3, input4 : in std_logic;
			output : out std_logic
		);
	end component;
	component mux2X1 is generic (n : positive := 16);
		port(
			selectionLine: in std_logic;
			input1, input2 : in std_logic_vector(n-1 downto 0);
			output : out std_logic_vector(n-1 downto 0)
		);
	end component;
	signal R0, R1, R2, R3, R4, R5, R6, R8_temp, R9_temp, R10, src1_temp, src2_temp, dst_temp: std_logic_vector(register_size-1 downto 0);
	signal registers_enables: std_logic_vector(15 downto 0);
	signal temp_CCR_input, temp_CCR_output: std_logic_vector(3 downto 0);
	signal zero_enable, negative_enable, carry_enable, overflow_enable, src1_out_mux_selectionline, src2_out_mux_selectionline, dst_out_mux_selectionline: std_logic;
	signal zero_selection_lines, neg_selection_lines, carry_selection_lines, overflow_selection_lines: std_logic_vector(1 downto 0);
	begin
	Reg0: Reg generic map(register_size) port map(Clk, Reset, registers_enables(0), write_data, R0);
	Reg1: Reg generic map(register_size) port map(Clk, Reset, registers_enables(1), write_data, R1);
	Reg2: Reg generic map(register_size) port map(Clk, Reset, registers_enables(2), write_data, R2);
	Reg3: Reg generic map(register_size) port map(Clk, Reset, registers_enables(3), write_data, R3);
	Reg4: Reg generic map(register_size) port map(Clk, Reset, registers_enables(4), write_data, R4);
	Reg5: Reg generic map(register_size) port map(Clk, Reset, registers_enables(5), write_data, R5);
	Reg8: Reg generic map(register_size) port map(Clk, Reset, registers_enables(8), write_data, R8_temp);
	Reg9: Reg generic map(register_size) port map(Clk, Reset, registers_enables(9), write_data, R9_temp);
	Reg10: Reg generic map(register_size) port map(Clk, Reset, INT_signal, write_data, R10);

	zero_enable <= '1' when ((DE_up_flags(0) = '1' and DE_RETI = '0') or (EM_up_flags(0) = '1' and EM_RETI = '1'))
			else '0';
	zero_selection_lines <= zero_enable & EM_RETI;
	zero_mux: mux4X1_1_bit port map(zero_selection_lines, temp_CCR_output(0), temp_CCR_output(0), ALU_CCR(0), memory_CCR(0), temp_CCR_input(0));

	negative_enable <= '1' when (DE_up_flags(1) = '1' and DE_RETI = '0') or (EM_up_flags(1) = '1' and EM_RETI = '1')
			else '0';
	neg_selection_lines <= negative_enable & EM_RETI;
	negative_mux: mux4X1_1_bit port map(neg_selection_lines, temp_CCR_output(1), temp_CCR_output(1), ALU_CCR(1), memory_CCR(1), temp_CCR_input(1));

	carry_enable <= '1' when (DE_up_flags(2) = '1' and DE_RETI = '0') or (EM_up_flags(2) = '1' and EM_RETI = '1')
			else '0';
	carry_selection_lines <= carry_enable & EM_RETI;
	carry_mux: mux4X1_1_bit port map(carry_selection_lines, temp_CCR_output(2), temp_CCR_output(2), ALU_CCR(2), memory_CCR(2), temp_CCR_input(2));

	overflow_enable <= '1' when (DE_up_flags(3) = '1' and DE_RETI = '0') or (EM_up_flags(3) = '1' and EM_RETI = '1')
			else '0';
	overflow_selection_lines <= overflow_enable & EM_RETI;
	overflow_mux: mux4X1_1_bit port map(overflow_selection_lines, temp_CCR_output(3), temp_CCR_output(3), ALU_CCR(3), memory_CCR(3), temp_CCR_input(3));

	CC_Reg: Reg generic map(4) port map(Clk, Reset, '1', temp_CCR_input, temp_CCR_output);
	my_SP_module: SP_module generic map(register_size) port map(Clk, Reset, DEC_SP_en, DEC_SP_fun, F_A0, F_A1, registers_enables(6), MW_Dst, EM_Dst, write_data, R6);
	src1_mux: mux16X1 generic map(register_size) port map(src1_address, R0, R1, R2, R3, R4, R5, R6, PC, R8_temp, R9_temp, R10, R10, R10, R10, R10, R10, src1_temp);
	src1_out_mux_selectionline <= '1' when (registers_enables(0) = '1' and src1_address = "0000") or (registers_enables(1) = '1' and src1_address = "0001") or (registers_enables(2) = '1' and src1_address = "0010") or (registers_enables(3) = '1' and src1_address = "0011") or (registers_enables(4) = '1' and src1_address = "0100") or (registers_enables(5) = '1' and src1_address = "0101") or (registers_enables(6) = '1' and src1_address = "0110") or (registers_enables(7) = '1' and src1_address = "0111")
								else '0';
	src1_out_mux: mux2X1 generic map(16) port map(src1_out_mux_selectionline, src1_temp, write_data, src1);
	src2_mux: mux16X1 generic map(register_size) port map(src2_address, R0, R1, R2, R3, R4, R5, R6, PC, R8_temp, R9_temp, R10, R10, R10, R10, R10, R10, src2_temp);
	src2_out_mux_selectionline <= '1' when (registers_enables(0) = '1' and src2_address = "0000") or (registers_enables(1) = '1' and src2_address = "0001") or (registers_enables(2) = '1' and src2_address = "0010") or (registers_enables(3) = '1' and src2_address = "0011") or (registers_enables(4) = '1' and src2_address = "0100") or (registers_enables(5) = '1' and src2_address = "0101") or (registers_enables(6) = '1' and src2_address = "0110") or (registers_enables(7) = '1' and src2_address = "0111")
								else '0';
	src2_out_mux: mux2X1 generic map(16) port map(src2_out_mux_selectionline, src2_temp, write_data, src2);
	dst_mux: mux16X1 generic map(register_size) port map(dst_address, R0, R1, R2, R3, R4, R5, R6, PC, R8_temp, R9_temp, R10, R10, R10, R10, R10, R10, dst_temp);
	dst_out_mux_selectionline <= '1' when (registers_enables(0) = '1' and dst_address = "0000") or (registers_enables(1) = '1' and dst_address = "0001") or (registers_enables(2) = '1' and dst_address = "0010") or (registers_enables(3) = '1' and dst_address = "0011") or (registers_enables(4) = '1' and dst_address = "0100") or (registers_enables(5) = '1' and dst_address = "0101") or (registers_enables(6) = '1' and dst_address = "0110") or (registers_enables(7) = '1' and dst_address = "0111")
								else '0';
	dst_out_mux: mux2X1 generic map(16) port map(dst_out_mux_selectionline, dst_temp, write_data, dst);
	output_decoder: decoder generic map(4) port map(write_enable, write_address, registers_enables);
	R8 <= R8_temp;
	R9 <= R9_temp;
	CCR <= temp_CCR_output;
end my_reg_file;