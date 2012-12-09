/**
 * GPL v3
 */

package org.kleini;

import lejos.nxt.Button;
import lejos.nxt.LCD;

/**
 *
 *
 * @author <a href="mailto:himself@kleini.org">Marcus Klein</a>
 */
public class Test {

    // middle faces                      "    |        |        |        |        |        |    "
    private static final String CUBE_1 = "GGYGGYGGYRRRRRRRRRWBBWBBWBBOOOOOOOOOWWWWWWGGGBBBYYYYYY"; // FFF
    private static final String CUBE_6 = "BBOBBOBBOWWWWWWWWWRGGRGGRGGYYYYYYYYYRRRRRRBBBGGGOOOOOO"; // FFF
    private static final String CUBE_2 = "GGBGGBGGBRRRRRRRRRGBBGBBGBBOOOOOOOOOWWWWWWYYYWWWYYYYYY"; // FF
    private static final String CUBE_3 = "GGWGGWGGWRRGRRYRRYYYYBBBBBBBOOWOOWOOWWRWWRBBRGGOYYOYYO"; // RRRF
    private static final String CUBE_4 = "OOOGGGRRRGGGRRRBBBRRRBBBOOOBBBOOOGGGWWWWWWWWWYYYYYYYYY"; // UD
    private static final String CUBE_5 = "YGWYGWYGWRRRRRRRRRYBWYBWYBWOOOOOOOOOGGGWWWBBBGGGYYYBBB"; // FB
    private static final String CUBE_X = "ORGOBBRGRWGWYWYYBGBRWRGRWOOROYBYBGOYBWBWRGOYOGWRWOGBYY"; // FBBBLLLBUUURRBFFFULLLUFFUUULUFFUULUUULLRRDUUULLRRDFFFLLRRBRLDRRRLBBBLLLDDDRRRLBBRRLLFFFLLLURLLLBBBRRRLUUURLLLBUULLLRBBLLLRR
    private static final String CUBE_Y = "WRBOGRORWOYOGRWBWBGBRRBGOORGWRWOGBYYGBYYWYWGWROYBYBGOY"; // UDDDRRRDFFFLLDUUUFRRRFUUFFFRFUUFFRFFFRRLLBFFFRRLLBUUURRLLDLRBLLLRDDDRRRBBBLLLRDDLLRRUUURRRFLRRRDDDLLLRFFFLRRRDFFRRRLDDRRRLL

    public static void main(String[] args) {
        Solver solver = new CornersFirst();
        long start = System.currentTimeMillis();
        final String solution = solver.solution(CUBE_X);
        long end = System.currentTimeMillis();
        Button.waitForAnyPress();
        LCD.clearDisplay();
        LCD.drawInt((int)(end - start), 0, 0);
        for (int i = 0; i < solution.length() / 16; i++) {
            int endPos = Math.max((i+1)*16, solution.length());
            LCD.drawString(solution.substring(i*16, endPos), 0, i + 1);
        }
        Button.waitForAnyPress();
    }
}
