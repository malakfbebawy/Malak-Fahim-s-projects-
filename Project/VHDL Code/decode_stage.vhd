library ieee;
use ieee.std_logic_1164.all;

entity Decode is
	port(
			--inputs
			sysClk, sysReset, WB_enable, EM_RETI, F_A0, F_A1, EM_stall, EM_IM: std_logic;
			FD_reg, Mem_ouput, WB_write_data, MW_Dst, EM_Dst, PC: in std_logic_vector(15 downto 0);
			Exc_CCR, WB_address, EM_up_flags: in std_logic_vector(3 downto 0);
			--outputs
			DE_reg: out std_logic_vector(105 downto 0);
			R8, R9, Dst: out std_logic_vector(15 downto 0);
			CCR: out std_logic_vector(3 downto 0);
			Flush: out std_logic
		);
end entity Decode;

architecture DecodeStage of Decode is
	component controlUnit is
		port(
			--inputs
			Op: in std_logic_vector(5 downto 0);
			CCR: in std_logic_vector(3 downto 0);
			DE_IM, DE_Flush: in std_logic;
			--outputs
			IM, INT, WB, M, CALL, SP_en, SP_fun, RETI, use_port, has_src1, has_src2, Flush: out std_logic;
			up_flags: out std_logic_vector(3 downto 0) -- up_zero, up_neg, up_carry, up_overflow
		);
	end component;
	component reg_file is
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
	end component;
	component my_nadder IS
       GENERIC (n : integer := 8);
		PORT(a,b : IN std_logic_vector(n-1  DOWNTO 0);
	             cin : IN std_logic;  
	             s : OUT std_logic_vector(n-1 DOWNTO 0);    
	             cout, overflow : OUT std_logic);
	END component;
	component mux2X1_1_bit is
		port(
			selectionLine, input1, input2: in std_logic;
			output : out std_logic
		);
	end component;
	component Reg is Generic ( n : integer := 16);
		port( clk,reset, regEnable : in std_logic;
		d : in std_logic_vector(n-1 downto 0);
		q : out std_logic_vector(n-1 downto 0));
	end component;
	component neg_FF is
		port( clk,reset, regEnable, d : in std_logic;
		q : out std_logic);
	end component;
	component FF is
		port( clk,reset, regEnable, d : in std_logic;
		q : out std_logic);
	end component;
	signal op: std_logic_vector(5 downto 0);
	signal CCR_reg, CCR_mux_output, DE_up_flags, temp_CCR, reg_file_CCR: std_logic_vector(3 downto 0);
	signal DE_reg_output, DE_input: std_logic_vector(105 downto 0);
	signal src1_add, src2_add: std_logic_vector(3 downto 0);
	signal src2_adder_input, reg_file_dst: std_logic_vector(15 downto 0);
	signal src2_adder_cin_temp, DE_reset, DE_enable, was_reset_en, neg_IM, neg_Stall, last_was_flush, neg_FLush, neg_last_was_flush: std_logic;
	signal was_reset_output, was_reset_input: std_logic_vector(0 downto 0);
	begin
	src1_add(3) <= '1' when DE_input(1) = '1' else '0';
	src1_add(2 downto 0) <= FD_reg(10 downto 8);
	src2_add <= '0' & FD_reg(7 downto 5);
	op <= FD_reg(15) & FD_reg(4 downto 0);
	DE_up_flags <= DE_reg_output(14 downto 11);
	src2_adder_cin_temp <= DE_input(5) and not DE_input(6);
	zero_mux: mux2X1_1_bit port map(DE_up_flags(0), CCR_reg(0), Exc_CCR(0), temp_CCR(0));
	negative_mux: mux2X1_1_bit port map(DE_up_flags(1), CCR_reg(1), Exc_CCR(1), temp_CCR(1));
	carry_mux: mux2X1_1_bit port map(DE_up_flags(2), CCR_reg(2), Exc_CCR(2), temp_CCR(2));
	
	src2_adder: my_nadder generic map(16) port map(src2_adder_input, (others => '0'), src2_adder_cin_temp, DE_input(62 downto 47), open, open);
	my_control_unit: controlUnit port map(op, temp_CCR, DE_reg_output(0), DE_reg_output(85), DE_input(0), DE_input(1), DE_input(2), DE_input(3), DE_input(4), DE_input(5), DE_input(6), DE_input(7), DE_input(8), DE_input(9), DE_input(10), DE_input(85), DE_input(14 downto 11));
	Flush <= DE_input(85);
	CCR <= CCR_reg;
	my_reg_file: reg_file generic map (16) port map(sysClk, sysReset, FD_reg(14 downto 11), src1_add, src2_add, WB_address, WB_enable, DE_input(1), WB_write_data, Mem_ouput(13 downto 10), Exc_CCR, EM_RETI, DE_reg_output(7), EM_up_flags, DE_reg_output(14 downto 11), DE_input(5), DE_input(6), F_A0, F_A1, MW_Dst, EM_Dst, PC, reg_file_dst, DE_input(46 downto 31), src2_adder_input, R8, R9, reg_file_CCR);
	DE_input(30 downto 15) <= reg_file_dst;
	DE_input(84 downto 63) <= reg_file_CCR & src2_add & src1_add & FD_reg(14 downto 11) & op;
	DE_input(105 downto 86) <= FD_reg(13 downto 11) & FD_reg(7 downto 1) & FD_reg(10 downto 1);
	Dst <= reg_file_dst;
	was_reset_input(0) <= sysReset;
	was_reset_en <= sysReset or was_reset_output(0);
	was_reset: Reg generic map(1) port map(sysClk, '0', was_reset_en, was_reset_input, was_reset_output);
	IM_FF: neg_FF port map(sysClk, sysReset, '1', EM_IM, neg_IM);
	stall_FF: neg_FF port map(sysClk, sysReset, '1', EM_stall, neg_Stall);
	DE_reset <= '1' when (sysReset='1' or ((EM_stall='1' and neg_Stall='0') or (EM_IM='1' and neg_IM = '0')))  else '0';
	DE_enable <= '0' when was_reset_output(0) = '1' else '1';
	DE_register: Reg generic map(106) port map (sysClk, DE_reset, DE_enable, DE_input, DE_reg_output);
	DE_reg <= DE_reg_output;

end DecodeStage;