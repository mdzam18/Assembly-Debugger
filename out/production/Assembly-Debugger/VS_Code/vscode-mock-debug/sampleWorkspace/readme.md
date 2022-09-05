Function main;
SP = SP - 8;
M[SP] = 3;
R1 = SP + 4;
M[R1] = 2;
R2 = M[R1];
R3 = .2M[SP];
SP = SP - 8;
M[SP] = R2; M[SP+4] = .2R3; SP = SP + 8;
SP = SP + 8;
RET;

