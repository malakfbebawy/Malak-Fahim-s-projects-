library ieee;
use ieee.std_logic_1164.all;

entity system is
	port(
		--inputs
		sysClk, sysReset, INT, RESET: in std_logic;
		IN_Port: in std_logic_vector(15 downto 0);
		--output
		OUT_Port: out std_logic_vector(15 downto 0)
		);
end entity system;

architecture my_system of system is
	component PC_Moudule is
		port(  --inputs
			clk , rst, stall , E_M_reti , Flush , INT ,F_PC0 , F_PC1 , RESET : in std_logic;
			WB_writeBack , R8 , R9 ,REG_FILE_dest_out , E_M_dest , ALU_output: in std_logic_vector(15 downto 0);
			M_W_DestNo : in std_logic_vector (3 downto 0) ;
			MEM_output : in std_logic_vector (15 downto 0);		
			--output 
			PC :out std_logic_vector(15 downto 0)
		);
	end component;
	component Fetch is
		port( clk,reset,DE_flush,stall,INT : in std_logic;
			PC : in std_logic_vector(15 downto 0);
			FD : out std_logic_vector(15 downto 0)
			);
	end component;
	component Decode is
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
	end component;
	component execution is
		port(
			--inputs
			clk, rst : IN std_logic;
			dec_exec_reg: in std_logic_vector(105 downto 0);
			mem_wb_mem_output: in std_logic_vector(15 downto 0);
			exec_mem_ALU_output: in std_logic_vector(15 downto 0);
			in_port: in std_logic_vector(15 downto 0);
			fetch_dec_reg_output: in std_logic_vector(15 downto 0);
			F_A0,F_A1,F_B0,F_B1, hazard_stall: in std_logic;
			--outputs
			exec_mem_output_module: out std_logic_vector(41 downto 0);
			ALU_output_module: out std_logic_vector(15 downto 0);
			Exec_CCR: out std_logic_vector(3 downto 0)
		);
	end component;
	component Memory IS 
		PORT (
			Clock: IN std_logic;
			Reset: IN std_logic;
			EM : IN  std_logic_vector(41 downto 0);
			MW_Register : OUT std_logic_vector(39 downto 0);
			Out_Port_output: OUT std_logic_vector(15 downto 0);
			Memory_output: OUT std_logic_vector(15 downto 0)
			);
	END component Memory;
	component WriteBack IS 
		PORT (
			Clock: IN std_logic;
			Reset: IN std_logic;
			IN_Port_Data: IN  std_logic_vector(15 downto 0);
			MW_Register : IN std_logic_vector(39 downto 0);
			Dst_No: OUT std_logic_vector(3 downto 0);
			Write_Data: OUT std_logic_vector(15 downto 0)
			);
	END component;
	component forword_unit is
		port(
			--inputs
			
			F_D_dst_no, D_E_dst_no, D_E_src1_no, D_E_src2_no, E_M_dst_no, M_W_dst_no: in std_logic_vector(3 downto 0);
			D_E_wb, D_E_has_src1, D_E_has_src2, D_E_IM, E_M_wb, E_M_m, E_M_useport, M_W_wB, M_W_useport: in std_logic;
			
			--outputs
			F_pc0, F_pc1, F_A0, F_A1, F_B0, F_B1: out std_logic
		);
	end component;
	component hazard_detection is
		port(
			--inputs
			
			F_D_src1_no,F_D_src2_no,D_E_dst_no: in std_logic_vector(3 downto 0);
			D_E_wb,D_E_M,D_E_reti,E_M_reti: in std_logic;
			
			--outputs
			stall: out std_logic
		);
	end component;
	signal FD, WB_write_data, mem_output, PC, R8, R9, reg_file_dst, ALU_Output: std_logic_vector(15 downto 0);
	signal DE: std_logic_vector(105 downto 0);
	signal EM: std_logic_vector(41 downto 0);
	signal MW: std_logic_vector(39 downto 0);
	signal F_A0, F_A1, F_B0, F_B1, F_PC0, F_PC1, flush, hazard_stall: std_logic;
	signal Execute_CCR, WB_Dst_address, decode_CCR, FD_src1_add, FD_src2_add: std_logic_vector(3 downto 0);
	
	begin
	my_PC_module: PC_Moudule port map(sysClk, sysReset, hazard_stall, EM(3), flush, INT, F_PC0, F_PC1, RESET, WB_write_data, R8, R9, reg_file_dst, EM(30 downto 15), ALU_Output, MW(35 downto 32), mem_output, PC);
	fectch_stage: Fetch port map(sysClk, sysReset, DE(85), hazard_stall, INT, PC, FD);
	decode_stage: Decode port map(sysClk, sysReset, MW(36), EM(3), F_A0, F_A1, EM(40), EM(41), FD, mem_output, WB_write_data, MW(31 downto 16), EM(29 downto 14), PC, Execute_CCR, WB_Dst_address, EM(13 downto 10), DE, R8, R9, reg_file_dst, decode_CCR, flush);
	execute_stage: execution port map(sysClk, sysReset, DE, MW(15 downto 0), EM(29 downto 14), IN_Port, FD, F_A0, F_A1, F_B0, F_B1, hazard_stall, EM, ALU_Output, Execute_CCR);
	memory_stage: Memory port map(sysClk, sysReset, EM, MW, OUT_Port, mem_output);
	WB_stage: WriteBack port map(sysClk, sysReset, IN_Port, MW, WB_Dst_address, WB_write_data);
	my_forwarding_unit: forword_unit port map(FD(14 downto 11), DE(72 downto 69), DE(76 downto 73), DE(80 downto 77), EM(9 downto 6), MW(35 downto 32), DE(2), DE(9), DE(10), DE(0), EM(0), EM(1), EM(4), MW(36), MW(39), F_PC0, F_PC1, F_A0, F_A1, F_B0, F_B1);
	FD_src1_add <= '0' & FD(10 downto 8);
	FD_src2_add <= '0' & FD(7 downto 5);
	my_hazard_detector: hazard_detection port map(FD_src1_add, FD_src2_add, DE(72 downto 69), DE(2), DE(3), DE(7), EM(3), hazard_stall);
end my_system;