/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "vector_calc.h"

bool_t
xdr_VecPair (XDR *xdrs, VecPair *objp)
{
	register int32_t *buf;

	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	 if (!xdr_array (xdrs, (char **)&objp->X.X_val, (u_int *) &objp->X.X_len, ~0,
		sizeof (int), (xdrproc_t) xdr_int))
		 return FALSE;
	 if (!xdr_array (xdrs, (char **)&objp->Y.Y_val, (u_int *) &objp->Y.Y_len, ~0,
		sizeof (int), (xdrproc_t) xdr_int))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_RealVec2 (XDR *xdrs, RealVec2 *objp)
{
	register int32_t *buf;

	int i;
	 if (!xdr_vector (xdrs, (char *)objp->real, 2,
		sizeof (double), (xdrproc_t) xdr_double))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_RealVec (XDR *xdrs, RealVec *objp)
{
	register int32_t *buf;

	 if (!xdr_int (xdrs, &objp->n))
		 return FALSE;
	 if (!xdr_array (xdrs, (char **)&objp->real.real_val, (u_int *) &objp->real.real_len, ~0,
		sizeof (double), (xdrproc_t) xdr_double))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_InputData (XDR *xdrs, InputData *objp)
{
	register int32_t *buf;

	 if (!xdr_VecPair (xdrs, &objp->VP))
		 return FALSE;
	 if (!xdr_double (xdrs, &objp->r))
		 return FALSE;
	return TRUE;
}
