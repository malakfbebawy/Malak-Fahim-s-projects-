################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL := cmd.exe
RM := rm -rf

USER_OBJS :=

LIBS := 
PROJ := 

O_SRCS := 
C_SRCS := 
S_SRCS := 
S_UPPER_SRCS := 
OBJ_SRCS := 
ASM_SRCS := 
PREPROCESSING_SRCS := 
OBJS := 
OBJS_AS_ARGS := 
C_DEPS := 
C_DEPS_AS_ARGS := 
EXECUTABLES := 
OUTPUT_FILE_PATH :=
OUTPUT_FILE_PATH_AS_ARGS :=
AVR_APP_PATH :=$$$AVR_APP_PATH$$$
QUOTE := "
ADDITIONAL_DEPENDENCIES:=
OUTPUT_FILE_DEP:=
LIB_DEP:=

# Every subdirectory with source files must be described here
SUBDIRS := 


# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS +=  \
../AMIT_FINAL_PROJET.c \
../GPIO.c \
../ldr.c \
../ldr_cfg.c \
../LED.c \
../LED_cfg.c \
../pir.c \
../pir_cfg.c \
../sound.c \
../sound_cfg.c


PREPROCESSING_SRCS += 


ASM_SRCS += 


OBJS +=  \
AMIT_FINAL_PROJET.o \
GPIO.o \
ldr.o \
ldr_cfg.o \
LED.o \
LED_cfg.o \
pir.o \
pir_cfg.o \
sound.o \
sound_cfg.o

OBJS_AS_ARGS +=  \
AMIT_FINAL_PROJET.o \
GPIO.o \
ldr.o \
ldr_cfg.o \
LED.o \
LED_cfg.o \
pir.o \
pir_cfg.o \
sound.o \
sound_cfg.o

C_DEPS +=  \
AMIT_FINAL_PROJET.d \
GPIO.d \
ldr.d \
ldr_cfg.d \
LED.d \
LED_cfg.d \
pir.d \
pir_cfg.d \
sound.d \
sound_cfg.d

C_DEPS_AS_ARGS +=  \
AMIT_FINAL_PROJET.d \
GPIO.d \
ldr.d \
ldr_cfg.d \
LED.d \
LED_cfg.d \
pir.d \
pir_cfg.d \
sound.d \
sound_cfg.d

OUTPUT_FILE_PATH +=AMIT_FINAL_PROJET.elf

OUTPUT_FILE_PATH_AS_ARGS +=AMIT_FINAL_PROJET.elf

ADDITIONAL_DEPENDENCIES:=

OUTPUT_FILE_DEP:= ./makedep.mk

LIB_DEP+= 

# AVR32/GNU C Compiler





















./%.o: .././%.c
	@echo Building file: $<
	@echo Invoking: AVR/GNU C Compiler : 4.8.1
	$(QUOTE)C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-gcc.exe$(QUOTE)  -x c -funsigned-char -funsigned-bitfields -DDEBUG  -O1 -ffunction-sections -fdata-sections -fpack-struct -fshort-enums -g2 -Wall -mmcu=atmega16a -c -std=gnu99 -MD -MP -MF "$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -MT"$(@:%.o=%.o)"   -o "$@" "$<" 
	@echo Finished building: $<
	



# AVR32/GNU Preprocessing Assembler



# AVR32/GNU Assembler




ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
endif

# Add inputs and outputs from these tool invocations to the build variables 

# All Target
all: $(OUTPUT_FILE_PATH) $(ADDITIONAL_DEPENDENCIES)

$(OUTPUT_FILE_PATH): $(OBJS) $(USER_OBJS) $(OUTPUT_FILE_DEP) $(LIB_DEP)
	@echo Building target: $@
	@echo Invoking: AVR/GNU Linker : 4.8.1
	$(QUOTE)C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-gcc.exe$(QUOTE) -o$(OUTPUT_FILE_PATH_AS_ARGS) $(OBJS_AS_ARGS) $(USER_OBJS) $(LIBS) -Wl,-Map="AMIT_FINAL_PROJET.map" -Wl,--start-group -Wl,-lm  -Wl,--end-group -Wl,--gc-sections -mmcu=atmega16a  
	@echo Finished building target: $@
	"C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-objcopy.exe" -O ihex -R .eeprom -R .fuse -R .lock -R .signature -R .user_signatures  "AMIT_FINAL_PROJET.elf" "AMIT_FINAL_PROJET.hex"
	"C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-objcopy.exe" -j .eeprom  --set-section-flags=.eeprom=alloc,load --change-section-lma .eeprom=0  --no-change-warnings -O ihex "AMIT_FINAL_PROJET.elf" "AMIT_FINAL_PROJET.eep" || exit 0
	"C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-objdump.exe" -h -S "AMIT_FINAL_PROJET.elf" > "AMIT_FINAL_PROJET.lss"
	"C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-objcopy.exe" -O srec -R .eeprom -R .fuse -R .lock -R .signature -R .user_signatures "AMIT_FINAL_PROJET.elf" "AMIT_FINAL_PROJET.srec"
	"C:\Program Files (x86)\Atmel\Atmel Toolchain\AVR8 GCC\Native\3.4.1051\avr8-gnu-toolchain\bin\avr-size.exe" "AMIT_FINAL_PROJET.elf"
	
	





# Other Targets
clean:
	-$(RM) $(OBJS_AS_ARGS) $(EXECUTABLES)  
	-$(RM) $(C_DEPS_AS_ARGS)   
	rm -rf "AMIT_FINAL_PROJET.elf" "AMIT_FINAL_PROJET.a" "AMIT_FINAL_PROJET.hex" "AMIT_FINAL_PROJET.lss" "AMIT_FINAL_PROJET.eep" "AMIT_FINAL_PROJET.map" "AMIT_FINAL_PROJET.srec" "AMIT_FINAL_PROJET.usersignatures"
	