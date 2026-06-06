module ALU(
  input         clock,
  input         reset,
  input  [31:0] io_operandA,
  input  [31:0] io_operandB,
  input  [3:0]  io_operation,
  output [31:0] io_aluResult
);
  wire [31:0] _io_aluResult_T_1 = io_operandA + io_operandB; // @[ALU.scala 31:33]
  wire [31:0] _io_aluResult_T_3 = io_operandA - io_operandB; // @[ALU.scala 33:31]
  wire [31:0] _io_aluResult_T_4 = io_operandA & io_operandB; // @[ALU.scala 35:31]
  wire [31:0] _io_aluResult_T_5 = io_operandA | io_operandB; // @[ALU.scala 37:33]
  wire [31:0] _io_aluResult_T_6 = io_operandA ^ io_operandB; // @[ALU.scala 39:31]
  wire [62:0] _GEN_11 = {{31'd0}, io_operandA}; // @[ALU.scala 41:31]
  wire [62:0] _io_aluResult_T_8 = _GEN_11 << io_operandB[4:0]; // @[ALU.scala 41:31]
  wire [31:0] _io_aluResult_T_10 = io_operandA >> io_operandB[4:0]; // @[ALU.scala 43:31]
  wire [31:0] _io_aluResult_T_11 = io_operandA; // @[ALU.scala 45:32]
  wire [31:0] _io_aluResult_T_14 = $signed(io_operandA) >>> io_operandB[4:0]; // @[ALU.scala 45:60]
  wire [31:0] _io_aluResult_T_16 = io_operandB; // @[ALU.scala 47:53]
  wire [31:0] _GEN_0 = io_operation == 4'ha ? io_operandB : 32'h0; // @[ALU.scala 28:16 50:44 51:16]
  wire [31:0] _GEN_1 = io_operation == 4'h9 ? {{31'd0}, io_operandA < io_operandB} : _GEN_0; // @[ALU.scala 48:43 49:16]
  wire [31:0] _GEN_2 = io_operation == 4'h8 ? {{31'd0}, $signed(_io_aluResult_T_11) < $signed(_io_aluResult_T_16)} :
    _GEN_1; // @[ALU.scala 46:42 47:16]
  wire [31:0] _GEN_3 = io_operation == 4'h7 ? _io_aluResult_T_14 : _GEN_2; // @[ALU.scala 44:42 45:16]
  wire [31:0] _GEN_4 = io_operation == 4'h6 ? _io_aluResult_T_10 : _GEN_3; // @[ALU.scala 42:42 43:16]
  wire [62:0] _GEN_5 = io_operation == 4'h5 ? _io_aluResult_T_8 : {{31'd0}, _GEN_4}; // @[ALU.scala 40:42 41:16]
  wire [62:0] _GEN_6 = io_operation == 4'h4 ? {{31'd0}, _io_aluResult_T_6} : _GEN_5; // @[ALU.scala 38:42 39:16]
  wire [62:0] _GEN_7 = io_operation == 4'h3 ? {{31'd0}, _io_aluResult_T_5} : _GEN_6; // @[ALU.scala 36:41 37:18]
  wire [62:0] _GEN_8 = io_operation == 4'h2 ? {{31'd0}, _io_aluResult_T_4} : _GEN_7; // @[ALU.scala 34:42 35:16]
  wire [62:0] _GEN_9 = io_operation == 4'h1 ? {{31'd0}, _io_aluResult_T_3} : _GEN_8; // @[ALU.scala 32:42 33:16]
  wire [62:0] _GEN_10 = io_operation == 4'h0 ? {{31'd0}, _io_aluResult_T_1} : _GEN_9; // @[ALU.scala 30:36 31:18]
  assign io_aluResult = _GEN_10[31:0];
endmodule
