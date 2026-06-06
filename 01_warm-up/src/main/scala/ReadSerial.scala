// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package readserial

import chisel3._
import chisel3.util._


/** controller class */
class Controller extends Module{
  
  val io = IO(new Bundle {

    val rxd      = Input(UInt(1.W))
    val bitDone  = Input(UInt(1.W))

    val shiftEn  = Output(UInt(1.W))
    val countEn  = Output(UInt(1.W))
    val countRst = Output(UInt(1.W))
    val valid    = Output(UInt(1.W))
    
    })

  val idle :: receive :: done :: Nil = Enum(3)
val state = RegInit(idle)

io.shiftEn  := 0.U
io.countEn  := 0.U
io.countRst := 0.U
io.valid    := 0.U

switch(state) {

  is(idle) {
    io.countRst := 1.U

    when(io.rxd === 0.U) {
      state := receive
    }
  }

  is(receive) {
    io.shiftEn := 1.U  //tell shif register to store the incoming bit
    io.countEn := 1.U  //tell counter to increase the count

    when(io.bitDone === 1.U) {        //if 8bits finished
      state := done
    }
  }

  is(done) {
    io.valid := 1.U
    state := idle
  }
}

  // state machine
  /* 
   * TODO: Describe functionality if the controller as a state machine
   */

}


/** counter class */
class Counter extends Module{
  
  val io = IO(new Bundle {
    val en    = Input(UInt(1.W))
    val rstC  = Input(UInt(1.W))

    val done  = Output(UInt(1.W))
    })

  // internal variables
  val cnt = RegInit(0.U(4.W))
  /* 
   * TODO: Define internal variables (registers and/or wires), if needed
   */
  when(io.rstC === 1.U) {
    cnt := 0.U
  } .elsewhen(io.en === 1.U) {
    cnt := cnt + 1.U
  }

  io.done := cnt === 7.U
  // state machine
  /* 
   * TODO: Describe functionality if the counter as a state machine
   */


}

/** shift register class */
class ShiftRegister extends Module{
  
  val io = IO(new Bundle {
    val en   = Input(UInt(1.W))
    val in   = Input(UInt(1.W))
    val data = Output(UInt(8.W))
    })

  // internal variables
  val reg = RegInit(0.U(8.W))
  when(io.en === 1.U) {
    reg := Cat(reg(6,0), io.in)  //shifted left and new entered in right side
  }

  io.data := reg

  /* 
   * TODO: Define internal variables (registers and/or wires), if needed
   */

  // functionality
  /* 
   * TODO: Describe functionality if the shift register
   */
}

/** 
  * The last warm-up task deals with a more complex component. Your goal is to design a serial receiver.
  * It scans an input line (“serial bus”) named rxd for serial transmissions of data bytes. A transmission 
  * begins with a start bit ‘0’ followed by 8 data bits. The most significant bit (MSB) is transmitted first. 
  * There is no parity bit and no stop bit. After the last data bit has been transferred a new transmission 
  * (beginning with a start bit, ‘0’) may immediately follow. If there is no new transmission the bus line 
  * goes high (‘1’, this is considered the “idle” bus signal). In this case the receiver waits until the next 
  * transmission begins. The outputs of the design are an 8-bit parallel data signal and a valid signal. 
  * The valid signal goes high (‘1’) for one clock cycle after the last serial bit has been transmitted, 
  * indicating that a new data byte is ready.
  */
class ReadSerial extends Module{
  
  val io = IO(new Bundle {
    val rxd   = Input(UInt(1.W))
    val data  = Output(UInt(8.W))
    val valid = Output(UInt(1.W))
    })


  val ctrl = Module(new Controller)
  val cnt  = Module(new Counter)
  val sr   = Module(new ShiftRegister)

  ctrl.io.rxd     := io.rxd
  ctrl.io.bitDone := cnt.io.done

  cnt.io.en   := ctrl.io.countEn
  cnt.io.rstC := ctrl.io.countRst

  sr.io.en := ctrl.io.shiftEn
  sr.io.in := io.rxd

  io.data  := sr.io.data
  io.valid := ctrl.io.valid
  // instanciation of modules
  /* 
   * TODO: Instanciate the modules that you need
   */

  // connections between modules
  /* 
   * TODO: connect the signals between the modules
   */

  // global I/O 
  /* 
   * TODO: Describe output behaviour based on the input values and the internal signals
   */

}
