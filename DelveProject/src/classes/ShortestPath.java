package classes;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ShortestPath
{
    public static void main(String[] args) {
        char[][] matrix =  {{'1','1','1', '1'},
                            {'1','S','1', '1'},
                            {'1','1','X', '1'},
                            {'1','1','X', 'E'}};

        System.out.println(shortestPath(matrix,1,1));
    }
    public static int shortestPath(char[][] matrix, int s_row, int s_col) {
        int count = 0;
        Queue<int[]> nextToVisit = new LinkedList<>();
        nextToVisit.offer(new int[] {s_row, s_col});
        boolean[][] visited = new boolean[matrix.length][matrix[0].length];
        visited[s_row][s_col] = true;
        Queue<int[]> temp = new LinkedList<>();

        while (!nextToVisit.isEmpty()) {
            int[] position = nextToVisit.poll();
            int row = position[0];
            int col = position[1];

            if (matrix[row][col] == 'E')
                return count;
            if (row > 0 && !visited[row - 1][col] && matrix[row - 1][col] != 'X') {
                temp.offer(new int[]{row - 1, col});
                visited[row - 1][col] = true;
            }
            if (row < matrix.length - 1 && !visited[row + 1][col] && matrix[row + 1][col] != 'X') {
                temp.offer(new int[]{row + 1, col});
                visited[row + 1][col] = true;
            }
            if (col > 0 && !visited[row][col - 1] && matrix[row][col - 1] != 'X') {
                temp.offer(new int[] {row, col - 1});
                visited[row][col - 1] = true;
            }
            if (col < matrix[0].length - 1 && !visited[row][col + 1] && matrix[row][col + 1] != 'X') {
                temp.offer(new int[]{row, col + 1});
                visited[row][col + 1] = true;
            }
            if (nextToVisit.isEmpty() && !temp.isEmpty()) {
                nextToVisit = temp;
                temp = new LinkedList<>();
                count++;
            }
        }
        if (visited[3][3])
            return count;
        else
            return -1;
    }
}
