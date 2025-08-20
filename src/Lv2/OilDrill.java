package Lv2;

import java.util.*;

public class OilDrill {

    static int[][] land;
    static boolean[][] visited;
    static int rows, cols;

    public static void main(String[] args) {
         // 지면(land)을 입력받은 후, 각 좌표를 돌며 BFS를 수행함
         // land[y][x]가 1이고 미방문이면, 하나의 석유 덩어리로 간주하고 BFS 탐색
         // BFS 도중 석유 덩어리에 포함된 모든 좌표를 방문 처리
         // 석유량(count)을 누적, 해당 덩어리가 포함된 열 번호들을 Set에 저장
         // BFS 종료 후, 그 덩어리에 포함된 열마다 석유량 누적
         // 모든 덩어리 처리 후, 열마다 계산된 시추량 중 최댓값을 반환

        land = new int[][]{
                {0, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0},
                {1, 1, 0, 0, 0, 1, 1, 0},
                {1, 1, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 0, 1, 1}
        };

        System.out.println("최대 시추량: " + solution(land));
    }

    public static int solution(int[][] inputLand) {
        land = inputLand; // 얕은 복사 이용
        rows = land.length;
        cols = land[0].length;
        visited = new boolean[rows][cols];

        // 열을 시추 했을 때 얻을 수 있는 총 석유량 열별 저장
        int[] colSum = new int[cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // 방문하지 않은 석유 발견시, 새로운 덩어리로 간주
                if (land[r][c] == 1 && !visited[r][c]) {
                    int oilCount = 0; // 해당 덩어리의 석유량
                    // 해당 열을 저장하는 해시 셋
                    Set<Integer> colsInGroup = new HashSet<>();

                    // BFS 탐색
                    Queue<int[]> q = new LinkedList<>();
                    q.offer(new int[]{r, c});
                    visited[r][c] = true;

                    while (!q.isEmpty()) {
                        int[] now = q.poll();
                        int y = now[0];
                        int x = now[1];

                        // 탐색 할 때마다, +1
                        oilCount++;
                        // 그 석유 덩어리가 포함된 열 번호 add
                        colsInGroup.add(x);

                        // 4방향 탐색
                        int[] dy = {-1, 1, 0, 0};
                        int[] dx = {0, 0, -1, 1};
                        for (int d = 0; d < 4; d++) {
                            int ny = y + dy[d];
                            int nx = x + dx[d];
                            // 범위 확인, 석유 존재 여부 확인
                            if (ny >= 0 && ny < rows && nx >= 0 && nx < cols &&
                                    land[ny][nx] == 1 && !visited[ny][nx]) {
                                visited[ny][nx] = true;
                                // 다음 탐색 좌표 큐에 추가
                                q.offer(new int[]{ny, nx});
                            }
                        }
                    }

                    // 이 덩어리가 걸친 모든 열에 시추량 더함
                    for (int col : colsInGroup) {
                        colSum[col] += oilCount;
                    }
                }
            }
        }

        // 열 중 최댓값 찾기
        int max = 0;
        for (int oil : colSum) {
            max = Math.max(max, oil);
        }

        return max;
    }
}
