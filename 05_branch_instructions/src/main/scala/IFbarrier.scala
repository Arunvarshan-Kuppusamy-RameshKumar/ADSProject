package core_tile

import chisel3._

class IFBarrier extends Module {
  val io = IO(new Bundle {
    val inInstr = Input(UInt(32.W))
    val inPC    = Input(UInt(32.W))
    val flush   = Input(Bool())

    val outInstr = Output(UInt(32.W))
    val outPC    = Output(UInt(32.W))
  })

  val instrReg = RegInit(0.U(32.W))  ///////change
  val pcReg    = RegInit(0.U(32.W))

  when(io.flush) {
    instrReg := 0.U
    pcReg    := 0.U
  }.otherwise {
    instrReg := io.inInstr
    pcReg    := io.inPC
  }

  io.outInstr := instrReg
  io.outPC    := pcReg
}