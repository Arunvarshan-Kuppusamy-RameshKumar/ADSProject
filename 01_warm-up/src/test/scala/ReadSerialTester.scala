// ADS I Class Project
// Chisel Introduction
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 18/10/2022 by Tobias Jauch (@tojauch)

package readserial

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec


/** 
  *read serial tester
  */
class ReadSerialTester extends AnyFlatSpec with ChiselScalatestTester {

  "ReadSerial" should "work" in {
    test(new ReadSerial).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
        // bus idle
        dut.io.rxd.poke(1.U)
        dut.clock.step(1)
        dut.io.valid.expect(0.U)

        // start bit
        dut.io.rxd.poke(0.U)
        dut.clock.step(1)

        // send data bits: 10110011, MSB first
        val bits = Seq(1, 0, 1, 1, 0, 0, 1, 1)

        for(bit <- bits) {
          dut.io.rxd.poke(bit.U)
          dut.clock.step(1)
        }

        // after 8 bits, valid should become high
        dut.io.valid.expect(1.U)
        dut.io.data.expect("b10110011".U)

        // one more cycle: valid should go low again
        dut.clock.step(1)
        dut.io.valid.expect(0.U)
        
        
        }
    } 
}

