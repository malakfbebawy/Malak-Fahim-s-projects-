vsim -gui work.alu(struct)
# vsim 
# Start time: 05:05:59 on May 15,2017
# Loading std.standard
# Loading std.textio(body)
# Loading ieee.std_logic_1164(body)
# Loading work.alu(struct)
# Loading work.my_nadder(a_my_nadder)
# Loading work.my_adder(a_my_adder)
add wave  \
sim:/alu/a \
sim:/alu/b \
sim:/alu/op \
sim:/alu/carryIn \
sim:/alu/carryOut \
sim:/alu/overflowFlag \
sim:/alu/output \
sim:/alu/tempout \
sim:/alu/tempin1 \
sim:/alu/tempin2 \
sim:/alu/cintemp \
sim:/alu/couttemp \
sim:/alu/aluOverflowFlag
force -freeze sim:/alu/a 16'h1F2e 0
force -freeze sim:/alu/b 16'he543 0
force -freeze sim:/alu/op 6'h00 0
run
force -freeze sim:/alu/op 6'h01 0
run
force -freeze sim:/alu/op 6'h10 0
run
force -freeze sim:/alu/op 6'h20 0
run
force -freeze sim:/alu/op 6'h21 0
run
force -freeze sim:/alu/op 6'h22 0
run
force -freeze sim:/alu/op 6'h23 0
run
force -freeze sim:/alu/op 6'h24 0
run
force -freeze sim:/alu/op 6'h25 0
run
force -freeze sim:/alu/op 6'h28 0
run
force -freeze sim:/alu/op 6'h29 0
run
force -freeze sim:/alu/op 6'h2a 0
run
force -freeze sim:/alu/a 16'h1F2f 0
force -freeze sim:/alu/op 6'h2a 0
run
force -freeze sim:/alu/op 6'h2b 0
run
force -freeze sim:/alu/op 6'h2c 0
run
force -freeze sim:/alu/carryIn {} 0
# ** Error: (vsim-4026) Value "" does not represent a literal of the enumeration type.
# ** Error: (vsim-4011) Invalid force value: {} 0.

# 
force -freeze sim:/alu/carryIn 1 0
run
force -freeze sim:/alu/op 6'h2d 0
run
force -freeze sim:/alu/op 6'h2f 0
run
force -freeze sim:/alu/op 6'h30 0
run
force -freeze sim:/alu/op 6'h31 0
run
force -freeze sim:/alu/op 6'h31 0
force -freeze sim:/alu/op 6'h32 0
run
force -freeze sim:/alu/op 6'h33 0
run
force -freeze sim:/alu/op 6'h34 0
run
force -freeze sim:/alu/op 6'h35 0
run
force -freeze sim:/alu/op 6'h36 0
run
force -freeze sim:/alu/op 6'h37 0
run
force -freeze sim:/alu/op 6'h38 0
run
force -freeze sim:/alu/op 6'h39 0
run
force -freeze sim:/alu/op 6'h3a 0
run
force -freeze sim:/alu/op 6'h3b 0
run
force -freeze sim:/alu/op 6'h3c 0
run
force -freeze sim:/alu/op 6'h3d 0
run
force -freeze sim:/alu/op 6'h3c 0
run
force -freeze sim:/alu/op 6'h3e 0
run
force -freeze sim:/alu/op 6'h3f 0
run