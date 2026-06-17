// ADS I Class Project
// Pipelined RISC-V Core - EX Stage
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 01/09/2026 by Tobias Jauch (@tojauch)

/*
Instruction Execute (EX) Stage: ALU operations and exception detection

Instantiated Modules:
    ALU: Integrate your module from Assignment02 for arithmetic/logical operations

ALU Interface:
    alu.io.operandA: first operand input
    alu.io.operandB: second operand input
    alu.io.operation: operation code controlling ALU function
    alu.io.aluResult: computation result output

Internal Signals:
    Map uopc codes to ALUOp values

Functionality:
    Map instruction uop to ALU operation code
    Pass operands to ALU
    Output results to pipeline

Outputs:
    aluResult: computation result from ALU
    exception: pass exception flag
*/

package core_tile

import chisel3._
import chisel3.util._
//import Assignment02.{ALU, ALUOp}
import uopc._

// -----------------------------------------
// Execute Stage
// -----------------------------------------
class EX extends Module {
  val io = IO(new Bundle {
    val inUOP         = Input(uopc())
    val inRD          = Input(UInt(5.W))
    val inOperandA    = Input(UInt(32.W))
    val inOperandB    = Input(UInt(32.W))
    val inXcptInvalid = Input(Bool())

    val outRD         = Output(UInt(5.W))
    val aluResult     = Output(UInt(32.W))
    val outXcptInvalid = Output(Bool())
  })

  val alu = Module(new ALU)

  alu.io.in1    := io.inOperandA
  alu.io.in2    := io.inOperandB
  alu.io.alu_op := io.inUOP

  io.aluResult      := alu.io.result
  io.outRD          := io.inRD
  io.outXcptInvalid := io.inXcptInvalid
}

//ToDo: Add your implementation according to the specification above here 
