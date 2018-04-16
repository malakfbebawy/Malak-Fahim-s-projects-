vsim -gui work.system
# vsim 
# Start time: 00:46:28 on May 17,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.system(my_system)
# Loading work.pc_moudule(pc_moudule_logic)
# Loading work.expander(logic)
# Loading work.mux_8_1(a_mux)
# Loading work.reg(aregister)
# Loading work.my_nadder(a_my_nadder)
# Loading work.my_adder(a_my_adder)
# Loading ieee.numeric_std(body)
# Loading ieee.math_real(body)
# Loading work.fetch(logic)
# Loading work.ram(sync_ram_a)
# Loading work.mux2x1(my_mux2x1)
# Loading work.decode(decodestage)
# Loading work.mux2x1_1_bit(my_mux2x1_1_bit)
# Loading work.controlunit(mycontrolunit)
# Loading work.reg_file(my_reg_file)
# Loading work.mux4x1_1_bit(my_mux4x1_1_bit)
# Loading work.sp_module(my_sp_module)
# Loading work.mux4x1(my_mux4x1)
# Loading work.sp_alu(my_sp_alu)
# Loading work.mux16x1(my_mux16x1)
# Loading work.decoder(my_decoder)
# Loading work.execution(my_execution)
# Loading work.alu(struct)
# Loading work.memory(memory_arch)
# Loading work.writeback(writeback_arch)
# ** Warning: Design size of 13584 statements or 134 leaf instances exceeds ModelSim PE Student Edition recommended capacity.
# Expect performance to be quite adversely affected.
# ** Warning: (vsim-8683) Uninitialized out port /system/decode_stage/Flush has no driver.
# 
# This port will contribute value (U) to the signal network.
add wave  \
sim:/system/sysClk \
sim:/system/sysReset \
sim:/system/INT \
sim:/system/RESET \
sim:/system/IN_Port \
sim:/system/OUT_Port \
sim:/system/FD \
sim:/system/WB_write_data \
sim:/system/mem_output \
sim:/system/PC \
sim:/system/R8 \
sim:/system/R9 \
sim:/system/reg_file_dst \
sim:/system/ALU_Output \
sim:/system/DE \
sim:/system/EM \
sim:/system/MW \
sim:/system/F_A0 \
sim:/system/F_A1 \
sim:/system/F_B0 \
sim:/system/F_B1 \
sim:/system/F_PC0 \
sim:/system/F_PC1 \
sim:/system/flush \
sim:/system/hazard_stall \
sim:/system/Execute_CCR \
sim:/system/WB_Dst_address \
sim:/system/decode_CCR

add wave sim:/system/decode_stage/my_reg_file/Reg0/q
add wave sim:/system/decode_stage/my_reg_file/Reg1/q
add wave sim:/system/decode_stage/my_reg_file/Reg2/q
add wave sim:/system/decode_stage/my_reg_file/Reg3/q
add wave sim:/system/decode_stage/my_reg_file/Reg4/q
add wave sim:/system/decode_stage/my_reg_file/Reg5/q
add wave sim:/system/decode_stage/my_reg_file/R6
add wave sim:/system/decode_stage/my_reg_file/Reg8/q
add wave sim:/system/decode_stage/my_reg_file/Reg9/q
add wave sim:/system/decode_stage/my_reg_file/Reg10/q
add wave  \
sim:/system/decode_stage/my_reg_file/dst_address \
sim:/system/decode_stage/my_reg_file/src1_address \
sim:/system/decode_stage/my_reg_file/src2_address \
sim:/system/decode_stage/my_reg_file/dst \
sim:/system/decode_stage/my_reg_file/src1 \
sim:/system/decode_stage/my_reg_file/src2

add wave sim:/system/decode_stage/DE_register/*

add wave sim:/system/execute_stage/muxAA/*
add wave sim:/system/execute_stage/muxBB/*
add wave sim:/system/decode_stage/my_reg_file/my_SP_module/*
# ** Warning: NUMERIC_STD.TO_INTEGER: metavalue detected, returning 0
#    Time: 350 ns  Iteration: 2  Instance: /system/decode_stage/my_reg_file/output_decoder
mem load -filltype value -filldata {8010 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(0)
mem load -filltype value -filldata 0000 -fillradix hexadecimal /system/fectch_stage/Ram/ram(1)
mem load -filltype value -filldata {8810 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(2)
mem load -filltype value -filldata 12 -fillradix hexadecimal /system/fectch_stage/Ram/ram(3)
mem load -filltype value -filldata 9010 -fillradix hexadecimal /system/fectch_stage/Ram/ram(4)
mem load -filltype value -filldata {15 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(5)
mem load -filltype value -filldata a010 -fillradix hexadecimal /system/fectch_stage/Ram/ram(6)
mem load -filltype value -filldata {b } -fillradix hexadecimal /system/fectch_stage/Ram/ram(7)
mem load -filltype value -filldata a810 -fillradix hexadecimal /system/fectch_stage/Ram/ram(8)
mem load -filltype value -filldata e -fillradix hexadecimal /system/fectch_stage/Ram/ram(9)
mem load -filltype value -filldata 7 -fillradix hexadecimal /system/fectch_stage/Ram/ram(10)
mem load -filltype value -filldata {981e } -fillradix hexadecimal /system/fectch_stage/Ram/ram(11)
mem load -filltype value -filldata {9b62 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(12)
mem load -filltype value -filldata {881b } -fillradix hexadecimal /system/fectch_stage/Ram/ram(13)
mem load -filltype value -filldata {0006 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(14)
mem load -filltype value -filldata {8060 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(15)
mem load -filltype value -filldata 7 -fillradix hexadecimal /system/fectch_stage/Ram/ram(16)
mem load -filltype value -filldata {a018 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(17)
mem load -filltype value -filldata {97c4 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(18)
mem load -filltype value -filldata {a818 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(19)
mem load -filltype value -filldata {0407 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(20)
mem load -filltype value -filldata {9b09 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(21)
mem load -filltype value -filldata {80d4 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(22)
mem load -filltype value -filldata {0207 } -fillradix hexadecimal /system/fectch_stage/Ram/ram(23)
force -freeze sim:/system/sysClk 1 0, 0 {50 ns} -r 100
force -freeze sim:/system/sysReset 1 0
run
# ** Warning: NUMERIC_STD.TO_INTEGER: metavalue detected, returning 0
#    Time: 0 ns  Iteration: 0  Instance: /system/memory_stage/R
# ** Warning: NUMERIC_STD.TO_INTEGER: metavalue detected, returning 0
#    Time: 0 ns  Iteration: 0  Instance: /system/fectch_stage/Ram
force -freeze sim:/system/sysReset 0 0
force -freeze sim:/system/IN_Port 16'h4526 0
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run

force -freeze sim:/system/IN_Port 16'h3526 0
run
run
run
run
run
run
run
run
run
run
run
force -freeze sim:/system/IN_Port 16'he526 0
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
run
force -freeze sim:/system/IN_Port 16'h1526 0
run
run
run
run
run
run
run
run
run
run
run