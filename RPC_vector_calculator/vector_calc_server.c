/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "vector_calc.h"

int *
dot_product_1_svc(VecPair *argp, struct svc_req *rqstp)
{
	static int  result;
	int sum=0;
	for (int i=0;i<argp->n;i++)
		sum += argp->X.X_val[i] * argp->Y.Y_val[i];
	result = sum;

	return &result;
}
double my_average(int vec[],int n)
{
	int sum=0;
	for (int i=0;i<n;i++)
		sum += vec[i];
	return (double)sum/n;
}
RealVec2 *
average_1_svc(VecPair *argp, struct svc_req *rqstp)
{
	static RealVec2  result;
	result.real[0] = my_average(argp->X.X_val,argp->n);
	result.real[1] = my_average(argp->Y.Y_val,argp->n);
	return &result;
}

RealVec *
multiply_1_svc(InputData *argp, struct svc_req *rqstp)
{
	static RealVec  result;
	result.n = result.real.real_len=argp->VP.n;
	result.real.real_val = (double*)malloc(sizeof(double)*result.real.real_len);
	for(int i=0;i < argp->VP.n;i++)
		result.real.real_val[i] = argp->r*(argp->VP.X.X_val[i] + argp->VP.Y.Y_val[i]);
	return &result;
}
