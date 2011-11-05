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

    /**
     * @param args
     */
    public static void main(String[] args) {
        Solver solver = new TiltedTwister();
        String solution = solver.solution("GGYGGYGGYRRRRRRRRRWBBWBBWBBOOOOOOOOOWWWWWWGGGBBBYYYYYY");
        System.out.println(solution);
    }

}
