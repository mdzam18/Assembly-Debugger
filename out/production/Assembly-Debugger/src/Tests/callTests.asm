Function main;
call <TEST_SUM1>;
call <Test_sum2>;
RET;
Function test_sum2;
SP = SP - 8;
M[SP] = 5;
m[sp+4] = 7;
call<sum>;
sp = sp + 8;
AEQ rv, 11;
ret;
Function test_sum1;
SP = SP - 8;
M[SP] = 5;
m[sp+4] = 7;
call<sum>;
sp = sp + 8;
AEQ rv, 12;
ALT rv, 10;
ret;


