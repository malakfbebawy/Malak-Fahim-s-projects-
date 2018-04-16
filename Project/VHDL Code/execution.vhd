library ieee;
Use ieee.std_logic_1164.all;

entity execution is
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
end entity execution;

architecture my_execution of execution is
  
	component mux_8_1 is GENERIC (n : integer := 16);
	 	PORT (a,b,c,d,e,f,g,h :IN std_logic_vector(n-1 downto 0);
	    	s0,s1,s2 :IN std_logic;
	    	s : out std_logic_vector(n-1 downto 0));
	end component;
 	
	component ALU is GENERIC (n : integer := 16);
	   PORT( a,b : IN Std_logic_vector(n-1 downto 0);
		     op : IN std_logic_vector (5 DOWNTO 0);
        	carryIn : IN std_logic;  
		    carryOut, overflowFlag : OUT std_logic;
		   output : OUT Std_logic_vector(n-1 downto 0)
		   );
 	end component;
 	
 	component Reg is GENERIC (n : integer := 16);
    	port( clk,reset, regEnable : in std_logic;
	        d : in std_logic_vector(n-1 downto 0);
	        q : out std_logic_vector(n-1 downto 0)
	        );
 	end component;
 	
 	signal input6_muxA,out_muxA,out_muxB,alu_output, zeros: std_logic_vector(15 downto 0);
 	signal LD_add, STD_add : std_logic_vector(15 downto 0);
 	signal exec_mem_reg_signal , out_exec_mem_reg: std_logic_vector(41 downto 0);
  	signal s0_muxA,s1_muxA,s2_muxA,s0_muxB,s1_muxB,s2_muxB,alu_carry,alu_overflow: std_logic;
 	begin
 	  
 	  -----------MUX A ------------------------------------
 	  input6_muxA <= "00" & dec_exec_reg(84 downto 81) & dec_exec_reg(40 downto 31) ;
 	  
 	  s0_muxA <= '1' when (dec_exec_reg(4)='1' or F_A0='1') else '0';
 	  s1_muxA <= '1' when (dec_exec_reg(4)='1' or dec_exec_reg(1)='1' or F_A1='1') else '0' ;
 	  s2_muxA <= '1' when (dec_exec_reg(4)='1' or dec_exec_reg(1)='1' or dec_exec_reg(0)='1') else '0';
 	  muxAA: mux_8_1 generic map(16) port map(dec_exec_reg(46 downto 31),mem_wb_mem_output,exec_mem_ALU_output,in_port,fetch_dec_reg_output,fetch_dec_reg_output,input6_muxA,dec_exec_reg(46 downto 31),s0_muxA,s1_muxA,s2_muxA,out_muxA); 
 	
 	-----------------------------Mux B----------------------------------
 	 s0_muxB <= '1' when F_B0='1' else '0' ;
 	  s1_muxB <= '1' when (F_B1='1' or (dec_exec_reg(3)='1' and dec_exec_reg(2)='1')) else '0' ;
 	  s2_muxB <= '1' when (dec_exec_reg(3)='1' and dec_exec_reg(5) = '0') else '0'; --M
 	  LD_add <= "000000" & dec_exec_reg(95 downto 86);
 	  STD_add <= "000000" & dec_exec_reg(105 downto 96);
 	 muxBB: mux_8_1 generic map(16) port map (dec_exec_reg(62 downto 47),mem_wb_mem_output,exec_mem_ALU_output,in_port,LD_add,LD_add,STD_add,STD_add,s0_muxB,s1_muxB,s2_muxB,out_muxB);
 	 
 	 -----------------------ALU-----------------------------
 	 aluu: ALU generic map(16) port map (out_muxA,out_muxB,dec_exec_reg(68 downto 63),dec_exec_reg(83),Exec_CCR(2),Exec_CCR(3),alu_output);
 	 zeros <= (others => '0');
 	 Exec_CCR(0) <= '1' when (alu_output = zeros) else '0'; --zero_flag
 	 Exec_CCR(1) <= alu_output(15); --Neg_Flag
 	 ALU_output_module <= alu_output;
   
   ------------------------Exec/memory Reg------------------------
   exec_mem_reg_signal  <= dec_exec_reg(0) & hazard_stall & out_muxB(9 downto 0)& alu_output&dec_exec_reg(14 downto 11)&dec_exec_reg(72 downto 69)&dec_exec_reg(5)&dec_exec_reg(8)&dec_exec_reg(7)&dec_exec_reg(4)&dec_exec_reg(3)&dec_exec_reg(2);
 	  Reeg : Reg generic map (42) port map(clk,rst,'1',exec_mem_reg_signal,out_exec_mem_reg);
 	  exec_mem_output_module <= out_exec_mem_reg ;
 	  
 	  end my_execution;
  
