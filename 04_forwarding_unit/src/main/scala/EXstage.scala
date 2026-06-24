package core_tile

import chisel3._
import chisel3.util._
import Assignment02.{ALU, ALUOp}

class EX extends Module {
  val io = IO(new Bundle {
    val inUOP         = Input(uopc())
    val inRD          = Input(UInt(5.W))
    val inOperandA    = Input(UInt(32.W))
    val inOperandB    = Input(UInt(32.W))
    val inXcptInvalid = Input(Bool())

    val outRD          = Output(UInt(5.W))
    val aluResult      = Output(UInt(32.W))
    val outXcptInvalid = Output(Bool())
  })

  // Use ALU from Assignment 02
  val alu = Module(new Assignment02.ALU)

  alu.io.operandA := io.inOperandA
  alu.io.operandB := io.inOperandB

  // Default operation
  alu.io.operation := ALUOp.ADD

  switch(io.inUOP) {
    is(uopc.ADD)  { alu.io.operation := ALUOp.ADD }
    is(uopc.ADDI) { alu.io.operation := ALUOp.ADD }

    is(uopc.SUB)  { alu.io.operation := ALUOp.SUB }

    is(uopc.AND)  { alu.io.operation := ALUOp.AND }
    is(uopc.ANDI) { alu.io.operation := ALUOp.AND }

    is(uopc.OR)   { alu.io.operation := ALUOp.OR }
    is(uopc.ORI)  { alu.io.operation := ALUOp.OR }

    is(uopc.XOR)  { alu.io.operation := ALUOp.XOR }
    is(uopc.XORI) { alu.io.operation := ALUOp.XOR }

    is(uopc.SLL)  { alu.io.operation := ALUOp.SLL }
    is(uopc.SLLI) { alu.io.operation := ALUOp.SLL }

    is(uopc.SRL)  { alu.io.operation := ALUOp.SRL }
    is(uopc.SRLI) { alu.io.operation := ALUOp.SRL }

    is(uopc.SRA)  { alu.io.operation := ALUOp.SRA }
    is(uopc.SRAI) { alu.io.operation := ALUOp.SRA }

    is(uopc.SLT)  { alu.io.operation := ALUOp.SLT }
    is(uopc.SLTI) { alu.io.operation := ALUOp.SLT }

    is(uopc.SLTU)  { alu.io.operation := ALUOp.SLTU }
    is(uopc.SLTIU) { alu.io.operation := ALUOp.SLTU }

    is(uopc.NOP) { alu.io.operation := ALUOp.PASSB }
  }
  
  io.aluResult      := alu.io.aluResult
  io.outRD          := io.inRD
  io.outXcptInvalid := io.inXcptInvalid
}