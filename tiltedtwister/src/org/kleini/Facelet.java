/**
 * GPL v3
 */

package org.kleini;

/**
 * <pre>
 * The names of the facelet positions of the cube
 *             |************|
 *             |*U1**U2**U3*|
 *             |************|
 *             |*U4**U5**U6*|
 *             |************|
 *             |*U7**U8**U9*|
 *             |************|
 * ************|************|************|************|
 * *L1**L2**L3*|*F1**F2**F3*|*R1**R2**F3*|*B1**B2**B3*|
 * ************|************|************|************|
 * *L4**L5**L6*|*F4**F5**F6*|*R4**R5**R6*|*B4**B5**B6*|
 * ************|************|************|************|
 * *L7**L8**L9*|*F7**F8**F9*|*R7**R8**R9*|*B7**B8**B9*|
 * ************|************|************|************|
 *             |************|
 *             |*D1**D2**D3*|
 *             |************|
 *             |*D4**D5**D6*|
 *             |************|
 *             |*D7**D8**D9*|
 *             |************|
 * </pre>
 *
 * A cube definition string "WRB..." means for example: In position U1 we have the W-color, in position U2 we have the R-color, in position
 * U3 we have the B color etc. according to the order L1, L2, L3, L4, L5, L6, L7, L8, L9, F1, F2, F3, F4, F5, F6, F7, F8, F9, R1, R2, R3,
 * R4, R5, R6, R7, R8, R9, B1, B2, B3, B4, B5, B6, B7, B8, B9, U1, U2, U3, U4, U5, U6, U7, U8, U9, D1, D2, D3, D4, D5, D6, D7, D8, D9 of the
 * enum constants.
 * 
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public enum Facelet {
	L1, L2, L3, L4, L5, L6, L7, L8, L9,
	F1, F2, F3, F4, F5, F6, F7, F8, F9,
	R1, R2, R3, R4, R5, R6, R7, R8, R9,
	B1, B2, B3, B4, B5, B6, B7, B8, B9,
	U1, U2, U3, U4, U5, U6, U7, U8, U9,
	D1, D2, D3, D4, D5, D6, D7, D8, D9
}
