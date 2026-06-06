// ADS I Class Project
// Pipelined RISC-V Core with Hazard Detection and Resolution
//
// Chair of Electronic Design Automation, RPTU in Kaiserslautern
// File created on 10/31/2025 by Tobias Jauch (tobias.jauch@rptu.de)

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

import Assignment02._

// Test ADD operation
class ALUAddTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Add_Tester" should "test ADD operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operandA.poke(10.U)
      dut.io.operandB.poke(10.U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect(20.U)
      dut.clock.step(1)

      //ToDo: add more test cases for ADD operation
      // ADD: zero + zero
      dut.io.operandA.poke(0.U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect(0.U)
      dut.clock.step(1)

      // ADD: value + zero
      dut.io.operandA.poke(25.U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect(25.U)
      dut.clock.step(1)

      // ADD: overflow wraparound
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect(0.U)
      dut.clock.step(1)

      // ADD: max + max
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.ADD)
      dut.io.aluResult.expect("hFFFFFFFE".U)   //33 bits generated keep lower 32  
      dut.clock.step(1)

    }
  }
}


class ALUSubTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Sub_Tester" should "test SUB operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // SUB: normal case
      dut.io.operandA.poke(20.U)
      dut.io.operandB.poke(10.U)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.aluResult.expect(10.U)
      dut.clock.step(1)

      // SUB: result zero
      dut.io.operandA.poke(10.U)
      dut.io.operandB.poke(10.U)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.aluResult.expect(0.U)
      dut.clock.step(1)

      // SUB: underflow wraparound
      dut.io.operandA.poke(0.U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SUB)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)
    }
  }
}

class ALUAndTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_And_Tester" should "test AND operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // AND: all zeros
      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("h00000000".U)
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // AND: all ones
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)

      // AND: all ones with random value
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      // AND: complementary nibble pattern
      dut.io.operandA.poke("hF0F0F0F0".U)     //11110000
      dut.io.operandB.poke("h0F0F0F0F".U)    //00001111
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // AND: alternating bit pattern
      dut.io.operandA.poke("hAAAAAAAA".U)     //10101010
      dut.io.operandB.poke("h55555555".U)     //01010101
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // AND: random 32-bit case
      dut.io.operandA.poke("hABCDEF12".U)    //random
      dut.io.operandB.poke("h12345678".U)    //random
      dut.io.operation.poke(ALUOp.AND)
      dut.io.aluResult.expect("h02044610".U)
      dut.clock.step(1)
    }
  }
}

// Test OR operation
class ALUOrTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Or_Tester" should "test OR operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // OR: all zeros
      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("h00000000".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // OR: all ones
      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)

      // OR: zero with random value
      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      // OR: complementary pattern
      dut.io.operandA.poke("hF0F0F0F0".U)
      dut.io.operandB.poke("h0F0F0F0F".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)

      // OR: alternating bits
      dut.io.operandA.poke("hAAAAAAAA".U)
      dut.io.operandB.poke("h55555555".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)

      // OR: random 32-bit case
      dut.io.operandA.poke("hABCDEF12".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.operation.poke(ALUOp.OR)
      dut.io.aluResult.expect("hBBFDFF7A".U)
      dut.clock.step(1)
    }
  }
}

// Test XOR operation
class ALUXorTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Xor_Tester" should "test XOR operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("h00000000".U)
      dut.io.operation.poke(ALUOp.XOR)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.XOR)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("h00000000".U)
      dut.io.operation.poke(ALUOp.XOR)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)

      dut.io.operandA.poke("hAAAAAAAA".U)
      dut.io.operandB.poke("h55555555".U)
      dut.io.operation.poke(ALUOp.XOR)
      dut.io.aluResult.expect("hFFFFFFFF".U)
      dut.clock.step(1)
      
      dut.io.operandA.poke("hABCDEF12".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.operation.poke(ALUOp.XOR)
      dut.io.aluResult.expect("hB9F9B96A".U)
      dut.clock.step(1)
      
    }
  }
}

// Test SLL operation
class ALUSllTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Sll_Tester" should "test SLL operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // SLL: shift by 0
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      // SLL: shift by 1
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect("h00000002".U)
      dut.clock.step(1)

      // SLL: shift by 31
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect("h80000000".U)
      dut.clock.step(1)

      // SLL: overflow bits are discarded
      dut.io.operandA.poke("h80000000".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // SLL: only lower 5 bits of operandB are used
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke(33.U) // 33 = 100001, lower 5 bits = 1
      dut.io.operation.poke(ALUOp.SLL)
      dut.io.aluResult.expect("h00000002".U)
      dut.clock.step(1)
      
    }
  }
}

// Test SRL operation
class ALUSrlTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Srl_Tester" should "test SRL operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // SRL: shift by 0
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      // SRL: shift by 1
      dut.io.operandA.poke("h00000004".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect("h00000002".U)
      dut.clock.step(1)

      // SRL: MSB filled with 0
      dut.io.operandA.poke("h80000000".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect("h40000000".U)
      dut.clock.step(1)

      // SRL: shift by 31
      dut.io.operandA.poke("h80000000".U)
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect("h00000001".U)
      dut.clock.step(1)

      // SRL: only lower 5 bits of operandB are used
      dut.io.operandA.poke("h00000004".U)
      dut.io.operandB.poke(33.U) // lower 5 bits = 1
      dut.io.operation.poke(ALUOp.SRL)
      dut.io.aluResult.expect("h00000002".U)
      dut.clock.step(1)
    }
  }
}

// Test SRA operation
class ALUSraTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Sra_Tester" should "test SRA operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // shift by 0
      dut.io.operandA.poke("h12345678".U)
      dut.io.operandB.poke(0.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      // positive number
      dut.io.operandA.poke("h00000004".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect("h00000002".U)
      dut.clock.step(1)

      // negative number sign extension
      dut.io.operandA.poke("h80000000".U)
      dut.io.operandB.poke(1.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect("hC0000000".U)    //copy the msb sign bit
      dut.clock.step(1)

      // shift by 31
      dut.io.operandA.poke("h80000000".U)
      dut.io.operandB.poke(31.U)
      dut.io.operation.poke(ALUOp.SRA)
      dut.io.aluResult.expect("hFFFFFFFF".U) 
      dut.clock.step(1)
    }
  }
}

// Test SLT operation
class ALUSltTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Slt_Tester" should "test SLT operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // signed: 1 < 2 = true
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke("h00000002".U)
      dut.io.operation.poke(ALUOp.SLT)
      dut.io.aluResult.expect("h00000001".U)
      dut.clock.step(1)

      // signed: 2 < 1 = false
      dut.io.operandA.poke("h00000002".U)
      dut.io.operandB.poke("h00000001".U)
      dut.io.operation.poke(ALUOp.SLT)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // signed: -1 < 1 = true
      dut.io.operandA.poke("hFFFFFFFF".U)   //-1
      dut.io.operandB.poke("h00000001".U)
      dut.io.operation.poke(ALUOp.SLT)
      dut.io.aluResult.expect("h00000001".U)
      dut.clock.step(1)

      // signed: 1 < -1 = false
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.SLT)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)
    }
  }
}

// Test SLTU operation
class ALUSltuTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_Sltu_Tester" should "test SLTU operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      // 1 < 2 = true
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke("h00000002".U)
      dut.io.operation.poke(ALUOp.SLTU)
      dut.io.aluResult.expect("h00000001".U)
      dut.clock.step(1)

      // 2 < 1 = false
      dut.io.operandA.poke("h00000002".U)
      dut.io.operandB.poke("h00000001".U)
      dut.io.operation.poke(ALUOp.SLTU)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // unsigned comparison
      dut.io.operandA.poke("hFFFFFFFF".U)  //4294967295
      dut.io.operandB.poke("h00000001".U)
      dut.io.operation.poke(ALUOp.SLTU)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)

      // reverse unsigned comparison
      dut.io.operandA.poke("h00000001".U)
      dut.io.operandB.poke("hFFFFFFFF".U)
      dut.io.operation.poke(ALUOp.SLTU)
      dut.io.aluResult.expect("h00000001".U)
      dut.clock.step(1)
    }
  }
}

// Test PASSB operation
class ALUPassBTest extends AnyFlatSpec with ChiselScalatestTester {
  "ALU_PassB_Tester" should "test PASSB operation" in {
    test(new ALU).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
      dut.clock.setTimeout(0)

      dut.io.operandA.poke("h00000000".U)
      dut.io.operandB.poke("h12345678".U)
      dut.io.operation.poke(ALUOp.PASSB)
      dut.io.aluResult.expect("h12345678".U)
      dut.clock.step(1)

      dut.io.operandA.poke("hFFFFFFFF".U)
      dut.io.operandB.poke("hABCDEF12".U)
      dut.io.operation.poke(ALUOp.PASSB)
      dut.io.aluResult.expect("hABCDEF12".U)
      dut.clock.step(1)

      dut.io.operandA.poke("h11111111".U)
      dut.io.operandB.poke("h00000000".U)
      dut.io.operation.poke(ALUOp.PASSB)
      dut.io.aluResult.expect("h00000000".U)
      dut.clock.step(1)
    }
  }
}