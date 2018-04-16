library ieee;
Use ieee.std_logic_1164.all;

entity controlUnit is
	port(
		--inputs
		Op: in std_logic_vector(5 downto 0);
		CCR: in std_logic_vector(3 downto 0);
		DE_IM, DE_Flush: in std_logic;
		--outputs
		IM, INT, WB, M, CALL, SP_en, SP_fun, RETI, use_port, has_src1, has_src2, Flush: out std_logic;
		up_flags: out std_logic_vector(3 downto 0) -- up_zero, up_neg, up_carry, up_overflow
	);
end entity controlUnit;
architecture myControlUnit of controlUnit is
	begin
	IM <= '0' when DE_Flush='1' else (Op(5) and Op(4) and not (Op(3) or Op(2) or (Op(1) and Op(0)))); -- LDM, SHL, SHR
	INT <= '0' when DE_Flush='1' else (Op(5) and (not Op(4)) and (not Op(3)) and Op(2) and Op(1) and Op(0)); -- INTR
	CALL <= '0' when DE_Flush='1' else Op(5) and Op(2) and not (Op(4) or Op(3) or Op(1) or Op(0)); -- CALL
	-- WB is 1 in LDD, POP, LDM, SHL, SHR, Category 2, ADD, SUB, AND, OR, IN
	WB <= '0' when DE_Flush='1'
		else '1' when ((Op(5) = '0' and Op(0) = '0') or (Op(5) = '1' and ((Op(4 downto 0) = "10111") or (Op(4 downto 2) = "100" and Op(1 downto 0) /= "11") or (Op(4 downto 3) = "01") or (Op(4 downto 2) = "000") or Op(4 downto 0) = "11110")))
		else '0';
	-- M is 1 in Category 0, CALL, INT, Category 3_2
	M <= '0' when DE_Flush='1'
		else '1' when ((Op(5)='0') or (Op(5) = '1' and ((Op(4 downto 2) = "001") or (Op(4 downto 2) = "101"))))
		else '0';
	-- SP_en is 1 in CALL, INT, Category 3_2
	SP_en <= '0' when DE_Flush='1'
		else '1' when (Op(5) = '1' and ((Op(4 downto 2) = "001") or (Op(4 downto 2) = "101")))
		else '0';
	-- SP_en is 1 in CALL, INT, PUSH
	SP_fun <='0' when DE_Flush='1'
		else  '1' when (Op(5) = '1' and ((Op(4 downto 2) = "001") or (Op(4 downto 0) = "10110")))
		else '0';
	-- RETI is 1 in RET, RTI
	RETI <= '0' when DE_Flush='1'
		else '1' when (Op(5) = '1' and Op(4 downto 1) = "1010")
		else '0';
	-- use_port is 1 in Category 4_2_2
	use_port <= '0' when DE_Flush='1'
		else '1' when (Op(5) = '1' and Op(4 downto 1) = "1111")
		else '0';
	-- Flush is 1 in CALL, conditional jumps with appropriate CCR (JMP, JC + Carry, JZ + Zero or JN + Negative)
	Flush <= '0' when DE_Flush='1'
		else '1' when (DE_IM = '0' and (Op(5) = '1' and (Op(4 downto 0) = "00100" or (Op(4 downto 2) = "110" and (Op(1 downto 0) = "00" or (Op(1 downto 0) = "01" and CCR(2) = '1') or (Op(1 downto 0) = "10" and CCR(0) = '1') or (Op(1 downto 0) = "11" and CCR(1) = '1'))))))
		else '0';
	-- has_src1 is 1 in STD, Category 1, Category 2, OUT, PUSH
	has_src1 <= '0' when DE_Flush='1'
		else '1' when ((Op(5) = '0' and Op(0) = '1') or (Op(5) = '1' and (Op(4) = '0' or Op(4 downto 0) = "11111" or Op(4 downto 0) = "10110")))
		else '0';
	-- has_src2 is 1 in Category 1, Category 3_2
	has_src2 <= '0' when DE_Flush='1'
		else '1' when (Op(5) = '1' and (Op(4 downto 3) = "00" or Op(4 downto 2) = "101"))
		else '0';
	-- up_flags is "1111" if ADD, SUB, AND, OR, NOT, NEG, INC, DEC, RTI
	up_flags <= "0000" when DE_Flush='1'
		else "1111" when (Op(5) = '1' and (Op(4 downto 2) = "000" or Op(4 downto 2) = "010" or Op(4 downto 0) = "10101"))
		-- up_flags(0) (zero) is also 1 in JZ
		else "0001" when (Op(5) = '1' and Op(4 downto 0) = "11010")
		-- up_flags(1) (neg) is also 1 in JN
		else "0010" when (Op(5) = '1' and Op(4 downto 0) = "11011")
		-- up_flags(2) (carry) is also 1 in Category 4_2_1, JC
		else "0100" when (Op(5) = '1' and (Op(4 downto 1) = "1110" or Op(4 downto 0) = "11001"))
		else "0000";
end myControlUnit;