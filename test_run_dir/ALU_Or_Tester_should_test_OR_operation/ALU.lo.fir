circuit ALU :
  module ALU :
    input clock : Clock
    input reset : UInt<1>
    input io_operandA : UInt<32>
    input io_operandB : UInt<32>
    input io_operation : UInt<4>
    output io_aluResult : UInt<32>

    node _T = eq(io_operation, UInt<1>("h0")) @[ALU.scala 30:21]
    node _io_aluResult_T = add(io_operandA, io_operandB) @[ALU.scala 31:33]
    node _io_aluResult_T_1 = tail(_io_aluResult_T, 1) @[ALU.scala 31:33]
    node _T_1 = eq(io_operation, UInt<1>("h1")) @[ALU.scala 32:27]
    node _io_aluResult_T_2 = sub(io_operandA, io_operandB) @[ALU.scala 33:31]
    node _io_aluResult_T_3 = tail(_io_aluResult_T_2, 1) @[ALU.scala 33:31]
    node _T_2 = eq(io_operation, UInt<2>("h2")) @[ALU.scala 34:27]
    node _io_aluResult_T_4 = and(io_operandA, io_operandB) @[ALU.scala 35:31]
    node _T_3 = eq(io_operation, UInt<2>("h3")) @[ALU.scala 36:27]
    node _io_aluResult_T_5 = or(io_operandA, io_operandB) @[ALU.scala 37:33]
    node _T_4 = eq(io_operation, UInt<3>("h4")) @[ALU.scala 38:27]
    node _io_aluResult_T_6 = xor(io_operandA, io_operandB) @[ALU.scala 39:31]
    node _T_5 = eq(io_operation, UInt<3>("h5")) @[ALU.scala 40:27]
    node _io_aluResult_T_7 = bits(io_operandB, 4, 0) @[ALU.scala 41:45]
    node _io_aluResult_T_8 = dshl(io_operandA, _io_aluResult_T_7) @[ALU.scala 41:31]
    node _T_6 = eq(io_operation, UInt<3>("h6")) @[ALU.scala 42:27]
    node _io_aluResult_T_9 = bits(io_operandB, 4, 0) @[ALU.scala 43:45]
    node _io_aluResult_T_10 = dshr(io_operandA, _io_aluResult_T_9) @[ALU.scala 43:31]
    node _T_7 = eq(io_operation, UInt<3>("h7")) @[ALU.scala 44:27]
    node _io_aluResult_T_11 = asSInt(io_operandA) @[ALU.scala 45:32]
    node _io_aluResult_T_12 = bits(io_operandB, 4, 0) @[ALU.scala 45:53]
    node _io_aluResult_T_13 = dshr(_io_aluResult_T_11, _io_aluResult_T_12) @[ALU.scala 45:39]
    node _io_aluResult_T_14 = asUInt(_io_aluResult_T_13) @[ALU.scala 45:60]
    node _T_8 = eq(io_operation, UInt<4>("h8")) @[ALU.scala 46:27]
    node _io_aluResult_T_15 = asSInt(io_operandA) @[ALU.scala 47:32]
    node _io_aluResult_T_16 = asSInt(io_operandB) @[ALU.scala 47:53]
    node _io_aluResult_T_17 = lt(_io_aluResult_T_15, _io_aluResult_T_16) @[ALU.scala 47:39]
    node _T_9 = eq(io_operation, UInt<4>("h9")) @[ALU.scala 48:27]
    node _io_aluResult_T_18 = lt(io_operandA, io_operandB) @[ALU.scala 49:32]
    node _T_10 = eq(io_operation, UInt<4>("ha")) @[ALU.scala 50:27]
    node _GEN_0 = mux(_T_10, io_operandB, UInt<1>("h0")) @[ALU.scala 28:16 50:44 51:16]
    node _GEN_1 = mux(_T_9, _io_aluResult_T_18, _GEN_0) @[ALU.scala 48:43 49:16]
    node _GEN_2 = mux(_T_8, _io_aluResult_T_17, _GEN_1) @[ALU.scala 46:42 47:16]
    node _GEN_3 = mux(_T_7, _io_aluResult_T_14, _GEN_2) @[ALU.scala 44:42 45:16]
    node _GEN_4 = mux(_T_6, _io_aluResult_T_10, _GEN_3) @[ALU.scala 42:42 43:16]
    node _GEN_5 = mux(_T_5, _io_aluResult_T_8, _GEN_4) @[ALU.scala 40:42 41:16]
    node _GEN_6 = mux(_T_4, _io_aluResult_T_6, _GEN_5) @[ALU.scala 38:42 39:16]
    node _GEN_7 = mux(_T_3, _io_aluResult_T_5, _GEN_6) @[ALU.scala 36:41 37:18]
    node _GEN_8 = mux(_T_2, _io_aluResult_T_4, _GEN_7) @[ALU.scala 34:42 35:16]
    node _GEN_9 = mux(_T_1, _io_aluResult_T_3, _GEN_8) @[ALU.scala 32:42 33:16]
    node _GEN_10 = mux(_T, _io_aluResult_T_1, _GEN_9) @[ALU.scala 30:36 31:18]
    io_aluResult <= bits(_GEN_10, 31, 0)
