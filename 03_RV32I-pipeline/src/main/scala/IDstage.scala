// ADS I Class Project
// Pipelined RISC-V Core - ID Stage
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 01/09/2026 by Tobias Jauch (@tojauch)

/*
Instruction Decode (ID) Stage: decoding and operand fetch

Extracted Fields from 32-bit Instruction (see RISC-V specification for reference):
    opcode: instruction format identifier
    funct3: selects variant within instruction format
    funct7: further specifies operation type (R-type only)
    rd: destination register address
    rs1: first source register address
    rs2: second source register address
    imm: 12-bit immediate value (I-type, sign-extended)

Register File Interfaces:
    regFileReq_A, regFileResp_A: read port for rs1 operand
    regFileReq_B, regFileResp_B: read port for rs2 operand

Internal Signals:
    Combinational decoders for instructions

Functionality:
    Decode opcode to determine instruction and identify operation (ADD, SUB, XOR, ...)
    Output: uop (operation code), rd, operandA (from rs1), operandB (rs2 or immediate)

Outputs:
    uop: micro-operation code (identifies instruction type)
    rd: destination register index
    operandA: first operand
    operandB: second operand 
    XcptInvalid: exception flag for invalid instructions
*/

package core_tile

import chisel3._
import chisel3.util._
import uopc._

// -----------------------------------------
// Decode Stage
// -----------------------------------------
class ID extends Module {
  val io = IO(new Bundle {
    val instr = Input(UInt(32.W))  // Instruction received from IF Barrier for decoding
    // Register File Read Port A: request address of rs1 and receive its data
    val regFileReq_A  = Output(new regFileReadReq)
    val regFileResp_A = Input(new regFileReadResp)
    // Register File Read Port B: request address of rs2 and receive its data
    val regFileReq_B  = Output(new regFileReadReq)
    val regFileResp_B = Input(new regFileReadResp)

    val uop         = Output(uopc()) // Decoded micro-operation code (ADD, SUB, AND, OR, etc.) sent to EX stage
    val rd          = Output(UInt(5.W))
    val operandA    = Output(UInt(32.W))
    val operandB    = Output(UInt(32.W))
    val XcptInvalid = Output(Bool()) // Exception flag indicating an invalid or unsupported instruction
  })

  val opcode = io.instr(6, 0)    //instruction type
  val rd     = io.instr(11, 7)   
  val funct3 = io.instr(14, 12)  //operation details
  val rs1    = io.instr(19, 15)  //source reg 1
  val rs2    = io.instr(24, 20)  //source reg 2
  val funct7 = io.instr(31, 25)  //extra operation details

  val immI = io.instr(31, 20).asSInt.asUInt  /////

  io.regFileReq_A.addr := rs1
  io.regFileReq_B.addr := rs2

  io.uop         := uopc.NOP
  io.rd          := rd
  io.operandA    := io.regFileResp_A.data
  io.operandB    := io.regFileResp_B.data
  io.XcptInvalid := false.B

  switch(opcode) {          ////////////////////

    // R-Type instructions
    is("b0110011".U) {
      io.operandB := io.regFileResp_B.data  //For R-type, second operand comes from register rs2.

      switch(funct3) {
        is("b000".U) {
          when(funct7 === "b0000000".U) {
            io.uop := uopc.ADD
          } .elsewhen(funct7 === "b0100000".U) {
            io.uop := uopc.SUB
          } .otherwise {
            io.XcptInvalid := true.B
          }
        }

        is("b001".U) { io.uop := uopc.SLL  }
        is("b010".U) { io.uop := uopc.SLT  }
        is("b011".U) { io.uop := uopc.SLTU }
        is("b100".U) { io.uop := uopc.XOR  }

        is("b101".U) {
          when(funct7 === "b0000000".U) {
            io.uop := uopc.SRL
          } .elsewhen(funct7 === "b0100000".U) {
            io.uop := uopc.SRA
          } .otherwise {
            io.XcptInvalid := true.B
          }
        }

        is("b110".U) { io.uop := uopc.OR  }
        is("b111".U) { io.uop := uopc.AND }
      }
    }

    // I-Type instructions
    is("b0010011".U) {
      io.operandB := immI

      switch(funct3) {
        is("b000".U) { io.uop := uopc.ADDI  }
        is("b010".U) { io.uop := uopc.SLTI  }
        is("b011".U) { io.uop := uopc.SLTIU }
        is("b100".U) { io.uop := uopc.XORI  }
        is("b110".U) { io.uop := uopc.ORI   }
        is("b111".U) { io.uop := uopc.ANDI  }

        is("b001".U) {
          when(funct7 === "b0000000".U) {
            io.uop := uopc.SLLI
          } .otherwise {
            io.XcptInvalid := true.B  //This instruction is invalid Raise an exception flag
          }
        }

        is("b101".U) {
          when(funct7 === "b0000000".U) {
            io.uop := uopc.SRLI
          } .elsewhen(funct7 === "b0100000".U) {
            io.uop := uopc.SRAI
          } .otherwise {
            io.XcptInvalid := true.B
          }
        }
      }
    }

    // Unknown opcode
    is("b0000000".U) {
      io.uop := uopc.NOP
      io.XcptInvalid := true.B
    }
  }
}

//ToDo: Add your implementation according to the specification above here 