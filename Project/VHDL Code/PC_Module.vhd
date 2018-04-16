Library ieee;
Use ieee.std_logic_1164.all;

Entity PC_Moudule is
	port(  --inputs
		clk , rst, stall , E_M_reti , Flush , INT ,F_PC0 , F_PC1 , RESET : in std_logic;
		WB_writeBack , R8 , R9 ,REG_FILE_dest_out , E_M_dest , ALU_output: in std_logic_vector(15 downto 0);
		M_W_DestNo : in std_logic_vector (3 downto 0) ;
		MEM_output : in std_logic_vector (15 downto 0);
				
		--output 
		PC :out std_logic_vector(15 downto 0)

);
end PC_Moudule;

Architecture PC_Moudule_logic of PC_Moudule is
	signal Adder_out , mux_out , PC_REG_out,Expander_out: std_logic_vector(15 downto 0);
	signal s0,s1,s2  : std_logic;
	signal dstXOR7 : std_logic_vector(3 downto 0);
	signal is_dst_7 , PC_REG_EN, neg_second_stall, second_stall : std_logic;
	begin
		--getting signals Ready
		s2 <= '1' when (INT /= '1') and (E_M_reti = '1' or Flush = '1') else '0';
		s1 <= '1' when (INT = '1' or (F_PC1 ='1' and Flush ='1') or E_M_reti='1' or RESET='1') else '0';
		dstXOR7 <=  M_W_DestNo xor "0111";
		is_dst_7 <= not( dstXOR7(0) or dstXOR7(1) or dstXOR7(2) or dstXOR7(3) );
		s0 <= '1' when ((F_PC0 ='1' and Flush ='1') or INT='1' or E_M_reti='1' or is_dst_7='1') else '0';
		neg_stall_FF: entity work.neg_FF port map(clk, rst, '1', stall, neg_second_stall);
		stall_FF: entity work.FF port map(clk, rst, '1', neg_second_stall, second_stall);
		PC_REG_EN <= '0' when stall = '1' and second_stall='0' else
			     '1';
		Expander : Entity work.Expander port map (MEM_output,Expander_out,E_M_reti);

		--Starting of the logic
		MUX : Entity work.mux_8_1 generic map(16) port map(Adder_out,WB_writeBack,R8,R9,REG_FILE_dest_out,E_M_dest,ALU_output,Expander_out,s0,s1,s2,mux_out);
		PC_REG : Entity work.Reg generic map(16) port map(clk,rst,PC_REG_EN,mux_out,PC_REG_out);
		Adder : Entity work.my_nadder generic map(16) port map(a=>PC_REG_out,b=>"0000000000000001",cin=>'0',s=>Adder_out);
		PC <= PC_REG_out;
end PC_Moudule_logic;
