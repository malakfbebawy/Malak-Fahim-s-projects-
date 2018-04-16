vsim -gui work.reg_file
# vsim 
# Start time: 17:51:25 on May 16,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.reg_file(my_reg_file)
# Loading work.reg(aregister)
# Loading work.mux4x1_1_bit(my_mux4x1_1_bit)
# Loading work.sp_module(my_sp_module)
# Loading work.mux4x1(my_mux4x1)
# Loading work.sp_alu(my_sp_alu)
# Loading work.my_nadder(a_my_nadder)
# Loading work.my_adder(a_my_adder)
# Loading work.mux16x1(my_mux16x1)
# Loading ieee.numeric_std(body)
# Loading ieee.math_real(body)
# Loading work.decoder(my_decoder)
# ** Warning: Design size of 10711 statements or 40 leaf instances exceeds ModelSim PE Student Edition recommended capacity.
# Expect performance to be quite adversely affected.
# vsim 
# Start time: 17:46:48 on May 16,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.reg_file(my_reg_file)
# Loading work.reg(aregister)
# Loading work.mux4x1_1_bit(my_mux4x1_1_bit)
# Loading work.sp_module(my_sp_module)
# Loading work.mux4x1(my_mux4x1)
# Loading work.sp_alu(my_sp_alu)
# Loading work.my_nadder(a_my_nadder)
# Loading work.my_adder(a_my_adder)
# Loading work.mux16x1(my_mux16x1)
# Loading ieee.numeric_std(body)
# Loading ieee.math_real(body)
# Loading work.decoder(my_decoder)
# ** Warning: Design size of 10690 statements or 40 leaf instances exceeds ModelSim PE Student Edition recommended capacity.
# Expect performance to be quite adversely affected.
# vsim 
# Start time: 17:38:42 on May 16,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.reg_file(my_reg_file)
# Loading work.reg(aregister)
# Loading work.mux4x1_1_bit(my_mux4x1_1_bit)
# Loading work.sp_module(my_sp_module)
# Loading work.mux4x1(my_mux4x1)
# Loading work.sp_alu(my_sp_alu)
# Loading work.my_nadder(a_my_nadder)
# Loading work.my_adder(a_my_adder)
# Loading work.mux16x1(my_mux16x1)
# Loading ieee.numeric_std(body)
# Loading ieee.math_real(body)
# Loading work.decoder(my_decoder)
# ** Warning: Design size of 10690 statements or 40 leaf instances exceeds ModelSim PE Student Edition recommended capacity.
# Expect performance to be quite adversely affected.
add wave  \
sim:/reg_file/sysClk \
sim:/reg_file/sysReset \
sim:/reg_file/dst_address \
sim:/reg_file/src1_address \
sim:/reg_file/src2_address \
sim:/reg_file/write_address \
sim:/reg_file/write_enable \
sim:/reg_file/INT_signal \
sim:/reg_file/write_data \
sim:/reg_file/memory_CCR \
sim:/reg_file/ALU_CCR \
sim:/reg_file/EM_RETI \
sim:/reg_file/DE_RETI \
sim:/reg_file/EM_up_flags \
sim:/reg_file/DE_up_flags \
sim:/reg_file/DEC_SP_en \
sim:/reg_file/DEC_SP_fun \
sim:/reg_file/F_A0 \
sim:/reg_file/F_A1 \
sim:/reg_file/MW_Dst \
sim:/reg_file/EM_Dst \
sim:/reg_file/PC \
sim:/reg_file/dst \
sim:/reg_file/src1 \
sim:/reg_file/src2 \
sim:/reg_file/R8 \
sim:/reg_file/R9 \
sim:/reg_file/CCR \
sim:/reg_file/R0 \
sim:/reg_file/R1 \
sim:/reg_file/R2 \
sim:/reg_file/R3 \
sim:/reg_file/R4 \
sim:/reg_file/R5 \
sim:/reg_file/R6 \
sim:/reg_file/R8_temp \
sim:/reg_file/R9_temp \
sim:/reg_file/R10 \
sim:/reg_file/registers_enables \
sim:/reg_file/temp_CCR_input \
sim:/reg_file/temp_CCR_output \
sim:/reg_file/zero_enable \
sim:/reg_file/negative_enable \
sim:/reg_file/carry_enable \
sim:/reg_file/overflow_enable \
force -freeze sim:/reg_file/sysClk 1 0, 0 {50 ns} -r 100
force -freeze sim:/reg_file/sysReset 1 0
run
force -freeze sim:/reg_file/sysReset 0 0
force -freeze sim:/reg_file/memory_CCR 4'h4 0
force -freeze sim:/reg_file/ALU_CCR 4'ha 0
force -freeze sim:/reg_file/EM_RETI 1 0
force -freeze sim:/reg_file/DE_RETI 0 0
force -freeze sim:/reg_file/EM_up_flags 4'h4 0
force -freeze sim:/reg_file/DE_up_flags 4'hb 0
run
force -freeze sim:/reg_file/EM_RETI 0 0
run
force -freeze sim:/reg_file/DE_up_flags 4'h5 0
force -freeze sim:/reg_file/ALU_CCR 4'h0 0
run
force -freeze sim:/reg_file/dst_address 4'h4 0
force -freeze sim:/reg_file/src1_address 4'hb 0
force -freeze sim:/reg_file/src1_address 4'h0 0
force -freeze sim:/reg_file/src2_address 4'h5 0
force -freeze sim:/reg_file/R1 16'h0001 0
force -freeze sim:/reg_file/R2 16'h0002 0
force -freeze sim:/reg_file/R3 16'h0003 0
force -freeze sim:/reg_file/R4 16'h0005 0
force -freeze sim:/reg_file/R5 16'h0006 0
force -freeze sim:/reg_file/R4 16'h0004 0
force -freeze sim:/reg_file/R5 16'h0005 0
force -freeze sim:/reg_file/R6 16'h0006 0
force -freeze sim:/reg_file/R8_temp 16'h0008 0
force -freeze sim:/reg_file/R9_temp 16'h0009 0
force -freeze sim:/reg_file/R10 16'h0010 0
run
noforce sim:/reg_file/R0
noforce sim:/reg_file/R1
noforce sim:/reg_file/R2
noforce sim:/reg_file/R3
noforce sim:/reg_file/R4
noforce sim:/reg_file/R5
noforce sim:/reg_file/R6
noforce sim:/reg_file/R8_temp
noforce sim:/reg_file/R9_temp
noforce sim:/reg_file/R10
force -freeze sim:/reg_file/write_address 4'h5 0
force -freeze sim:/reg_file/write_enable 1 0
force -freeze sim:/reg_file/write_data 16'h0500 0
run
force -freeze sim:/reg_file/write_enable 0 0
run
force -freeze sim:/reg_file/write_enable 1 0
force -freeze sim:/reg_file/write_address 4'h2 0
force -freeze sim:/reg_file/write_data 16'h0200 0
run
force -freeze sim:/reg_file/write_address 4'h0 0
force -freeze sim:/reg_file/write_data 16'h020f 0
run