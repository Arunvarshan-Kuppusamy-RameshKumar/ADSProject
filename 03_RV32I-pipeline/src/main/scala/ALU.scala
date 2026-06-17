// ToDo: Add your ALU implementation from Assignment02 here

package core_tile

import chisel3._
import chisel3.util._

class ALU extends Module{
    val io = IO(new Bundle{
        val in1 = Input(UInt(32.W))
        val in2 = Input(UInt(32.W))
        val alu_op = Input(uopc())
        val result = Output(UInt(32.W))
    })
    io.result := 0.U

    switch(io.alu_op) {
    is(uopc.ADD)  { io.result := io.in1 + io.in2 }
    is(uopc.ADDI) { io.result := io.in1 + io.in2 }

    is(uopc.SUB)  { io.result := io.in1 - io.in2 }

    is(uopc.SLL)  { io.result := io.in1 << io.in2(4, 0) }
    is(uopc.SLLI) { io.result := io.in1 << io.in2(4, 0) }

    is(uopc.SLT)  { io.result := (io.in1.asSInt < io.in2.asSInt).asUInt }
    is(uopc.SLTI) { io.result := (io.in1.asSInt < io.in2.asSInt).asUInt }

    is(uopc.SLTU)  { io.result := io.in1 < io.in2 }
    is(uopc.SLTIU) { io.result := io.in1 < io.in2 }

    is(uopc.XOR)  { io.result := io.in1 ^ io.in2 }
    is(uopc.XORI) { io.result := io.in1 ^ io.in2 }

    is(uopc.SRL)  { io.result := io.in1 >> io.in2(4, 0) }
    is(uopc.SRLI) { io.result := io.in1 >> io.in2(4, 0) }

    is(uopc.SRA)  { io.result := (io.in1.asSInt >> io.in2(4, 0)).asUInt }
    is(uopc.SRAI) { io.result := (io.in1.asSInt >> io.in2(4, 0)).asUInt }

    is(uopc.OR)   { io.result := io.in1 | io.in2 }
    is(uopc.ORI)  { io.result := io.in1 | io.in2 }

    is(uopc.AND)  { io.result := io.in1 & io.in2 }
    is(uopc.ANDI) { io.result := io.in1 & io.in2 }

    is(uopc.NOP)  { io.result := 0.U }
  }
}