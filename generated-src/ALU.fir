circuit ALU :
  module ALU :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip operandA : UInt<32>, flip operandB : UInt<32>, flip operation : UInt<4>, aluResult : UInt<32>}

    io.aluResult <= UInt<1>("h0") @[ALU.scala 28:16]
    node _T = eq(io.operation, UInt<1>("h0")) @[ALU.scala 30:21]
    when _T : @[ALU.scala 30:36]
      node _io_aluResult_T = add(io.operandA, io.operandB) @[ALU.scala 31:33]
      node _io_aluResult_T_1 = tail(_io_aluResult_T, 1) @[ALU.scala 31:33]
      io.aluResult <= _io_aluResult_T_1 @[ALU.scala 31:18]
    else :
      node _T_1 = eq(io.operation, UInt<1>("h1")) @[ALU.scala 32:27]
      when _T_1 : @[ALU.scala 32:42]
        node _io_aluResult_T_2 = sub(io.operandA, io.operandB) @[ALU.scala 33:31]
        node _io_aluResult_T_3 = tail(_io_aluResult_T_2, 1) @[ALU.scala 33:31]
        io.aluResult <= _io_aluResult_T_3 @[ALU.scala 33:16]
      else :
        node _T_2 = eq(io.operation, UInt<2>("h2")) @[ALU.scala 34:27]
        when _T_2 : @[ALU.scala 34:42]
          node _io_aluResult_T_4 = and(io.operandA, io.operandB) @[ALU.scala 35:31]
          io.aluResult <= _io_aluResult_T_4 @[ALU.scala 35:16]
        else :
          node _T_3 = eq(io.operation, UInt<2>("h3")) @[ALU.scala 36:27]
          when _T_3 : @[ALU.scala 36:41]
            node _io_aluResult_T_5 = or(io.operandA, io.operandB) @[ALU.scala 37:33]
            io.aluResult <= _io_aluResult_T_5 @[ALU.scala 37:18]
          else :
            node _T_4 = eq(io.operation, UInt<3>("h4")) @[ALU.scala 38:27]
            when _T_4 : @[ALU.scala 38:42]
              node _io_aluResult_T_6 = xor(io.operandA, io.operandB) @[ALU.scala 39:31]
              io.aluResult <= _io_aluResult_T_6 @[ALU.scala 39:16]
            else :
              node _T_5 = eq(io.operation, UInt<3>("h5")) @[ALU.scala 40:27]
              when _T_5 : @[ALU.scala 40:42]
                node _io_aluResult_T_7 = bits(io.operandB, 4, 0) @[ALU.scala 41:45]
                node _io_aluResult_T_8 = dshl(io.operandA, _io_aluResult_T_7) @[ALU.scala 41:31]
                io.aluResult <= _io_aluResult_T_8 @[ALU.scala 41:16]
              else :
                node _T_6 = eq(io.operation, UInt<3>("h6")) @[ALU.scala 42:27]
                when _T_6 : @[ALU.scala 42:42]
                  node _io_aluResult_T_9 = bits(io.operandB, 4, 0) @[ALU.scala 43:45]
                  node _io_aluResult_T_10 = dshr(io.operandA, _io_aluResult_T_9) @[ALU.scala 43:31]
                  io.aluResult <= _io_aluResult_T_10 @[ALU.scala 43:16]
                else :
                  node _T_7 = eq(io.operation, UInt<3>("h7")) @[ALU.scala 44:27]
                  when _T_7 : @[ALU.scala 44:42]
                    node _io_aluResult_T_11 = asSInt(io.operandA) @[ALU.scala 45:32]
                    node _io_aluResult_T_12 = bits(io.operandB, 4, 0) @[ALU.scala 45:53]
                    node _io_aluResult_T_13 = dshr(_io_aluResult_T_11, _io_aluResult_T_12) @[ALU.scala 45:39]
                    node _io_aluResult_T_14 = asUInt(_io_aluResult_T_13) @[ALU.scala 45:60]
                    io.aluResult <= _io_aluResult_T_14 @[ALU.scala 45:16]
                  else :
                    node _T_8 = eq(io.operation, UInt<4>("h8")) @[ALU.scala 46:27]
                    when _T_8 : @[ALU.scala 46:42]
                      node _io_aluResult_T_15 = asSInt(io.operandA) @[ALU.scala 47:32]
                      node _io_aluResult_T_16 = asSInt(io.operandB) @[ALU.scala 47:53]
                      node _io_aluResult_T_17 = lt(_io_aluResult_T_15, _io_aluResult_T_16) @[ALU.scala 47:39]
                      io.aluResult <= _io_aluResult_T_17 @[ALU.scala 47:16]
                    else :
                      node _T_9 = eq(io.operation, UInt<4>("h9")) @[ALU.scala 48:27]
                      when _T_9 : @[ALU.scala 48:43]
                        node _io_aluResult_T_18 = lt(io.operandA, io.operandB) @[ALU.scala 49:32]
                        io.aluResult <= _io_aluResult_T_18 @[ALU.scala 49:16]
                      else :
                        node _T_10 = eq(io.operation, UInt<4>("ha")) @[ALU.scala 50:27]
                        when _T_10 : @[ALU.scala 50:44]
                          io.aluResult <= io.operandB @[ALU.scala 51:16]

