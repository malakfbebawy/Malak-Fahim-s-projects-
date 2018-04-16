library ieee;
Use ieee.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use ieee.numeric_std.all;

entity forword_unit is
	port(
		--inputs
		
		F_D_dst_no, D_E_dst_no, D_E_src1_no, D_E_src2_no, E_M_dst_no, M_W_dst_no: in std_logic_vector(3 downto 0);
		D_E_wb, D_E_has_src1, D_E_has_src2, D_E_IM, E_M_wb, E_M_m, E_M_useport, M_W_wB, M_W_useport: in std_logic;
		
		--outputs
		F_pc0, F_pc1, F_A0, F_A1, F_B0, F_B1: out std_logic
	);
end entity forword_unit;

architecture my_forword_unit of forword_unit is
  signal check_vec_out1, check_vec2_out2, check_vec1_out3, check_vec2_out3, check_vec1_out5, check_vec2_out5, FA0_cond1, FA0_cond2 : std_logic;
  signal cond1_out1 : std_logic_vector(3 downto 0);
begin
  
  --------output 1-------------------
  check_vec_out1 <= '1' when (E_M_dst_no = F_D_dst_no) else  '0' ;
  F_pc0 <= '1' when E_M_wb='1' and check_vec_out1 = '1' else '0';
  ------------output 2--------
  check_vec2_out2 <= '1' when (D_E_dst_no = F_D_dst_no) else '0';
  F_pc1 <= (E_M_wb and E_M_m and check_vec_out1 ) or (D_E_wb and not D_E_IM and check_vec2_out2 ) ;
  -----------output 3----------------------------
  check_vec1_out3 <= '1' when (E_M_dst_no = D_E_src1_no) else '0'; 
  
  check_vec2_out3 <= '1' when (M_W_dst_no = D_E_src1_no) else '0';
  
F_A1 <= '1' when D_E_has_src1='1' and ( (E_M_wb = '1' and check_vec1_out3='1') or (M_W_wB='1' and M_W_useport='1' and  check_vec2_out3='1' ) ) else '0';

---------------output 4 ----------------------------------------------
FA0_cond1 <= '1' when (M_W_wB='1' and (M_W_dst_no = D_E_src1_no)) else '0';
FA0_cond2 <= '1' when ( E_M_useport='1' and E_M_wb='1' and (E_M_dst_no = D_E_src1_no)) else '0';
F_A0 <= '1' when D_E_has_src1='1' and ( FA0_cond1='1' or FA0_cond2='1') else '0'; 

-----------output 5----------------------------
  check_vec1_out5 <= '1' when (E_M_dst_no = D_E_src2_no)
else '0'; 
  
  check_vec2_out5 <= '1' when (M_W_dst_no = D_E_src2_no)
else '0';
  
F_B1 <= '1' when D_E_has_src2='1' and ( (E_M_wb = '1' and check_vec1_out5='1') or (M_W_wB='1' and M_W_useport='1' and  check_vec2_out5='1' ) ) else '0';
  ----------------------------output 6 -----------------------------------------
 
  F_B0 <= '1' when D_E_has_src2='1' and ( (M_W_wB='1' and (E_M_dst_no = D_E_src2_no)) or ( E_M_useport='1' and E_M_wb='1' and (E_M_dst_no = D_E_src2_no))) else '0'; 
  
  
  
end my_forword_unit;

