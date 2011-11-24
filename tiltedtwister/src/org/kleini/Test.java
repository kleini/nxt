/**
 * GPL v3
 */

package org.kleini;

/**
 *
 *
 * @author <a href="mailto:m@kleini.org">Marcus Klein</a>
 */
public class Test {

    private static final String CUBE_1 = "GGYGGYGGYRRRRRRRRRWBBWBBWBBOOOOOOOOOWWWWWWGGGBBBYYYYYY"; // FFF
    private static final String CUBE_2 = "GGBGGBGGBRRRRRRRRRGBBGBBGBBOOOOOOOOOWWWWWWYYYWWWYYYYYY"; // FF
    private static final String CUBE_3 = "GGWGGWGGWRRGRRYRRYYYYBBBBBBBOOWOOWOOWWRWWRBBRGGOYYOYYO"; // RRRF
    private static final String CUBE_4 = "OOOGGGRRRGGGRRRBBBRRRBBBOOOBBBOOOGGGWWWWWWWWWYYYYYYYYY";
    private static final String CUBE_5 = "YGWYGWYGWRRRRRRRRRYBWYBWYBWOOOOOOOOOGGGWWWBBBGGGYYYBBB"; // FB
    private static final String CUBE_X = "ORGOBBRGRWGWYWYYBGBRWRGRWOOROYBYBGOYBWBWRGOYOGWRWOGBYY";
    private static final String CUBE_Y = "WRBOGRORWOYOGRWBWBGBRRBGOORGWRWOGBYYGBYYWYWGWROYBYBGOY"; // UDDDRRRDFFFLLDUUUFRRRFUUFFFRFUUFFRFFFRRLLBFFFRRLLBUUURRLLDLRBLLLRDDDRRRBBBLLLRDDLLRRUUURRRFLRRRDDDLLLRFFFLRRRDFFRRRLDDRRRLL

    /**
     * @param args
     */
    public static void main(String[] args) {
        Solver solver = new TiltedTwister();
        String solution = solver.solution(CUBE_X);
        System.out.println(solution);
    }

}
