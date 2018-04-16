vsim -gui work.controlunit
# vsim 
# Start time: 03:28:05 on May 15,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.controlunit(mycontrolunit)
# vsim 
# Start time: 03:13:20 on May 15,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.controlunit(mycontrolunit)
add wave  \
sim:/controlunit/IR \
sim:/controlunit/CCR \
sim:/controlunit/DE_IM \
sim:/controlunit/IM \
sim:/controlunit/INT \
sim:/controlunit/WB \
sim:/controlunit/M \
sim:/controlunit/CALL \
sim:/controlunit/SP_en \
sim:/controlunit/SP_fun \
sim:/controlunit/RETI \
sim:/controlunit/use_port \
sim:/controlunit/Flush \
sim:/controlunit/has_src1 \
sim:/controlunit/has_src2 \
sim:/controlunit/up_flags
force -freeze sim:/controlunit/IR 6'h00 0
run
force -freeze sim:/controlunit/IR 6'h01 0
run
force -freeze sim:/controlunit/IR 6'h20 0
run
force -freeze sim:/controlunit/IR 6'h21 0
run
force -freeze sim:/controlunit/IR 6'h22 0
run
force -freeze sim:/controlunit/IR 6'h23 0
run
force -freeze sim:/controlunit/DE_IM 0 0
force -freeze sim:/controlunit/IR 6'h24 0
run
force -freeze sim:/controlunit/DE_IM 1 0
run
force -freeze sim:/controlunit/IR 6'h28 0
run
force -freeze sim:/controlunit/IR 6'h27 0
run
force -freeze sim:/controlunit/IR 6'h28 0
run
force -freeze sim:/controlunit/IR 6'h29 0
run
force -freeze sim:/controlunit/IR 6'h2a 0
run
force -freeze sim:/controlunit/IR 6'h2b 0
run
force -freeze sim:/controlunit/IR 6'h2c 0
run
force -freeze sim:/controlunit/IR 6'h2d 0
run
force -freeze sim:/controlunit/IR 6'h2f 0
run
force -freeze sim:/controlunit/IR 6'h30 0
run
force -freeze sim:/controlunit/IR 6'h31 0
run
force -freeze sim:/controlunit/IR 6'h32 0
run
force -freeze sim:/controlunit/IR 6'h33 0
run
force -freeze sim:/controlunit/IR 6'h34 0
run
force -freeze sim:/controlunit/IR 6'h35 0
run
force -freeze sim:/controlunit/IR 6'h36 0
run
force -freeze sim:/controlunit/IR 6'h37 0
run
force -freeze sim:/controlunit/DE_IM 1 0
force -freeze sim:/controlunit/IR 6'h38 0
run
force -freeze sim:/controlunit/CCR(0) 0 0
force -freeze sim:/controlunit/IR 6'h39 0
run
force -freeze sim:/controlunit/DE_IM 1 0
force -freeze sim:/controlunit/CCR(2) 1 0
run
force -freeze sim:/controlunit/DE_IM 0 0
run
force -freeze sim:/controlunit/DE_IM 1 0
force -freeze sim:/controlunit/IR 6'h38 0
run
force -freeze sim:/controlunit/IR 6'h3a 0
force -freeze sim:/controlunit/DE_IM 0 0
force -freeze sim:/controlunit/CCR(0) 0 0
run
force -freeze sim:/controlunit/CCR(0) 1 0
run
force -freeze sim:/controlunit/DE_IM 1 0
run
force -freeze sim:/controlunit/IR 6'h3b 0
force -freeze sim:/controlunit/CCR(1) 1 0
run
force -freeze sim:/controlunit/DE_IM 0 0
run
force -freeze sim:/controlunit/CCR(1) 0 0
run
force -freeze sim:/controlunit/IR 6'h3c 0
run
force -freeze sim:/controlunit/IR 6'h3d 0
run
force -freeze sim:/controlunit/IR 6'h3e 0
run
force -freeze sim:/controlunit/IR 6'h3f 0
run