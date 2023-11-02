struct VecPair
{
	int n;
	int X<>;
	int Y<>;
};
struct RealVec2
{
	double real[2];
};
struct RealVec
{
	int n;
	double real<>;
};

struct InputData
{
	VecPair VP;
	double r;
};


program VECTOR_CALC_PROG 
{
		version VECTOR_CALC_VERS 
        {
			int DOT_PRODUCT(VecPair) = 1;
			RealVec2 AVERAGE(VecPair) = 2;
			RealVec MULTIPLY(InputData) = 3;
	    } = 1;
} = 0x23451111;