library ieee;
Use ieee.std_logic_1164.all;
use IEEE.std_logic_unsigned.all;
use ieee.numeric_std.all;

entity hazard_detection is
	port(
		--inputs
		
		F_D_src1_no,F_D_src2_no,D_E_dst_no: in std_logic_vector(3 downto 0);
		D_E_wb,D_E_M,D_E_reti,E_M_reti: in std_logic;
		
		--outputs
		stall: out std_logic
	);
end entity hazard_detection;

architecture my_hazard_detection of hazard_detection is
  signal cond1,cond2,cond3,cond4,cond3w4_result,cond2w3w4_result,cond1w2w3w4_result : std_logic;
begin
  cond1 <= '1' when D_E_reti='1' or E_M_reti='1' else '0';
  cond2 <= '1' when D_E_wb='1' and D_E_M='1' else '0';
  cond3 <= '1' when D_E_dst_no = F_D_src1_no else '0';
  cond4 <= '1' when D_E_dst_no = F_D_src2_no else '0';
    
    cond3w4_result <= cond3 or cond4;
    cond2w3w4_result <= cond2 and cond3w4_result ;
    cond1w2w3w4_result <= cond1 or cond2w3w4_result;
  stall <= '1' when cond1w2w3w4_result = '1' --cond2w3w4_result
else  '0' ;

end my_hazard_detection;
